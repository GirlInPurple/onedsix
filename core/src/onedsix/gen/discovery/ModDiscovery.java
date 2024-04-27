package onedsix.gen.discovery;

import onedsix.util.Logger;

import java.io.File;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ModDiscovery {
    
    private static final Logger L = new Logger(ModDiscovery.class);
    
    public static void startupModDiscovery() {
        
        File jarDir = new File("path/to/your/jar/directory");
        File[] jarFiles = jarDir.listFiles((dir, name) -> name.endsWith(".jar"));
        Class[] checkClasses = {};
        
        if (jarFiles != null) {
            // Iterate over JAR files
            for (File jarFile : jarFiles) {
                // Create a JarFile object
                try (JarFile jar = new JarFile(jarFile)) {
                    // Iterate over entries in the JAR file
                    Enumeration<JarEntry> entries = jar.entries();
                    while (entries.hasMoreElements()) {
                        JarEntry entry = entries.nextElement();
                        // Check if the entry is a class file
                        if (entry.getName().endsWith(".class")) {
                            // Convert entry name to class name
                            String className = entry.getName().replace('/', '.').replace(".class", "");
                            // Load class dynamically
                            Class<?> loadedClass = Class.forName(className);
                            // Check if the loaded class implements OnStartup interface
                            /*
                            if (OnStartup.class.isAssignableFrom(loadedClass)) {
                                // Instantiate the class
                                OnStartup instance = (OnStartup) loadedClass.newInstance();
                                // Invoke the OnStartup method
                                instance.onStartup();
                            }
                            
                             */
                        } else
                        if (entry.getName().endsWith(".mod.json")) {
                        
                        } else
                        if (entry.getName().endsWith(".cell.json")) {
                        
                        }
                    }
                } catch (Exception e) {
                    L.error(e.getMessage(), e);
                }
            }
            L.info("Scanned "+jarFiles.length+" jar files!");
        }
        else {
            L.info("No jar files found!");
        }
    }
}
