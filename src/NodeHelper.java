import java.util.ArrayList;


/**
 * Helper class for Node.
 * Contains misc functions not necessary to
 * the functionality of Node
 * 
 * @author Dytrich
 *
 */
public class NodeHelper {
	protected static void printTileMap(Node node) {
		int[][] tileMap = node.getTileMap();
		for(int[] arr : tileMap) {
			for(int i : arr) {
				System.out.printf("%d ", i);
			}
			System.out.printf("\n");
		}
        System.out.printf("\n");
	}
	
	protected static String childIds(Node node) {
		
		ArrayList<Integer> children = node.childrenId();
		
		// Start the string with the first child ID.
        StringBuilder toReturn = new StringBuilder();
        
        // Handling for if there is no child. It will just return
        // a string with value "None"
        if(children.size() > 0) {
        	toReturn.append(children.get(0).toString());
        }
        else {
        	toReturn.append("None");
        }
		
		// Iterate from 1 since we already have the first child ID as the string.
		for(int i = 1; i < children.size(); i++) {
			toReturn.append(", " + children.get(i));
		}
		
		return toReturn.toString();
	}
	
	public static void printAll(Node node) {
		System.out.println("---------------");
		System.out.println("PARENT ID: " + node.getParentId());
		System.out.println("ID: " + node.getId());
		System.out.println("DEPTH: " + node.getDepth());
		System.out.printf("CHILDREN: %s\n", childIds(node));
		System.out.println("PREV MOVE: " + node.getPrevMove());
        System.out.println("TILE MAP: ");
		printTileMap(node);
		System.out.println("---------------");

	}
}
