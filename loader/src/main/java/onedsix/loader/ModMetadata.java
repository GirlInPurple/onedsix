package onedsix.loader;

import com.moandjiezana.toml.Toml;
import onedsix.core.event.AsmPhaseEventManager.AsmPhaseListener;
import onedsix.core.event.ModStartupEventManager.ModStartupListener;
import onedsix.core.util.Logger;

import java.io.File;
import java.net.URL;
import java.util.*;

import static onedsix.core.event.AsmPhaseEventManager.addAsmStartupListener;
import static onedsix.core.event.ModStartupEventManager.addModStartupListener;

public class ModMetadata {
    
    private static final Logger L = new Logger(ModMetadata.class);
    private static final LinkedList<Metadata> MOD_LIST = new LinkedList<>();
    public static LinkedList<Metadata> getModList() {return MOD_LIST;}
    public static int getModCount() {return MOD_LIST.size();}
    /** Gets the metadata of the mod with the inputted ID.
     * @return Either {@code null}, or the metadata or the requested mod.
     * */
    public static Metadata getModMetadata(String modid) {
        for (Metadata m : MOD_LIST) {
            if (Objects.equals(m.modProp.modId, modid)) {
                return m;
            }
        }
        return null;
    }
    
    public static class Metadata {
        public final File extractionDirectory;
        public final Toml toml;
        public final ModProperties modProp;
        public final DependencyProperties depProp;
        
        public Metadata(File exDir, Toml toml) {
            this.extractionDirectory = exDir;
            this.toml = toml;
            this.modProp = new Toml().read(toml).getTable("mod").to(ModProperties.class);
            this.depProp = new Toml().read(toml).getTable("dependencies."+this.modProp.modId+"[0]").to(DependencyProperties.class);
        }
        
        protected List<DependencyProperties> getDependencies() {
            List<Toml> depTomls = new Toml(toml).getList("dependencies."+this.modProp.modId);
            List<DependencyProperties> depProp = new ArrayList<>();
            for (Toml toml : depTomls) {
                depProp.add(new Toml(toml).to(DependencyProperties.class));
            }
            return depProp;
        }
    
        protected ModProperties getModProperties() {
            return new Toml(toml).to(ModProperties.class);
        }
        
        public static final class ModProperties {
            public final String modId;
            public final String version;
            public final String displayName;
            public final String description;
            public final String logoFile;
            public final URL modUrl;
            public final URL issueTracker;
            public final URL licenseUrl;
            public final String license;
            public final String[] credits;
            public final String[] authors;
            public final HashMap<String, Object> otherProperties;
    
            ModProperties(String modId, String version, String displayName, String description, String logoFile, URL modUrl, URL issueTracker, URL licenseUrl, HashMap<String, Object> otherProperties, String license, String[] credits, String[] authors) {
                this.modId = modId;
                this.version = version;
                this.displayName = displayName;
                this.description = description;
                this.logoFile = logoFile;
                this.modUrl = modUrl;
                this.issueTracker = issueTracker;
                this.licenseUrl = licenseUrl;
                this.otherProperties = otherProperties;
                this.license = license;
                this.credits = credits;
                this.authors = authors;
            }
        }
        
        public static final class DependencyProperties {
    
            public static final class CompatibilityType {
                public static final String REQUIRED = "REQUIRED";
                public static final String OPTIONAL = "OPTIONAL";
                public static final String INCOMPATIBLE = "INCOMPATIBLE";
                public static final String DISCOURAGED = "DISCOURAGED";
            }
    
            public static final class OrderingType {
                public static final String BEFORE = "BEFORE";
                public static final String AFTER = "AFTER";
                public static final String NONE = "NONE";
            }
    
            public static final class SideType {
                public static final String CLIENT = "CLIENT";
                public static final String SERVER = "SERVER";
                public static final String BOTH = "BOTH";
            }
    
            public final String modid;
            public final String compatibilityType;
            public final String compatibilityReason;
            public final String versionRange;
            public final String ordering;
            public final String side;
            public final String otherProperties;
    
            DependencyProperties(String modid, String compatibilityType, String compatibilityReason, String versionRange, String ordering, String side, String otherProperties) {
                this.modid = modid;
                this.compatibilityType = compatibilityType;
                this.compatibilityReason = compatibilityReason;
                this.versionRange = versionRange;
                this.ordering = ordering;
                this.side = side;
                this.otherProperties = otherProperties;
            }
            
        }
        
    }
    
    public static class ZipMetadata extends Metadata {
        public final File mainFile;
        public final HashMap<Class<? extends EventListener>, File> eventFiles;
    
        public ZipMetadata(File exDir, File toml, File mainFile, HashMap<Class<? extends EventListener>, File> eventFiles) {
            super(exDir, new Toml().read(toml));
            this.mainFile = mainFile;
            this.eventFiles = eventFiles;
            
            MOD_LIST.add(this);
        }
    
        public ZipMetadata(File exDir, File toml, File mainFile) {
            super(exDir, new Toml().read(toml));
            this.mainFile = mainFile;
            this.eventFiles = new HashMap<>();
    
            MOD_LIST.add(this);
        }
    }
    
    public static class JarMetadata extends Metadata {
        
        public JarMetadata(File exDir, File toml, ModStartupListener mainFile, AsmPhaseListener asmFile) {
            super(exDir, new Toml().read(toml));
            ModStartupEventManager.addModStartupListener(mainFile);
            AsmPhaseEventManager.addAsmStartupListener(asmFile);
    
            MOD_LIST.add(this);
        }
    
        public JarMetadata(File exDir, File toml, ModStartupListener mainFile) {
            super(exDir, new Toml().read(toml));
            ModStartupEventManager.addModStartupListener(mainFile);
    
            MOD_LIST.add(this);
        }
    }
}
