import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;


public class basicSearch {
	
	public static int numberOfRows = 1;
	public static int numberOfColumns = 0;
	public static int startXCoordinate = 0;
	public static int startYCoordinate = 0;
	
	public static void getDimensions(String fileName) {
		try {
			Scanner in = new Scanner(new File(fileName));
			String firstRow = in.nextLine();
			numberOfColumns = firstRow.length()-1;
			while(in.hasNext()) {
				in.nextLine();
				numberOfRows++;
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

//	public static String[][] readWorld(String fileName){
//		String [][] world = new String[numberOfRows][numberOfColumns];
//		try {
//			Scanner in = new Scanner(new File(fileName));
//			for(int i=0; i<world.length; i++) {
//				String[] columns = in.nextLine();
//				for(int j=0; j<world[i].length; j++) {
//					world[i][j] = columns[j];
//					if(world[i][j].equals("S")) {
//						startXCoordinate = i;
//						startYCoordinate = j;
//						world[i][j] = " ";  
//					}
//				}
//			}
//			
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return world;
//	}
	public int countCoinsInWorldString(String[][] world) {
		int numberOfCoins = 0;
		for(int i=0; i<world.length; i++) {
			for(int j=0; j<world[i].length; j++) {
				if(world[i][j]==".") {
					numberOfCoins++;
				}
			}
		}
		return numberOfCoins;
	}
	public static int countCoinsInWorld(String[][] world) {
		int numberOfCoinsInWorld = 0;
		for(int i=0; i<world.length; i++) {
			for(int j=0; j<world[i].length; j++) {
				if(world[i][j].equals(".")) {
					numberOfCoinsInWorld++;
				}
			}
		}
		return numberOfCoinsInWorld;
	}
	
	public static long depthFirstSearch(dungeonCrawlerState state, int coinCount) {
		//DFS function
		//should take the children from the generate children function
		//should use a stack to push the children into
		//Use a hashmap to store if the state has been visited 
		
		Stack<dungeonCrawlerState> stack = new Stack<dungeonCrawlerState>();
		HashMap<String, Boolean> visitedStates = new HashMap<String, Boolean>();
		long nodesExpanded = 0;
		if(state.getCoinCount() == coinCount) {
			nodesExpanded = visitedStates.size();
			return nodesExpanded;
		}
		String world = state.toString();
		visitedStates.put(world, true);
		dungeonCrawlerState[] children = state.generateChildren();
		
		for(int i=0; i<children.length; i++) {
			stack.push(children[i]);
		}
		while(!stack.isEmpty()) {
			dungeonCrawlerState currentState = stack.pop();
			if(currentState.getCoinCount()==coinCount) {
				nodesExpanded = visitedStates.size();
				return nodesExpanded;
			}
			if(visitedStates.containsKey(currentState.toString())) {
				continue;
			}
			else {
				visitedStates.put(currentState.toString(), true);
				dungeonCrawlerState[] childrenOfCurentChild = currentState.generateChildren();
				for(int i=0; i<childrenOfCurentChild.length; i++) {
					stack.push(childrenOfCurentChild[i]);
				}
			}
		}
		return nodesExpanded;
	}
	
	public static long breadthFirstSearch(dungeonCrawlerState state, int coinCount) {
		//BFS function
		//should do the same as the DFS function
		//But uses queue instead of a stack!
		//pops the first in element
		Queue<dungeonCrawlerState> queue = new LinkedList<dungeonCrawlerState>();				//use the queue in the same way	
		HashMap<String, Boolean> visitedStates = new HashMap<String, Boolean>();	//add all children to the queue, 
																	//add all the children to the hash and set values to true.
																	//once all the children have been visited, generate children sequentially for the children that already been added  
		long nodesExpanded = 0;
		String world = state.toString();
		dungeonCrawlerState[] children = state.generateChildren();
		visitedStates.put(world, true);
		if(state.getCoinCount() == coinCount) {
			nodesExpanded = visitedStates.size(); 					//check all the states the search has visited already
			return nodesExpanded;
		}
		
		for(int i=0; i<children.length; i++) {
			queue.add(children[i]);
		}
		
		while(!queue.isEmpty()) {
			dungeonCrawlerState currentState = queue.remove();
			if(currentState.getCoinCount() == coinCount) {
				nodesExpanded = visitedStates.size();
				return nodesExpanded;
			}
			if(visitedStates.containsKey(currentState.toString())) {
				continue;
			}
			else {
				visitedStates.put(currentState.toString(), true);
				dungeonCrawlerState[] childrenOfCurrentChild = currentState.generateChildren();
				for(int j=0; j<childrenOfCurrentChild.length; j++) {
					queue.add(childrenOfCurrentChild[j]);
				}
			}
		}
		return nodesExpanded;
		
	}
	
	public static void main(String [] args) {
		getDimensions("DungeonLayouts/smallSearch.txt");
//		String [][] world = readWorld("DungeonLayouts/smallSearch.txt");
		System.out.println("Dimensions of current world "+numberOfRows+"x"+numberOfColumns);
		
//		int numberOfCoinsInWorld = countCoinsInWorld(world);
//		System.out.println("Number of coins in current world: "+numberOfCoinsInWorld);
//		dungeonCrawlerState state = new dungeonCrawlerState(world, startXCoordinate, startYCoordinate, numberOfRows, numberOfColumns, 0);
//		
//		System.out.println("Nodes Expanded before reaching solution(Depth First Search): " +depthFirstSearch(state, numberOfCoinsInWorld));
//		System.out.println("Nodes Expanded before reaching solution(Breadth First Search): " +breadthFirstSearch(state, numberOfCoinsInWorld));
//		
		
	
	}
}
