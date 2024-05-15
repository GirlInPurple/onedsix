package onedsix.client.graphics;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.kotcrab.vis.ui.widget.VisLabel;
import onedsix.core.Vars;

import java.util.HashMap;
import java.util.Map;

public class ScreenHandler {

    public static class FontHandler {

        private final Map<Integer, BitmapFont> fonts = new HashMap<>();

        private final String fontPath;

        public FontHandler(String fontPath) {
            this.fontPath = fontPath;
        }

        public BitmapFont getTTFFont(final int size, Color color) {
            if(this.fonts.containsKey(size)) return this.fonts.get(size);

            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(this.fontPath));
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.size = size;

            BitmapFont font = generator.generateFont(parameter);
            font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            font.setColor(color);

            this.fonts.put(size, font);

            return font;
        }
    }

    public static class LabelHandler {

        public LabelHandler() {}

        public VisLabel createLabel(final String text, final int size, final Color color) {
            Label.LabelStyle style = new Label.LabelStyle();
            style.font = new FontHandler("LRpixel.ttf").getTTFFont(size, new Color(1, 1, 1, 1));
            style.fontColor = color;

            return new VisLabel(text, style);
        }
    }

    /** Captures the input ModelInstance(s) and outputs a Sprite after rendering it internally. */
    public static Sprite renderModels(Iterable<ModelInstance> modelInstances, Environment environment) {
        FrameBuffer frameBuffer = null;
        try {
            frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), true);
            frameBuffer.begin();

            Vars.modelBatch.begin(Vars.cam3D);
            Vars.modelBatch.render(modelInstances, environment);
            Vars.modelBatch.end();

            frameBuffer.end();
            return new Sprite(frameBuffer.getColorBufferTexture());
        }
        finally {
            if (frameBuffer != null) frameBuffer.dispose();
        }
    }
}
