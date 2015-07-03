package com.dnfeitosa.codegraph.cli;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class CodeGraphMain {

    private ApplicationContext applicationContext;

    public CodeGraphMain(String... springProfiles) {
        GenericApplicationContext parentContext = new GenericApplicationContext();
        parentContext.getEnvironment().setActiveProfiles(springProfiles);
        applicationContext = new ClassPathXmlApplicationContext(new String[] { "/codegraph-db.xml" });
    }

    public static void main(String[] args) {
        CodeGraphMain codeGraph = new CodeGraphMain("production");
        codeGraph.execute();
    }

    private void execute() {
//        ApplicationIndexer indexer = applicationContext.getBean(ApplicationIndexer.class);
//
//        indexer.index("", DescriptorType.IVY);
    }
}
