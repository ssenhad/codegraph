package com.dnfeitosa.codegraph.gradle.plugin.client

import com.dnfeitosa.codegraph.gradle.plugin.client.resources.Artifact
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import org.apache.commons.lang.StringUtils
import org.gradle.api.InvalidUserDataException
import org.gradle.api.logging.Logging

class Client {

    private static final logger = Logging.getLogger(Client)

    private HTTPBuilder httpBuilder;

    Client(String url) {
        logger.info("Opening connection to Codegraph server at '{}'", url)
        if (StringUtils.isBlank(url)) {
            throw new InvalidUserDataException("Codegraph URL must be specified in configuration closure.")
        }
        httpBuilder = new HTTPBuilder(url)
    }

    void addArtifact(Artifact artifact) {
        httpBuilder.post(path: '/artifacts', body: artifact, requestContentType: ContentType.JSON) { response ->
            logger.info("Indexing artifact {} with {} dependencies", artifact.name, artifact.dependencies.size())
            logger.info("Server response ${response.statusLine.statusCode}")
        }
    }
}
