package appian.ci.getlogfile;

import appian.ci.core.EndpointResolver;
import common.HttpRequest;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Command {
    
    private static final Logger logger = Logger.getLogger(appian.ci.listmissingprecedents.Command.class.getName());
    
    public String execute(URL url, String logFileId, String username, String password) {
        
        URL logFileUrl = EndpointResolver.resolve(url, "doc/" + logFileId);
        
        logger.log(Level.INFO, "GET {0}", logFileUrl.toString());
        
        HttpRequest request = HttpRequest.get(logFileUrl)
                                .basic(username, password);
        
        for (String header : request.headers().keySet())
        {
            for (String item : request.headers(header))
            {
                logger.log(Level.INFO, "Response {0}: {1}", new Object[]{header, item});                
            }
        }
        
        return request.body();
    }
    
}
