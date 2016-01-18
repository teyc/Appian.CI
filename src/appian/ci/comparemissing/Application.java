package appian.ci.comparemissing;

import common.IApplication;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.cli.CommandLine;

public class Application implements IApplication {

    @Override
    public void execute(CommandLine commandLine) {
        
        try {
            Path source = Paths.get(commandLine.getOptionValue(CommandlineOptions.SOURCE));
            Path target = Paths.get(commandLine.getOptionValue(CommandlineOptions.TARGET));
            String targetName = commandLine.getOptionValue(CommandlineOptions.TARGET_NAME);
            if (targetName == null) targetName = commandLine.getOptionValue(CommandlineOptions.TARGET);
            
            Command command = new Command();
            command.execute(source, target, targetName);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
}
