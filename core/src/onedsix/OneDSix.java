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
import onedsix.gen.DatagenHandler;
import onedsix.graphics.GeometryHandler;
import onedsix.graphics.Title;
import onedsix.util.Logger;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static onedsix.gen.discovery.ModDiscovery.startupModDiscovery;
import static onedsix.net.http.Requests.GithubRequest.makeRequest;
import static onedsix.systems.CrashHandler.createCrash;
import static onedsix.systems.GameSettings.*;
import static onedsix.systems.ScreenshotFactory.saveScreenshot;
import static onedsix.event.settings.SettingsChangeManager.addSettingsChangeListener;
import static onedsix.graphics.Title.randomSplash;
import static onedsix.gen.js.Javascript.jsInit;
import static onedsix.Vars.*;
import static onedsix.util.FileHandler.createDirectory;

public class OneDSix extends ApplicationAdapter implements SettingsChangeListener, LifecycleListener {
    
    private static final Logger L = new Logger(OneDSix.class);
    
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
        
        Model model = modelBuilder.createBox(50f, 1f, 50f,
                new Material(matAttr),
                miscAttr);
        modelInstances.add(GeometryHandler.transformShorthand(0, -10, 0, new ModelInstance(model)));
    }
    
    @Override
    public void create() {
        
        makeRequest();
        
        // Change Phase
        currentPhase = Phases.DISCOVERY;
        
        // Needs to load these at startup. Read on first frame!
        loadList.add("playerdata.json");
        loadList.add("gamedata.json");
        loadList.add("grass.json");
        loadList.add("back_bay.json");
        
        // Mods
        startupModDiscovery();
        
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
    
        // Create directories
        createDirectory("./temp/");
        createDirectory("./mods/");
        createDirectory("./screenshots/");
        createDirectory("./logs/");
        
        // Version and Lights
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        // Cameras
        camOffset = new Vector3(10f, 10f, 0f);
        cam3D.position.set(camOffset);
        cam3D.lookAt(0f,0f,0f);
        cam3D.near = 1f;
        cam3D.far = 300f;
        cam3D.update();
        camHUD.position.set(camHUD.viewportWidth / 2.0f, camHUD.viewportHeight / 2.0f, 1.0f);
        Gdx.input.setInputProcessor(new InputMultiplexer(camController));
        
        // Window size bugfix
        windowSize.add(800);
        windowSize.add(480);
        windowSize.add(0);
        windowSize.add(0);
        
        // Beta/Dev stuff
        testing();
    }
    
    @Override
    public void render() {
        
        DatagenHandler.read();
    
        if ((System.nanoTime() + 5000) <= windowSize.get(2) && windowSize.get(3) == 0) {
            L.info("Resized ("+ windowSize.get(0)+", "+ windowSize.get(1)+")");
            windowSize.set(3, 1);
        }
    
        boolean tookScreenshot = false;
        String screenshotFile = "";
        
        if (true) {
            if (keyCalls.debug) {
                if (Gdx.input.isKeyJustPressed(Input.Keys.F2) ||
                            Gdx.input.isKeyJustPressed(Input.Keys.O)) {
                    tookScreenshot = true;
                    screenshotFile = saveScreenshot();
                }
                if (Gdx.input.isKeyJustPressed(Input.Keys.F3) ||
                            Gdx.input.isKeyJustPressed(Input.Keys.P)) {
                    debugMode = !debugMode;
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
    
            if (keyCalls.inventory &&
                        Gdx.input.isKeyJustPressed(Input.Keys.E)) {
                // Inventory
            }
        }
        
        if (shouldAttemptRendering) {
            // Camera
            // Player's perFrame(); is in here!
            if (Objects.nonNull(player)) {
                cam3D.position.set(GeometryHandler.addVectors(player.perFrame(), camOffset));
                //cam3D.lookAt(); // Camera View Offset
            }
            cam3D.update(true);
            // Window
            Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            // Pre-Render Fix
            ScreenUtils.clear(0, 0, 0, 1, true);
            //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT); ehhh???
    
            // Init HUD camera and SpriteBatch to render text
            camHUD.update();
            spriteBatch.setProjectionMatrix(camHUD.combined);
            spriteBatch.begin();
            
            if (currentPhase >= Phases.INIT) {
                // Render Models and Geometry
                modelBatch.begin(cam3D);
                for (ModelInstance m : modelInstances) {
                    modelBatch.render(m, environment);
                }
                modelBatch.end();
    
                // Render Player
                if (Objects.nonNull(player)) {
                    player.img.setPosition(player.position);
                    player.img.lookAt(cam3D.position, cam3D.up);
                    decalBatch.add(player.img);
                }
    
                // Render other Decals
                for (Decal d : decals) {
                    d.lookAt(cam3D.position, cam3D.up);
                    decalBatch.add(d);
                }
                decalBatch.flush();
                
                // Render Sprites
                for (Sprite s : sprites) {
                    s.draw(spriteBatch);
                }
                font.setColor(0, 0, 0, 1);
            }
            else {
                
                // Render Font
                font.setColor(1, 1, 1, 1);
                font.draw(spriteBatch, "loading...", camHUD.viewportWidth/2, camHUD.viewportWidth/5);
    
                AtomicInteger line = new AtomicInteger(1);
                if (debugMode) {
                    font.draw(spriteBatch, "Loading Log Disabled; Debug Mode Enabled", 0, font.getLineHeight());
                } else {
                    loadingLogs.descendingIterator().forEachRemaining((ll) -> {
                        font.setColor(1, 1, 1, 1);
                        font.draw(spriteBatch, ll.text, 0, (font.getLineHeight() * line.get()));
                        line.getAndIncrement();
                    });
                }
    
            }
            if (debugMode) {
                font.draw(spriteBatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 0, camHUD.viewportHeight);
                font.draw(spriteBatch, "DTT: " + Gdx.graphics.getDeltaTime(), 0, camHUD.viewportHeight - (font.getLineHeight()));
                font.draw(spriteBatch, "FID: " + Gdx.graphics.getFrameId(), 0, camHUD.viewportHeight - (font.getLineHeight() * 2));
                font.draw(spriteBatch, "POS: " + cam3D.position.toString(), 0, camHUD.viewportHeight - (font.getLineHeight() * 3));
            }
            if (tookScreenshot) {
                font.draw(spriteBatch, "Screenshot saved to " + screenshotFile, 0, camHUD.viewportHeight);
            }
            
            // Dispose Spritebatch
            spriteBatch.end();
        }
    }
    
    @Override
    public void dispose() {
        
        // Dispose assets
        spriteBatch.dispose();
        modelBatch.dispose();
        for (ModelInstance m : modelInstances) {
            m.model.dispose();
        }
        decalBatch.dispose();
        
        // Delete Temp Folder
        File tempdir = new File("./temp/");
        for (File f : tempdir.listFiles()) {
            f.delete();
        }
        
        L.info("Stopped");
    }
    
    @Override
    public void resize(int width, int height) {
        windowSize.set(0, width);
        windowSize.set(1, height);
        windowSize.set(2, (int) System.nanoTime());
        windowSize.set(3, 0);
        Gdx.graphics.setWindowedMode(width, height);
    }
    
    @Override
    public void pause() {
        shouldAttemptRendering = false;
        L.info("Paused");
    }
    
    @Override
    public void resume() {
        shouldAttemptRendering = true;
        L.info("Unpaused");
    }

    /** Event listener for when settings are changed. */
    @Override
    public void settingsChanged(SettingsChangeEvent event, SettingsJson js) {
        L.info("Refreshing settings...");
        Gdx.graphics.setForegroundFPS(settings.targetFps);
        Gdx.graphics.setVSync(settings.useVsync);
    
        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            // If running on Desktop and splash texts are turned off, set title without splash.
            if (!settings.useSplashText) {
                Gdx.graphics.setTitle(Title.title);
            }
            // Otherwise, go wild.
            else {
                Gdx.graphics.setTitle(randomSplash());
            }
        }
    }
}
