package onedsix.core.asm;

import onedsix.core.util.Logger;

import java.io.IOException;
import java.nio.file.*;

/** Shorthands for basic ASM methods. */
@SuppressWarnings("unused")
public final class AsmHelper {
    
    private static final Logger L = new Logger(AsmHelper.class);
    
    public AsmHelper() {}
    
    public static byte[] readBytes() {
        byte[] outputBytes = null;
        try {
            outputBytes = Files.readAllBytes(Paths.get("OriginalClass.class"));
        }
        catch (IOException ioe) {
            L.error(ioe.getMessage(), ioe);
        }
        return outputBytes;
    }
    
    
}
