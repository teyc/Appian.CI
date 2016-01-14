package appian.ci.core;

import appian.ci.commands.QueryNameByUuid;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import static java.nio.file.FileVisitResult.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DirectoryWalker {
    
    private static final Logger logger = Logger.getLogger(QueryNameByUuid.class.getName());
    
    public String[] listFileNames(Path directory) throws IOException
    {
        final List<String> paths = new LinkedList<>();
        
        Files.walkFileTree(directory, new SimpleFileVisitor() {

            @Override
            public FileVisitResult preVisitDirectory(Object directory, BasicFileAttributes bfa) throws IOException {
                
                String pathName = ((Path) directory).toString();
                FileVisitResult result = pathName.endsWith(".git") ? SKIP_SUBTREE : CONTINUE;
                if (result == SKIP_SUBTREE)
                {
                    logger.log(Level.INFO, "Skipping {0}", pathName);
                }
                return result;
            }

            
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
}
