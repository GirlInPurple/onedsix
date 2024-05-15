package onedsix.client;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.*;
import com.badlogic.gdx.graphics.g3d.environment.*;
import com.badlogic.gdx.math.*;
import com.kotcrab.vis.ui.VisUI;
import onedsix.client.graphics.GeometryHandler;
import onedsix.client.graphics.Title;
import onedsix.core.Vars;
import onedsix.core.systems.DatagenHandler;
import onedsix.client.graphics.screens.CustomScreen;
import onedsix.core.util.Logger;

import static onedsix.core.event.SettingsChangeEventManager.*;
import static onedsix.core.systems.CrashHandler.createCrash;
import static onedsix.core.systems.GameSettings.*;
import static onedsix.core.systems.ScreenshotFactory.saveScreenshot;
import static onedsix.core.util.FileHandler.*;
import static onedsix.core.util.EnvType.*;

public class OndsixClient extends Game implements SettingsChangeListener, LifecycleListener {

    private static final Logger L = new Logger(OndsixClient.class);

    public OndsixClient() {super();}

    /**
     * Debug and Development testing function.<br>
     * Should not be used in prod.
     * */
    private void testing() {
        L.info("Testing method running...");

        // Will be modified a lot
        Texture t;

        t = new Texture(Gdx.files.internal("grass.jpg"), Pixmap.Format.RGB565, true);
        t.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        long miscAttr = Usage.Position | Usage.Normal | Usage.TextureCoordinates;
        Attribute[] matAttr = {
                TextureAttribute.createDiffuse(t),
                new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA),
                new DepthTestAttribute(false)
        };

        Model model = Vars.modelBuilder.createBox(50f, 1f, 50f,
                new Material(matAttr),
                miscAttr);
        Vars.modelInstances.add(GeometryHandler.transformShorthand(0, -10, 0, new ModelInstance(model)));
    }

    @Override public void create() {

        // Load UI backend now because Vars needs it
        VisUI.load(VisUI.SkinScale.X1);

        // Load Client Variables
        Vars.clientInit();

        // Settings
        addSettingsChangeListener(OndsixClient.this);
        readSettings();

        // Splash Text
        Gdx.graphics.setTitle(Title.title);
        if (Vars.envType.getEnv() == ValidEnvironments.DESKTOP) {
            Gdx.graphics.setTitle(Title.randomSplash());
        }

        // Version and Branch
        /*
        import java.io.InputStream;
        import java.util.Properties;

        InputStream inputStream = Gdx.files.internal("version.properties").read();
        Properties properties = new Properties();
        properties.load(inputStream);
        String version = properties.getProperty("version");
        L.info(version);
        */

        // Create directories
        createDirectory("./temp/");
        createDirectory("./screenshots/");

        // Window size bugfix
        Vars.windowSize.add(800);
        Vars.windowSize.add(480);
        Vars.windowSize.add(0);
        Vars.windowSize.add(0);

        // Create and Set Screens
        Vars.mainMenuScreen.createScreen();
        Vars.connectScreen.createScreen();
        Vars.settingsScreen.createScreen();
        Vars.modListScreen.createScreen();
        Vars.singlePlayerScreen.createScreen();
        super.setScreen(Vars.mainMenuScreen);

        // Environment and Lights
        Vars.environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        Vars.environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        // Cameras
        Vars.camOffset = new Vector3(10f, 10f, 0f);
        Vars.cam3D.position.set(Vars.camOffset);
        Vars.cam3D.lookAt(0f,0f,0f);
        Vars.cam3D.near = 1f;
        Vars.cam3D.far = 300f;
        Vars.cam3D.update();
        Vars.camHUD.position.set(Vars.camHUD.viewportWidth / 2.0f, Vars.camHUD.viewportHeight / 2.0f, 1.0f);

        // Input handling
        //Gdx.input.setInputProcessor(new InputMultiplexer(keyCalls // Global Keycalls that should be able to run (almost) anywhere));

        // Beta/Dev stuff
        testing();
    }

    @Override public void render() {

        DatagenHandler.read();

        if ((System.nanoTime() + 5000) <= Vars.windowSize.get(2) && Vars.windowSize.get(3) == 0) {
            L.info("Resized ("+ Vars.windowSize.get(0)+", "+ Vars.windowSize.get(1)+")");
            Vars.windowSize.set(3, 1);
        }

        boolean tookScreenshot = false;
        String screenshotFile = "";

        if (Vars.keyCalls.debug) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.F2) ||
                        Gdx.input.isKeyJustPressed(Input.Keys.O)) {
                tookScreenshot = true;
                screenshotFile = saveScreenshot();
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.F3) ||
                        Gdx.input.isKeyJustPressed(Input.Keys.P)) {
                Vars.debugMode = !Vars.debugMode;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.F4) ||
                        Gdx.input.isKeyJustPressed(Input.Keys.I)) {
                createCrash(new Throwable("Debug Crash"));
            }
            if (Gdx.input.isKeyPressed(Input.Keys.L)) {
                Gdx.graphics.setForegroundFPS(Gdx.graphics.getFramesPerSecond() + 10);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.K)) {
                Gdx.graphics.setForegroundFPS(Gdx.graphics.getFramesPerSecond() - 10);
            }
        }

        if (Vars.shouldAttemptRendering) {


        }

        // Pass it onto GDX for the rest
        super.render();
    }

    @Override public void dispose() {

        // Dispose assets
        Vars.spriteBatch.dispose();
        Vars.modelBatch.dispose();
        for (ModelInstance m : Vars.modelInstances) {
            m.model.dispose();
        }
        Vars.decalBatch.dispose();

        L.info("Stopped");
    }

    @Override public void resize(int width, int height) {
        Vars.windowSize.set(0, width);
        Vars.windowSize.set(1, height);
        Vars.windowSize.set(2, (int) System.nanoTime());
        Vars.windowSize.set(3, 0);
        //Gdx.graphics.setWindowedMode(width, height);
        Vars.viewport3D.update(width, height);
        Vars.viewportHUD.update(width, height);
    }

    @Override public void pause() {
        Vars.shouldAttemptRendering = false;
        L.info("Paused");
    }

    @Override public void resume() {
        Vars.shouldAttemptRendering = true;
        L.info("Unpaused");
    }

    /** Event listener for when settings are changed. */
    @Override
    public void settingsChanged(SettingsChangeEvent event, SettingsJson sj) {
        L.info("Refreshing settings...");
        Gdx.graphics.setForegroundFPS(Vars.settings.targetFps);
        Gdx.graphics.setVSync(Vars.settings.useVsync);
    }

    @Override public void setScreen(Screen screen) {super.setScreen(screen);}

    public CustomScreen getScreen() {return (CustomScreen) this.screen;}

    public static OndsixClient getClient() {
        return (OndsixClient) Gdx.app.getApplicationListener();
    }
}
