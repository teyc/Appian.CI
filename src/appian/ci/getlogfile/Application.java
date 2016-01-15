package appian.ci.getlogfile;

import common.IApplication;
import common.Encryption;
import common.SuggestEncrypt;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.commons.cli.CommandLine;

public class Application implements IApplication {

    @Override
    public void execute(CommandLine commandLine) {
        
        try {
            
            URL url = new URL(commandLine.getOptionValue(appian.ci.getlogfile.CommandlineOptions.URL));
            String username = commandLine.getOptionValue(appian.ci.getlogfile.CommandlineOptions.USERNAME);
            String password = commandLine.getOptionValue(appian.ci.getlogfile.CommandlineOptions.PASSWORD);
            String decryptionKey = commandLine.getOptionValue(appian.ci.getlogfile.CommandlineOptions.KEY);
            String logFileId = commandLine.getOptionValue(appian.ci.getlogfile.CommandlineOptions.LOGID);
            
            if (decryptionKey != null) {
                password = new Encryption().decrypt(password, decryptionKey);
            } else {
                SuggestEncrypt.recommendCommandLineOption(System.out, password);
            }
            
            String result = new appian.ci.getlogfile.Command().execute(url, logFileId, username, password);
            System.out.print(result);
            
        } catch (MalformedURLException ex) {
            
            throw new RuntimeException(ex);
        }
    }
    
}
