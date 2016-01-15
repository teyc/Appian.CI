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
            
            if (options == null) {
                System.err.println("I don't understand '" + command + "' command");
            }
            
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
                    
                default:
                    
                    System.err.println("I don't understand '" + command + "' command");
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

        for (Class klass : new Class[] { 
                ListMissingPrecedents.class, 
                QueryNameByUuid.class,
                GetLogFile.class })
        {
            final String simpleName = klass.getSimpleName();
            
            if (command == null || simpleName.equals(command)) {
                formatter.printHelp(76, "./Appian.CI " + simpleName, header, getOptions(simpleName), footer);
            }
        }
        
    }
}
