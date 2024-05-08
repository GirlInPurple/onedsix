package onedsix.core.util;

import com.badlogic.gdx.Gdx;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.moandjiezana.toml.Toml;
import onedsix.core.Vars;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.io.*;
import java.nio.file.*;
import java.util.*;

import static onedsix.core.util.FileHandler.JSON.*;

public class FileHandler {
    
    private static final Logger L = new Logger(FileHandler.class);
    
    public static class NOFORMAT {
        /**
         * @param filepath The file to read
         * @return Returns a String with the file contents
         * */
        public static String readTXT(Path filepath) throws IOException {
            BufferedReader br = new BufferedReader(new FileReader(filepath.toFile()));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) content.append(line).append("\n");
            br.close();
            return content.toString();
        }

        /**
         * @param data A String
         * @param filepath The file to write
         * */
        public static void writeTXT(String data, Path filepath) throws IOException {
            PrintWriter writer = new PrintWriter(new FileWriter(filepath.toFile()));
            writer.write(data);
            writer.close();
        }
    }

    public static class JSON {
        
        public static final String EMPTY_JSON = "{}";
        
        /**
         * @param file The .JSON file to read
         * @return Returns a {@link JsonObject}
         * */
        public static JsonObject readJSON(File file) {
            Reader reader = null;
            try {
                Type type = new TypeToken<Map<String, Object>>() {}.getType();
                reader = new FileReader(file);
                Map<String, Object> map = Vars.GSON.fromJson(reader, type);
                
                if (map == null) map = new HashMap<>();
                map.put("", "");
                return JsonParser.parseString(map.toString()).getAsJsonObject();
            }
            catch (IOException ioe) {
                L.error(ioe.getMessage(), ioe);
                return null;
            }
            finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException ioe) {
                        L.error(ioe.getMessage(), ioe);
                    }
                }
            }
        }
    
        /**
         * @param file The .JSON file to read
         * @param type The class type to which JSON should be deserialized
         * @return Returns an instance of the specified class populated with JSON data
         */
        public static <T> T readJSON(File file, Class<T> type) {
            Reader reader = null;
            try {
                reader = new FileReader(file);
                return Vars.GSON.fromJson(reader, type);
            }
            catch (IOException ioe) {
                L.error(ioe.getMessage(), ioe);
                return null;
            }
            finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException ioe) {
                        L.error(ioe.getMessage(), ioe);
                    }
                }
            }
        }

        /**
         * @param json A {@link JsonObject}
         * @param filepath The .JSON file to read
         * */
        public static void writeJSON(JsonObject json, Path filepath) {
            FileWriter writer = null;
            try {
                writer = new FileWriter(filepath.toFile());
                Vars.GSON.toJson(json, writer);
            }
            catch (IOException ioe) {
                L.error(ioe.getMessage(), ioe);
            }
            finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException ioe) {
                        L.error(ioe.getMessage(), ioe);
                    }
                }
            }
        }
    }
    
    public static class TOML {
        
        public static Toml readTOML(File file) {
            try {
                return new Toml().read(new FileReader(file));
            }
            catch (Exception e) {
                L.error(e.getMessage(), e);
                return null;
            }
        }
        
    }
    
    public static class RESOURCES {
    
        public static JsonObject getJson(String path) {
            File to;
            if (new File(path).exists()) {
                to = new File(path);
            } else {
                to = Gdx.files.internal(path).file();
            }
            return readJSON(to);
        }
    
        public static <T> T getJson(String path, Class<T> type) {
            File to;
            if (new File(path).exists()) {
                to = new File(path);
            } else {
                to = Gdx.files.internal(path).file();
            }
            return readJSON(to, type);
        }
        
        public static <T> T invoke(Class<T> outputType, Method into, String path) throws InvocationTargetException, IllegalAccessException, FileNotFoundException {
            Path to;
            if (Gdx.files.internal(path).exists()) {
                to = Gdx.files.internal(path).file().toPath();
            } else if (new File(path).exists()) {
                to = new File(path).toPath();
            } else {
                throw new FileNotFoundException();
            }
            return (T) into.invoke(to, outputType);
        }
    }

    public static List<String> splitOnNewline(String in) {
        return new ArrayList<>(Arrays.asList(in.split("\\r?\\n")));
    }
    
    public static String newlineOnArray(String[] in) {
        StringBuilder sb = new StringBuilder();
        for (String s : in) {
            sb.append(s);
            sb.append("\n");
        }
        return sb.toString();
    }
    
    public static String newlineOnArray(StackTraceElement[] in) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement s : in) {
            sb.append(s);
            sb.append("\n");
        }
        return sb.toString();
    }
    
    public static void createDirectory(String folder) {
        File dir = new File(folder);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
    
    public static File findInDirectory(String fileName, File startDirectory) {
        if (startDirectory == null) startDirectory = new File("./temp/");
        File outFile = null;
        
        File[] files = startDirectory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    File tempOut;
                    tempOut = findInDirectory(fileName, file);
                    if (tempOut != null) {
                        outFile = file;
                    }
                }
                else if (file.getName().equals(fileName)) {
                    outFile = file;
                }
            }
        }
        
        return outFile;
    }
}
