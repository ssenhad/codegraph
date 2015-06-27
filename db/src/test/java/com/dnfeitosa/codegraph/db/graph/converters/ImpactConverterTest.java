package com.dnfeitosa.codegraph.db.graph.converters;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import com.dnfeitosa.codegraph.db.graph.nodes.ImpactResult;
import com.dnfeitosa.codegraph.db.graph.nodes.Module;
import org.junit.Before;
import org.junit.Test;

import com.dnfeitosa.codegraph.model.Impact;

public class ImpactConverterTest {

	private final Module impactor = new Module() {
		{
			setName("impactor");
		}
	};
	private final Module impacted = new Module() {
		{
			setName("impacted");
		}
	};
	private ImpactConverter impactConverter;

	@Before
	public void setUp() {
		impactConverter = new ImpactConverter();
	}

	@Test
	public void shouldConvertAnImpactResultToImpact() {
		Impact impact = impactConverter.fromNode(new ImpactResultStub(impactor, impacted));

		assertThat(impact.getImpacting().getName(), is("impactor"));
		assertThat(impact.getImpacted().getName(), is("impacted"));
	}

	public static class ImpactResultStub implements ImpactResult {
		private final Module impactor;
		private final Module impacted;

		public ImpactResultStub(Module impactor, Module impacted) {
			this.impactor = impactor;
			this.impacted = impacted;
		}

		@Override
		public Module getImpactor() {
			return impactor;
		}

		@Override
		public Module getImpacted() {
			return impacted;
		}
	}
}
