package onedsix.core.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import onedsix.core.util.Logger;

import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotFactory {
    
    private static final Logger L = new Logger(ScreenshotFactory.class);
    
    public static String saveScreenshot() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy_HH.mm.ss.SSS");
        Date dt = new Date();
        String instant = sdf.format(dt);
        
        String screenshotFile = "./screenshots/screenshot_" + instant + ".png";
        FileHandle fh = new FileHandle(screenshotFile);
        Pixmap pixmap = getScreenshot(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        PixmapIO.writePNG(fh, pixmap);
        pixmap.dispose();
        L.info("Saved screenshot in "+screenshotFile);
        return screenshotFile;
    }
    
    private static Pixmap getScreenshot(int x, int y, int w, int h, boolean yDown) {
        final Pixmap pixmap = Pixmap.createFromFrameBuffer(x, y, w, h);
        
        if (yDown) {
            // Flip the pixmap upside down
            ByteBuffer pixels = pixmap.getPixels();
            int numBytes = w * h * 4;
            byte[] lines = new byte[numBytes];
            int numBytesPerLine = w * 4;
            for (int i = 0; i < h; i++) {
                pixels.position((h - i - 1) * numBytesPerLine);
                pixels.get(lines, i * numBytesPerLine, numBytesPerLine);
            }
            pixels.clear();
            pixels.put(lines);
        }
        
        return pixmap;
    }
}