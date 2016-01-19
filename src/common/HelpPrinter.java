package common;

import appian.ci.AppianCI;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HelpPrinter {

    public void printHelp(String command, OutputStream outputStream) {
        String path = String.format("appian/ci/%s/README.md", command.toLowerCase());
        try (InputStream inputStream = AppianCI.class.getClassLoader().getResourceAsStream(path)) {
            byte[] buffer = new byte[10 * 1024];

            for (int length; (length = inputStream.read(buffer)) != -1;) {

                outputStream.write(buffer, 0, length);
                outputStream.flush();
                
            }
        } catch (IOException ex) {
            try {
                outputStream.write(("No README found at " + path).getBytes());
            } catch (IOException ex1) {
                Logger.getLogger(HelpPrinter.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
}
