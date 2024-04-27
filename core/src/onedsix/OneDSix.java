package onedsix;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.*;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.environment.*;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import onedsix.event.settings.SettingsChangeEvent;
import onedsix.event.settings.SettingsChangeListener;
import onedsix.gen.handling.DatagenHandler;
import onedsix.graphics.GeometryHandler;
import onedsix.graphics.Title;
import org.slf4j.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static onedsix.systems.CrashHandler.createCrash;
import static onedsix.systems.GameSettings.*;
import static onedsix.systems.ScreenshotFactory.saveScreenshot;
import static onedsix.gen.asm.AsmTest.asmTest;
import static onedsix.event.settings.SettingsChangeManager.addSettingsChangeListener;
import static onedsix.graphics.Title.randomSplash;
import static onedsix.gen.js.Javascript.jsInit;
import static onedsix.Vars.*;

public class OneDSix extends ApplicationAdapter implements SettingsChangeListener, LifecycleListener {
    
    private static final Logger L = LoggerFactory.getLogger(OneDSix.class);
    
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
    
    @Override
    public void create() {
        
        asmTest();
        
        // Change Phase
        Vars.currentPhase = Vars.Phases.DISCOVERY;
        
        // Needs to load these at startup. Read on first frame!
        Vars.loadList.add("playerdata.json");
        Vars.loadList.add("grass.json");
        Vars.loadList.add("back_bay.json");
        
        // Settings
        addSettingsChangeListener(OneDSix.this);
        readSettings();
        
        // Version and Branch
        try (InputStream inputStream = Gdx.files.internal("version.properties").read()) {
            Properties properties = new Properties();
            properties.load(inputStream);
            String version = properties.getProperty("version");
            L.info(version);
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        // Javascript
        jsInit();
    
        // Create temporary directory
        File tempdir = new File("./temp/");
        if (!tempdir.exists()) {
            tempdir.mkdirs();
        }
        
        // Version and Lights
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
        Gdx.input.setInputProcessor(new InputMultiplexer(Vars.camController));
        
        // Window size bugfix
        Vars.windowSize.add(800);
        Vars.windowSize.add(480);
        Vars.windowSize.add(0);
        Vars.windowSize.add(0);
        
        // Beta/Dev stuff
        testing();
    }
    
    @Override
    public void render() {
        
        DatagenHandler.read();
    
        if ((System.nanoTime() + 5000) <= Vars.windowSize.get(2) && Vars.windowSize.get(3) == 0) {
            L.info("Resized ("+ Vars.windowSize.get(0)+", "+ Vars.windowSize.get(1)+")");
            Vars.windowSize.set(3, 1);
        }
    
        boolean tookScreenshot = false;
        String screenshotFile = "";
        
        if (true) {
            if (Vars.keyCalls.isDebug()) {
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
    
            if (Vars.keyCalls.isInventory() &&
                        Gdx.input.isKeyJustPressed(Input.Keys.E)) {
                // Inventory
            }
        }
        
        if (Vars.shouldAttemptRendering) {
            // Camera
            // Player's perFrame(); is in here!
            if (Objects.nonNull(Vars.player)) {
                Vars.cam3D.position.set(GeometryHandler.addVectors(Vars.player.perFrame(), Vars.camOffset));
                //Vars.cam3D.lookAt(); // Camera View Offset
            }
            Vars.cam3D.update(true);
            // Window
            Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            // Pre-Render Fix
            ScreenUtils.clear(0, 0, 0, 1, true);
            //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT); ehhh???
    
            // Init HUD camera and SpriteBatch to render text
            Vars.camHUD.update();
            Vars.spriteBatch.setProjectionMatrix(Vars.camHUD.combined);
            Vars.spriteBatch.begin();
            
            if (Vars.currentPhase > Vars.Phases.INIT) {
                // Render Models and Geometry
                Vars.modelBatch.begin(Vars.cam3D);
                for (ModelInstance m : Vars.modelInstances) {
                    Vars.modelBatch.render(m, Vars.environment);
                }
                Vars.modelBatch.end();
    
                // Render Player
                if (Objects.nonNull(Vars.player)) {
                    Vars.player.getImg().setPosition(Vars.player.getPosition());
                    Vars.player.getImg().lookAt(Vars.cam3D.position, Vars.cam3D.up);
                    Vars.decalBatch.add(Vars.player.getImg());
                }
    
                // Render other Decals
                for (Decal d : Vars.decals) {
                    d.lookAt(Vars.cam3D.position, Vars.cam3D.up);
                    Vars.decalBatch.add(d);
                }
                Vars.decalBatch.flush();
                
                // Render Sprites
                for (Sprite s : Vars.sprites) {
                    s.draw(Vars.spriteBatch);
                }
                Vars.font.setColor(0, 0, 0, 1);
            }
            else {
                
                // Render Font
                Vars.font.setColor(1, 1, 1, 1);
                Vars.font.draw(Vars.spriteBatch, "loading...", Vars.camHUD.viewportWidth/2, Vars.camHUD.viewportWidth/5);
    
                AtomicInteger line = new AtomicInteger(1);
                if (Vars.debugMode) {
                    Vars.font.draw(Vars.spriteBatch, "Loading Log Disabled; Debug Mode Enabled", 0, Vars.font.getLineHeight());
                } else {
                    loadingLogs.descendingIterator().forEachRemaining((ll) -> {
                        Vars.font.setColor(1, 1, 1, 1);
                        Vars.font.draw(Vars.spriteBatch, ll.text, 0, (Vars.font.getLineHeight() * line.get()));
                        line.getAndIncrement();
                    });
                }
    
            }
            if (Vars.debugMode) {
                Vars.font.draw(Vars.spriteBatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 0, Vars.camHUD.viewportHeight);
                Vars.font.draw(Vars.spriteBatch, "DTT: " + Gdx.graphics.getDeltaTime(), 0, Vars.camHUD.viewportHeight - (Vars.font.getLineHeight()));
                Vars.font.draw(Vars.spriteBatch, "FID: " + Gdx.graphics.getFrameId(), 0, Vars.camHUD.viewportHeight - (Vars.font.getLineHeight() * 2));
                Vars.font.draw(Vars.spriteBatch, "POS: " + Vars.cam3D.position.toString(), 0, Vars.camHUD.viewportHeight - (Vars.font.getLineHeight() * 3));
            }
            if (tookScreenshot) {
                Vars.font.draw(Vars.spriteBatch, "Screenshot saved to " + screenshotFile, 0, Vars.camHUD.viewportHeight);
            }
            
            // Dispose Spritebatch
            Vars.spriteBatch.end();
        }
    }
    
    @Override
    public void dispose() {
        
        // Dispose assets
        Vars.spriteBatch.dispose();
        Vars.modelBatch.dispose();
        for (ModelInstance m : Vars.modelInstances) {
            m.model.dispose();
        }
        Vars.decalBatch.dispose();
        
        // Delete Temp Folder
        File tempdir = new File("./temp/");
        for (File f : tempdir.listFiles()) {
            f.delete();
        }
        tempdir.delete();
        
        L.info("Stopped");
    }
    
    @Override
    public void resize(int width, int height) {
        Vars.windowSize.set(0, width);
        Vars.windowSize.set(1, height);
        Vars.windowSize.set(2, (int) System.nanoTime());
        Vars.windowSize.set(3, 0);
        Gdx.graphics.setWindowedMode(width, height);
    }
    
    @Override
    public void pause() {
        Vars.shouldAttemptRendering = false;
        L.info("Paused");
    }
    
    @Override
    public void resume() {
        Vars.shouldAttemptRendering = true;
        L.info("Unpaused");
    }

    /** Event listener for when settings are changed. */
    @Override
    public void settingsChanged(SettingsChangeEvent event, SettingsJson js) {
        L.info("Refreshing settings...");
        Gdx.graphics.setForegroundFPS(Vars.settings.targetFps);
        Gdx.graphics.setVSync(Vars.settings.useVsync);
    
        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            // If running on Desktop and splash texts are turned off, set title without splash.
            if (!Vars.settings.useSplashText) {
                Gdx.graphics.setTitle(Title.title);
            }
            // Otherwise, go wild.
            else {
                Gdx.graphics.setTitle(randomSplash());
            }
        }
    }
}
