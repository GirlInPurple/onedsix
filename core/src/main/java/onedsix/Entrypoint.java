package onedsix;

import com.badlogic.gdx.backends.lwjgl3.*;
import lombok.extern.slf4j.Slf4j;
import onedsix.client.OndsixClient;
import onedsix.server.OnedsixServer;

import static onedsix.core.util.Logger.WRITER;

@Slf4j
public class Entrypoint {

	public static void main(String[] args) {
		log.info("Entrypoint!");
		desktop();
	}

	private static void desktop() {
		log.info("Starting Lwjgl3...");
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

		//config.setWindowIcon("./icon.png");
		config.setWindowedMode(1920, 1440);
		config.setMaximized(true);
		config.useVsync(true);
		config.setForegroundFPS(60);

		new Lwjgl3Application(new OndsixClient(), config);
		log.info("Stopped Lwjgl3!");
		WRITER.close();
	}

	private static void server() {
		log.info("Starting server...");

		new OnedsixServer(true);

		log.info("Stopped server!");
		WRITER.close();
	}
}
