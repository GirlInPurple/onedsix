package onedsix.gen.assets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Model;
import lombok.Getter;
import lombok.Setter;

public class Equipable {
    @Getter @Setter private Texture img;
    @Getter @Setter private Model model;
    @Getter @Setter private String name;
    @Getter @Setter private int level;
}
