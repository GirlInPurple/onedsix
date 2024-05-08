package onedsix.loader;

import com.moandjiezana.toml.Toml;
import onedsix.core.util.Logger;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.zip.*;

import static onedsix.loader.ModCacher.cache;
import static onedsix.loader.ModMetadata.*;

class ModDiscovery {
    
    private static final Logger L = new Logger(ModDiscovery.class);
    
    public static final File MOD_DIRECTORY = new File("./mods/");
    
    public static void jarDiscovery() {
        File[] jarFiles = MOD_DIRECTORY.listFiles((dir, name) -> name.endsWith(".jar"));
        
        L.info("Beginning scanning jar files.");
        for (File jarFile : jarFiles) {
    
            String extractDirectory = "./temp/jar/" + new File(jarFile.getPath()).getName() + "/";
    
            URLClassLoader ucl = null;
            ZipFile jar = null;
    
            File toml = null;
            ModStartupListener msl = null;
            AsmPhaseListener apl = null;
            
            try {
                // Needed later
                File directory = new File(extractDirectory);
                ucl = new URLClassLoader(new URL[]{jarFile.toURI().toURL()});
                jar = new ZipFile(jarFile);
    
                cache(directory, extractDirectory, jarFile);
        
                // Find needed files in the extracted directory
                for (File file : directory.listFiles()) {
                    if (file.isFile() && file.getName().endsWith(".mod.toml")) {
                        toml = file;
                        break;
                    }
                }
                
                // Start on class processing
                Enumeration<? extends ZipEntry> entries = jar.entries();
                boolean hasErrored = false;
                while (entries.hasMoreElements()) {
                    try {
                        ZipEntry entry = entries.nextElement();
                        if (entry.getName().endsWith(".class")) {
                            L.info(entry.getName());
                            
                            // Convert entry to class
                            String className = entry.getName().replace('/', '.').replace(".class", "");
                            Class<?> loadedClass = ucl.loadClass(className);
                            
                            // Check class for specific entrypoints/usages
                            if (ModStartupListener.class.isAssignableFrom(loadedClass)) {
                                msl = (ModStartupListener) loadedClass.getDeclaredConstructor().newInstance();
                            }
                            if (AsmPhaseListener.class.isAssignableFrom(loadedClass)) {
                                apl = (AsmPhaseListener) loadedClass.getDeclaredConstructor().newInstance();
                            }
                        }
                        if (entry.getName().endsWith(".jar")) {
                            L.info("test!");
                        }
                    }
                    catch (NoClassDefFoundError ncdfe) {
                        if (!hasErrored) {
                            L.error("It seems this mod (" + jarFile.getName() + ") is for a different version,");
                            L.error("as it's trying to access code that doesn't exist.");
                            L.error("If you are a user, your best bet is to remove the mod and contact its creator.");
                            try {
                                if (toml != null) {
                                    String issuesUrl = new Toml().read(toml).getTable("mod").getString("issueTracker");
                                    if (issuesUrl != null) {
                                        L.error("Here is a link to the mod's issues page: " + issuesUrl);
                                    }
                                }
                            } catch (Exception ignored) {}
                            L.error("If you are a developer, check the newest API changes and ask around if you need help.");
                            L.error("The class in question is " + ncdfe.getMessage());
                            L.error("This message will not appear again.");
                            hasErrored = true;
                        }
                        L.error(ncdfe.getMessage(), ncdfe);
                    }
                    catch (Exception e) { // Catch-all for everything else, there's like 10 more after NCDFE
                        L.error(e.getMessage(), e);
                    }
                }
    
                // Register the mod
                if (toml != null) {
                    if (msl != null) {
                        if (apl != null) {
                            new JarMetadata(directory, toml, msl, apl);
                        } else {
                            new JarMetadata(directory, toml, msl);
                        }
                    }
                    else {
                        L.error("Could not find ModStartupListener inside "+jar.getName());
                        L.error("Assuming the mod is a non-running dependency.");
                        L.error("The mod will now disable.");
                    }
        
                }
                else {
                    L.error("Could not find *.mod.toml inside "+jar.getName());
                    L.error("The mod will now disable.");
                }
                
            }
            catch (Exception e) {
                L.error(e.getMessage(), e);
            }
            finally {
                if (ucl != null) {
                    try {
                        ucl.close();
                    }
                    catch (IOException ioe) {
                        L.error(ioe.getMessage(), ioe);
                    }
                }
                if (jar != null) {
                    try {
                        jar.close();
                    }
                    catch (IOException ioe) {
                        L.error(ioe.getMessage(), ioe);
                    }
                }
            }
        }

        L.info("Scanned "+jarFiles.length+" jar file(s)!");
    }
    
    public static void zipDiscovery() {
        File[] zipFiles = MOD_DIRECTORY.listFiles((dir, name) -> name.endsWith(".zip"));
        
        L.info("Beginning scanning zip files.");
        for (File zipFile : zipFiles) {
            String extractDirectory = "./temp/zip/" + new File(zipFile.getPath()).getName() + "/";
            
            try {
                File directory = new File(extractDirectory);
                
                cache(directory, extractDirectory, zipFile);
                
                // Find needed files in the extracted directory
                File mainFile = null;
                File tomlFile = null;
                HashMap<Class<? extends EventListener>, File> eventFiles = new HashMap<>();
                
                for (File file : directory.listFiles()) {
                    if (file.isFile() && file.getName().equals("main.js")) {
                        mainFile = file;
                    }
                    if (file.isFile() && file.getName().equals("asm.js")) {
                        eventFiles.put(AsmPhaseListener.class, file);
                    }
                    if (file.isFile() && file.getName().equals("settings.js")) {
                        eventFiles.put(SettingsChangeListener.class, file);
                    }
                    if (file.isFile() && file.getName().endsWith(".mod.toml")) {
                        tomlFile = file;
                    }
                    if (tomlFile != null && mainFile != null) break;
                }
                
                if (tomlFile != null) {
                    if (mainFile != null) {
                        if (!eventFiles.isEmpty()) {
                            new ZipMetadata(directory, tomlFile, mainFile, eventFiles);
                        } else {
                            new ZipMetadata(directory, tomlFile, mainFile);
                        }
                    } else {
                        L.error("Could not find main.js inside "+extractDirectory);
                        L.error("Assuming the mod is a non-running dependency.");
                        L.error("The mod will now disable.");
                    }
                    
                } else {
                    L.error("Could not find *.mod.toml inside "+extractDirectory);
                    L.error("The mod will now disable.");
                }
                
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        L.info("Scanned "+zipFiles.length+" zip file(s)!");
    }
}
