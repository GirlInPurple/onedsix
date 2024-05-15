package onedsix.dedicated;

import onedsix.server.OnedsixServer;
import onedsix.core.util.Logger;

import static onedsix.core.util.Logger.WRITER;

public class DedicatedServerLauncher {

    public static final Logger L = new Logger(DedicatedServerLauncher.class);

    public static void main(String[] args) {
        L.print("Starting server...");

        new OnedsixServer(true);

        L.print("Stopped server!");
        WRITER.close();
    }

}
