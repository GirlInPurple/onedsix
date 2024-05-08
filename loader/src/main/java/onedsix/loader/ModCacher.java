package onedsix.loader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

class ModCacher {
    
    static void cache(File directory, String extractDirectory, File targetZip) throws IOException {
        if (!directory.exists()) {
            directory.mkdirs();
        
            ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(Paths.get(targetZip.getPath())));
        
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
            
                String entryName = entry.getName();
                File outputFile = new File(extractDirectory + entryName);
            
                if (entryName.endsWith("/")) {
                    // If it's a directory, create it instead of writing bytes
                    outputFile.mkdir();
                }
                else {
                    // Write the entry to the output file
                    FileOutputStream outputStream = new FileOutputStream(outputFile);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = zipInputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, length);
                    }
                    outputStream.close();
                }
            }
        }
    }
    
}
