package onedsix.client.graphics.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import onedsix.core.Vars;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static onedsix.core.util.Utils.addVectors;

public class GameScreen extends CustomScreen {
    @Override
    public void createScreen() {}


    @Override
    public void render(float delta) {

        if (Vars.keyCalls.inventory &&
                    Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            // Inventory
        }

        // Camera (ClientPlayer's perFrame() is in here!)
        if (Objects.nonNull(Vars.player)) {
            Vars.cam3D.position.set(addVectors(Vars.player.perFrame(), Vars.camOffset));
            //cam3D.lookAt(); // Camera View Offset
        }
        Vars.cam3D.update(true);

        // Init HUD camera and SpriteBatch to render text
        Vars.camHUD.update();

        if (true) {
            // Render Models and Geometry
            Vars.modelBatch.begin(Vars.cam3D);
            for (ModelInstance m : Vars.modelInstances) {
                Vars.modelBatch.render(m, Vars.environment);
            }
            Vars.modelBatch.end();

            // Render ClientPlayer
            if (Objects.nonNull(Vars.player)) {
                Vars.player.img.setPosition(Vars.player.position);
                Vars.player.img.lookAt(Vars.cam3D.position, Vars.cam3D.up);
                Vars.decalBatch.add(Vars.player.img);
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
                Vars.loadingLogs.descendingIterator().forEachRemaining((ll) -> {
                    Vars.font.setColor(1, 1, 1, 1);
                    Vars.font.draw(Vars.spriteBatch, ll.text, 0, (Vars.font.getLineHeight() * line.get()));
                    line.getAndIncrement();
                });
            }

        }

        super.render(delta);
    }
}
