package basemod.entities;

import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.math.Vector3;
import onedsix.core.assets.abstracts.Entity;
import onedsix.core.assets.data.Attributes;
import onedsix.core.assets.data.Identifier;
import basemod.entities.data.Brain;

public abstract class AiEntity extends Entity implements Brain {
	public AiEntity(Identifier<? extends Entity> ident, Attributes attr, Decal img, String name, Vector3 position) {
		super(ident, attr, img, name, position);
	}


}
