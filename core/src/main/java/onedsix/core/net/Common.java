package onedsix.core.net;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class Common {
    
    static public final int port = 36000;
    
    static public void register (EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        
    }

}
