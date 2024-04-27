package onedsix.graphics;

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
    
    /**
     * A shorthand method for moving an instance to a specified location.<br>
     * Takes in a list and a position, then returns a modified version of that instance<br>
     * <b>Does not remove or replace the original instance,</b> you must do that yourself.
     * */
    public static ModelInstance transformShorthand(float x, float y, float z, List<ModelInstance> lm, int pos) {
        ModelInstance m = lm.get(pos);
        return transformShorthand(x, y, z, m);
    }
    
    /**
     * Takes in multiple vectors and adds them together.
     * */
    public static Vector3 addVectors(Vector3... vectors) {
        float x = 0;
        float y = 0;
        float z = 0;
        for (Vector3 v : vectors) {
            x += v.x;
            y += v.y;
            z += v.z;
        }
        return new Vector3(x, y, z);
    }
}
