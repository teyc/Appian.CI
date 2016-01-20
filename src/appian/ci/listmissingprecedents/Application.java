package appian.ci.listmissingprecedents;

import common.IApplication;
import appian.ci.core.UuidUtil;
import common.HelpPrinter;
import static java.lang.System.out;
import java.nio.file.FileSystems;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.commons.cli.CommandLine;

public class Application
    implements IApplication {

    @Override
    public void execute(CommandLine commandLine) {

        if (commandLine.hasOption(CommandlineOptions.HELP)) {
            showHelp();
            return;
        }
        
        try {
            UuidUtil uuidUtil = new UuidUtil();
            SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
            appian.ci.listmissingprecedents.Command listCommand
                = new appian.ci.listmissingprecedents.Command(uuidUtil, saxParser);
            List<String> missingPrecedents = listCommand.execute(
                FileSystems.getDefault().getPath(commandLine.getOptionValue(appian.ci.listmissingprecedents.CommandlineOptions.DIRECTORY)));
            for (String precedent : missingPrecedents) {
                out.println(precedent);
            }
        } catch (Exception ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void showHelp() {
        
        final CommandlineOptions cmdline = new CommandlineOptions();
        
        new HelpPrinter().printHelp(cmdline.getCommand(), cmdline.getOptions(), System.out);
        
    }
}
