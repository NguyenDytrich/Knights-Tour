import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class Main {

	public static void main(String[] args) {

		// DEBUG AND TESTING
		//NodeTest.initializationTest();
		//NodeTest.childrenTest();
		//TreeTest.initTest();
		//TreeTest.generationTest();
		//TreeTest.lastNodeTest();
		//TreeTest.depthTest();
		//System.out.println("NODE COUNT: " + AppHelper.getNodeCount());
		//TreeTest.pruneTest();
		//
		
		
		// TIMED SOLUTION
		
		System.out.println("Beginning solve from tile A8.");
		
		// Establish a start time
		long startTime = System.currentTimeMillis();
		
		// Generate the tree
		Tree myTree = new Tree(new Node(0, new int[] {0, 0}, -1, AppHelper.newTileMap(), -1));
		ArrayList<int[][]> solutions = myTree.pruneMoves();
		
		// Establish an end time
		long endTime = System.currentTimeMillis();
		
		long totalTime = endTime - startTime;
		
		System.out.printf("Found %d solutions in %d milliseconds.\n", solutions.size(), totalTime);
		
		// Print solutions to an output file since there are so many.
		// I'm using File to create a file object so that I can 
		// reference its path in an output message.
		// PrintWriter to write strings using StringBuilder to the file.
		try {
			File file = new File("solutions-output.txt");
			PrintWriter writer = new PrintWriter(file, "UTF-8");
			for(int[][] moveSet : solutions) {
				StringBuilder str = new StringBuilder();
				for (int i[] : moveSet) {
					char letter = '?';
					switch(i[1]){
						case 0: letter = 'A'; break;
						case 1: letter = 'B'; break;
						case 2: letter = 'C'; break;
						case 3: letter = 'D'; break;
						case 4: letter = 'E'; break;
						case 5: letter = 'F'; break;
						case 6: letter = 'G'; break;
						case 7: letter = 'H'; break;
					}
				
					int numeric = 8 - i[0];
				
					str.append(letter);
					str.append(numeric);
					str.append(" ");
				}
				str.deleteCharAt(str.length()-1);
				str.append('\n');
				writer.write(str.toString());
			}
			System.out.printf("File accessible at %s", file.getAbsoluteFile());
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}