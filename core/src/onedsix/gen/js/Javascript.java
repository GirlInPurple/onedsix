package onedsix.gen.js;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.*;

public class Javascript {
    
    public static final String engineName = "nashorn";
    public static ScriptEngineManager manager;
    public static ScriptEngine js;
    public static Bindings bindings;
    private static final Logger L = LoggerFactory.getLogger(Javascript.class);
    
    public static void jsInit() {
        try {
            manager = new ScriptEngineManager();
            js = manager.getEngineByName(engineName);
            bindings = js.getBindings(ScriptContext.ENGINE_SCOPE);
            
            bindings.put("stdout", L);
            if ((double) js.eval("Math.cos(Math.PI);") == -1.0d) {
                L.info("Javascript engine started!");
            }
        } catch (ScriptException se) {
            L.error(se.getMessage());
            se.printStackTrace();
            
        }
    }
}
