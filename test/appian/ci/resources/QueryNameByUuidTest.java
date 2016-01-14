package appian.ci.resources;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;
import org.junit.Assert;
import org.junit.Test;

public class QueryNameByUuidTest {

    @Test
    public void failsWhenUuidsOrUuidsFileMissing() {

        String[] arguments = new String[]{
            "-username", "quux",
            "-password", "bar",
            "-url", "https://some.appiancloud.com/suite",};

        try {
        
            assertParsesWithNoExceptions(arguments);
            Assert.fail();
    
        } catch (ParseException ex) {
            
            // 
        }

    }

    @Test
    public void parseCommandLineWithUuids() throws ParseException {

        String[] arguments = new String[]{
            "-username", "quux",
            "-password", "bar",
            "-url", "https://some.appiancloud.com/suite",
            "-uuids", "_e-0001dc11-f23b-8000-9aee-01075c01075c_199,_g-0000dc11-edca-8000-f92f-7f0000014e7a_74"
        };

        assertParsesWithNoExceptions(arguments);
    }

    @Test
    public void parseCommandLineWithUuidFile() throws ParseException {

        String[] arguments = new String[]{
            "-username", "quux",
            "-password", "bar",
            "-url", "https://some.appiancloud.com/suite",
            "-uuidsFile", "C:\\dev\\testDir"
        };

        assertParsesWithNoExceptions(arguments);
    }

    private void assertParsesWithNoExceptions(String[] arguments) throws ParseException {
        CommandLine parsed = new DefaultParser().parse(
            new appian.ci.options.QueryNameByUuid().getOptions(),
            arguments);
    }
}
