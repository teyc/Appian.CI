package appian.ci.options;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class QueryNameByUuid {
    
    public static String UUIDS = "uuids";
    public static String URL = "url";
    public static String USERNAME = "username";
    public static String PASSWORD = "password";
    public static String KEY = "key";
   
    public Options getOptions()
    {
        return new Options()
                .addOption(Option.builder(USERNAME).desc("Appian logon name").hasArg().required().build())
                .addOption(Option.builder(PASSWORD).desc("Appian password").hasArg().required().build())
                .addOption(Option.builder(KEY).desc("Optional decryption key for Appian password").build())
                .addOption(Option.builder(URL).desc("URL to Appian server including /suite e.g. https://xyz.appiancloud.com/suite").hasArg().required().build())
                .addOption(Option.builder(UUIDS).desc("UUIDs separated by commas").hasArg().required().build());
    }
}
