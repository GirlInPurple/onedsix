package onedsix.server;

import com.badlogic.gdx.Gdx;
import onedsix.core.Vars;
import onedsix.core.util.*;

public class OnedsixServer implements Runnable {

    private static final Logger L = new Logger(OnedsixServer.class);

    /** Creates a new server.<br> If {@code isDedicated} is true, the server will not activate any client sided code.<br>If false, it runs an internal server instead. */
    public OnedsixServer(boolean isDedicated) {
        Vars.envType = new EnvType(isDedicated);
        Vars.serverInit();

        if (!isDedicated)
            Gdx.app.postRunnable(this);
        else
            this.run();
    }

    /** OnDSix's Dedicated Server. */
    @Override
    public void run() {
    }
}
