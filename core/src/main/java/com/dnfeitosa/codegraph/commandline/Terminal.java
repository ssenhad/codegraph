package com.dnfeitosa.codegraph.commandline;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.String.format;
import static org.apache.commons.lang.StringUtils.join;

public class Terminal {

	private static final Logger LOGGER = Logger.getLogger(Terminal.class);

	public List<String> execute(String... strings) {
		String command = join(strings, " ");
		try {
			LOGGER.debug(format("Executing '%s'", command));
			Process process = Runtime.getRuntime().exec(strings);
			return waitAndRespond(process);
		} catch (Exception e) {
			throw new TerminalException(format("Cannot execute command '%s'.", command), e);
		}
	}

	public List<String> execute(File workingDir, String... strings) {
		String command = join(strings, " ");
		try {
			LOGGER.debug(format("Executing '%s'", command));
			Process process = Runtime.getRuntime().exec(strings, new String[0], workingDir);
			return waitAndRespond(process);
		} catch (Exception e) {
			throw new TerminalException(format("Cannot execute command '%s'.", command), e);
		}
	}

	private List<String> waitAndRespond(Process process) throws InterruptedException, IOException {
		List<String> output = readOutput(process.getInputStream());
		logErrors(process);
		process.waitFor();
		return output;
	}

	private void logErrors(Process process) throws IOException {
		List<String> error = readOutput(process.getErrorStream());
		if (!error.isEmpty()) {
			LOGGER.warn(join(error, " "));
		}
	}

	private List<String> readOutput(InputStream inputStream) throws IOException {
		List<String> result = new ArrayList<>();

		Scanner scanner = new Scanner(inputStream);
		while (scanner.hasNext()) {
			result.add(scanner.nextLine());
		}
		inputStream.close();
		scanner.close();
		return result;
	}
}