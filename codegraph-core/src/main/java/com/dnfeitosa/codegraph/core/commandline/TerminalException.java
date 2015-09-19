package com.dnfeitosa.codegraph.core.commandline;

public class TerminalException extends RuntimeException {

	private static final long serialVersionUID = 3746603238992530353L;

	public TerminalException(String message) {
		super(message);
	}

	public TerminalException(String message, Throwable cause) {
		super(message, cause);
	}
}