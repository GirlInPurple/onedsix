package onedsix.graphics;

import com.badlogic.gdx.Gdx;
import com.github.weisj.jsvg.SVGDocument;
import com.github.weisj.jsvg.geometry.size.FloatSize;
import com.github.weisj.jsvg.parser.SVGLoader;
import onedsix.util.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class SvgHandler {
    private static final Logger L = new Logger(SvgHandler.class);
    public static final SVGLoader loader = new SVGLoader();
    
    /**
     * Takes in a path to an internal file containing an SVG-formatted document.<br>
     * Assumes the file is in the {@code assets} directory.
     * */
    public static SVGDocument loadSvgFromFile(String path) {
        assert path.endsWith(".svg");
        InputStream svgUrl = Gdx.files.internal(path).read();
        return loader.load(svgUrl);
    }
    
    public static SVGDocument loadSvgFromString(String svg) {
        return loader.load(new ByteArrayInputStream(svg.getBytes(StandardCharsets.UTF_8)));
    }
    
    /**
     * Stores a compiled SVG document as a PNG in the temp directory.<br>
     * For use in all SVG related code.<br>
     * This code is very weirdly written, i recommend not touching it.
     * */
    public static String svgToPng(SVGDocument svg, String name) {
        try {
            // Get needed values
            FloatSize size = svg.size();
            String fileName = "./temp/"+name+".png";
            
            // Get the image to write
            BufferedImage bi = new BufferedImage((int) size.width, (int) size.height, 2);
            
            // Get the graphics context of the buffered image
            Graphics2D g = bi.createGraphics();
    
            // Render the SVG onto the buffered image
            svg.render(null, g);
    
            // Dispose the graphics context
            g.dispose();
    
            // Save the buffered image as PNG
            File outputfile = new File(fileName);
            ImageIO.write(bi, "png", outputfile);
            L.loadingLogger("Saved temporary SVG to "+fileName, Logger.Level.INFO);
            return fileName;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return "error.svg";
        }
    }
}
