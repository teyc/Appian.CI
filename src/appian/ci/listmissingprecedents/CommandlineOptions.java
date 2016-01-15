package appian.ci.listmissingprecedents;

import common.IOptions;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class CommandlineOptions implements IOptions  {
    
    public static String DIRECTORY = "directory";
    
    public Options getOptions() {
        return new Options()
                .addOption(Option.builder(DIRECTORY).desc("Path to the unzipped exported file, unzipped").hasArg().required().build());
    }
}
