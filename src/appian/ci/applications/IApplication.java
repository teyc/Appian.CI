
package appian.ci.applications;

import org.apache.commons.cli.CommandLine;

public interface IApplication {
    void execute(CommandLine commandLine);
}
