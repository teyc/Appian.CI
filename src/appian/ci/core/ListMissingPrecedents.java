package appian.ci.core;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import static java.nio.file.FileVisitResult.CONTINUE;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

public class ListMissingPrecedents {

    private final SAXParser saxParser;
    
    public ListMissingPrecedents() throws ParserConfigurationException, SAXException
    {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        saxParser = saxParserFactory.newSAXParser();
    }
    
    public String[] execute(Path directory) throws IOException
    {
        final List<String> paths = new LinkedList<>();
        
        Files.walkFileTree(directory, new SimpleFileVisitor() {

            @Override
            public FileVisitResult visitFile(Object file, BasicFileAttributes bfa) throws IOException {
                
                String filename = ((Path) file).toFile().getPath();
                paths.add(filename);
                return CONTINUE;
            }
            
        });
        
        String[] result = new String[paths.size()];
        paths.toArray(result);
        return result;
    }
    
    public String[] getPrecedents(String fileName) throws SAXException, IOException
    {
        UuidFinder uuidFinder = new UuidFinder();       
        saxParser.parse(new File(fileName), uuidFinder);
        
        List<String> uuids = uuidFinder.getUuids();
        String[] results = new String[uuids.size()];
        uuidFinder.getUuids().toArray(results);
        return results;
    }
    
    
}
