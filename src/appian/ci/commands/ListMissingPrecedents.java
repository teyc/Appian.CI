package appian.ci.commands;

import appian.ci.core.DirectoryWalker;
import appian.ci.core.UuidFinder;
import appian.ci.core.UuidUtil;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.SAXParser;
import org.xml.sax.SAXException;

public class ListMissingPrecedents {

    private String[] filenames;
    private final static Logger logger = Logger.getLogger(ListMissingPrecedents.class.getName());
    private final UuidUtil uuidUtil;
    private final SAXParser saxParser;
    
    public ListMissingPrecedents(UuidUtil uuidUtil, SAXParser saxParser)
    {
        this.uuidUtil = uuidUtil;
        this.saxParser = saxParser;
    }
    
    public List<String> execute(Path directory) throws IOException, Exception
    {
        Set<String> precedents = new HashSet<>(getPrecedents(directory));
        Set<String> present = getPresentUuids();
        precedents.removeAll(present);
        
        List<String> results = new ArrayList<>(); 
        results.addAll(precedents);
        
        return results;
    }
    
    private Set<String> getPrecedents(Path directory) throws IOException, SAXException
    {
        
        Set<String> precedents = new HashSet<>();
        filenames = new DirectoryWalker().listFileNames(directory);
        
        for (String filename : filenames)
        {
            precedents.addAll(getPrecedents(filename));
        }
        
        return precedents;
    }
    
    private Set<String> getPresentUuids() {
        
        if (filenames == null) throw new RuntimeException("getPrecedents must be called first");
        
        List<String> result = new ArrayList<>();
        
        for (String fullpath : filenames)
        {
            String[] parts = (new File(fullpath)).getName().split("\\\\", 0);
            String filename = parts[0];
            String basename = filename.split("\\.", 0)[0];
            
            if (uuidUtil.fromString(basename) != null)
            {
                logger.log(Level.INFO, "getPresentUuids {0}", basename);
                result.add(basename);
            }
            else
            {
                logger.log(Level.WARNING, "getPresentUUids - skipped {0}", basename);
            }
        }
        
        return new HashSet<>(result);
    }
    
    private List<String> getPrecedents(String fileName) throws SAXException, IOException
    {
        if (fileName.endsWith(".xml")) 
        {
            UuidFinder uuidFinder = new UuidFinder();       
            saxParser.parse(new File(fileName), uuidFinder);      
            return uuidFinder.getUuids();
        }
        else 
        {
            return new ArrayList<>();
        }
    }
    
    
}
