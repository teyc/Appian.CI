package appian.ci.getlogfile;

import common.IApplication;
import common.Encryption;
import common.HelpPrinter;
import common.SuggestEncrypt;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.commons.cli.CommandLine;

public class Application implements IApplication {

    @Override
    public void execute(CommandLine commandLine) {
        
        if (commandLine.hasOption(CommandlineOptions.HELP)) {
            showHelp();
            return;
        }
        
        try {
            
            URL url = new URL(commandLine.getOptionValue(CommandlineOptions.URL));
            String username = commandLine.getOptionValue(CommandlineOptions.USERNAME);
            String password = commandLine.getOptionValue(CommandlineOptions.PASSWORD);
            String decryptionKey = commandLine.getOptionValue(CommandlineOptions.KEY);
            String logFileId = commandLine.getOptionValue(CommandlineOptions.LOGID);
            
            if (decryptionKey != null) {
                password = new Encryption().decrypt(password, decryptionKey);
            } else {
                SuggestEncrypt.recommendCommandLineOption(System.out, password);
            }
            
            String result = new Command().execute(url, logFileId, username, password);
            System.out.print(result);
            
        } catch (MalformedURLException ex) {
            
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void showHelp() {
        
        new HelpPrinter().printHelp(new CommandlineOptions().getCommand(), System.out);
        
    }
    
}
