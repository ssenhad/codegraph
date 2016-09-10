package com.dnfeitosa.codegraph.gradle.plugin

import com.dnfeitosa.codegraph.gradle.plugin.extensions.CodegraphExtension
import com.dnfeitosa.codegraph.gradle.plugin.task.CodegraphTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.logging.Logging

class CodegraphPlugin implements Plugin<Project> {

    private static final logger = Logging.getLogger(CodegraphPlugin)

    @Override
    public void apply(final Project project) {
        logger.info("Applying Codegraph plugin to " + project.getName())

        project.getExtensions().create("codegraph", CodegraphExtension)
        project.getTasks().create("codegraph", CodegraphTask)
    }
}
