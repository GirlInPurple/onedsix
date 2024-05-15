package onedsix.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.decals.*;
import com.badlogic.gdx.graphics.g3d.utils.*;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.*;
import com.esotericsoftware.kryonet.*;
import com.google.gson.Gson;
import com.moandjiezana.toml.Toml;
import onedsix.client.graphics.screens.*;
import onedsix.core.util.EnvType;
import onedsix.core.util.KeyCalls;
import onedsix.core.util.Logger;
import onedsix.core.systems.DatagenHandler;
import onedsix.core.assets.abstracts.Asset;
import onedsix.client.graphics.GeometryHandler;
import onedsix.client.ClientPlayer;
import onedsix.core.util.Logger.LoadingLogs;

import javax.script.*;
import java.lang.reflect.Method;
import java.util.*;

import static onedsix.core.systems.GameSettings.SettingsJson;

/**
 * Where a majority of the game is stored, alongside a short description of the usage of that variable/method. <br>
 * The idea for this setup was taken from Mindustry.
 * */
public class Vars {
    /** Logger for Vars, not a public logger!
     * @see Logger */
    private static final Logger L = new Logger(Vars.class);
    /** Current Phase
     * @see Vars.Phases
     * */
    public static int currentPhase;
    /** Internal ID, used for mod compatibility */
    public static final String MOD_ID = "onedsix";
    /** Current environment the game is running in. */
    public static EnvType envType = new EnvType(false);
    /** Preferred language of the user. */
    public static Locale locale = Locale.getDefault();
    /** Logs for when the game is loading. */
    public static LinkedList<LoadingLogs> loadingLogs = new LinkedList<>();
    /** Global Gson Instance to save on memory. */
    public static final Gson GSON = new Gson();
    /** Settings for the game. Is {@code null} until later in the startup process. */
    public static SettingsJson settings;
    /** General Version, like Lighting, Color Skewing, etc. */
    public static Environment environment;
    /** 3D Camera. */
    public static PerspectiveCamera cam3D;
    /** 2D Camera, used in GUIs and very specific edge-cases. */
    public static OrthographicCamera camHUD;
    /** Camera Offset for all decals to look at */
    public static Vector3 camOffset;
    /**
     * Camera Controller.
     * @see Vars#cam3D
     * */
    public static CameraInputController camController;
    /** OpenGL Viewport. Used for rendering 3D objects */
    public static Viewport viewport3D;
    /** OpenGL Viewport. Used for rendering UIs and Sprites */
    public static Viewport viewportHUD;
    /** The list of assets in need of being loaded. */
    public static final LinkedList<String> loadList = new LinkedList<>();
    /** Handles everything to do with the Version Geometry, Players, Some GUI elements, etc. */
    public static ModelBatch modelBatch;
    /** A list of all the loaded models. Will get quite large! */
    public static List<ModelInstance> modelInstances = new ArrayList<>();
    /**
     * Builds models
     * @see GeometryHandler GeometryHandler
     * */
    public static final ModelBuilder modelBuilder = new ModelBuilder();
    /**
     * A Map of cells that have been loaded and their Epoch timestamp for when they were last loaded. A kind of rudimentary cache of sorts.<br>
     * May get quite large due to how many cells can be loaded at once.<br>
     * @see DatagenHandler#cellGC()
     * */
    public static Map<DatagenHandler, Long> cells = new HashMap<>();
    /** Handles everything to do with GUIs, Sprites, etc. */
    public static SpriteBatch spriteBatch;
    /** All Loaded Sprites, mostly used by GUIs. */
    public static List<Sprite> sprites = new ArrayList<>();
    /** Handles everything to do with Billboarding/Decals. */
    public static DecalBatch decalBatch;
    public static List<Decal> decals = new ArrayList<>();
    /** The font of the game, usually used for GUIs. */
    public static BitmapFont font;
    /** If the game is docked, or no longer visible, pause rendering to save on resources. */
    public static boolean shouldAttemptRendering = true;
    /** Debug mode toggle, shows an F3-like display on the screen.<br>Not development mode! */
    public static boolean debugMode = false;
    /** Variable used to store the size of the window, as well as the last time it was logged.<br>
     * <li>0 = width</li>
     * <li>1 = height</li>
     * <li>2 = miliseconds since last resized</li>
     * <li>3 = has the resize been logged yet</li>
     * @see Client#resize(int, int)
     * */
    public static List<Integer> windowSize = new LinkedList<>();
    /**
     * If the game should be checking keystrokes.
     * @see KeyCalls
     * */
    public static KeyCalls keyCalls;
    public static MainMenuScreen mainMenuScreen;
    public static ConnectScreen connectScreen;
    public static SettingsScreen settingsScreen;
    public static ModListScreen modListScreen;
    public static SinglePlayerScreen singlePlayerScreen;
    /** The player instance, created at startup */
    public static ClientPlayer player;
    /** The global KyroNet instance.<br>Checking if it is {@link Client} or {@link Server} is necessary at all times. */
    public static EndPoint networker;
    /**
     * The target server for both HTTP and TCP connections.
     * @see Vars#targetPort
     * */
    public static String targetHostname = "localhost";
    /** Target Port
     * @see Vars#targetHostname
     * */
    public static int targetPort = 36000;
    /** Milliseconds to try to reconnect to a connected server. */
    public static final int RECONNECT_DELAY_MS = 5000;
    /**
     * Hostname of the central server.<br> Used for server scanning, authentication, and a few other small services.
     * @see Vars#centralPort
     * */
    public static String centralHostname;
    /** Port of the central server.
     * @see Vars#centralHostname
     * */
    public static int centralPort = 80;
    /** Used for zip mods. */
    public static ScriptEngine scriptEngine;

    /** Init variables only meant for the OndsixClient. */
    public static void clientInit() {
        envType = new EnvType(false);
        environment = new Environment();
        cam3D = new PerspectiveCamera(50, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camHUD = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        L.info(Gdx.graphics.getWidth()+", "+Gdx.graphics.getHeight());
        camController = new CameraInputController(cam3D);
        viewport3D = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), cam3D);
        viewportHUD = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camHUD);
        spriteBatch = new SpriteBatch();
        decalBatch = new DecalBatch(new CameraGroupStrategy(cam3D));
        keyCalls = new KeyCalls();
        font = new BitmapFont(Gdx.files.internal("font.fnt"));
        modelBatch = new ModelBatch();
        mainMenuScreen = new MainMenuScreen();
        connectScreen = new ConnectScreen();
        settingsScreen = new SettingsScreen();
        modListScreen = new ModListScreen();
        singlePlayerScreen = new SinglePlayerScreen();
        networker = new Client();
        globalInit();
    }

    /** Init variables only meant for Servers. */
    public static void serverInit() {
        envType = new EnvType(true);
        networker = new Server();
        globalInit();
    }

    /** Init variables meant for both. */
    static void globalInit() {
        scriptEngine = new ScriptEngineManager().getEngineByName("nashorn");
        scriptEngine.put("console", new Logger(ScriptEngine.class));
        scriptEngine.put("Class", Class.class);
        scriptEngine.put("Method", Method.class);
        scriptEngine.put("Gson", Gson.class);
        scriptEngine.put("Toml", Toml.class);
        scriptEngine.put("Asset", Asset.class);
		/*
		scriptEngine.put("TypeDescription", TypeDescription.class);
		scriptEngine.put("DynamicType", DynamicType.class);
		scriptEngine.put("TypeResolution", TypeDescription.Generic.class);
		scriptEngine.put("ByteBuddy", ByteBuddy.class);
	 	*/
    }
}
