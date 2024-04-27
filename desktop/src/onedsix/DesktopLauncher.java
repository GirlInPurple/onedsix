package onedsix;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	private static final Logger L = LoggerFactory.getLogger(DesktopLauncher.class);
	
	public static void main(String[] args) {
		L.info("Starting Lwjgl3...");
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

		// Moved splash text to core/src/OneDSix line 80
		config.setWindowIcon("./assets/icon.png");
		config.setWindowedMode(800, 480);
		config.useVsync(true);
		config.setForegroundFPS(60);

		Lwjgl3Application l3a = new Lwjgl3Application(new OneDSix(), config);
	}
}
