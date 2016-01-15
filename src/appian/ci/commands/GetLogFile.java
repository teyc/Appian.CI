package appian.ci.commands;

import appian.ci.core.EndpointResolver;
import common.HttpRequest;
import java.net.URL;

public class GetLogFile {
    
    public String execute(URL url, String logFileId, String username, String password) {
        
        URL logFileUrl = EndpointResolver.resolve(url, "doc/" + logFileId);
        return HttpRequest.get(logFileUrl)
                          .basic(username, password)
                          .body();
    }
    
}
