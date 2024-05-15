package onedsix.client.graphics;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

import java.util.List;

public class GeometryHandler {

    /**
     * A shorthand method for moving an instance to a specified location.
     * */
    public static ModelInstance transformShorthand(float x, float y, float z, ModelInstance m) {
        // Create needed Variables
        Matrix4 transform = new Matrix4();
        Vector3 translation = new Vector3();
        // Set the needed position
        translation.x = x;
        translation.y = y;
        translation.z = z;
        // Set the modified transform
        transform.translate(translation);
        // Update vector position
        m.transform.set(transform);
        return m;
    }
}
