package appian.ci.test;

import common.JunitReport;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.junit.Test;

public class JUnitReportTest {
 
    @Test
    public void createJunitErrorReport() throws IOException
    {
        JunitReport report = new JunitReport();
        report.addError("Test environment 0002dc47-c97e-8000-f92f-7f0000014e7a [Name: Assessment demo, Id: 57589]");
        
        try (ByteArrayOutputStream out = new ByteArrayOutputStream())
        {
            report.write(out);
            
            System.out.println(out.toString());
        }
    }
}
