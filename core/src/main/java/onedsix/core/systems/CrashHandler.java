package onedsix.core.systems;

import com.badlogic.gdx.Gdx;
import onedsix.core.Vars;
import onedsix.core.util.EnvType;
import onedsix.core.util.FileHandler;
import onedsix.core.util.Logger;

import java.awt.*;
import java.awt.event.*;

public class CrashHandler {
    
    private static final Logger L = new Logger(CrashHandler.class);
    
    public static void createCrash(Throwable e) {
    
        // Write to the log file
        L.error("= =-- ====== | ==========");
        L.error("1D6 has crashed!");
        L.error(e.getMessage(), e);
        
        // Stop Everything except this thread
        L.error("An error has been encountered, stopping game...");
        Gdx.app.exit();
        
        // Create crash window, but only on Desktop
        if (Vars.envType.getEnv() == EnvType.ValidEnvironments.DESKTOP) {
            Frame frame = new Frame("1D6 has crashed!");
    
            frame.add(new Label("1D6 has crashed for the following reason:"));
            frame.add(new Label(e.getMessage()));
            frame.add(new Label(FileHandler.newlineOnArray(e.getStackTrace())));
            frame.setSize(500, 500);
            frame.setLocationRelativeTo(null);
    
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    frame.dispose();
                }
            });
    
            frame.setVisible(true);
        }
    }
}
