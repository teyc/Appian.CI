package appian.ci.test;

import common.JunitReport;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;

public class JUnitReportTest {
 
    @Test
    public void createJunitErrorReport() throws IOException, ParserConfigurationException, SAXException
    {
        JunitReport report = new JunitReport();
        report.addError("0002dc47-c97e-8000-f92f-7f0000014e7a", "Test environment > 0002dc47-c97e-8000-f92f-7f0000014e7a [Name: Assessment demo, Id: 57589]");
        
        try (ByteArrayOutputStream out = new ByteArrayOutputStream())
        {
            report.write(out);
            
            try (InputStream in = new ByteArrayInputStream(out.toByteArray()))
            {
                DocumentBuilder domBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();            
                domBuilder.parse(in);
                Assert.assertTrue("Should parse successfully", true);
            }
            
            System.out.println(out.toString());
        }
    }
}
