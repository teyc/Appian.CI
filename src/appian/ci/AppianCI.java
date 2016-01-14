package appian.ci;

import appian.ci.core.UuidUtil;
import appian.ci.options.ListMissingPrecedents;
import appian.ci.options.QueryNameByUuid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.xml.sax.SAXException;

public class AppianCI {

    /**
     * @param args the command line arguments
     */ 
    public static void main(String[] args) {

        String command = args.length > 0 ? args[0] : "";
        Options options = getOptions(command);

        if (options == null) {
            showHelp(command);
        }

        try {

            CommandLine commandLine = parseCommandLine(options, args);

            switch (command) {
                case "ListMissingPrecedents":

                    UuidUtil uuidUtil = new UuidUtil();
                    SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
                    appian.ci.commands.ListMissingPrecedents listCommand
                        = new appian.ci.commands.ListMissingPrecedents(uuidUtil, saxParser);
                    listCommand.execute(
                        FileSystems.getDefault().getPath(commandLine.getOptionValue(ListMissingPrecedents.DIRECTORY)));
                    break;

                case "QueryNameByUuid":

                    appian.ci.commands.QueryNameByUuid queryCommand = new appian.ci.commands.QueryNameByUuid();
                    String uuids = commandLine.getOptionValue(QueryNameByUuid.UUIDS);
                    URI url = new URI(commandLine.getOptionValue(QueryNameByUuid.URL));
                    queryCommand.execute(
                        Arrays.asList(uuids.split(",")),
                        url,
                        commandLine.getOptionValue(QueryNameByUuid.USERNAME),
                        commandLine.getOptionValue(QueryNameByUuid.PASSWORD));

                    break;
            }

        } catch (MissingOptionException ex) {

            System.err.println(ex.getMessage());
            showHelp(command);
            System.exit(-1);

        } catch (MalformedURLException | ParserConfigurationException | ParseException | SAXException | URISyntaxException ex) {

            Logger.getLogger(AppianCI.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-2);

        } catch (IOException ex) {

            Logger.getLogger(AppianCI.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-3);

        } catch (Exception ex) {

            Logger.getLogger(AppianCI.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-4);

        }
        
    }

    private static CommandLine parseCommandLine(Options options, String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        CommandLine commandLine = parser.parse(options, args);
        return commandLine;
    }

    public static Options getOptions(String command) {

        if (ListMissingPrecedents.class.getSimpleName().equalsIgnoreCase(command)) {
            return new ListMissingPrecedents().getOptions();
        }

        if (QueryNameByUuid.class.getSimpleName().equalsIgnoreCase(command)) {
            return new QueryNameByUuid().getOptions();
        }

        return null;

    }

    private static void showHelp(String command) {
        String header = "";
        String footer = "";
        HelpFormatter formatter = new HelpFormatter();

        if (command == null || ListMissingPrecedents.class.getSimpleName().equals(command)) {
            formatter.printHelp(76, "./Appian.CI " + ListMissingPrecedents.class.getSimpleName(), header, getOptions(ListMissingPrecedents.class.getSimpleName()), footer);
        }

        if (command == null || QueryNameByUuid.class.getSimpleName().equals(command)) {
            formatter.printHelp(76, "./Appian.CI " + QueryNameByUuid.class.getSimpleName(), header, getOptions(QueryNameByUuid.class.getSimpleName()), footer);
        }

    }
}
