package onedsix.core.assets.abstracts;

import com.badlogic.gdx.graphics.Texture;
import onedsix.core.assets.data.Attributes;
import onedsix.core.assets.data.Identifier;
import org.jetbrains.annotations.Nullable;

/**
 * The basis of everything in 1D6, the {@link Asset}.<br>
 * It is perfectly fine to set anything in the constructors of this object to {@code null}, as there is no code that does anything to them inside this class.
 * */
public abstract class Asset {
    
    public final Identifier<? extends Asset> ident;
    @Nullable final Attributes attributes;
    @Nullable public String assetName;
    @Nullable public Texture img;
    
    protected Asset(Identifier<? extends Asset> ident, @Nullable Attributes attributes, @Nullable String name, @Nullable Texture img) {
        this.ident = ident;
        this.attributes = attributes;
        this.assetName = name;
        this.img = img;
    }
    
    protected Asset(Identifier<? extends Asset> ident, @Nullable Attributes attributes, @Nullable String name) {
        this.ident = ident;
        this.attributes = attributes;
        this.assetName = name;
        this.img = null;
    }
    
    protected Asset(Identifier<? extends Asset> ident, @Nullable Attributes attributes) {
        this.ident = ident;
        this.attributes = attributes;
        this.assetName = null;
        this.img = null;
    }
    
    protected Asset(Identifier<? extends Asset> ident) {
        this.ident = ident;
        this.attributes = null;
        this.assetName = null;
        this.img = null;
    }
}
