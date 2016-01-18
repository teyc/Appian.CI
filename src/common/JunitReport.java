package common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.List;

public class JunitReport {

    List<String> errors = new LinkedList<>();
    private String name = "";
    
    public void addError(String message)
    {
        errors.add(message);
    }
    
    public void write(OutputStream stream) throws IOException
    {
        try (
            OutputStreamWriter outStreamWriter = new OutputStreamWriter(stream);
            BufferedWriter writer = new BufferedWriter(outStreamWriter))
        {
            writer.write("<?xml version\"1.0\" ?>\n");
            writer.write(String.format("<testsuite name=\"%s\" errors=\"%d\" failures=\"0\" tests=\"%d\" time=\"0.000\" >\n",
                quote(name),
                errors.size(),
                errors.size()
            ));
            
            for (String error: errors)
            {
                writer.write("\t<testcase name=\"error\">\n");
                writer.write("\t\t<error>" + quote(error) + "</error>\n");
                writer.write("\t</testcase>\n");
            }
            
            writer.write("</testsuite>");
            writer.newLine();
        }
    }
    
    public void write(File filename) throws IOException
    {
        write(new FileOutputStream(filename));
    }
    
    private String quote(String s)
    {
        return s.replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }

    public JunitReport name(String name) {
        this.name = name;
        return this;
    }
}
