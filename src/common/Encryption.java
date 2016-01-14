package common;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.xml.bind.DatatypeConverter;

public class Encryption {

    final String algorithm = "PBEWithMD5AndDES";

    public String encrypt(String clearText, String encryptionKey) {

        try {
            Cipher pbeCipher = getCipher(algorithm, encryptionKey, Cipher.ENCRYPT_MODE);
            byte[] cipherText = pbeCipher.doFinal(clearText.getBytes());
            return DatatypeConverter.printBase64Binary(cipherText);
        } catch (IllegalBlockSizeException | BadPaddingException ex) {
            throw new RuntimeException(ex);
        }

    }

    public String decrypt(String cipherTextBase64, String encryptionKey) {

        try {
            byte[] cipherText = DatatypeConverter.parseBase64Binary(cipherTextBase64);
            Cipher pbeCipher = getCipher(algorithm, encryptionKey, Cipher.DECRYPT_MODE);

            byte[] clearText = pbeCipher.doFinal(cipherText);
            return new String(clearText);

        } catch (IllegalBlockSizeException | BadPaddingException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Cipher getCipher(final String algorithm, String encryptionKey, int encryptMode) {

        try {
            Cipher pbeCipher = Cipher.getInstance(algorithm);
            char[] encryptionKeyChars = encryptionKey.toCharArray();
            byte[] salt = "SomeS@lt".getBytes();
            int count = 20;
            PBEParameterSpec pbeParamSpec = new PBEParameterSpec(salt, count);
            PBEKeySpec pbeKeySpec = new PBEKeySpec(encryptionKeyChars);
            SecretKeyFactory keyFac;
            keyFac = SecretKeyFactory.getInstance(algorithm);
            SecretKey pbeKey = keyFac.generateSecret(pbeKeySpec);
            pbeCipher.init(encryptMode, pbeKey, pbeParamSpec);
            return pbeCipher;
        } catch (InvalidKeySpecException | InvalidKeyException |
            InvalidAlgorithmParameterException |
            NoSuchAlgorithmException | NoSuchPaddingException ex) {
            Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
    }

}
