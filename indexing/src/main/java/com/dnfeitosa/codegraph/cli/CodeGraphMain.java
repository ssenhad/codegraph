package com.dnfeitosa.codegraph.cli;

import com.dnfeitosa.codegraph.core.descriptors.DescriptorType;
import com.dnfeitosa.codegraph.core.descriptors.readers.ApplicationReader;
import com.dnfeitosa.codegraph.core.descriptors.readers.MultiModuleApplicationReader;
import com.dnfeitosa.codegraph.core.loaders.ApplicationLoader;
import com.dnfeitosa.codegraph.indexing.ApplicationIndexer;
import com.dnfeitosa.codegraph.services.ApplicationService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CodeGraphMain {

    private ApplicationContext applicationContext;

    public CodeGraphMain() {
        applicationContext = new ClassPathXmlApplicationContext(new String[] { "/codegraph-indexing.xml" });
    }

    public static void main(String[] args) {
        CodeGraphMain codeGraph = new CodeGraphMain();
        codeGraph.execute();
    }

    private void execute() {
        ApplicationReader applicationReader = applicationContext.getBean(MultiModuleApplicationReader.class);
        ApplicationLoader applicationLoader = applicationContext.getBean(ApplicationLoader.class);
        ApplicationService applicationService = applicationContext.getBean(ApplicationService.class);
        ApplicationIndexer indexer = new ApplicationIndexer(applicationReader, applicationLoader, applicationService);

        indexer.index("", DescriptorType.MAVEN);
    }
}
