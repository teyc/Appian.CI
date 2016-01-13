package appian.ci.resources;

import appian.ci.core.ListMissingPrecedents;
import appian.ci.core.UuidFinder;
import appian.ci.core.UuidUtil;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
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
        ListMissingPrecedents lister = new ListMissingPrecedents(
            new UuidUtil(),
            getSaxParser());
        
        lister.execute(Paths.get(TESTDIR));
    }

    @Test
    public void findAllUuids() throws ParserConfigurationException, SAXException, IOException {
        
        SAXParser saxParser = getSaxParser();

        InputStream inputStream = ListMissingPrecedentsTest.class
            .getClassLoader()
            .getResourceAsStream("appian/ci/resources/dc36.xml");

        UuidFinder uuidFinder = new UuidFinder();
        saxParser.parse(inputStream, uuidFinder);
        
        Assert.assertTrue(uuidFinder.getUuids().size() > 0);

    }

    private SAXParser getSaxParser() throws ParserConfigurationException, SAXException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        return saxParser;
    }

}
