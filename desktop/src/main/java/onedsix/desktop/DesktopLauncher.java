package onedsix.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import onedsix.core.client.OndsixClient;
import onedsix.core.util.Logger;

import static onedsix.core.util.Logger.WRITER;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	private static final Logger L = new Logger(DesktopLauncher.class);
	
	public static void main(String[] args) {
		L.print("Starting Lwjgl3...");
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

		//config.setWindowIcon("./icon.png");
		config.setWindowedMode(1920, 1440);
		config.setMaximized(true);
		config.useVsync(true);
		config.setForegroundFPS(60);

		new Lwjgl3Application(new OndsixClient(), config);
		L.print("Stopped Lwjgl3!");
		WRITER.close();
	}
}
