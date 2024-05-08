package onedsix.core.util;

import com.badlogic.gdx.math.Vector3;

public class Utils {
    
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
