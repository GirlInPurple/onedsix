package onedsix.net.tcp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import onedsix.util.Logger;

public class TcpClient {
    
    private static final Logger L = new Logger(TcpClient.class);
    public static final SocketHints hints = new SocketHints();
    private static final Socket socket = Gdx.net.newClientSocket(Net.Protocol.TCP, "localhost", 36000, hints);
    
    public static void TcpClient() {
        L.info(String.valueOf(socket.isConnected()));
    }
    
    
}
