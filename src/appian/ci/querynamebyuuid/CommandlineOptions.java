package appian.ci.querynamebyuuid;

import common.IOptions;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;

public class CommandlineOptions implements IOptions {
    
    public static String UUIDS = "uuids";
    public static String UUIDSFILE = "uuidsFile";
    public static String URL = "url";
    public static String USERNAME = "username";
    public static String PASSWORD = "password";
    public static String KEY = "key";
    public static String HELP = "help";
   
    public Options getOptions()
    {
        OptionGroup uuidOptions = new OptionGroup()                  
                    .addOption(Option.builder(UUIDS).desc("UUIDs separated by commas").hasArg().required().build())
                    .addOption(Option.builder(UUIDSFILE).desc("file containing one UUID on each line").hasArg().required().build());
        uuidOptions.setRequired(true);
        
        return new Options()
                .addOption(Option.builder(USERNAME).desc("Appian logon name").hasArg().required().build())
                .addOption(Option.builder(PASSWORD).desc("Appian password").hasArg().required().build())
                .addOption(Option.builder(KEY).desc("Optional decryption key for Appian password").hasArg().build())
                .addOption(Option.builder(URL).desc("URL to Appian server including /suite e.g. https://xyz.appiancloud.com/suite").hasArg().required().build())
                .addOptionGroup(uuidOptions)
                .addOption(Option.builder(HELP).desc("Help").build());
    }

    @Override
    public String getCommand() {
        return "QueryNameByUuid";
    }
}
