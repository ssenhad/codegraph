require 'json'
require 'net/http'
require 'enumerator'

artifact = JSON.parse(File.read('/Users/dnfeitosa/Desktop/wsd-opt.json'))


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

queries = artifact['types'].collect do |type|
  q = []
  #q << create_type(artifact, type, 't')

  (type['fields'] || []).each_with_index do |field, i|

    field_preffix = "f#{i}"
    create_field = " 
  #{create_field(artifact, type, field, field_preffix)}
       
  #{create_type_reference(field['type'], "ft#{i}")} 
  CREATE (t)-[:DECLARES]->(#{field_preffix})
  CREATE (#{field_preffix})-[:OF_TYPE]->(ft#{i})
    "

    q << create_field if create_field
  end
  q
end

def send_and_commit(operations)
  uri = URI.parse('http://neo4j.codegraph-dev.dnfeitosa.com:7476/db/data/transaction')
  req = Net::HTTP::Post.new(uri.request_uri, 'Content-Type' => 'application/json', 'X-Stream' => 'true')
  req.body = JSON.pretty_generate(operations)
  puts req.body

  res = Net::HTTP.start(uri.hostname, uri.port, read_timeout: 3600) do |http|
    http.request(req)
  end

  puts res.body

  uri = URI.parse(JSON.parse(res.body)['commit'])
  req = Net::HTTP::Post.new(uri.request_uri, 'Content-Type' => 'application/json')
  res = Net::HTTP.start(uri.hostname, uri.port) do |http|
    http.request(req)
  end
  puts res.body
=begin
=end
end

queries.each do |q|
  puts q.join
  puts '-'*100
end

puts "#{queries.size} statements"

  
=begin
queries.select { |q| not q.empty? }.each do |q|
  send_and_commit({ statements: [ { statement: q.join } ] })
end
=end

operations = { statements: [ { statement: 'USING PERIODIC COMMIT 500' } ] }
operations[:statements] += queries.select { |q| not q.empty? }.enum_for(:each_with_index).collect do |q, i|
  { statement: q.join }
end

send_and_commit(operations)

