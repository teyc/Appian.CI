/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appian.ci.resources;

import appian.ci.core.EndpointResolver;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Chui
 */
public class EndpointResolverTest {
 
    @Test
    public void ResolveUriTest() throws URISyntaxException, MalformedURLException
    {
        String serverUrl = "https://somserver.appiancloud.com/suite";
        testResolve(serverUrl);
    }

    @Test
    public void ResolveUriTest2() throws URISyntaxException, MalformedURLException
    {
        String serverUrl = "https://somserver.appiancloud.com/suite/";
        testResolve(serverUrl);
    }
    
    private void testResolve(String serverUrl) throws MalformedURLException {
        URL endPoint = EndpointResolver.resolve(new URL(serverUrl), "webapi/getContent");
        Assert.assertEquals("/suite/webapi/getContent", endPoint.getPath());
    }
    
}
