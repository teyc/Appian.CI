package appian.ci.core;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import static java.nio.file.FileVisitResult.CONTINUE;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;

public class DirectoryWalker {
    public String[] listFileNames(Path directory) throws IOException
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
}
