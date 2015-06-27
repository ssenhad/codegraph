package com.dnfeitosa.codegraph.model;

public class Impact {

	private final Module impacting;
	private final Module impacted;

	public Impact(Module impacting, Module impacted) {
		this.impacting = impacting;
		this.impacted = impacted;
	}

	public Module getImpacting() {
		return impacting;
	}

	public Module getImpacted() {
		return impacted;
	}

	@Override
	public String toString() {
		return String.format("Impact{'%s' -=> '%s'}", impacting, impacted);
	}
}