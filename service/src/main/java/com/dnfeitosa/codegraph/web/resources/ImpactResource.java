package com.dnfeitosa.codegraph.web.resources;

public class ImpactResource {

	private ModuleResource impacting;
	private ModuleResource impacted;

	public ModuleResource getImpacted() {
		return impacted;
	}

	public void setImpacted(ModuleResource impacted) {
		this.impacted = impacted;
	}

	public ModuleResource getImpacting() {
		return impacting;
	}

	public void setImpacting(ModuleResource impacting) {
		this.impacting = impacting;
	}
}