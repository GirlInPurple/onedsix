package onedsix.core.util;

import com.badlogic.gdx.Gdx;
import onedsix.core.Vars;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * A logging utility wrapper around GDX's logger.<br>
 * Completely replaces (drop-in, actually) {@link com.badlogic.gdx.utils.Logger}, with the addition of
 * Log files, crash reports, and actually decent tags as well.
 * */
public class Logger {

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

    public static final PrintWriter WRITER;
    public static final Calendar DATE = GregorianCalendar.getInstance();
    /** Date formatting for logging.<br>Miliseconds are handled in {@link #generateTag(String)}. */
    public static final SimpleDateFormat SDF = new SimpleDateFormat("HH:mm:ss");

    static {
        try {
            DATE.setTime(new Date());
            FileHandler.createDirectory("./logs/");
            WRITER = new PrintWriter(new FileWriter(getLogFile(), true));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public final Class clazz;
    public boolean infoEnabled = true;
    public boolean errorEnabled = true;
    public boolean debugEnabled = true;

    public Logger(Class clazz) {
        this.clazz = clazz;
    }

    protected final String generateTag(String lvl) {
        String name = this.clazz.getName();
        int compression = 0;
        while (name.length() > 30) {
            String[] spiltString = name.split("\\.");
            spiltString[compression] = ""+spiltString[compression].charAt(0)+spiltString[compression].charAt(1);
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
                "%s:%s - %s",
                SDF.format(DATE.getTime()),
                System.currentTimeMillis() % 100,
                name
        );

        returns = StringUtils.rightPad(returns,45);

        return String.format("%s - %s", returns, lvl);
    }

    static String getLogFile() {
        String filepath = "internalError";
        final String name = new StringBuilder()
            .append(DATE.get(Calendar.YEAR))
            .append("-")
            .append(DATE.get(Calendar.MONTH))
            .append("-")
            .append(DATE.get(Calendar.DAY_OF_MONTH))
            .toString();
        int index = 1;
        boolean notFound = false;

        while (!notFound) {
            File f = new File("./logs/" + name + "-" + index + ".log"); // EX: ./logs/2024-3-0-1.txt
            if (!f.exists()) {
                notFound = true;
                filepath = f.toPath().toString();
            }
            index++;
        }

        return filepath;
    }

    /** Info Logger for when GDX hasn't loaded yet. */
    public void print(String text) {
        String printed = "["+generateTag("Platf")+"] "+text;
        System.out.println(printed);
        WRITER.print(printed+"\n");
    }

    /** Error Logger for when GDX hasn't loaded yet. */
    public void print(String text, Throwable e) {
        String printed = "["+generateTag("Platf")+"] "+text;
        System.out.println(printed);
        WRITER.print(printed+"\n");
        System.out.println(e);
        WRITER.print(e+"\n");
    }

    /** Standard Info Logger */
    public void info(String text) {
		String printed = "["+generateTag("Platf")+"] "+text;
		System.out.println(printed);
		WRITER.print(printed+"\n");
    }

    /** Standard Error Logger */
    public void error(String text) {
		String printed = "["+generateTag("Platf")+"] "+text;
		System.out.println(printed);
		WRITER.print(printed+"\n");
    }

    /** Standard Error Logger */
    public void error(String text, Throwable e) {
		String printed = "["+generateTag("Platf")+"] "+text;
		System.out.println(printed);
		WRITER.print(printed+"\n");
		System.out.println(e);
		WRITER.print(e+"\n");
    }

    /** Standard Debug Logger */
    public void debug(String text) {
		String printed = "["+generateTag("Platf")+"] "+text;
		System.out.println(printed);
		WRITER.print(printed+"\n");
    }

    public void loadingLogger(String text, int lvl) {
        Vars.loadingLogs.add(new LoadingLogs(text));
        switchLogger(lvl, text); // Routes to other loggers, log writing handled there.
    }

    void switchLogger(int lvl, String text) {
        if (lvl == Level.INFO) {this.info(text);} else
        if (lvl == Level.DEBUG) {this.info(text);} else
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
