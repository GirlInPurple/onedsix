package onedsix.api;

import onedsix.core.Vars;
import onedsix.core.util.Logger;

import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
//import static onedsix.mods.ModMetadata.*;

/** Public API for .zip (javascript-based) mods. */
@SuppressWarnings("unused")
public final class JavascriptHelper {
    private static final Logger L = new Logger(JavascriptHelper.class);
    
    /** Runs all .zip mods with the specified name. Used mostly for sending events. */
    /*
    public void sendEventToJS(String fileName, Object... objects) {
        for (File f : getAllNamed(fileName)) {
            FileReader fr = null;
            try {
                fr = new FileReader(f);
                Vars.scriptEngine.eval(fr);
            }
            catch (Exception e) {
                L.error(e.getMessage(), e);
            }
            finally {
                try {
                    if (fr != null) {
                        fr.close();
                    }
                }
                catch (Exception e) {
                    L.error(e.getMessage(), e);
                }
            }
        }
    }
    
    /** Gets all the files with the specified name, used mostly in APIs and better event handling.*/
    /*
    public LinkedList<File> getAllNamed(String fileName) {
        LinkedList<File> output = new LinkedList<>();
        for (Metadata z : getModList()) {
            for (File f : z.extractionDirectory.listFiles()) {
                if (f.getName().equals(fileName)) {
                    output.add(f);
                }
            }
        }
        return output;
    }*/
}
