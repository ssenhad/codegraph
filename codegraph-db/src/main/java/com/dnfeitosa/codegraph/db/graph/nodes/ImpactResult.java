package com.dnfeitosa.codegraph.db.graph.nodes;

import org.neo4j.ogm.annotation.ResultColumn;
import org.springframework.data.neo4j.annotation.QueryResult;


@QueryResult
public interface ImpactResult {

	@ResultColumn("impactor")
	Module getImpactor();

	@ResultColumn("impacted")
	Module getImpacted();
}