package com.dnfeitosa.codegraph.cli;

import com.dnfeitosa.codegraph.cli.commands.Index;
import io.airlift.airline.Cli;
import io.airlift.airline.Help;

public class CodeGraphMain {

    public static void main(String[] args) {
        Cli<Runnable> cli = Cli.<Runnable>builder("codegraph")
                .withDescription("CodeGraph indexer tool")
                .withDefaultCommand(Help.class)
                .withCommands(Help.class, Index.class)
                .build();

        Runnable command = cli.parse(args);
        command.run();
    }
}
