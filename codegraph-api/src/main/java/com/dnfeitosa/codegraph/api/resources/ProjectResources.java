package com.dnfeitosa.codegraph.api.resources;

import java.util.List;

public class ProjectResources {

    private List<ProjectResource> projects;

    public ProjectResources() {
    }

    public ProjectResources(List<ProjectResource> projects) {
        this.projects = projects;
    }

    public List<ProjectResource> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectResource> projects) {
        this.projects = projects;
    }
}
