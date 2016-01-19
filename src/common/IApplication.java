
package common;

import org.apache.commons.cli.CommandLine;

public interface IApplication {
    void execute(CommandLine commandLine);
    void showHelp();
}
