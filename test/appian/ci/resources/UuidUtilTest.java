
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
    public void parsedUuids()
    {   
        Assert.assertEquals(
            "0000dc11-edca-8000-f92f-7f0000014e7a", 
            util.fromString("_g-0000dc11-edca-8000-f92f-7f0000014e7a_74").toString());
    }

    @Test
    public void uuidsAreDetected()
    {   
        Assert.assertNotNull(util.fromString("_g-0000dc11-edca-8000-f92f-7f0000014e7a_74"));
        Assert.assertNotNull(util.fromString("60388428-5429-4df4-a72b-3272ffa3ce8e"));
        
        Assert.assertNull(util.fromString("H0388428-5429-4df4-a72b-3272ffa3ce8e"));
    }
    
}
