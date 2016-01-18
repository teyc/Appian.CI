package common;

import org.apache.commons.cli.Options;

public interface IOptions {
    
    String getCommand();
    
    Options getOptions();
}
