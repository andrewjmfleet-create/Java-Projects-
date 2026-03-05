/*******
 * @author Andrew Fleet
 * @dueDate Wed May 21
 * @description This is the panel class of my game it extends JPanel 
 * This has a lot of the harder logic that I coded, mostly the starting logic.  
 */
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
	private Blocks[][] gameArray = new Blocks[14][18]; // 2D grid of blocks
	private Blocks selectedBlock = null; // currently selected block by user
	private int numMines = 40; // total number of mines
	private int numNonMines = 252 - numMines; // remaining safe blocks
	/**
	 * this is the constructor for this class where everything is initialized 
	 */
	public GamePanel() {
		this.setLayout(new GridLayout(14, 18)); // set layout of panel to grid

		boolean[] isMinePosition = new boolean[252]; // track which of 252 spots will be mines

		int numMinesPlaced = 0;

		// Randomly assign 40 mines to unique positions
		while (numMinesPlaced < numMines) {
			int pos = (int)(Math.random() * 252); // random index between 0-251
			if (!isMinePosition[pos]) { // only place if not already a mine
				isMinePosition[pos] = true;//spot becomes mine
				numMinesPlaced++;//increases the num mines placed
			}
		}

		// Initialize the grid with blocks and assign mine status
		gameArray = new Blocks[14][18]; // (re)initialize the array
		int index=0;
		for (int row = 0; row < 14; row++) {
			for (int col = 0; col < 18; col++) {
				
				boolean isMine = isMinePosition[index]; // check if current index is a mine
				index++; 
				Blocks block = new Blocks(isMine, col, row); // create new block
				block.addActionListener(new selectedListener(block)); // attach listener(this works with all blocks so when they get selected they go red)
				gameArray[row][col] = block; // store in grid
				this.add(block); // add to Panel
			}
		}
		this.startOfGame(); // calculate initial danger levels
		this.revealSafeRegion(bestStartBlock()); // auto-reveal from safest start
	}
	/**
	 * gets called when a block is selected 
	 */
	public class selectedListener implements ActionListener {
		public Blocks block;
		/**
		 * constructor for this code in which the block currently selected is put in
		 * @param block current block selected
		 */
		public selectedListener(Blocks block) {
			this.block = block;
		}

		public void actionPerformed(ActionEvent e) {
			if (selectedBlock != null) {//don't do it if no block is selected previously
				selectedBlock.setIsSelected(false); // unselect previous block
			}
			selectedBlock = block; // update selected
			block.setIsSelected(true); // visually mark as selected
		}
	}
	/**
	 * it is the get method for numMines
	 * @return this is the value(int) for numMines
	 */
	public int getNumMines() {
		return this.numMines;
	}
	/**
	 * it is the get method for selectedBlock
	 * @return this is the value(block) for selectedBlock
	 */
	public Blocks getSelectedBlock() {
		return selectedBlock;
	}
	/**
	 * it is the get method for the gameArray
	 * @return return the gameArray
	 */
	public Blocks[][] getGameArray() {
		return gameArray;
	}
	/**
	 * this is the method where each of the blocks is assigned a danger level the algorithm is below
	 */
	public void startOfGame() {

		//Basically every mine has a danger level of 0
		//then everything outside of it has an increasing danger level
		//with the higher the number the further away from a mine
		//this is done through using a queue
		//basically all the mines are added to the queue
		//then the top one is done first where all the surrounding blocks are assigned numbers
		//and also added to the queue 
		//this goes on till the queue is empty
		//because of the queue all of the blocks are gone through at least once 
		//assigning each block with a number
		Queue checkingQueue = new Queue(); // for BFS

		// Initialize danger levels: 0 for mines, -1 for unvisited non-mines
		for (int row = 0; row < gameArray.length; row++) {
			for (int col = 0; col < gameArray[row].length; col++) {
				Blocks block = gameArray[row][col];
				if (block.getIsMine()) {
					block.setDangerLevel(0);//mines are assigned 0
					checkingQueue.add(block); // start from mines
				} else {
					block.setDangerLevel(-1); // unvisited marker
				}
			}
		}

		//go through the queue assigning danger level
		while (!checkingQueue.isEmpty()) {
			Blocks current = checkingQueue.remove();//remove the first block and thats the one it starts with
			int currentLevel = current.getDangerLevel();//sets the current danger level its at
			int cx = current.getXCoord();//what its cord
			int cy = current.getYCoord();//what its cord

			for (int dx = -1; dx <= 1; dx++) {//goes around the block
				for (int dy = -1; dy <= 1; dy++) {//goes around the block
					if (!(dx == 0 && dy == 0)) { // skip self
						int nx = cx + dx;//going around the block
						int ny = cy + dy;//going around the block

						// check bounds
						if (nx >= 0 && nx < gameArray[0].length && ny >= 0 && ny < gameArray.length) {//making sure it is not out of bounds due to edge cases
							Blocks neighbor = gameArray[ny][nx];//assigns this block as the neighbour

							// if not a mine and unvisited update level and queue it
							if (neighbor.getDangerLevel() == -1 && !neighbor.getIsMine()) {
								neighbor.setDangerLevel(currentLevel + 1);
								checkingQueue.add(neighbor);
							}
						}
					}
				}
			}
		}
	}
	/**
	 * this method finds which ever block is the best starting location
	 * @return returns the best block
	 */
	public Blocks bestStartBlock() {

		int maxLevel = -1;
		ArrayList<Blocks> bestCandidates = new ArrayList<>();

		// Find block(s) with the highest danger level
		for (int row = 0; row < gameArray.length; row++) {
			for (int col = 0; col < gameArray[row].length; col++) {
				Blocks block = gameArray[row][col];
				int level = block.getDangerLevel();
				if (level > maxLevel) {//check if level is now raised
					maxLevel = level;//set new level
					bestCandidates.clear();//if the maxLevel is found to be higher then all blocks with less are removed
					bestCandidates.add(block);//adds the block
				} else if (level == maxLevel) {
					bestCandidates.add(block);//just add the block
				}
			}
		}
		//just for errors doesn't happen anymore
		if (bestCandidates.isEmpty()) {
			System.out.println("No valid starting block found.");
			return null;
		}

		// Choose block farthest from edge (more space around it)
		Blocks bestStart = bestCandidates.getFirst();
		int bestEdgeDistance = -1;

		int rows = gameArray.length;
		int cols = gameArray[0].length;

		for (Blocks b : bestCandidates) {
			int row = b.getYCoord();
			int col = b.getXCoord();

			// compute minimum distance to any edge
			int edgeDist = Math.min(Math.min(row, rows - 1 - row), Math.min(col, cols - 1 - col));//finding distance to closest edge
			//current col is distance from left and cols - 1- col is the right side 
			//current row is distance from top and rows - 1 - row is the distance from bottom
			//compares those two and takes the smaller one and then takes the smaller one from which ever side is the closest
			
			if (edgeDist > bestEdgeDistance) {//to see which one is the farthest away
				bestEdgeDistance = edgeDist;//if it is better then set it as the best
				bestStart = b;//if it is the best then set the block as the best
			}
		}

		return bestStart;//return the best block for starting
	}
	/**
	 * this is the code for revealing the safe region of the board to start the game using relatively the same algorithm as before
	 * @param startBlock the starting block that this code will start with
	 */
	public void revealSafeRegion(Blocks startBlock) {
		//uses same algorithm as before with the queue

		Queue queue = new Queue();
		boolean[][] visited = new boolean[gameArray.length][gameArray[0].length];

		queue.add(startBlock);//first block to look at since it is the start block
		visited[startBlock.getYCoord()][startBlock.getXCoord()] = true;

		while (!queue.isEmpty()) {
			Blocks current = queue.remove();//removes the block from queue setting at as current block
			int x = current.getXCoord();
			int y = current.getYCoord();
			int danger = current.getDangerLevel();//basically the danger we are basing whats gonna happen of off

			// reveal only safe not revealed blocks
			if (!current.getIsMine() && !current.getIsRevealed()) {
				if (danger >= 2) {//as long as its not beside of mine
					// show "nothing" image(no mines)
					ImageIcon pic = new ImageIcon("nothing.jpeg");
					Image img = pic.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
					current.setIcon(new ImageIcon(img));

					current.setIsRevealed(true);
					current.setIsFlagged(false);
					current.setBorder(null);
					numNonMines--;//making it so that the number of blocks that the player has to reveal is now less since that block is already revealed

					// queue all neighbors
					for (int dy = -1; dy <= 1; dy++) {
						for (int dx = -1; dx <= 1; dx++) {
							int nx = x + dx;
							int ny = y + dy;

							// bounds and not visited
							if ((dx != 0 || dy != 0) && nx >= 0 && ny >= 0 &&
									nx < gameArray[0].length && ny < gameArray.length &&
									!visited[ny][nx]) {
								queue.add(gameArray[ny][nx]);
								visited[ny][nx] = true;
							}
						}
					}
				} else if (danger == 1) {

					int count = 0;
					for (int dy = -1; dy <= 1; dy++) {
						for (int dx = -1; dx <= 1; dx++) {
							int nx = x + dx;
							int ny = y + dy;

							// check 8 neighbors and count how many are mines(make sure its not out of bounds)
							if ((dx != 0 || dy != 0) && 
									nx >= 0 && ny >= 0 && 
									nx < gameArray[0].length && ny < gameArray.length && gameArray[ny][nx].getIsMine()) {
								count++;
							}
						}
					}
					if (count >= 0 && count <= 8) {
						String filename = count + ".jpeg";
						ImageIcon pic = new ImageIcon(filename);
						Image img = pic.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
						current.setIcon(new ImageIcon(img));
					}
					numNonMines--;//since block is revealed decrease this number
					current.setIsRevealed(true);
					current.setIsFlagged(false);
					current.setBorder(null);
				}
			}
		}
	}
	/**
	 * it is the get method for numNonMines
	 * @return this is the value(int) for numNonMines
	 */
	public int getNumNonMines() {
		return this.numNonMines;
	}
}