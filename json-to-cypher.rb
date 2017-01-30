require 'json'
require 'net/http'
require 'enumerator'
require 'awesome_print'



def create_type_reference(type, preffix='tr') 
  package = type['package']
  name = type['name']
  "MERGE (#{preffix}:TypeReference { qualifiedName: '#{package}.#{name}'}) ON CREATE SET #{preffix}.name = '#{name}', #{preffix}.package = '#{package}'"
end

def type_reference(type)
  "(t:TypeReference { qualifiedName: #{type_qn(artifact, type)} })"
end

def type(artifact, type)
end

def type_qn(artifact, type)
  package = type['package']
  name = type['name']
  usage = type['usage']
  version = artifact['version']
  artifact_name = artifact['name']

  "#{artifact_name}:#{version}:#{package}.#{name}"
end

def create_type(artifact, type, preffix='t')
  package = type['package']
  name = type['name']
  usage = type['usage']
  version = artifact['version']
  type_type = type['type']
  artifact_name = artifact['name']
  "CREATE (#{preffix}:Type { 
    qualifiedName: '#{type_qn(artifact, type)}', 
    type: '#{type_type}',
    usage: '#{usage}',
    name: '#{name}', 
    package: '#{package}', 
    version: '#{version}' 
  })"
end

def create_field(artifact, type, field, preffix='f')
  name = field['name']
  "CREATE (#{preffix}:Field {
    qualifiedName: '#{type_qn(artifact, type)}.#{name}', 
    name: '#{field['name']}'
  })" 
  ""
end

def qn(type, artifact=nil)
  package = type['package']
  name = type['name']
  usage = type['usage']
  type_type = type['type']
  return name unless artifact or package
  return "#{package}.#{name}" unless artifact

  version = artifact['version']
  artifact_name = artifact['name']
  "#{artifact_name}:#{version}:#{package}.#{name}"
end

def tr(type)
  type_reference = { 
    name: type['name'], 
    qualifiedName: qn(type) 
  }
  type_reference[:package] = type['package'] if type['package']
  type_reference
end

def send_and_commit(operations)
  uri = URI.parse('http://neo4j.codegraph-dev.dnfeitosa.com:7476/db/data/transaction/commit')
  req = Net::HTTP::Post.new(uri.request_uri, 'Content-Type' => 'application/json', 'X-Stream' => 'true')
  req.body = JSON.pretty_generate(operations)
  #puts req.body

  res = Net::HTTP.start(uri.hostname, uri.port, read_timeout: 3600) do |http|
    http.request(req)
  end

  puts res.body

=begin
  uri = URI.parse(JSON.parse(res.body)['commit'])
  req = Net::HTTP::Post.new(uri.request_uri, 'Content-Type' => 'application/json')
  res = Net::HTTP.start(uri.hostname, uri.port) do |http|
    http.request(req)
  end
  puts res.body
=end
end

def process_file(file)
  nodes = { types: [], fields: [], methods: [], type_references: [] }
  relationships = { types_to_fields: [], types_to_methods: [], type_references: [], method_returns: [], parameters: [], field_types: [], extends: [], implements: [] }

  artifact = JSON.parse(file)
  (artifact['types'] || []).collect do |type|
    package = type['package']
    name = type['name']
    usage = type['usage']
    version = artifact['version']
    type_type = type['type']
    artifact_name = artifact['name']
    nodes[:types] << { 
      package: package, 
      name: name, 
      usage: usage, 
      version: version, 
      type: type_type, 
      qualifiedName: qn(type, artifact)
    }
    type_qn = qn(type, artifact)

    (type['fields'] || []).each do |field|
      qn = "#{qn(type, artifact)}.#{field['name']}"
      nodes[:fields] << { name: field['name'], qualifiedName: qn }
      relationships[:types_to_fields] << { type: type_qn, field: qn }
      relationships[:field_types] << { field: qn, type: qn(field['type']) }

      field_type = field['type']
      nodes[:type_references] << tr(field_type)
    end

    if type['superclass'] 
      superclass = type['superclass']
      relationships[:extends] << { type: type_qn, extends: qn(superclass) }
      nodes[:type_references] << tr(superclass)
    end

    (type['interfaces'] || []).each do |interface|
      relationships[:implements] << { type: type_qn, implements: qn(interface) }
      nodes[:type_references] << tr(interface)
    end

    (type['methods'] || []).each do |method|
      qn = "#{qn(type, artifact)}.#{method['name']}.#{(method['parameters'] || []).map { |p| "#{p['order']}#{p['type']['name']}" }.join('_') }"
      nodes[:methods] << { name: method['name'], qualifiedName: qn }

      (method['returnTypes'] || []).each do |rt|
        relationships[:method_returns] << { method: qn, type: qn(rt) }
        relationships[:types_to_methods] << { method: qn, type: type_qn }
        nodes[:type_references] << tr(rt)
      end

      (method['parameters'] || []).each do |param|
        relationships[:parameters] << { method: qn, order: param['order'], type: qn(param['type']) }
        nodes[:type_references] << tr(param['type'])
      end
    end
  end

  #ap nodes[:type_references].uniq
  #ap relationships[:extends]
  #ap relationships[:implements]


  operations = { statements: [ ] }

  operations[:statements] << {
    statement: "CREATE (t:Type {props})",
    parameters: {
      props: nodes[:types].uniq.select { |type| not type[:name].nil? }
    }
  } unless nodes[:types].uniq.empty?

  operations[:statements] << {
    statement: "
    UNWIND {props} AS p 
    MERGE (tr:TypeReference { qualifiedName: p.qualifiedName }) 
    ON CREATE SET tr.qualifiedName = p.qualifiedName, tr.name = p.name, tr.package = p.package",
    parameters: {
      props: nodes[:type_references].uniq
    }
  } unless nodes[:type_references].uniq.empty?

  puts nodes[:type_references].uniq.size

  operations[:statements] << {
    statement: "CREATE (f:Field {props})",
    parameters: {
      props: nodes[:fields].uniq
    }
  } unless nodes[:fields].uniq.empty?

  operations[:statements] << {
    statement: "
    UNWIND {relations} AS relation
    MATCH (t:Type { qualifiedName: relation.type })
    MATCH (f:Field { qualifiedName: relation.field }) 
    MERGE (t)-[:DECLARES]->(f)",
    parameters: {
      relations: relationships[:types_to_fields].uniq
    }
  } unless relationships[:types_to_fields].uniq.empty?

  operations[:statements] << {
    statement: "
    UNWIND {relations} AS relation
    MATCH (t:TypeReference { qualifiedName: relation.type })
    MATCH (f:Field { qualifiedName: relation.field }) 
    MERGE (f)-[:OF_TYPE]->(t)",
    parameters: {
      relations: relationships[:field_types].uniq
    }
  } unless relationships[:field_types].uniq.empty?

  operations[:statements] << {
    statement: "CREATE (m:Method {props})",
    parameters: {
      props: nodes[:methods].uniq
    }
  } unless nodes[:methods].uniq.empty?

  operations[:statements] << {
    statement: "
    UNWIND {relations} AS relation
    MATCH (t:Type { qualifiedName: relation.type })
    MATCH (m:Method { qualifiedName: relation.method }) 
    MERGE (t)-[:DECLARES]->(m)",
    parameters: {
      relations: relationships[:types_to_methods].uniq
    }
  } unless relationships[:types_to_methods].uniq.empty?

  operations[:statements] << {
    statement: "
    UNWIND {relations} AS relation
    MATCH (m:Method { qualifiedName: relation.method }) 
    MATCH (pt:TypeReference { qualifiedName: relation.type })
    CREATE (m)-[:TAKES]->(pt)",
    parameters: {
      relations: relationships[:parameters].uniq
    }
  } unless relationships[:parameters].uniq.empty?

  operations[:statements] << {
    statement: "
    UNWIND {relations} AS relation
    MATCH (m:Type { qualifiedName: relation.type }) 
    MATCH (i:TypeReference { qualifiedName: relation.implements })
    CREATE (m)-[:IMPLEMENTS]->(i)",
    parameters: {
      relations: relationships[:implements].uniq
    }
  } unless relationships[:implements].uniq.empty?

  operations[:statements] << {
    statement: "
    UNWIND {relations} AS relation
    MATCH (m:Type { qualifiedName: relation.type }) 
    MATCH (s:TypeReference { qualifiedName: relation.extends })
    CREATE (m)-[:EXTENDS]->(s)",
    parameters: {
      relations: relationships[:extends].uniq
    }
  } unless relationships[:extends].uniq.empty?
=begin
=end

# ap operations

  ap "#{operations[:statements].size} statements for #{artifact['name']}"
  send_and_commit(operations) unless operations.empty?
end

index_operations = { statements: [ 
  { statement: "create constraint on (t:Type) assert t.qualifiedName is unique" },
  { statement: "create constraint on (t:TypeReference) assert t.qualifiedName is unique" },
  { statement: "create constraint on (f:Field) assert f.qualifiedName is unique" },
  { statement: "create constraint on (m:Method) assert m.qualifiedName is unique" }
] }
send_and_commit(index_operations)

Dir['/tmp/*.json'].each do |file|
  ap file
  process_file(File.read(file))
#process_file(File.read('~/Desktop/wsd-opt.json'))
end
=begin
=end

#process_file(File.read('/tmp/module-data.json'))


