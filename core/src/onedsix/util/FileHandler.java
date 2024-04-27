package onedsix.util;

import com.badlogic.gdx.Gdx;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.io.*;
import java.nio.file.*;
import java.util.*;

import static onedsix.Vars.*;
import static onedsix.util.FileHandler.JSON.*;

public class FileHandler {
    
    private static final Logger L = new Logger(FileHandler.class);
    
    public static class NOFORMAT {
        /**
         * @param filepath The file to read
         * @return Returns a String with the file contents
         * */
        public static String readTXT(Path filepath) {
            try (BufferedReader br = new BufferedReader(new FileReader(filepath.toFile()))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {content.append(line).append("\n");}
                return content.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }

        /**
         * @param data A String
         * @param filepath The file to write
         * */
        public static void writeTXT(String data, Path filepath) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(filepath.toFile()))) {
                writer.write(data);
            } catch (Exception e) {e.printStackTrace();}
        }
    }

    public static class JSON {
        
        public static final String EMPTY_JSON = "{}";
        
        /**
         * @param file The .JSON file to read
         * @return Returns a {@link JsonObject}
         * */
        public static JsonObject readJSON(File file) {
            try {
                Type type = new TypeToken<Map<String, Object>>(){}.getType();
                Map<String, Object> map = GSON.fromJson(new FileReader(file), type);
                if (map == null) {map = new HashMap<>(); map.put("", "");}
                return JsonParser.parseString(map.toString()).getAsJsonObject();
            } catch (Exception e) {
                L.error(e.getMessage(), e);
                return null;
            }
        }
    
        /**
         * @param filepath The .JSON file to read
         * @param type The class type to which JSON should be deserialized
         * @return Returns an instance of the specified class populated with JSON data
         */
        public static <T> T readJSON(Path filepath, Class<T> type) {
            try (Reader reader = new FileReader(filepath.toString())) {
                return GSON.fromJson(reader, type);
            } catch (Exception e) {
                L.error(e.getMessage(), e);
                return null;
            }
        }

        /**
         * @param json A {@link JsonObject}
         * @param filepath The .JSON file to read
         * */
        public static void writeJSON(JsonObject json, Path filepath) {
            try (FileWriter writer = new FileWriter(filepath.toFile())) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(json, writer);
            } catch (Exception e) {
                L.error(e.getMessage(), e);
            }
        }
    }
    
    public static class RESOURCES {
    
        public static JsonObject getJson(String path) {
            try {
                File to;
                if (new File(path).exists()) {
                    to = new File(path);
                } else {
                    to = Gdx.files.internal(path).file();
                }
                return readJSON(to);
            } catch (Exception e) {
                L.error(e.getMessage(), e);
                return null;
            }
        }
    
        public static <T> T getJson(String path, Class<T> type) {
            try {
                Path to;
                if (Gdx.files.internal(path).exists()) {
                    to = Gdx.files.internal(path).file().toPath();
                } else if (new File(path).exists()) {
                    to = new File(path).toPath();
                } else {
                    throw new FileNotFoundException();
                }
                return readJSON(to, type);
            } catch (Exception e) {
                L.error(e.getMessage(), e);
                return null;
            }
        }
        
        public static <T> T invoke(Class<T> outputType, Method into, String path) {
            try {
                Path to;
                if (Gdx.files.internal(path).exists()) {
                    to = Gdx.files.internal(path).file().toPath();
                } else if (new File(path).exists()) {
                    to = new File(path).toPath();
                } else {
                    throw new FileNotFoundException();
                }
                return (T) into.invoke(to, outputType);
            } catch (InvocationTargetException | IllegalAccessException | FileNotFoundException e) {
                L.error(e.getMessage(), e);
                return null;
            }
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
}
