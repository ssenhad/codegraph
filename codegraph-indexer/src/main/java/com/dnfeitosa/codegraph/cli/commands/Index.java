package com.dnfeitosa.codegraph.cli.commands;

import com.dnfeitosa.codegraph.core.descriptors.DescriptorType;
import com.dnfeitosa.codegraph.indexer.ApplicationIndexer;
import io.airlift.airline.Arguments;
import io.airlift.airline.Command;
import io.airlift.airline.Option;

import java.util.List;

import static java.lang.System.out;
import static java.util.Arrays.asList;


@Command(name = "index", description = "Indexes a codebase into CodeGraph")
public class Index extends AbstractCommand {

    @Arguments(title="dir", description = "application(s) directory")
    public String dir;

    @Option(name = "--ignore", description = "comma separated list of directories inside <dir> to ignore")
    public String ignore;

    @Override
    public void run() {
        ApplicationIndexer indexer = new ApplicationIndexer(getReader(), getLoader(), getService());

        List<String> ignores = asList(ignore.split(","));
        out.println(String.format("Indexing code in '%s'.", dir));
        out.println(String.format("~> ignoring data in '%s'.", ignores));
        indexer.index(dir, DescriptorType.IVY, name -> ignores.contains(name));
    }
}
