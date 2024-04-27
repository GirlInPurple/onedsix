package onedsix.util;

import com.badlogic.gdx.Gdx;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Date;

import static onedsix.Vars.loadingLogs;

/**
 * A logging utility wrapper around GDX's logger.<br>
 * Completely replaces (drop-in, actually) {@link com.badlogic.gdx.utils.Logger}, with the addition of
 * Log files, crash reports, and actually decent tags as well.
 * */
public class Logger {
    
    
    public static final Date DATE = new Date();
    
    public static final class LoadingLogs {
        
        public final String text;
        public final Long timestamp;
        
        public LoadingLogs(String text) {
            this.text = text;
            this.timestamp = System.nanoTime();
        }
    }
    
    public static class Level {
        public static final int INFO = 0;
        public static final int ERROR = 2;
        public static final int DEBUG = 1;
    }
    
    public final Class clazz;
    public boolean info = true;
    public boolean error = true;
    public boolean debug = true;
    
    public Logger(Class clazz) {
        this.clazz = clazz;
    }
    
    String generateTag(String lvl) {
        String name = this.clazz.getName();
        int compression = 0;
        while (name.length() > 30) {
            String[] spiltString = name.split("\\.");
            spiltString[compression] = Character.toString(spiltString[compression].charAt(0));
            StringBuilder builder = new StringBuilder();
            int loop = 0;
            for (String s : spiltString) {
                if (loop == 0) {builder.append(s);}
                else {builder.append(".").append(s);}
                loop++;
            }
            name = builder.toString();
            compression++;
        }
        
        String returns = String.format(
                "%s:%s:%s:%s - %s",
                DATE.getHours(),
                DATE.getMinutes(),
                DATE.getSeconds(),
                (System.currentTimeMillis() + 10) % 100,
                name
        );
    
        returns = StringUtils.rightPad(returns,45);
        
        return String.format("%s - %s", returns, lvl);
    }
    
    /** Info Logger for when GDX hasn't loaded yet. */
    public void print(String text) {
        System.out.println("["+generateTag("Platf")+"] "+text);
    }
    
    /** Error Logger for when GDX hasn't loaded yet. */
    public void print(String text, Throwable e) {
        System.out.println("["+generateTag("Platf")+"] "+text);
    }
    
    /** Standard Info Logger */
    public void info(String text) {
        Gdx.app.log(generateTag("Info "), text);
    }
    
    public void error(String text) {
        Gdx.app.error(generateTag("Error"), text);
    }
    
    public void error(String text, Throwable e) {
        Gdx.app.error(generateTag("Error"), text, e);
    }
    
    /**  */
    public void debug(String text) {
        Gdx.app.debug(generateTag("Debug"), text);
    }
    
    public void loadingLogger(String text, int lvl) {
        loadingLogs.add(new LoadingLogs(text));
        switchLogger(lvl, text);
    }
    
    void switchLogger(int lvl, String text) {
        if (lvl == Level.INFO) {this.info(text);} else
        if (lvl == Level.DEBUG) {this.debug(text);} else
        if (lvl == Level.ERROR) {this.error(text);}
    }
    
    public void everyInterval(int lvl, String text, int interval) {
        if ((Gdx.graphics.getFrameId() & interval) == 0) {
            switchLogger(lvl, text);
        }
    }
    
    public void logForEach(int lvl, Object[] objs) {
        for (Object o : objs) {
            String msg = o.toString();
            switchLogger(lvl, msg);
        }
    }
}
