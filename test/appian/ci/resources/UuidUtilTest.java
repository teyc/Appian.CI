
package appian.ci.resources;

import appian.ci.core.UuidUtil;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Chui
 */
public class UuidUtilTest {
    
    static UuidUtil util;
    
    @BeforeClass
    public static void beforeClass()
    {
        util = new UuidUtil();
    }
    
    @Test
    public void processModelFoldersAreNotUuids()
    {   
        /* This is an Appian bug */
        Assert.assertNull(
            util.fromString("_g-0000dc11-edca-8000-f92f-7f0000014e7a_74"));
    }

    @Test
    public void applicationsAreUuids()
    {   
        Assert.assertEquals("_a-0000dc11-edca-8000-f92f-7f0000014e7a",
            util.fromString("_a-0000dc11-edca-8000-f92f-7f0000014e7a"));
    }

    @Test
    public void nonHexNumbersAreNotUuids()
    {
        Assert.assertNull(util.fromString("H0388428-5429-4df4-a72b-3272ffa3ce8e"));
      
    }

    @Test
    public void uuidsAreDetected()
    {   
        Assert.assertEquals("60388428-5429-4df4-a72b-3272ffa3ce8e", util.fromString("60388428-5429-4df4-a72b-3272ffa3ce8e"));        
    }
    
}
