package com.dnfeitosa.codegraph.testing;

import org.apache.commons.lang.StringUtils;

import static com.dnfeitosa.codegraph.testing.TestContext.Path.join;

public class TestContext {

	public static final String FAKE_CODEBASE = TestContext.class.getResource("/test-codebases").getFile();;
	public static final String MODULE_DESCRIPTORS = TestContext.class.getResource("/module-descriptors").getFile();;
	public static final String MULTIPLE_APPLICATIONS = join(FAKE_CODEBASE, "multiple-applications");

    public static String descriptorLocation(String descriptorFile) {
        return join(MODULE_DESCRIPTORS, descriptorFile);
    }

    public static FakeApp mirror() {
        return new FakeApp("mirror", join(FAKE_CODEBASE, "mirror"));
    }

    public static FakeApp vraptor() {
        return new FakeApp("vraptor4", join(FAKE_CODEBASE, "vraptor4"));
    }

    public static FakeApp ivyBased() {
        return new FakeApp("ivy-based-application", join(MULTIPLE_APPLICATIONS, "/ivy-based-application"));
    }

    public static class FakeApp {

		private final String name;
		private final String location;

		FakeApp(String name, String location) {
            this.name = name;
            this.location = location;
        }

        public String name() {
            return name;
        }

        public String locationOf(String moduleName) {
			return join(location(), moduleName);
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
