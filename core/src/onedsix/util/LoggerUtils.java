package onedsix.util;

import com.badlogic.gdx.Gdx;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.event.Level;

public class LoggerUtils {
    
    public static class LoadingLogs {
        
        public final String text;
        public final Long timestamp;
        
        public LoadingLogs(String text) {
            this.text = text;
            this.timestamp = System.nanoTime();
        }
    }
    
    static void switchLogger(Logger log, @NotNull Level lvl, String msg) {
        if (lvl == Level.INFO) {log.info(msg);} else
        if (lvl == Level.WARN) {log.warn(msg);} else
        if (lvl == Level.DEBUG) {log.debug(msg);} else
        if (lvl == Level.TRACE) {log.trace(msg);} else
        if (lvl == Level.ERROR) {log.error(msg);}
    }
    
    public static void everyInterval(Logger log, Level lvl, String msg, int interval) {
        if ((Gdx.graphics.getFrameId() & interval) == 0) {
            switchLogger(log, lvl, msg);
        }
    }
    
    public static void logForEach(Logger log, Level lvl, Object[] each) {
        for (Object o : each) {
            String msg = o.toString();
            switchLogger(log, lvl, msg);
        }
    }
}
