package com.dnfeitosa.codegraph.cli.commands;

import com.dnfeitosa.codegraph.core.descriptors.readers.ApplicationReader;
import com.dnfeitosa.codegraph.core.descriptors.readers.MultiApplicationReader;
import com.dnfeitosa.codegraph.core.loaders.ApplicationLoader;
import com.dnfeitosa.codegraph.services.ApplicationService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class AbstractCommand implements Runnable {

    protected ApplicationContext applicationContext;

    public AbstractCommand() {
        applicationContext = new ClassPathXmlApplicationContext(new String[] {"/codegraph-indexer.xml"});
    }

    protected ApplicationService getService() {
        return applicationContext.getBean(ApplicationService.class);
    }

    protected ApplicationLoader getLoader() {
        return applicationContext.getBean(ApplicationLoader.class);
    }

    protected ApplicationReader getReader() {
        return applicationContext.getBean(MultiApplicationReader.class);
    }
}
