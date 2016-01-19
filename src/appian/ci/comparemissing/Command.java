package appian.ci.comparemissing;

import common.JunitReport;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class Command {

    private static final Logger logger = Logger.getLogger(appian.ci.listmissingprecedents.Command.class.getName());
    private static final Charset defaultCharset = Charset.forName("UTF-16");

        
    public void execute(Path source, Path target, String targetName) throws IOException {

        List<String> uuidReportSource = Files.readAllLines(source, defaultCharset);
        List<String> uuidReportTarget = Files.readAllLines(target, defaultCharset);
        List<AppianObject> missingObjects = execute(uuidReportSource, uuidReportTarget);

        JunitReport junitReport = new JunitReport().name(targetName);
        for (AppianObject missing : missingObjects) {

            String message = String.format("[%s] %s - %s ", targetName, missing.uuid, missing.description);
            junitReport.addError(missing.uuid, message);
        }

        junitReport.write(System.out);
    }

    public List<AppianObject> execute(List<String> uuidReportSource, List<String> uuidReportTarget) {

        Map<String, String> uuidToDescriptionSource = new HashMap<>();
        Map<String, String> uuidToDescriptionTarget = new HashMap<>();

        // reports are in this format
        // <uuid>:description
        for (String source : uuidReportSource) {

            logger.info(source);

            String[] temp = source.split(":", 2);

            if (temp.length == 2) {
                String uuid = temp[0];
                String description = temp[1];
                uuidToDescriptionSource.put(uuid, description);
            }
        }

        // reports are in this format
        // <uuid>:description
        for (String target : uuidReportTarget) {
            String[] temp = target.split(":", 2);
            if (temp.length == 2) {
                String uuid = temp[0];
                String description = temp[1];
                uuidToDescriptionTarget.put(uuid, description);
            }
        }

        return execute(uuidToDescriptionSource, uuidToDescriptionTarget);
    }

    private List<AppianObject> execute(Map<String, String> uuidToDescriptionSource, Map<String, String> uuidToDescriptionTarget) {

        List<AppianObject> missingObjects = new LinkedList<>();

        for (String uuid : uuidToDescriptionSource.keySet()) {
            final String sourceDescription = uuidToDescriptionSource.get(uuid);
            final String targetDescription = uuidToDescriptionTarget.get(uuid);
            if (!sourceDescription.equals(targetDescription)) {
                if (targetDescription == null || targetDescription.equals("Process Model Not Found")) {
                    missingObjects.add(new AppianObject(uuid, sourceDescription));
                }
            }
        }

        return missingObjects;
    }

    public class AppianObject {

        public AppianObject(String uuid, String description) {
            this.uuid = uuid;
            this.description = description;
        }

        public String uuid;
        public String description;
    }
}
