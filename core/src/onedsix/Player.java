package onedsix;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector3;
import onedsix.gen.assets.Entity;
import onedsix.gen.assets.Faction;
import onedsix.gen.assets.Effect;
import onedsix.gen.assets.Item;
import onedsix.gen.assets.Perk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

import static onedsix.Vars.keyCalls;

public class Player extends Entity {
    
    private static final Logger L = LoggerFactory.getLogger(Player.class);
    
    public Player(Decal img, String name, int cash, HashMap<Faction, Integer> factionStanding, List<Effect> statusEffects, List<Item> inventory, List<Perk> perks, Vector3 position) {
        super(img, name, cash, factionStanding, statusEffects, inventory, perks, position);
        L.info("Successfully created player");
    }
    
    @Override
    public Vector3 perFrame() {
        if (keyCalls.isMovement()) {
            if (Gdx.input.isKeyPressed(Input.Keys.UP) ||
                        Gdx.input.isKeyPressed(Input.Keys.W)) {
                this.setPosition(new Vector3(
                        this.getPosition().x -= (this.getSpeed() * Gdx.graphics.getDeltaTime()),
                        this.getPosition().y,
                        this.getPosition().z
                ));
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN) ||
                        Gdx.input.isKeyPressed(Input.Keys.S)) {
                this.setPosition(new Vector3(
                        this.getPosition().x += (this.getSpeed() * Gdx.graphics.getDeltaTime()),
                        this.getPosition().y,
                        this.getPosition().z
                ));
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) ||
                        Gdx.input.isKeyPressed(Input.Keys.A)) {
                this.setPosition(new Vector3(
                        this.getPosition().x,
                        this.getPosition().y,
                        this.getPosition().z += (this.getSpeed() * Gdx.graphics.getDeltaTime())
                ));
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) ||
                        Gdx.input.isKeyPressed(Input.Keys.D)) {
                this.setPosition(new Vector3(
                        this.getPosition().x,
                        this.getPosition().y,
                        this.getPosition().z -= (this.getSpeed() * Gdx.graphics.getDeltaTime())
                ));
            }
        }
        
        // Needs to return position no matter what because camera uses it for its position!!!
        return this.getPosition();
    }
}
