package com.dnfeitosa.codegraph.analysis;

import com.dnfeitosa.codegraph.commandline.Terminal;
import com.dnfeitosa.codegraph.loaders.ApplicationLoader;
import com.dnfeitosa.codegraph.loaders.ApplicationsLoader;
import com.dnfeitosa.codegraph.loaders.ClassFileLoader;
import com.dnfeitosa.codegraph.loaders.ModuleLoader;
import com.dnfeitosa.codegraph.loaders.ModulesLoader;
import com.dnfeitosa.codegraph.loaders.finders.ApplicationsFinder;
import com.dnfeitosa.codegraph.loaders.finders.IvyFileFinder;
import com.dnfeitosa.codegraph.loaders.finders.JavaFileFinder;
import com.dnfeitosa.codegraph.web.TaintedEggs;

import static com.dnfeitosa.codegraph.testing.TestContext.FAKE_CODEBASE;

public class TaintedTest {

    public static TaintedEggs initialize() {
        Terminal terminal = new Terminal();
        ApplicationsLoader applicationsLoader = new ApplicationsLoader(
            new ApplicationsFinder(terminal),
            new ApplicationLoader(
                new ModulesLoader(
                    new IvyFileFinder(terminal),
                    new ModuleLoader(
                        new ClassFileLoader(
                            new JavaFileFinder(terminal)))
        )));
        return new TaintedEggs(applicationsLoader, FAKE_CODEBASE);
    }
}
