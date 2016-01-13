package appian.ci.resources;

import appian.ci.core.UuidFinder;
import java.io.IOException;
import java.io.InputStream;
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

    

    @Test
    public void findAllUuids() throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();

        InputStream inputStream = ListMissingPrecedentsTest.class
            .getClassLoader()
            .getResourceAsStream("appian/ci/resources/dc36.xml");

        UuidFinder uuidFinder = new UuidFinder();
        saxParser.parse(inputStream, uuidFinder);
        
        Assert.assertTrue(uuidFinder.getUuids().size() > 0);

    }

}
