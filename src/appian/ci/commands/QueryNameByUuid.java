
package appian.ci.commands;

import common.HttpRequest;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class QueryNameByUuid {
    
    public Iterable<String> execute(Iterable<String> uuids, URI server, String username, String password) throws MalformedURLException
    {
        List<String> result = new LinkedList<String>();
        
        for (String uuid : uuids)
        {
            result.add(execute(uuid, server, username, password));
        }
        
        return result;
    }
    
    public String execute(String uuid, URI server, String username, String password) throws MalformedURLException
    {
        URL endPoint = server.resolve("webapi/getContent").toURL();
        
        HttpRequest request = HttpRequest
            .get(HttpRequest.append(endPoint.toString(), "uuid", uuid))
            .basic(username, password);
            
        return request.body();
    }
        
}
