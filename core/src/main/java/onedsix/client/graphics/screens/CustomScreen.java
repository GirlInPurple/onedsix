package onedsix.client.graphics.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.kotcrab.vis.ui.widget.VisScrollPane;
import com.kotcrab.vis.ui.widget.VisTable;
import onedsix.client.graphics.ScreenHandler;
import onedsix.core.Vars;
import onedsix.core.util.Logger;

import java.util.Objects;

import static onedsix.core.util.Utils.addVectors;

/** The root of all screens. Contains some needed variables and some basic overrides for {@link Screen} <br>Overwrite {@link CustomScreen#createScreen()} to create a new screen. */
public abstract class CustomScreen implements Screen {
    private static final Logger L = new Logger(CustomScreen.class);
    public boolean renderBackground = true;
    public static final NinePatchDrawable background = new NinePatchDrawable(
            new NinePatch(new Texture(Gdx.files.internal("error_large.png")),
                    5, 5, 5, 5));
    public final Stage stage = new Stage();
    public final VisTable root = new VisTable();
    public final VisScrollPane scroll = new VisScrollPane(null);

    public abstract void createScreen();

    @Override public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override public void render(float delta) {
        this.stage.act(delta);

        boolean tookScreenshot = false;
        String screenshotFile = "";

        // Window
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        // Start SpriteBatch
        Vars.spriteBatch.setProjectionMatrix(Vars.camHUD.combined);
        Vars.spriteBatch.begin();

        // Render Models and Geometry
        ScreenHandler.renderModels(Vars.modelInstances, Vars.environment).draw(Vars.spriteBatch);

        // Render ClientPlayer
        if (Objects.nonNull(Vars.player)) {
            Vars.player.img.setPosition(Vars.player.position);
            Vars.player.img.lookAt(Vars.cam3D.position, Vars.cam3D.up);
            Vars.decalBatch.add(Vars.player.img);
        }

        // Render other Decals
        for (Decal d : Vars.decals) {
            d.lookAt(addVectors(d.getPosition(), Vars.camOffset), Vars.cam3D.up);
            Vars.decalBatch.add(d);
        }
        Vars.decalBatch.flush();

        // Render Sprites
        for (Sprite s : Vars.sprites) {
            s.draw(Vars.spriteBatch);
        }

        // Render Debug text...
        if (Vars.debugMode) {
            Vars.font.draw(Vars.spriteBatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 0, Vars.camHUD.viewportHeight);
            Vars.font.draw(Vars.spriteBatch, "DTT: " + Gdx.graphics.getDeltaTime(), 0, Vars.camHUD.viewportHeight - (Vars.font.getLineHeight()));
            Vars.font.draw(Vars.spriteBatch, "FID: " + Gdx.graphics.getFrameId(), 0, Vars.camHUD.viewportHeight - (Vars.font.getLineHeight() * 2));
            Vars.font.draw(Vars.spriteBatch, "POS: " + Vars.cam3D.position.toString(), 0, Vars.camHUD.viewportHeight - (Vars.font.getLineHeight() * 3));
        }
        // ...and Screenshot text
        if (tookScreenshot && !Vars.debugMode) {
            Vars.font.draw(Vars.spriteBatch, "Screenshot saved to " + screenshotFile, 0, Vars.camHUD.viewportHeight);
        }

        // End SpriteBatch
        Vars.spriteBatch.end();

        this.stage.draw();
    }

    @Override public void resize(int width, int height) {
        Vars.windowSize.set(0, width);
        Vars.windowSize.set(1, height);
        Vars.windowSize.set(2, (int) System.nanoTime());
        Vars.windowSize.set(3, 0);
        //Gdx.graphics.setWindowedMode(width, height);
        Vars.viewport3D.update(width, height, true);
        Vars.viewportHUD.update(width, height, true);
    }

    @Override public void pause() {}

    @Override public void resume() {}

    @Override public void hide() {}

    @Override public void dispose() {}

    @Override public String toString() {
        return this.getClass().getName();
    }
}
