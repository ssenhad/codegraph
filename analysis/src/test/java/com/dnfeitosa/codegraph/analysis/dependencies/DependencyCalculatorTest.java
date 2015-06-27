package com.dnfeitosa.codegraph.analysis.dependencies;

import com.dnfeitosa.codegraph.analysis.TaintedTest;
import com.dnfeitosa.codegraph.model.Dependencies;
import com.dnfeitosa.codegraph.model.Dependency;
import com.dnfeitosa.codegraph.model.Module;
import com.dnfeitosa.codegraph.web.TaintedEggs;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DependencyCalculatorTest {

    private final TaintedEggs taintedEggs = TaintedTest.initialize();
    private final Module presentationCommon = taintedEggs.getModule("app4-Module4");

    private DependencyCalculator calculator;

    @Before
    public void setUp() {
        calculator = new DependencyCalculator(taintedEggs);
    }

    @Test
    public void whenCalculatingTheDependenciesOfAModule() {
        Dependencies dependencies = calculator.dependenciesOf(presentationCommon);

        itShouldGetTheDirectDependencies(dependencies);
        itShouldGetAllDependencies(dependencies);
    }

    @Test
    public void whenCalculatingTheOrgOnlyDependenciesOfAModule() {
        Dependencies dependencies = calculator.dependenciesOf(presentationCommon, true);

        itShouldNotIncludeAnyDependenciesFromOtherOrganizations(dependencies);
    }

    private void itShouldNotIncludeAnyDependenciesFromOtherOrganizations(Dependencies dependencies) {
        List<Dependency> direct = dependencies.getAll();

        assertThat(direct.size(), is(4));
        isDependencyOf(direct.get(0), "app4-Module4", "app4-Module1");
        isDependencyOf(direct.get(1), "app4-Module1", "app2-Module1");
        isDependencyOf(direct.get(2), "app4-Module4", "app2-Module2");
        isDependencyOf(direct.get(3), "app2-Module2", "app2-Module1");
    }

    private void itShouldGetTheDirectDependencies(Dependencies dependencies) {
        List<Dependency> direct = dependencies.getDirect();
        isDependencyOf(direct.get(0), "app4-Module4", "commons-logging");
        isDependencyOf(direct.get(1), "app4-Module4", "app4-Module1");
        isDependencyOf(direct.get(2), "app4-Module4", "app2-Module2");
    }

    private void itShouldGetAllDependencies(Dependencies dependencies) {
        List<Dependency> direct = dependencies.getAll();

        assertThat(direct.size(), is(8));
        isDependencyOf(direct.get(0), "app4-Module4", "commons-logging");
        isDependencyOf(direct.get(1), "app4-Module4", "app4-Module1");
        isDependencyOf(direct.get(2), "app4-Module1", "commons-beanutils");
        isDependencyOf(direct.get(3), "app4-Module1", "app2-Module1");
        isDependencyOf(direct.get(4), "app2-Module1", "commons-beanutils");
        isDependencyOf(direct.get(5), "app4-Module4", "app2-Module2");
        isDependencyOf(direct.get(6), "app2-Module2", "commons-beanutils");
        isDependencyOf(direct.get(7), "app2-Module2", "app2-Module1");
    }

    private void isDependencyOf(Dependency dependency, String moduleName, String jarName) {
        assertThat(dependency.getDependent(), is(taintedEggs.getModule(moduleName)));
        assertThat(dependency.getJar().getName(), is(jarName));
    }
}
