package com.dnfeitosa.codegraph.core.loaders.finders;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.dnfeitosa.codegraph.core.loaders.finders.code.UsageResult;

public class StringToUsageResultTest {

	private StringToUsageResult function;

	@Before
	public void setUp() {
		function = new StringToUsageResult();
	}

	@Test
	public void shouldUseThePreviousFileNameWhenResultDoesNotContainTheFileInformation() {
		assertThatIsUsageResult(convert("./src/pkg/file:Class"), "src/pkg/file", "Class");
		assertThatIsUsageResult(convert("./src/pkg/fileName:Class"), "src/pkg/fileName", "Class");
		assertThatIsUsageResult(convert("Class"), "src/pkg/fileName", "Class");
		assertThatIsUsageResult(convert("AnotherClass"), "src/pkg/fileName", "AnotherClass");
		assertThatIsUsageResult(convert("./src/pkg/anotherFileName:AnotherClass"), "src/pkg/anotherFileName", "AnotherClass");
	}

	private UsageResult convert(String input) {
		return function.apply(input);
	}

	private void assertThatIsUsageResult(UsageResult result, String fileName, String className) {
		assertThat(result.getFileName(), is(fileName));
		assertThat(result.getClassName(), is(className));
	}
}
