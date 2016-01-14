package appian.ci.commands;

import appian.ci.core.EndpointResolver;
import common.HttpRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QueryNameByUuid {
    
    private static final Logger logger = Logger.getLogger(QueryNameByUuid.class.getName());
    private static final String API_PATH = "webapi/getContent";
    
    public Iterable<String> execute(Iterable<String> uuids, URL server, String username, String password) throws MalformedURLException
    {
        List<String> result = new LinkedList<>();
        
        for (String uuid : uuids)
        {
            result.add(execute(uuid, server, username, password));
        }
        
        return result;
    }
    
    public String execute(String uuid, URL server, String username, String password) throws MalformedURLException
    {
        URL endPoint = EndpointResolver.resolve(server, API_PATH);
        
        logger.log(Level.INFO, "GET {0}", endPoint.toString());
        
        HttpRequest request = HttpRequest
            .get(HttpRequest.append(endPoint.toString(), "uuid", uuid))
            .basic(username, password);
            
        return request.body();
    }
        
}
