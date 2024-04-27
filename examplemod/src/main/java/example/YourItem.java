package example;

import com.badlogic.gdx.math.Vector3;
import onedsix.Player;
import onedsix.gen.assets.*;

public class YourItem extends Item {
    
    // This means it will create an item with no texture, name, or model,
    // but will create everything else needed for the item to exist and be accessible.
    public YourItem(Attributes attributes, Recipe recipe, long roughCost) {
        super(attributes, recipe, roughCost);
    }
    
    // Whenever the item is used (outside battles) it will run this code here.
    @Override public void onUse(Player player) {
        player.position = new Vector3(
                player.position.x + 1,
                player.position.y,
                player.position.z
        );
    }
    
    // Your IDE will tell you to create these, you can ignore them for now
    @Override public void onInteract(Player player) {}
    @Override public void onInteractBattle(Player player) {}
    @Override public void onUseBattle(Player player) {}
}
