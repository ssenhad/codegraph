package com.dnfeitosa.codegraph.analysis.impactzone;

import com.dnfeitosa.codegraph.analysis.TaintedTest;
import com.dnfeitosa.codegraph.model.Impact;
import com.dnfeitosa.codegraph.model.ImpactZone;
import com.dnfeitosa.codegraph.model.Module;
import com.dnfeitosa.codegraph.web.TaintedEggs;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

public class ImpactZoneCalculatorTest {

    private final TaintedEggs taintedEggs = TaintedTest.initialize();
    private final Module app2 = taintedEggs.getModule("app2-Module1");

    private ImpactZoneCalculator calculator;

    @Before
    public void setUp() {
        calculator = new ImpactZoneCalculator(taintedEggs.getAllModules());
    }

    @Test
    public void whenCalculatingTheImpactZoneOfAModule() {
        ImpactZone impactZone = calculator.impactOf(app2);

        itShouldReturnTheDirectlyImpactedModules(impactZone);
        itShouldReturnTheImpactedModulesWithinTheSameApplication(impactZone);
        itShouldReturnTheTransitivelyImpactedModules(impactZone);
    }

    private void itShouldReturnTheTransitivelyImpactedModules(ImpactZone impactZone) {
        List<Impact> full = impactZone.getFull();

        assertThat(full.size(), is(4));

        isImpactOf(full.get(0), "app2-Module1", "app4-Module1");
        isImpactOf(full.get(1), "app4-Module1", "app4-Module4");
        isImpactOf(full.get(2), "app2-Module1", "app2-Module2");
        isImpactOf(full.get(3), "app2-Module2", "app4-Module4");
    }

    private void itShouldReturnTheImpactedModulesWithinTheSameApplication(ImpactZone impactZone) {
        List<Impact> impacts = impactZone.getFromSameApplication();

        assertThat(impacts.size(), is(1));

        isImpactOf(impacts.get(0), "app2-Module1", "app2-Module2");
    }

    private void itShouldReturnTheDirectlyImpactedModules(ImpactZone impactZone) {
        List<Impact> impacts = impactZone.getDirect();
        assertThat(impacts.size(), is(2));

        isImpactOf(impacts.get(0), "app2-Module1", "app4-Module1");
        isImpactOf(impacts.get(1), "app2-Module1", "app2-Module2");
    }

    private void isImpactOf(Impact impact, String impacting, String impacted) {
        assertThat("Impacting mismatch", impact.getImpacting(), sameInstance(module(impacting)));
        assertThat("Impacted mismatch", impact.getImpacted(), sameInstance(module(impacted)));
    }

    private Module module(String name) {
        return taintedEggs.getModule(name);
    }
}
