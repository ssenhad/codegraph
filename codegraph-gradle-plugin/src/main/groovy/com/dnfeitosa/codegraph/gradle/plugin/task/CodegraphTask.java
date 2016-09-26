package com.dnfeitosa.codegraph.gradle.plugin.task;

import com.dnfeitosa.codegraph.client.CodegraphClient;
import com.dnfeitosa.codegraph.gradle.plugin.CodegraphIndexer;
import com.dnfeitosa.codegraph.gradle.plugin.extensions.CodegraphExtension;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import javax.inject.Inject;

class CodegraphTask extends DefaultTask {

    @Inject
    public CodegraphTask() {
        super();
    }

    @TaskAction
    public void index() {
        CodegraphExtension extension = getExtension();
        CodegraphIndexer indexer = new CodegraphIndexer(new CodegraphClient(extension.getUrl()));
        indexer.index(getProject());
    }

    private CodegraphExtension getExtension() {
        return getProject().getExtensions().getByType(CodegraphExtension.class);
    }
}
