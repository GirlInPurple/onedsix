package onedsix.core.util;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;

public class EnvType {

    public static final class ValidEnvironments {
        public static final int ANDROID = 0;
        public static final int DESKTOP = 1;
        public static final int SERVER = 6;
    }

    private static final Logger L = new Logger(EnvType.class);

    public ApplicationType appEnvironment;
    public boolean isDedicated;

    public EnvType(boolean isDedicated) {
        try {this.appEnvironment = Gdx.app.getType();}
        catch (NullPointerException ignored) {}
        this.isDedicated = isDedicated;
    }

    /** Outputs an int corresponding to the current environment */
    public int getEnv() {
        if (appEnvironment == null) return ValidEnvironments.SERVER;
        return appEnvironment.ordinal();
    }

}
