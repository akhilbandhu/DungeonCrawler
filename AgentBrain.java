import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class AgentBrain {

	private Queue<AgentAction> nextMoves;
	public static int numberOfRows = 0;
	public static int numberOfColumns = 0;
	public static int startXCoordinate = 0;
	public static int startYCoordinate = 0;

	public AgentBrain() {
		nextMoves = new LinkedList<AgentAction>();
	}
	
	
	public void addNextMove(AgentAction nextMove) {
		this.nextMoves.add(nextMove);
	}

	public void clearAllMoves() {
		nextMoves = new LinkedList<AgentAction>();
	}

	public AgentAction getNextMove() {
		if(nextMoves.isEmpty()) {
			return AgentAction.doNothing;
		}
		return nextMoves.remove();
	}
	
	
	public void search(String [][] theMap) {
		//TODO - Figure out if you need more parameters and then add all the moves
		//I want to store the action I did to get to this state
		//to do that I will probably have to create an agent action which stores how we got there in the state class		
		numberOfRows = theMap.length;
		System.out.println(numberOfRows);
		numberOfColumns = theMap[0].length;
		System.out.println(numberOfColumns);
		readWorld(theMap);
		for(int i=0; i<numberOfRows; i++) {
			for(int j=0; j<numberOfColumns; j++) {
				System.out.print(theMap[i][j]);
			}
			System.out.println();
		}
		
		int numberOfCoinsInWorld = countCoinsInWorld(theMap);
		System.out.println(numberOfCoinsInWorld);
		
		char[][] map = stringToCharArray(theMap);
//		for(int i=0; i<map.length; i++) {
//			for(int j=0; j<map[0].length; j++) {
//				System.out.print(map[i][j]);
//			}
//			System.out.println();
//		}
		
		dungeonCrawlerState state = new dungeonCrawlerState(map, startXCoordinate, startYCoordinate, numberOfRows, numberOfColumns, 0);
//		
		
		//Depth First Search Code
//		Stack<dungeonCrawlerState> stack = new Stack<dungeonCrawlerState>();
//		HashMap<String, Boolean> visitedStates = new HashMap<String, Boolean>();
//		long nodesExpanded = 0;
//		
//		if(state.getCoinCount() == numberOfCoinsInWorld) {
//			nextMoves.add(AgentAction.declareVictory);
//			nodesExpanded = visitedStates.size();
//			System.out.println(nodesExpanded);
//		}
//		String world = state.toString();
//		visitedStates.put(world, true);
//		dungeonCrawlerState[] children = state.generateChildren();
//		for(int i=0; i<children.length; i++) {
//			System.out.println(children[i].toString());
//		}
//		
//		for(int i=0; i<children.length; i++) {
//			stack.push(children[i]);
//		}
//
//		while(!stack.isEmpty()) {
//			dungeonCrawlerState currentState = stack.pop();
//			System.out.println(currentState.getAction());
//			System.out.println(currentState.getParent().toString());
//			System.out.println(currentState.getCoinCount());
//			if(currentState.getCoinCount()==numberOfCoinsInWorld) {
//				nodesExpanded = visitedStates.size();
////				System.out.println(nextMoves.peek());
//				System.out.println("Nodes Expanded: "+nodesExpanded);
//				ArrayList<AgentAction> actions = currentState.getAllActions();
//				for(int i=0;i<actions.size(); i++) {
//					nextMoves.add(actions.get(i));
//					System.out.println(actions.get(i));
//				}
//				nextMoves.add(AgentAction.declareVictory);	
//				return;
//			}
//			if(visitedStates.containsKey(currentState.toString())) {
//				continue;
//			}
//			else {
//				visitedStates.put(currentState.toString(), true);
//				dungeonCrawlerState[] childrenOfCurentChild = currentState.generateChildren();
//				for(int i=0; i<childrenOfCurentChild.length; i++) {
//					stack.push(childrenOfCurentChild[i]);
//					System.out.print(childrenOfCurentChild[i]);
//					System.out.println();
//				}
//			}
//		}
		
		
		//Breadth First Search Code
//		Queue<dungeonCrawlerState> queue = new LinkedList<dungeonCrawlerState>();				//use the queue in the same way	
//		HashMap<String, Boolean> visitedStates = new HashMap<String, Boolean>();	//add all children to the queue, 
//																	//add all the children to the hash and set values to true.
//																	//once all the children have been visited, generate children sequentially for the children that already been added  
//		long nodesExpanded = 0;
//		String world = state.toString();
//		dungeonCrawlerState[] children = state.generateChildren();
//		visitedStates.put(world, true);
//		if(state.getCoinCount() == numberOfCoinsInWorld) {
//			nodesExpanded = visitedStates.size(); 					//check all the states the search has visited already
//		}
//		
//		for(int i=0; i<children.length; i++) {
//			if(children[i]!= null) {
//				queue.add(children[i]);
//			}
//		}
//		
//		while(!queue.isEmpty()) {
//			dungeonCrawlerState currentState = queue.remove();
//			if(currentState.getCoinCount() == numberOfCoinsInWorld) {
//				nodesExpanded = visitedStates.size();
//				System.out.println("Nodes Expanded: "+nodesExpanded);
//				ArrayList<AgentAction> actions = currentState.getAllActions();
//				for(int i=0;i<actions.size(); i++) {
//					nextMoves.add(actions.get(i));
//					System.out.println(actions.get(i));
//				}
//				nextMoves.add(AgentAction.declareVictory);	
//				return;
//			}
//			if(visitedStates.containsKey(currentState.toString())) {
//				continue;
//			}
//			else {
//				visitedStates.put(currentState.toString(), true);
//				dungeonCrawlerState[] childrenOfCurrentChild = currentState.generateChildren();
//				for(int j=0; j<childrenOfCurrentChild.length; j++) {
//					if(childrenOfCurrentChild[j]!=null) {
//						queue.add(childrenOfCurrentChild[j]);
//					}
//				}
//			}
//		}	
		
		//Uniform Cost/Best Cost Search Code
		PriorityQueue<dungeonCrawlerState> queue = new PriorityQueue<dungeonCrawlerState>();				//use the queue in the same way	
		HashMap<String, Boolean> visitedStates = new HashMap<String, Boolean>();	//add all children to the queue, 
																	//add all the children to the hash and set values to true.
																	//once all the children have been visited, generate children sequentially for the children that already been added  
		long nodesExpanded = 0;
		String world = state.toString();
		dungeonCrawlerState[] children = state.generateChildren();
		visitedStates.put(world, true);
		if(state.getCoinCount() == numberOfCoinsInWorld) {
			nodesExpanded = visitedStates.size(); 					//check all the states the search has visited already
		}
		
		for(int i=0; i<children.length; i++) {
			if(children[i]!= null) {
				queue.add(children[i]);
			}
		}
		
		while(!queue.isEmpty()) {
			dungeonCrawlerState currentState = queue.remove();
			if(currentState.getCoinCount() == numberOfCoinsInWorld) {
				nodesExpanded = visitedStates.size();
				System.out.println("Nodes Expanded: "+nodesExpanded);
				ArrayList<AgentAction> actions = currentState.getAllActions();
				for(int i=0;i<actions.size(); i++) {
					nextMoves.add(actions.get(i));
					System.out.println(actions.get(i));
				}
				nextMoves.add(AgentAction.declareVictory);	
				return;
			}
			if(visitedStates.containsKey(currentState.toString())) {
				continue;
			}
			else {
				visitedStates.put(currentState.toString(), true);
				dungeonCrawlerState[] childrenOfCurrentChild = currentState.generateChildren();
				for(int j=0; j<childrenOfCurrentChild.length; j++) {
					if(childrenOfCurrentChild[j]!=null) {
						queue.add(childrenOfCurrentChild[j]);
					}
				}
			}
		}	
		
		
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
	
	public void readWorld(String[][] theMap){
			for(int i=0; i<theMap.length; i++) {
				for(int j=0; j<theMap[i].length; j++) {
					if(theMap[i][j].equals("S")) {
						startXCoordinate = i;
						startYCoordinate = j;
						theMap[i][j] = " ";  
					}
			}
		}
	}
	
	public char[][] stringToCharArray(String[][] theMap) {
		char[][] world = new char[numberOfRows][numberOfColumns];
		for(int i=0; i<numberOfRows; i++) {
			for(int j=0; j<numberOfColumns; j++) {
				world[i][j] = theMap[i][j].charAt(0);			
				}
			}
		return world;
	}
	
}
