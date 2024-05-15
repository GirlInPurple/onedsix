package onedsix.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector3;
import onedsix.core.Vars;
import onedsix.core.assets.abstracts.Entity;
import onedsix.core.assets.abstracts.PlayerEntity;
import onedsix.core.assets.data.Attributes;
import onedsix.core.assets.data.Identifier;
import onedsix.core.util.Logger;

public class ClientPlayer extends PlayerEntity {

    private static final Logger L = new Logger(ClientPlayer.class);

    public ClientPlayer(Identifier<? extends Entity> ident, Attributes attr, Decal img, String name, Vector3 position) {
        super(ident, attr, img, name, position);
        L.info("Successfully created player");
    }

    @Override
    public Vector3 perFrame() {
        if (Vars.keyCalls.movement) {
            if (Gdx.input.isKeyPressed(Input.Keys.UP) ||
                        Gdx.input.isKeyPressed(Input.Keys.W)) {
                this.position = new Vector3(
                        this.position.x -= (this.speed * Gdx.graphics.getDeltaTime()),
                        this.position.y,
                        this.position.z
                );
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN) ||
                        Gdx.input.isKeyPressed(Input.Keys.S)) {
                this.position = new Vector3(
                        this.position.x += (this.speed * Gdx.graphics.getDeltaTime()),
                        this.position.y,
                        this.position.z
                );
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) ||
                        Gdx.input.isKeyPressed(Input.Keys.A)) {
                this.position = new Vector3(
                        this.position.x,
                        this.position.y,
                        this.position.z += (this.speed * Gdx.graphics.getDeltaTime())
                );
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) ||
                        Gdx.input.isKeyPressed(Input.Keys.D)) {
                this.position = new Vector3(
                        this.position.x,
                        this.position.y,
                        this.position.z -= (this.speed * Gdx.graphics.getDeltaTime())
                );
            }
        }

        // Needs to return position no matter what because camera uses it for its position!!!
        return this.position;
    }
}
