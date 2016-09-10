package com.dnfeitosa.codegraph.gradle.plugin.task

import com.dnfeitosa.codegraph.gradle.plugin.CodegraphIndexer
import com.dnfeitosa.codegraph.gradle.plugin.client.Client
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class CodegraphTask extends DefaultTask {

    @TaskAction
    def void index() {
        new CodegraphIndexer(new Client(project.codegraph.url)).index(project)
    }
}
