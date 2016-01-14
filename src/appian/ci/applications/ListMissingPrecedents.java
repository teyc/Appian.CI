package appian.ci.applications;

import appian.ci.core.UuidUtil;
import static java.lang.System.out;
import java.nio.file.FileSystems;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.commons.cli.CommandLine;

public class ListMissingPrecedents
    implements IApplication {

    @Override
    public void execute(CommandLine commandLine) {

        try {
            UuidUtil uuidUtil = new UuidUtil();
            SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
            appian.ci.commands.ListMissingPrecedents listCommand
                = new appian.ci.commands.ListMissingPrecedents(uuidUtil, saxParser);
            List<String> missingPrecedents = listCommand.execute(
                FileSystems.getDefault().getPath(commandLine.getOptionValue(appian.ci.options.ListMissingPrecedents.DIRECTORY)));
            for (String precedent : missingPrecedents) {
                out.println(precedent);
            }
        } catch (Exception ex) {
            Logger.getLogger(ListMissingPrecedents.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
    }

}
