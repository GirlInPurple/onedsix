package onedsix.gen.js;

import onedsix.util.Logger;

import javax.script.*;

public class Javascript {
    
    private static final Logger L = new Logger(Javascript.class);
    public static final String engineName = "nashorn";
    public static ScriptEngineManager manager;
    public static ScriptEngine js;
    public static Bindings bindings;
    
    public static void jsInit() {
        try {
            manager = new ScriptEngineManager();
            js = manager.getEngineByName(engineName);
            bindings = js.getBindings(ScriptContext.ENGINE_SCOPE);
            
            bindings.put("stdout", L);
            if ((double) js.eval("Math.cos(Math.PI);") == -1.0d) {
                L.info("Javascript engine started!");
            }
        } catch (Exception e) {
            L.error(e.getMessage(), e);
            
        }
    }
}
