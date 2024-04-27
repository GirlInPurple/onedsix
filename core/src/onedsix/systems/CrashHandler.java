package onedsix.systems;


import com.badlogic.gdx.Gdx;
import onedsix.util.FileHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.event.*;

import static onedsix.util.FileHandler.newlineOnArray;

public class CrashHandler {
    
    private static final Logger L = LoggerFactory.getLogger(CrashHandler.class);
    
    public static void createCrash(Throwable e) {
        
        // Stop Everything except this thread
        Gdx.app.exit();
        
        // Write to a crash file
        
        
        // Create crash window
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
