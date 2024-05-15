package onedsix.core.net.anticheat;

import com.badlogic.gdx.Gdx;
import onedsix.core.util.Pair;

import java.util.LinkedList;

public class MouseTracker {

	private static final LinkedList<Pair<Integer, Integer>> path = new LinkedList<>();
	public static void addPosition(int x, int y) {path.add(new Pair<>(x, y));}
	public static void addPosition() {path.add(new Pair<>(Gdx.input.getX(), Gdx.input.getY()));}
	public static LinkedList<Pair<Integer, Integer>> getPath() {return path;}

	public static void checkMousePath() {

	}

}
