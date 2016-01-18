package appian.ci.test;

import appian.ci.core.DirectoryWalker;
import java.io.IOException;
import static java.lang.System.out;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import javax.xml.parsers.ParserConfigurationException;
import junit.framework.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;

public class DirectoryWalkerTest {
 
    @Test
    public void listAllFiles() throws IOException, ParserConfigurationException, SAXException {
        DirectoryWalker list = new DirectoryWalker();
        Path currentDirectory = FileSystems.getDefault().getPath("..\\");
        String[] files = list.listFileNames(currentDirectory);
        
        for (String file : files)
        {
            out.println("::" + file + "--");
            Assert.assertTrue(file.length() > 0);
        }
        Assert.assertTrue(files.length > 0);
    }
}
