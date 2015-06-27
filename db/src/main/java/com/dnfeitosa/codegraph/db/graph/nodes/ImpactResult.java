package com.dnfeitosa.codegraph.db.graph.nodes;

import org.springframework.data.neo4j.annotation.QueryResult;
import org.springframework.data.neo4j.annotation.ResultColumn;

@QueryResult
public interface ImpactResult {

	@ResultColumn("impactor")
	Module getImpactor();

	@ResultColumn("impacted")
	Module getImpacted();
}