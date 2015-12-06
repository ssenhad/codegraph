package com.dnfeitosa.codegraph.db.graph.converters;

import com.dnfeitosa.codegraph.core.model.Impact;
import com.dnfeitosa.codegraph.db.graph.nodes.ImpactResult;
import com.dnfeitosa.codegraph.db.graph.nodes.ModuleNode;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@Ignore("functionality is not implemented")
public class ImpactConverterTest {

	private final ModuleNode impactor = new ModuleNode() {
		{
			setName("impactor");
		}
	};
	private final ModuleNode impacted = new ModuleNode() {
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
		private final ModuleNode impactor;
		private final ModuleNode impacted;

		public ImpactResultStub(ModuleNode impactor, ModuleNode impacted) {
			this.impactor = impactor;
			this.impacted = impacted;
		}

		@Override
		public ModuleNode getImpactor() {
			return impactor;
		}

		@Override
		public ModuleNode getImpacted() {
			return impacted;
		}
	}
}
