
/**
 * Helper contains methods that help
 * the application
 * 
 * @author Dytrich Nguyen
 *
 */
public class AppHelper {
	
	// Number of nodes. Also serves as an ID number
	private static int nodeCount = 0;
	public static int getNodeCount() {
		return nodeCount;
	}
	public static int new_id() {
		// Store the return value and then increment
		// the node count
		int toReturn = nodeCount;
		nodeCount++;
		return toReturn;
	}
	// Resets the nodeCount to 0
	public static void resetNodeCount() {
		nodeCount = 0;
	}
	//
	
	// An array representing the heuristic map
	public static int[][] heuristicMap = new int[][] {
		{ 2, 3, 4, 4, 4, 4, 3, 2},
		{ 3, 4, 6, 6, 6, 6, 4, 3},
		{ 4, 6, 8, 8, 8, 8, 6, 4},
		{ 4, 6, 8, 8, 8, 8, 6, 4},
		{ 4, 6, 8, 8, 8, 8, 6, 4},
		{ 4, 6, 8, 8, 8, 8, 6, 4},
		{ 3, 4, 6, 6, 6, 6, 4, 3},
		{ 2, 3, 4, 4, 4, 4, 3, 2}
	};
	//
	
	// An array indexing each possible move going counter-clockwise
	// starting from quadrant I of a Cartesian plane.
	public static int[][] moves = new int[][] {
		{ 2,  1}, { 1,  2},
		{-1,  2}, {-2,  1},
		{-2, -1}, {-1, -2},
		{ 1, -2}, { 2, -1}
	};
	//

	// Returns a representation of an 8x8 chess board with no
	// pieces
	public static int[][] newTileMap() {
		return new int[][] {
				{0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0}
		};
	}
	//
	
}