package onedsix.core.assets.abstracts;

import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector3;
import onedsix.core.assets.data.Attributes;
import onedsix.core.assets.data.Identifier;

public abstract class PlayerEntity extends Entity {
	public PlayerEntity(Identifier<? extends Entity> ident, Attributes attr, Decal img, String name, Vector3 position) {
		super(ident, attr, img, name, position);
	}
}
