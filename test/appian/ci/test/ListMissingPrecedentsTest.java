package appian.ci.test;

import appian.ci.listmissingprecedents.Command;
import appian.ci.core.UuidFinder;
import appian.ci.core.UuidUtil;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import junit.framework.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 *
 * @author Chui
 */
public class ListMissingPrecedentsTest {

    String TESTDIR = "C:\\dev\\toyapps\\Appian.Tools\\resources\\application_files";
    
    @Test
    public void findMissingPrecedents() throws Exception 
    {
        Command lister = new Command(
            new UuidUtil(),
            getSaxParser());
        
        List<String> missingPrecedents = lister.execute(Paths.get(TESTDIR));
        
        for (String missingUuid : missingPrecedents )
        {
            System.out.println("Missing " + missingUuid);
        }
     
        Assert.assertTrue(missingPrecedents.size() > 0);
    }

    @Test
    public void findAllUuids() {
        
        String resourceFileName = "appian/ci/resources/dc36.xml";
        
        List<String> uuidsFound = findAllUuidsFromResource(resourceFileName, "processModel\\");
        Assert.assertEquals(3, uuidsFound.size());

    }

    @Test
    public void processModelUuidsShouldBeExcluded()
    {
            
        String resourceFileName = "appian/ci/resources/0002dc47-c97e-8000-f92f-7f0000014e7a.xml";
        
        List<String> uuidsFound = findAllUuidsFromResource(resourceFileName, "processModel\\");
        Assert.assertEquals(3, uuidsFound.size());
    }
    
    private List<String> findAllUuidsFromResource(String resourceFileName, String apparentDirectory) 
         {
        
        try {
            SAXParser saxParser = getSaxParser();
            InputStream inputStream = ListMissingPrecedentsTest.class
                .getClassLoader()
                .getResourceAsStream(resourceFileName);
            UuidFinder uuidFinder = new UuidFinder(apparentDirectory + resourceFileName);
            saxParser.parse(inputStream, uuidFinder);
            List<String> uuidsFound = uuidFinder.getUuids();
            return uuidsFound;
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    
    
    private SAXParser getSaxParser() throws ParserConfigurationException, SAXException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        return saxParser;
    }

}
