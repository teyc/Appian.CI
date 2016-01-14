package common;

import java.io.PrintStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.bind.DatatypeConverter;

public class SuggestEncrypt {

    public static void recommendCommandLineOption(PrintStream out, String password) {
        String encryptionKey = suggestKey(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        String encryptedPassword = new Encryption().encrypt(password, encryptionKey);
        String message = String.format(
            "You should use -key %s -password %s to mask your password.", 
            encryptionKey, encryptedPassword);
        out.println(message);
    }

    public static String suggestKey(String seed) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(seed.getBytes());
            return DatatypeConverter.printBase64Binary(md.digest()).substring(3);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }
}
