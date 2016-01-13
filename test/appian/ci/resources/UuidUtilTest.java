/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appian.ci.resources;

import appian.ci.core.UuidUtil;
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
    }
}
