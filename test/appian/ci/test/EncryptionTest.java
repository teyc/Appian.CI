package appian.ci.test;

import common.Encryption;
import junit.framework.Assert;
import org.junit.Test;

public class EncryptionTest {
    
    @Test
    public void Encrypt()
    {
        Encryption crypt = new Encryption();
        String clearText = "secret password";
        final String encryptionKey = "machinekey";
        String cipherText = crypt.encrypt(clearText, encryptionKey);
        String clearText2 = crypt.decrypt(cipherText, encryptionKey);
        Assert.assertEquals(clearText, clearText2);
    }
}
