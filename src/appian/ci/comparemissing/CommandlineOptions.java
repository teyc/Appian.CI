
package appian.ci.comparemissing;

import common.IOptions;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class CommandlineOptions implements IOptions {

    public static String SOURCE = "source";
    public static String TARGET = "target";
    public static String TARGET_NAME = "targetname";
        
    @Override
    public Options getOptions() {
        return new Options()
                .addOption(Option.builder(SOURCE).desc("Path to output file from QueryNameByUuid on the development server").hasArg().required().build())
                .addOption(Option.builder(TARGET).desc("Path to output file from QueryNameByUuid on the server where application will be deployed.").hasArg().required().build())
                .addOption(Option.builder(TARGET_NAME).desc("Optional - name of the target system e.g. STAGING").hasArg().build());
    }

    @Override
    public String getCommand() {
        return "CompareMissing";
    }
    
}
