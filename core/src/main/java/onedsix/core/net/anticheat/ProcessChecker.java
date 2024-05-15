package onedsix.core.net.anticheat;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;

@Slf4j
public class ProcessChecker {

	private static final LinkedList<String> knownTools = new LinkedList<>();
	public static void addTool(String toolName) {knownTools.add(toolName);}

	public static LinkedList<String> getProcessList() {
		LinkedList<String> lines = new LinkedList<>();
		try {
			ProcessBuilder processBuilder = new ProcessBuilder("tasklist");
			Process process = processBuilder.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			reader.lines().forEach(lines::add);
		}
		catch (Exception e) {
			log.warn("Failed to check processes. Assuming the client is not cheating in good faith.", e);
		}
		return lines;
	}

	private LinkedList<String> processes;

	/** Used to check the processes running on the system.
	 * Is disabled by default, and only enabled whenever a server requests for it */
	public ProcessChecker() {}

	public ProcessChecker getProcesses() {
		this.processes = getProcessList();
		return this;
	}

	public void checkFor(LinkedList<String> processNames) {

	}

	public void checkFor(String processName) {

	}

	public void checkFor() {

	}

	void checkForSpecifedProcesses(LinkedList<String> processNames) {

	}
}
