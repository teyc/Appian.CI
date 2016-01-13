package appian.ci.resources;

import appian.ci.core.ListMissingPrecedents;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.System.out;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import junit.framework.Assert;
import org.junit.Test;
import org.xml.sax.AttributeList;
import org.xml.sax.HandlerBase;
import org.xml.sax.SAXException;

/**
 *
 * @author Chui
 */
public class ListMissingPrecedentsTest {

    @Test
    public void listAllFiles() throws IOException 
    {
        ListMissingPrecedents list = new ListMissingPrecedents();
        Path currentDirectory = FileSystems.getDefault().getPath("");
        String[] files = list.execute(currentDirectory);
        for (String file : files)
        {
            out.println(file);
        }
        Assert.assertTrue(files.length > 0);
    }
    
    @Test
    public void findAllUuids() throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();

        InputStream inputStream = ListMissingPrecedentsTest.class
            .getClassLoader()
            .getResourceAsStream("appian/ci/resources/dc36.xml");

        saxParser.parse(inputStream, new HandlerBase() {

            boolean isUuidNode;
            String textNodeAccumulator;

            @Override
            public void startElement(String nodeName, AttributeList al) throws SAXException {
                if (nodeName.matches(".*(?i)(Uuid)")) {
                    out.println(nodeName);
                    isUuidNode = true;
                    textNodeAccumulator = "";
                }
            }

            public void characters(char[] chars, int start, int length) throws SAXException {
                if (isUuidNode) {
                    String contents = new String(chars, start, length);
                    textNodeAccumulator += contents;
                }
            }

            public void endElement(String string) throws SAXException {
                if (isUuidNode) {
                    out.println(textNodeAccumulator);
                    isUuidNode = false;
                }
            }
        });

    }
}
