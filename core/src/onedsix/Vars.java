package onedsix;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.decals.*;
import com.badlogic.gdx.graphics.g3d.utils.*;
import com.badlogic.gdx.math.Vector3;
import com.google.gson.Gson;
import onedsix.gen.DatagenHandler;
import onedsix.graphics.GeometryHandler;
import onedsix.util.KeyCalls;
import onedsix.util.Logger.*;

import java.util.*;

import static onedsix.systems.GameSettings.SettingsJson;

/**
 * Where a majority of the game is stored, alongside a short description of the usage of that variable/method. <br>
 * The idea for this setup was taken from Mindustry.
 * */
public class Vars {
    /** Startup Phases <br>
     * There is enough le-way in the variables here for additions to be made.
     * */
    public static final class Phases {
        /** Mod Discovery */
        public static final int DISCOVERY = -5;
        /** Mod Init / Loading */
        public static final int INIT = 0;
        /** In-Game */
        public static final int COMPLETE = 5;
    }
    /** Current Phase
     * @see Vars.Phases
     * */
    public static int currentPhase;
    /** Logs for when the game is loading. */
    public static LinkedList<LoadingLogs> loadingLogs = new LinkedList<>();
    /** Global Gson Instance to save on memory. */
    public static final Gson GSON = new Gson();
    /** Settings for the game. Is {@code null} until later in the startup process. */
    public static SettingsJson settings;
    /**
     * General Version, like Lighting, Color Skewing, etc.
     * */
    public static Environment environment = new Environment();;
    /**
     * 3D Camera.
     * */
    public static PerspectiveCamera cam3D = new PerspectiveCamera(50, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    /**
     * 2D Camera, used in GUIs and very specific edge-cases.
     * */
    public static OrthographicCamera camHUD = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    /**
     * Camera Offset for all decals to look at
     * */
    public static Vector3 camOffset;
    /**
     * Camera Controller.
     * @see Vars#cam3D
     * */
    public static final CameraInputController camController = new CameraInputController(cam3D);
    /** The list of assets in need of being loaded. */
    public static final LinkedList<String> loadList = new LinkedList<>();
    /**
     * Handles everything to do with the Version Geometry, Players, Some GUI elements, etc.
     * */
    public static final ModelBatch modelBatch = new ModelBatch();
    /**
     * A list of all the loaded models. Will get quite large!
     * */
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
    /**
     * Handles everything to do with GUIs, Sprites, etc.
     * */
    public static SpriteBatch spriteBatch = new SpriteBatch();
    /**
     * All Loaded Sprites, mostly used by GUIs.
     * */
    public static List<Sprite> sprites = new ArrayList<>();
    /**
     * Handles everything to do with Billboarding/Decals.
     * */
    public static final DecalBatch decalBatch = new DecalBatch(new CameraGroupStrategy(cam3D));
    public static List<Decal> decals = new ArrayList<>();
    /**
     * The font of the game, usually used for GUIs.
     * */
    public static final BitmapFont font = new BitmapFont(Gdx.files.internal("font.fnt"));
    /**
     * Random, use this as to not create so many instances
     * */
    public static final Random rand = new Random();
    /**
     * If the game is docked, or no longer visible, pause rendering to save on resources.
     * */
    public static boolean shouldAttemptRendering = true;
    /**
     * Debug mode toggle, shows an F3-like display on the screen.<br>Not development mode!
     * */
    public static boolean debugMode = false;
    /** Variable used to store the size of the window, as well as the last time it was logged. */
    public static List<Integer> windowSize = new LinkedList<>();
    /**
     * If the game should be checking keystrokes.
     * @see KeyCalls
     * */
    public static final KeyCalls keyCalls = new KeyCalls();
    /** The player instance, created at startup */
    public static Player player;
    /**
     * The target server for both HTTP and TCP connections.
     * @see Vars#targetPort
     * */
    public static String targetHostname;
    /** Target Port
     * @see Vars#targetHostname
     * */
    public static int targetPort;
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
    public static int centralPort;
}
