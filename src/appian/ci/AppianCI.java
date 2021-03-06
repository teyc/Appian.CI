package appian.ci;

import common.IOptions;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.*;

public class AppianCI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String command = getCommand(args);
        Options options = getOptions(command);

        if (command == null || options == null) {
            showHelp(command);

            if (options == null && command != null) {
                System.err.println("I don't understand '" + command + "' command");
                showHelp(null);
            }

            System.exit(-21);
        }

        try {

            CommandLine commandLine = parseCommandLine(options, args);

            switch (command) {
                
                case "CompareMissing":
                    new appian.ci.comparemissing.Application().execute(commandLine);
                    break;
                    
                case "ListMissingPrecedents":

                    new appian.ci.listmissingprecedents.Application().execute(commandLine);
                    break;

                case "QueryNameByUuid":

                    new appian.ci.querynamebyuuid.Application().execute(commandLine);
                    break;

                case "GetLogFile":

                    new appian.ci.getlogfile.Application().execute(commandLine);
                    break;

                default:

                    System.err.println("I don't understand the '" + command + "' command");
                    
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

    private static String getCommand(String[] args) {
        String command = args.length > 0 ? args[0] : null;
        command = (command !=null && !command.startsWith("-")) ? command : null;
        return command;
    }

    private static Class[] getOptionTypes() {
        return new Class[]{
            appian.ci.comparemissing.CommandlineOptions.class,
            appian.ci.getlogfile.CommandlineOptions.class,
            appian.ci.querynamebyuuid.CommandlineOptions.class,
            appian.ci.listmissingprecedents.CommandlineOptions.class
        };
    }

    private static String getCommandName(Class klass) {
        
        try {
            return ((IOptions) klass.newInstance()).getCommand();
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
        
    }

    private static CommandLine parseCommandLine(Options options, String[] args) throws ParseException {
        
        Options helpOptions = new Options().addOption("help", "Help");
        
        CommandLineParser parser = new DefaultParser();
        CommandLine commandLine;
        
        commandLine = parser.parse(helpOptions, args);
        if (commandLine.hasOption("help")) return commandLine;
        
        commandLine = parser.parse(options, args);
        return commandLine;
    }

    public static Options getOptions(String command) {

        for (Class klass : getOptionTypes()) {
            if (getCommandName(klass).equalsIgnoreCase(command)) {
                try {
                    return ((IOptions) klass.newInstance()).getOptions();
                } catch (InstantiationException | IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        return null;

    }

    private static void showHelp(String command) {
        String header = "";
        String footer = "-------------------------------------------------------------";
        HelpFormatter formatter = new HelpFormatter();

        for (Class klass : getOptionTypes()) {
            
            final String simpleName = getCommandName(klass);

            if (command == null) {
                formatter.printHelp(76, "./Appian.CI " + simpleName, header, getOptions(simpleName), footer);
            }

            if (simpleName.equalsIgnoreCase(command)) {
                formatter.printHelp(76, "./Appian.CI " + simpleName, header, getOptions(command), footer);
            }
        }

    }
}
