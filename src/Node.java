import java.util.ArrayList;


/**
 * This class represents a node in a tree
 * representing the solution to the problem.
 * 
 * @author Dytrich
 *
 */
public class Node {

	// ID of the node
	private int id;
	public int getId() {
		return id;
	}
	
	// Depth of the node
	private int depth;
	public int getDepth() {
		return depth;
	}
	
	// Current position of the knight
	private int[] position;
	public int[] getPosition() {
		return position;
	}
	
	// ID of the parent. -1 if node is root.
	private int parentId;
	public int getParentId() {
		return parentId;
	}
	
	// List of direct children from node
	private ArrayList<Node> children;
	public ArrayList<Node> getChildren() {
		return children;
	}
	
	// Returns the IDs of each child
	public ArrayList<Integer> childrenId() {
		ArrayList<Integer> toReturn = new ArrayList<Integer>();
		for(Node n : children) {
			toReturn.add(n.getId());
		}
		return toReturn;
	}
	
	/**
	 * A 2D-array that contains information about what tiles have
	 * been landed on by the knight. I'm using binary to represent
	 * boolean variables as they are more natural to interpret for me.
	 * They are also much faster to type. N > 0 will be interpreted as
	 * true; N <= 0 will be interpreted as false.
	 */
	private int[][] tileMap;
	public int[][] getTileMap() {
		return tileMap;
	}
	
	// The previous move. -1 if root.
	private int prevMove;
	public int getPrevMove() {
		return prevMove;
	}
	
	// Constructor
	public Node(int _depth, int[] _position, int _parentId, int[][] _tileMap, int _prevMove)
	{
		// Automatically generate a new ID and assign it
		id = AppHelper.new_id();
		depth = _depth;
		position = _position;
		parentId = _parentId;
		tileMap = _tileMap;
		prevMove = _prevMove;
		
		// Mark the starting position as being landed on
		// tileMap[][] effectively represents a (y, x) plane
		// with the origin at the top left corner, like curses.
		// Since @position represents an (x, y) coordinate pair,
		// I set the tileMap value like so.
		tileMap[position[1]][position[0]] = 1;
		
		// Recursively generate the children of the node
		// Implementation note: This means that when the root is
		// generated, so will every child node. This is possibly
		// unwanted behavior, so alternatively it could be manually
		// called.
		children = genChildren();
	}
	//

	// Returns an ArrayList of children
	private ArrayList<Node> genChildren() {
		// Initialize an array of the same length as moves,
		// as there are some positions where all 7 moves are
		// possible.
		// TODO: Potentially refactor to use an ArrayList to Array
		ArrayList<Node> toReturn = new ArrayList<Node>();

		// Old method
		//int[] legalMoves = getMoves();
		
		// New method
		ArrayList<Integer> legalMoves = filteredMoves();
		
		for(int move : legalMoves) {
			// Calculate the new position
			int[] new_pos = new int[]{
                position[0] + AppHelper.moves[move][0],
                position[1] + AppHelper.moves[move][1]
			};
			// Add the Node to the toReturn ArrayList
			toReturn.add(new Node(depth + 1, new_pos, id, cloneTileMap(), move));
		}
		
		return toReturn;
	}
	//
	
	/**
	 * Returns all unique moves for the current position
	 * these moves are returned unfiltered, i.e. they do
	 * not pay attention to the priority heuristic. Thus,
	 * they will have to be filtered using the filterMoves()
	 * method. However, this allows this method to be used in
	 * a more computationally expensive algorithm since it 
	 * does not natively return priority-sorted values.
	 */
	private ArrayList<Integer> getMoves() {
		// There are a total of 8 possible moves, so the array
		// must represent the maximum expected number of elements.
		ArrayList<Integer> toReturn = new ArrayList<Integer>();

		// Current position of the knights
		int[] current_pos = position;
		
		// Iterate over each move.
		for(int i = 0; i < AppHelper.moves.length; i++)  {
			
			// Retrieve the ith move from moves
			int[] move = AppHelper.moves[i];
			
			// Compute a potential position
			int[] potential_pos = new int[] {
				current_pos[0] + move[0],
				current_pos[1] + move[1]
			};
			
			// Check if the potential move is legal
			boolean isLegal = (potential_pos[0] >= 0 && potential_pos[0] <= 7 &&
					   		   potential_pos[1] >= 0 && potential_pos[1] <=7)
					   		   ? true : false;
			
			// Check if the move is unique. This also includes the
			// isLegal boolean, so if isUnique is true, it must also
			// be a legal move.
			boolean isUnique = (isLegal && tileMap[potential_pos[1]][potential_pos[0]] == 0)
								? true : false;
			
			// Assign the move index to an element in the return array
			if(isUnique){
				toReturn.add(i);
			}
		}
		return toReturn;
	}
	//
	
	/**
	 * A method to filter moves in terms of priority.
	 * It will return an array that contains only the
	 * most economic moves. I.e. if getMoves()
	 */
	private ArrayList<Integer> filteredMoves() {
		// Initialize an list of values to check as returned by getMoves()
		ArrayList<Integer> toCheck = getMoves();

		// Initialize the return list of the same size as the input parameter
		// as that will be the maximum number of elements to expect
		ArrayList<Integer> toReturn = new ArrayList<Integer>();
		
		// Reference to the position attribute
		int[] current_pos = position;
		
		// Initialize a very large value to compare economic values of moves
		// using a similar algorithm to my chapter 5 exercise 11 solution
		int currentSmallest = Integer.MAX_VALUE;
		
		// Iterate over each element in toCheck to find the lowest value
		// TODO: Reduce this redundant code that computes potential positions
		for(int i = 0; i < toCheck.size(); i++) {

			// Reference a move in moves
			int[] move = AppHelper.moves[toCheck.get(i)];

			// Compute a potential position
			int[] potential_pos = new int[] {
                current_pos[0] + move[0],
                current_pos[1] + move[1]
			};
        
			// Computes the priority of a tile based on the heuristic map
			int current_priority = AppHelper.heuristicMap[potential_pos[1]][potential_pos[0]];
        
			if( current_priority < currentSmallest) {
                currentSmallest = current_priority;
			}
		}
		
		// Iterate over each toCheck and compare the priority of each
		for(int i = 0; i < toCheck.size(); i++) {
			
			// Reference a move in moves
			int move[] = AppHelper.moves[toCheck.get(i)];
                                
			// Compute a potential position
			int[] potential_pos = new int[] {
                current_pos[0] + move[0],
                current_pos[1] + move[1]
			};

			// Computes the priority of a tile based on the heuristic map
			int current_priority = AppHelper.heuristicMap[potential_pos[1]][potential_pos[0]];

			// Check if the move is lower or equal to the value of currentSmallest
			boolean isPriority = (current_priority <= currentSmallest) ? true : false;
        
			if(isPriority) {
                        
                // Assign the element in toCheck to an element in the return value
                toReturn.add(toCheck.get(i));
			}
		}
		
		return toReturn;
	}
	//

	/**
	 * Deep clone method. Java uses shallow cloning by default, and
	 * there is no native method for deep cloning. This results in
	 * an inability to create a new child array from a parent, which
	 * is part of how the application implements its child generation.
	 */
 	private int[][] cloneTileMap() {

		// Initialize an array to return the same dimensions as tileMap.
		int[][] toReturn = new int[tileMap.length][tileMap[0].length];
		
		// Iterate over each element of the Node's tileMap and copy
		// each value to a new array
		for(int i = 0; i < toReturn.length; i++) {

			// Initialize a new temporary array with 8 elements
			int[] tempRow = new int[8];
			
			// Iterate over each element of tileMap[i] and
			// index it to temp_row[j]
			for(int j = 0; j < toReturn[0].length; j++) {
				tempRow[j] = tileMap[i][j];
			}
			
			// Assign the value of temp_row to toReturn[i]
			toReturn[i] = tempRow;
		}
		
		return toReturn;
	}
	//
}