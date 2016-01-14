package appian.ci.core;

import java.net.MalformedURLException;
import java.net.URL;

public class EndpointResolver {

    public static URL resolve(URL server, String path) {
        try {

            if (server.getPath().endsWith("/suite")) {

                server = new URL(server.toString() + "/");
                return new URL(server, path);

            } else if (server.getPath().endsWith("/suite/")) {

                return new URL(server, path);

            } else {

                throw new RuntimeException("Appian url must end with /suite");
            }

        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
