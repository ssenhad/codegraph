package com.dnfeitosa.codegraph.web.resources;

import com.dnfeitosa.codegraph.web.resources.Resource.Resource;

import java.util.List;

import static java.lang.String.format;
import static org.apache.commons.lang.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang.builder.ToStringBuilder.reflectionToString;

public class ApplicationResource implements Resource {

	private String name;
	private List<String> modules;

    public ApplicationResource(String name) {
        this.name = name;
    }

    public String getName() {
		return name;
	}

	public List<String> getModules() {
		return modules;
	}

	public void setModules(List<String> modules) {
		this.modules = modules;
	}

    @Override
    public String getUri() {
        return format("/applications/%s", name);
    }

    @Override
    public boolean equals(Object o) {
        return reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return reflectionToString(this);
    }
}