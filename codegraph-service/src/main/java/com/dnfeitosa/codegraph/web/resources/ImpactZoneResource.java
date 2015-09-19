package com.dnfeitosa.codegraph.web.resources;

import java.util.List;

public class ImpactZoneResource {

	private ModuleResource module;
	private List<ModuleResource> direct;
	private List<ModuleResource> indirect;
	private List<ModuleResource> sameApplication;

	public ModuleResource getModule() {
		return module;
	}

	public void setModule(ModuleResource module) {
		this.module = module;
	}

	public List<ModuleResource> getDirect() {
		return direct;
	}

	public void setDirect(List<ModuleResource> direct) {
		this.direct = direct;
	}

	public List<ModuleResource> getIndirect() {
		return indirect;
	}

	public void setIndirect(List<ModuleResource> indirect) {
		this.indirect = indirect;
	}

	public List<ModuleResource> getSameApplication() {
		return sameApplication;
	}

	public void setSameApplication(List<ModuleResource> sameApplication) {
		this.sameApplication = sameApplication;
	}
}
