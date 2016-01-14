package appian.ci.applications;

import appian.ci.core.UuidUtil;
import common.Encryption;
import common.HttpRequest;
import common.SuggestEncrypt;
import java.io.File;
import static java.lang.System.out;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.CommandLine;

public class QueryNameByUuid
    implements IApplication {

    @Override
    public void execute(CommandLine commandLine) {
        try {
            UuidUtil uuidUtil2 = new UuidUtil();
            appian.ci.commands.QueryNameByUuid queryCommand = new appian.ci.commands.QueryNameByUuid(uuidUtil2);
            String uuids = commandLine.getOptionValue(appian.ci.options.QueryNameByUuid.UUIDS);
            String uuidsFile = commandLine.getOptionValue(appian.ci.options.QueryNameByUuid.UUIDSFILE);
            URL url = new URL(commandLine.getOptionValue(appian.ci.options.QueryNameByUuid.URL));
            String password = commandLine.getOptionValue(appian.ci.options.QueryNameByUuid.PASSWORD);
            String decryptionKey = commandLine.getOptionValue(appian.ci.options.QueryNameByUuid.KEY);

            if (decryptionKey != null) {
                password = new Encryption().decrypt(password, decryptionKey);
            } else {
                SuggestEncrypt.recommendCommandLineOption(System.out, password);
            }

            try {

                Iterable<appian.ci.commands.QueryNameByUuid.Result> values;

                values = uuidsFile != null
                    ? queryCommand.executeWithFile(
                        new File(uuidsFile),
                        url,
                        commandLine.getOptionValue(appian.ci.options.QueryNameByUuid.USERNAME), password)
                    : queryCommand.execute(Arrays.asList(
                            uuids.split(",")),
                        url,
                        commandLine.getOptionValue(appian.ci.options.QueryNameByUuid.USERNAME), password);

                for (appian.ci.commands.QueryNameByUuid.Result value : values) {
                    out.printf("%s:%s\n", value.fixedUuid, value.response);
                }

            } catch (HttpRequest.HttpRequestException httpRequestException) {
                System.err.printf("Could not connect. Have you tried \n java -Dhttps.proxyHost=.... -Dhttps.proxyPort=... -Dhttps.proxyUser=... -Dhttps.proxyPassword=...");
                throw httpRequestException;
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(QueryNameByUuid.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
