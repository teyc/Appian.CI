package appian.ci.applications;

import common.Encryption;
import common.SuggestEncrypt;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.commons.cli.CommandLine;

public class GetLogFile implements IApplication {

    @Override
    public void execute(CommandLine commandLine) {
        
        try {
            
            URL url = new URL(commandLine.getOptionValue(appian.ci.options.GetLogFile.URL));
            String username = commandLine.getOptionValue(appian.ci.options.GetLogFile.USERNAME);
            String password = commandLine.getOptionValue(appian.ci.options.GetLogFile.PASSWORD);
            String decryptionKey = commandLine.getOptionValue(appian.ci.options.GetLogFile.KEY);
            String logFileId = commandLine.getOptionValue(appian.ci.options.GetLogFile.LOGID);
            
            if (decryptionKey != null) {
                password = new Encryption().decrypt(password, decryptionKey);
            } else {
                SuggestEncrypt.recommendCommandLineOption(System.out, password);
            }
            
            String result = new appian.ci.commands.GetLogFile().execute(url, logFileId, username, password);
            System.out.print(result);
            
        } catch (MalformedURLException ex) {
            
            throw new RuntimeException(ex);
        }
    }
    
}
