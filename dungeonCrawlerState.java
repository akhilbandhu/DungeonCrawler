import java.util.ArrayList;

public class dungeonCrawlerState implements Comparable<dungeonCrawlerState>{

	private char [][] world;
	private int currentX;
	private int currentY;
	private int coinCount;
	private int numberOfColumns;
	private int numberOfRows;
	private AgentAction action;
	private dungeonCrawlerState parent;
	
	public dungeonCrawlerState(char[][] world, int currentX, int currentY, int numberOfRows, int numberOfColumns, int coinCount) {
		this.world = new char[numberOfRows][numberOfColumns];   //created world of number of rows and columns as the file.
		for(int i=0; i<world.length; i++) {
			for(int j=0; j<world[i].length; j++) {
				this.world[i][j] = world[i][j];
			}
		}
		this.currentX = currentX;
		this.currentY = currentY;
		this.numberOfColumns = numberOfColumns;
		this.numberOfRows = numberOfRows;
		this.coinCount = coinCount;
	}
	
	public AgentAction getAction() {
		return this.action;
	}
	public int getCoinCount() {
		return this.coinCount;
	}
	public void setCurrentX(int currentX) {
		this.currentX = currentX;
	}
	public void setCurrentY(int currentY) {
		this.currentY = currentY;
	}
	public int getCurrentX(int currentX) {
		return this.currentX;
	}
	public int getCurrentY(int currentY) {
		return this.currentY;
	}
	public dungeonCrawlerState getParent() {
		return this.parent;
	}
	public String toString() {
		String worldString = currentX +" "+currentY + " ";
		for(int i=0; i<world.length; i++) {
			for(int j=0; j<world[i].length; j++) {
				worldString += world[i][j];
			}
		}
		return worldString;
	}
	
	public dungeonCrawlerState[] generateChildren(){
		dungeonCrawlerState[] childrenStates = new dungeonCrawlerState[5]; //generate the five children: up, right, left, down, pickup
//		dungeonCrawlerState parentState = new dungeonCrawlerState(world, currentX, currentY, numberOfRows, numberOfColumns, coinCount);
		for(int i=0; i<5; i++) {
			childrenStates[i] = new dungeonCrawlerState(world, currentX, currentY, numberOfRows, numberOfColumns, coinCount);
			childrenStates[i].parent = this;
		}
		
		if(currentX-1>=0 && world[currentX-1][currentY] != 'w') {
			childrenStates[0].currentX = currentX-1; //state after going up 1
			childrenStates[0].action = AgentAction.moveUp;
			
		}
		
		if(currentY+1<numberOfColumns && world[currentX][currentY+1] != 'w') {
			childrenStates[1].currentY = currentY+1; //state after going right 1
			childrenStates[1].action = AgentAction.moveRight;
//			childrenStates[1].parent = parentState;
		}
		
		if(currentX+1<numberOfRows && world[currentX+1][currentY] != 'w') {
			childrenStates[2].currentX = currentX+1; //state after going down 1
			childrenStates[2].action = AgentAction.moveDown;
//			childrenStates[2].parent = parentState;
		}
		
		if(currentY-1>=0 && world[currentX][currentY-1] != 'w') {
			childrenStates[3].currentY = currentY-1; //state after going left 1
			childrenStates[3].action = AgentAction.moveLeft;
//			childrenStates[3].parent = parentState;
		}
		
		if(world[currentX][currentY] == '.') {
			
			childrenStates[0] = null;
			childrenStates[1] = null;
			childrenStates[2] = null;
			childrenStates[3] = null;
			childrenStates[4].coinCount++;
			childrenStates[4].world[currentX][currentY] = ' ';
			childrenStates[4].action = AgentAction.pickupSomething;
//			childrenStates[4].parent = parentState;
			
		//	childrenStates[4] = new dungeonCrawlerState(world, currentX, currentY, numberOfRows, numberOfColumns); //picking up gold, think about how to change the state.

		}
	
		return childrenStates;
	}	
	public ArrayList<AgentAction> getAllActions(){
		ArrayList<AgentAction> allActions = new ArrayList<AgentAction>();
		if(parent==null) {
			allActions.add(action);
			return allActions;
		}
		else {
			allActions = parent.getAllActions();
			allActions.add(action);
			return allActions;
		}
	}

	//we need a heuristic to probably count the number of gold in our state
	//+2 for gold 
	//-1 for if we are standing on this gold
	
	public int getNumberOfActions() {
		int numberOfActions = 0;
		for(int i=0; i<world.length; i++) {
			for(int j=0; j<world[0].length; j++) {
				if(world[i][j] == '.') {
					numberOfActions += 2;
				}
			}
		}
		if(world[currentX][currentY] == '.') {
			numberOfActions--;
		}
		return numberOfActions;
	}
	@Override
	public int compareTo(dungeonCrawlerState o) {
		// TODO Auto-generated method stub
		if(this.getNumberOfActions() + this.getAllActions().size() < o.getNumberOfActions() + o.getAllActions().size()) {
			return -1;
		}
		else if(this.getNumberOfActions() + this.getAllActions().size() == o.getNumberOfActions() + o.getAllActions().size()) {
			return 0;
		}
		return 1;
	}
}
