package appian.ci.querynamebyuuid;

import common.IApplication;
import appian.ci.core.UuidUtil;
import common.Encryption;
import common.HelpPrinter;
import common.HttpRequest;
import common.SuggestEncrypt;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.CommandLine;

public class Application implements IApplication {

    @Override
    public void execute(CommandLine commandLine) {
        
        if (commandLine.hasOption(CommandlineOptions.HELP)) {
            showHelp();
            return;
        }
        
        try {
            UuidUtil uuidUtil2 = new UuidUtil();
            appian.ci.querynamebyuuid.Command queryCommand = new appian.ci.querynamebyuuid.Command(uuidUtil2);
            String uuids = commandLine.getOptionValue(appian.ci.querynamebyuuid.CommandlineOptions.UUIDS);
            String uuidsFile = commandLine.getOptionValue(appian.ci.querynamebyuuid.CommandlineOptions.UUIDSFILE);
            URL url = new URL(commandLine.getOptionValue(appian.ci.querynamebyuuid.CommandlineOptions.URL));
            String password = commandLine.getOptionValue(appian.ci.querynamebyuuid.CommandlineOptions.PASSWORD);
            String decryptionKey = commandLine.getOptionValue(appian.ci.querynamebyuuid.CommandlineOptions.KEY);
            if (decryptionKey != null) {
                password = new Encryption().decrypt(password, decryptionKey);
            } else {
                SuggestEncrypt.recommendCommandLineOption(System.out, password);
            }
            try {
                Iterable<appian.ci.querynamebyuuid.Command.Result> values;
                values = uuidsFile != null ? queryCommand.executeWithFile(new File(uuidsFile), url, commandLine.getOptionValue(appian.ci.querynamebyuuid.CommandlineOptions.USERNAME), password) : queryCommand.execute(Arrays.asList(uuids.split(",")), url, commandLine.getOptionValue(appian.ci.querynamebyuuid.CommandlineOptions.USERNAME), password);
                for (appian.ci.querynamebyuuid.Command.Result value : values) {
                    System.out.printf("%s:%s\n", value.fixedUuid, value.response);
                }
            } catch (HttpRequest.HttpRequestException httpRequestException) {
                System.err.printf("Could not connect. Have you tried \n java -Dhttps.proxyHost=.... -Dhttps.proxyPort=... -Dhttps.proxyUser=... -Dhttps.proxyPassword=...");
                throw httpRequestException;
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void showHelp() {
        
        new HelpPrinter().printHelp(new CommandlineOptions().getCommand(), System.out);
        
    }
    
}
