package appian.ci.comparemissing;

import common.HelpPrinter;
import common.IApplication;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;

public class Application implements IApplication {

    @Override
    public void execute(CommandLine commandLine) {
        
        if (commandLine.hasOption(CommandlineOptions.HELP)) {
            showHelp();
            return;
        }
        
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
    
    @Override
    public void showHelp() {
        
        CommandlineOptions cmdline = new CommandlineOptions();
        
        new HelpPrinter().printHelp(cmdline.getCommand(), cmdline.getOptions(), System.out);
        
        
    }
}
