package com.dnfeitosa.codegraph.testing;

import org.apache.commons.lang.StringUtils;

public class TestContext {

	public static final String FAKE_CODEBASE = TestContext.class.getResource("/test-codebases").getFile();;
	public static final String MODULE_DESCRIPTORS = TestContext.class.getResource("/module-descriptors").getFile();;

    public static String descriptorLocation(String descriptorFile) {
        return Path.join(MODULE_DESCRIPTORS, descriptorFile);
    }

    public static FakeApp mirror() {
        return new FakeApp(Path.join(FAKE_CODEBASE, "mirror"));
    }

    public static FakeApp vraptor() {
        return new FakeApp(Path.join(FAKE_CODEBASE, "vraptor4"));
    }

    public static FakeApp ivyBased() {
        return new FakeApp(Path.join(FAKE_CODEBASE, "ivy-based-application"));
    }

    public static class FakeApp {

		private final String location;

		FakeApp(String location) {
			this.location = location;
		}

		public String locationOf(String moduleName) {
			return Path.join(location, moduleName);
		}

		public String location() {
			return location;
		}
	}

	static class Path {
		static String join(String... parts) {
			return StringUtils.join(parts, "/");
		}
	}
}
