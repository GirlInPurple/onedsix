package basemod.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/** A complete implementation of the A* pathing algorithm.
 * {@link }*/
@Slf4j
@Data
public class AStar {

	public final LinkedList<Node> network;

	private static class Node {
		private Node parent;
		private LinkedList<Node> neighbours;
		private double cost, heuristic, function;
		private boolean valid;
	}
}
