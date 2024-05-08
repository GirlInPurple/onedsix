package onedsix.core.net;

import onedsix.core.Vars;
import onedsix.core.util.Logger;

public class TcpClient {
    
    private static final Logger L = new Logger(TcpClient.class);
    
    public TcpClient() {
        L.info("Creating OndsixClient");
        
        Vars.networker.start();
    
        Common.register(Vars.networker);
    }
    
    
}
