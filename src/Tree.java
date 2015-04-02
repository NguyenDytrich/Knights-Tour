import java.util.ArrayList;
import java.util.HashMap;

/**
 * Contains all of the nodes and methods for
 * traversal.
 * 
 * @author Dytrich
 *
 */
public class Tree {
	
	// An ArrayList of branches
	private HashMap<Integer, Node> nodes;
	public HashMap<Integer, Node> getNodes() {
		return nodes;
	}
	//
	
	// Constructor
	public Tree(Node rootNode) {
		
		// Initialize the ArrayList
		nodes = new HashMap<Integer, Node>();
		
		nodes.put(rootNode.getId(), rootNode);
		
		
		// Quick and dirty solution.
		// TODO: make this more robust
		
		ArrayList<Node> workingNodes = new ArrayList<Node>();
		// Add the rootNode
		workingNodes.add(rootNode);
		
        // Create a temporary Node holder
        ArrayList<Node> buffer = new ArrayList<Node>();

		// Number of iterations
		int targetIterations = workingNodes.size();
		
		// Iterate over each Node in workingNodes
		while(targetIterations > 0) {
			

			// Iterate over each workingNode
			// TODO:
			// BUG: This will eventually break because the end of
			// a branch has null children.
			for(int i = 0; i < workingNodes.size(); i++) {
				for(Node child : workingNodes.get(i).getChildren()) {

					// Add each child to the tree
					nodes.put(child.getId(), child);
				
					// Add each child to the buffer
					buffer.add(child);
				}
			}
			
			// Copy the buffer to workingNodes
			// First clear the list
			workingNodes.clear();
			// Add all values from the buffer
			workingNodes.addAll(buffer);
			// Clear the buffer
			buffer.clear();
			
			targetIterations = workingNodes.size();
			
    	}
	}
	//
	
	// Query by ID
	public Node findById(int id) {
		return nodes.get(id);
	}
	//
	
	// Query for endpoints
	public ArrayList<Node> findEnds() {
		// Define a new arraylist
		ArrayList<Node> toReturn = new ArrayList<Node>();
		
		// Iterate through each element
		for(Node n : nodes.values()) {
			if(n.getChildren().size() == 0)
				toReturn.add(n);
		}
		
		return toReturn;
	}
	//
	
	// Query by depth
	public ArrayList<Node> findDepth(int depth) {
		// Define a new  arraylist
		ArrayList<Node> toReturn = new ArrayList<Node>();
		
		// Iterate through each element
		for(Node n : nodes.values()) {
			if(n.getDepth() == depth) {
				toReturn.add(n);
			}
		}
		
		return toReturn;
	}
	
	// Return solution nodes
	public ArrayList<Node> findSolutions() {
		return findDepth(63);
	}
	
	// Return solutions as sets of moves as raw coordinates
	public ArrayList<int[][]> pruneMoves() {
		ArrayList<int[][]> toReturn = new ArrayList<int[][]>();
		
		// Get the solutions
		ArrayList<Node> solutions = findSolutions();
		
		// Iterate over each solution
		for(Node n : solutions){
			
			// Initialize an array to add with 64 elements
			int[][] toAdd = new int[64][2];
			
			// Current node
			Node workingNode = n;
			
			// Iterate over toAdd in reverse
			for(int i = toAdd.length - 1; i > 0; i--) {
				
				// Add position of the working node to toAdd[i]
				toAdd[i] = workingNode.getPosition();
				
				// Assign workingNode to the parent of the current node
				workingNode = findById(workingNode.getParentId());
				
			}
			
			toReturn.add(toAdd);
		}
		
		return toReturn;
	}
	
	// Size of the tree
	public int size() {
		return nodes.size();
	}
	//
}