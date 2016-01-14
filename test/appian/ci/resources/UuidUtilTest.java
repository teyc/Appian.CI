/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appian.ci.resources;

import appian.ci.core.UuidUtil;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Chui
 */
public class UuidUtilTest {
    
    @Test
    public void Test()
    {   
        UuidUtil util = new UuidUtil();
        Assert.assertNotNull(util.fromString("_g-0000dc11-edca-8000-f92f-7f0000014e7a_74"));
        Assert.assertNotNull(util.fromString("60388428-5429-4df4-a72b-3272ffa3ce8e"));
  
    }
    
}
