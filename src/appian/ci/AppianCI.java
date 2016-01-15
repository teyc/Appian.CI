package appian.ci;

import appian.ci.options.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.*;

public class AppianCI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String command = args.length > 0 ? args[0] : null;
        Options options = getOptions(command);

        if (command == null || options == null) {
            showHelp(command);
            System.exit(-21);
        }

        try {

            CommandLine commandLine = parseCommandLine(options, args);

            switch (command) {
                case "ListMissingPrecedents":

                    new appian.ci.applications.ListMissingPrecedents().execute(commandLine);
                    break;

                case "QueryNameByUuid":

                    new appian.ci.applications.QueryNameByUuid().execute(commandLine);
                    break;
                    
                case "GetLogFile":
                    
                    new appian.ci.applications.GetLogFile().execute(commandLine);
                    break;
            }

        } catch (MissingOptionException ex) {

            System.err.println(ex.getMessage());
            showHelp(command);
            System.exit(-1);

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

        if (GetLogFile.class.getSimpleName().equalsIgnoreCase(command)) {
            return new GetLogFile().getOptions();
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

        if (command == null || GetLogFile.class.getSimpleName().equals(command)) {
            formatter.printHelp(76, "./Appian.CI " + GetLogFile.class.getSimpleName(), header, getOptions(GetLogFile.class.getSimpleName()), footer);
        }
    }
}
