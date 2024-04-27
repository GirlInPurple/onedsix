package onedsix.net.tcp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import onedsix.util.Logger;

import static onedsix.net.tcp.TcpClient.hints;

public class TcpServer {
    
    private static final Logger L = new Logger(TcpServer.class);
    
    private static final ServerSocket server = Gdx.net.newServerSocket(Net.Protocol.TCP, 36000, new ServerSocketHints());
    
    public static void TcpServer() {
        L.info(String.valueOf(server.accept(hints)));
    }
    
    
    
}
