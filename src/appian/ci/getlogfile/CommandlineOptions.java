package appian.ci.getlogfile;

import common.IOptions;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class CommandlineOptions implements IOptions {
    
    public static String URL = "url";
    public static String USERNAME = "username";
    public static String PASSWORD = "password";
    public static String KEY = "key";
    public static String LOGID = "logid";
    public static String HELP = "help";
        
    
    public Options getOptions()
    {    
        return new Options()
                .addOption(Option.builder(USERNAME).desc("Appian logon name").hasArg().required().build())
                .addOption(Option.builder(PASSWORD).desc("Appian password").hasArg().required().build())
                .addOption(Option.builder(KEY).desc("Optional decryption key for Appian password").hasArg().build())
                .addOption(Option.builder(URL).desc("URL to Appian server including /suite e.g. https://xyz.appiancloud.com/suite").hasArg().required().build())
                .addOption(Option.builder(LOGID).desc("Numeric code of the log file id.").hasArg().required().build())
                .addOption(Option.builder(HELP).desc("Help").build());
    }

    @Override
    public String getCommand() {
        return "GetLogFile";
    }
}
