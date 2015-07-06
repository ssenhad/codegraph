import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.junit.Test;

public class FooTest {

    @Test
    public void foo() {
        Options options = new Options();
        options.addOption(Option
                .builder("list")
                .argName("dir")
                .hasArg()
                .numberOfArgs(1)
                .optionalArg(true)
                .build());

        options.addOption(Option
                .builder("index")
                .argName("dir")
                .hasArg()
                .numberOfArgs(1)
                .build());

        options.addOption(Option
                .builder("list")
                .argName("dir")
                .hasArg()
                .numberOfArgs(1)
                .optionalArg(true)
                .build());

        options.addOption(Option
                .builder()
                .longOpt("multi-app")
                .build());

        options.addOption(Option
                .builder()
                .longOpt("single-module")
                .build());

        new HelpFormatter().printHelp("codegraph", options);

        String[] args = { "-list", "/asdf/uqewrwer" };

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine commandLine = parser.parse(options, args);
            Option option = commandLine.getOptions()[0];
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
