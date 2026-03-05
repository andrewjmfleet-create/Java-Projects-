/*******
 * @author Andrew Fleet
 * @dueDate Wed May 21
 * @description This is the class for the GameFrame which extends JFrame it is where most of the code is combined 
 * it has most of the main logic 
 */
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GameFrame extends JFrame {
	private GamePanel gameP = new GamePanel(); // Main game panel that holds the grid of Blocks
	private int numFlags = gameP.getNumMines(); // Number of flags available (starts as total number of mines)
	private int correctFlags = 0; // Tracks how many correct flags (on actual mines) have been placed
	private int correctReveal = 0; // Tracks how many non-mine blocks have been correctly revealed
	private JLabel flagCountLabel = new JLabel(); // Displays remaining flag count
	/**
	 * This is the constructor for the class and it initiates all the code 
	 */
	public GameFrame() {
		super("Minesweeper");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());

		// Top panel showing flag image, flag count, and instructions button
		JLabel flags = new JLabel();
		ImageIcon pic = new ImageIcon("flags.jpeg");
		Image img = pic.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		flags.setIcon(new ImageIcon(img)); 

		JButton instructions = new JButton("INSTRUCTIONS");
		instructions.addActionListener(new instructionsListener());

		JPanel flagsPanel = new JPanel();
		flagsPanel.add(flags, BorderLayout.WEST);
		flagsPanel.add(flagCountLabel, BorderLayout.CENTER);
		flagsPanel.add(instructions, BorderLayout.EAST);

		flagCountLabel.setText(": " + Integer.toString(numFlags)); // show initial flag count

		// Bottom panel with Reveal, Place Flag, and Reveal All buttons
		JPanel confirmationButtons = new JPanel(new GridLayout(1, 2));
		JButton placeFlag = new JButton("Place Flag");
		JButton reveal = new JButton("Reveal Block");
		//JButton revealAll = new JButton("Reveal All");

		placeFlag.addActionListener(new placeFlagListener(this));
		reveal.addActionListener(new revealBlockListener(this));
		//revealAll.addActionListener(new revealAllListener());

		confirmationButtons.add(reveal);
		confirmationButtons.add(placeFlag);
		//confirmationButtons.add(revealAll);

		// Add panels to frame
		this.add(gameP, BorderLayout.CENTER);
		this.add(confirmationButtons, BorderLayout.SOUTH);
		this.add(flagsPanel, BorderLayout.NORTH);
		this.pack();
		this.setVisible(true);
	}
	/**
	 * Action listener for the instructor button 
	 */
	public class instructionsListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFrame instructionsFrame = new JFrame();
			instructionsFrame.setLocation(875,0);
			JPanel instructionsPanel = new JPanel();
			JLabel instructionsLabel = new JLabel("<html>"
					+ "Minesweeper Instructions<br><br>"
					+ "1. Click on a square to select it.<br>"
					+ "2. Use the buttons at the bottom:<br>"
					+ "- Reveal: uncovers the selected square.<br>"
					+ "  • If it's a mine, you lose.<br>"
					+ "  • If it's a number, it shows how many mines are nearby.<br>"
					+ "  • If it's empty, nearby safe squares will be revealed automatically.<br>"
					+ "- Place Flag: marks the selected square as a possible mine.<br><br>"
					+ "3. Keep going until you reveal all safe squares (you win) or reveal a mine (you lose).<br><br>"
					+ "Tip: Use the numbers to figure out where the mines are."
					+ "</html>");

			instructionsPanel.add(instructionsLabel);
			instructionsFrame.add(instructionsPanel);
			instructionsFrame.setVisible(true);
			instructionsFrame.pack();
		}
	}

	/**
	 * this is for the placing the flag button which is used after player selects a block
	 */
	public class placeFlagListener implements ActionListener {
		GameFrame gFrame;
		/**
		 * Constructor just passing through the frame through the code
		 * @param gf the game frame
		 */
		public placeFlagListener(GameFrame gf) {
			gFrame = gf;
		}
		public void actionPerformed(ActionEvent e) {
			Blocks selected = gameP.getSelectedBlock();
			if (selected != null) {
				if (!selected.getIsRevealed() && !selected.getIsFlagged()) {
					numFlags--;
					flagCountLabel.setText(": " + Integer.toString(numFlags)); // Update remaining flags

					ImageIcon pic = new ImageIcon("flags.jpeg");
					Image img = pic.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
					selected.setIcon(new ImageIcon(img));
					selected.setBorder(null);
					selected.setIsFlagged(true);

					if (selected.getIsMine()) {
						correctFlags++; // Correct flag placed
					}
				}
			}
			// Check for win by flagging all mines
			if (correctFlags == gameP.getNumMines()&&numFlags==0) {
				Blocks[][] temp = gameP.getGameArray();
				for (int row = 0; row < temp.length; row++) {
					for (int col = 0; col < temp[row].length; col++) {
						if (temp[row][col].getIsMine()) {
							ImageIcon pic = new ImageIcon("winBlock.jpeg");
							Image img = pic.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
							temp[row][col].setIcon(new ImageIcon(img));
						}
					}
				}
				// Display win message
				JFrame winFrame = new JFrame();
				JPanel winPanel = new JPanel();
				JLabel winLabel = new JLabel("YOU WIN!");
				JButton winButton = new JButton("PLAY AGAIN?");
				winButton.addActionListener(new playAgainListener(this.gFrame, winFrame));
				winPanel.add(winLabel, BorderLayout.NORTH);
				winPanel.add(winButton, BorderLayout.SOUTH);
				winFrame.add(winPanel);
				winFrame.pack();
				winFrame.setVisible(true);
				winFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		}
	}

	/**
	 * this is the code used for the revealBlock button 
	 */
	public class revealBlockListener implements ActionListener {
		GameFrame gFrame;
		/**
		 * Constructor just passing through the frame through the code
		 * @param gf the game frame
		 */
		public revealBlockListener(GameFrame gf) {
			gFrame = gf;
		}
		public void actionPerformed(ActionEvent e) {
			Blocks selected = gameP.getSelectedBlock();
			if (selected != null) {
				// Remove flag if present
				if (selected.getIsFlagged()) {
					numFlags++;
					flagCountLabel.setText(": " + Integer.toString(numFlags));
				}
				// Only act if block isn't already revealed
				if (!selected.getIsRevealed()) {
					if (selected.getIsMine()) {
						// Game over: reveal all mines
						Blocks[][] mines = gameP.getGameArray();
						ImageIcon pic = new ImageIcon("mine.jpeg");
						Image img = pic.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
						selected.setIcon(new ImageIcon(img));
						selected.setBorder(null);
						selected.setIsRevealed(true);

						for (int row = 0; row < mines.length; row++) {
							for (int col = 0; col < mines[row].length; col++) {
								if (mines[row][col].getIsMine()) {
									ImageIcon pic1 = new ImageIcon("mine.jpeg");
									Image img1 = pic1.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
									mines[row][col].setIcon(new ImageIcon(img1));
								}
							}
						}
						// Show lose message
						JFrame loseFrame = new JFrame();
						JPanel losePanel = new JPanel();
						JLabel loseLabel = new JLabel("YOU LOSE");
						JButton loseButton = new JButton("TRY AGAIN?");
						loseButton.addActionListener(new playAgainListener(this.gFrame, loseFrame));
						losePanel.add(loseLabel, BorderLayout.NORTH);
						losePanel.add(loseButton, BorderLayout.SOUTH);
						loseFrame.add(losePanel);
						loseFrame.pack();
						loseFrame.setVisible(true);
						loseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					} else {
						// Count nearby mines
						Blocks[][] tempArray = gameP.getGameArray();
						int x = selected.getXCoord();
						int y = selected.getYCoord();
						int count = 0;

						for (int dy = -1; dy <= 1; dy++) {
							for (int dx = -1; dx <= 1; dx++) {
								if (!(dx == 0 && dy == 0)) {
									int nx = x + dx;
									int ny = y + dy;
									if (nx >= 0 && nx < tempArray[0].length && ny >= 0 && ny < tempArray.length) {
										if (tempArray[ny][nx].getIsMine()) {
											count++;
										}
									}
								}
							}
						}

						if (count == 0) {
							ImageIcon imgIcon = new ImageIcon("nothing.jpeg");
							Queue queue = new Queue();
							Blocks[][] gameArray = gameP.getGameArray();
							boolean[][] visited = new boolean[gameArray.length][gameArray[0].length];

							queue.add(selected);
							visited[selected.getYCoord()][selected.getXCoord()] = true;

							while (!queue.isEmpty()) {
								Blocks current = queue.remove();//removes the block from queue setting at as current block
								int x1 = current.getXCoord();
								int y1= current.getYCoord();
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
										correctReveal++;
										
										// queue all neighbors
										for (int dy = -1; dy <= 1; dy++) {
											for (int dx = -1; dx <= 1; dx++) {
												int nx = x1 + dx;
												int ny = y1 + dy;

												
												if ((dx != 0 || dy != 0) && nx >= 0 && ny >= 0 &&
														nx < gameArray[0].length && ny < gameArray.length &&
														!visited[ny][nx]) {//can't be itself, nx and ny have to be greater than 0 to be a cord
													//have to be greater than 0 to be in the array and less than length for same reason
													//not be visited yet
													queue.add(gameArray[ny][nx]);
													visited[ny][nx] = true;
												}
											}
										}
									} else if (danger == 1) {

										int count1 = 0;
										for (int dy = -1; dy <= 1; dy++) {
											for (int dx = -1; dx <= 1; dx++) {
												int nx = x1 + dx;
												int ny = y1 + dy;

												// check 8 neighbors and count how many are mines(make sure its not out of bounds)
												if ((dx != 0 || dy != 0) && 
														nx >= 0 && ny >= 0 && 
														nx < gameArray[0].length && ny < gameArray.length && gameArray[ny][nx].getIsMine()) {
													count1++;
												}
											}
										}

										if (count1 >= 0 && count1 <= 8) {
											String filename = count1 + ".jpeg";
											ImageIcon pic = new ImageIcon(filename);
											Image img = pic.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
											current.setIcon(new ImageIcon(img));
										}
										correctReveal++;//since block is revealed increase this number
										current.setIsRevealed(true);
										current.setIsFlagged(false);
										current.setBorder(null);
									}
								}
							}
						}else { 
							// Set image based on nearby mine count 
							ImageIcon imgIcon = new ImageIcon(count + ".jpeg");
							Image img = imgIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
							selected.setIcon(new ImageIcon(img));
							selected.setBorder(null);
							selected.setIsRevealed(true);
							selected.setIsFlagged(false);
							correctReveal++;

							// Check win by revealing all non-mines
							if (correctReveal == gameP.getNumNonMines()) {
								Blocks[][] temp = gameP.getGameArray();
								for (int row = 0; row < temp.length; row++) {
									for (int col = 0; col < temp[row].length; col++) {
										if (temp[row][col].getIsMine()) {
											ImageIcon pic = new ImageIcon("winBlock.jpeg");
											Image imgWin = pic.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
											temp[row][col].setIcon(new ImageIcon(imgWin));
										}
									}
								}
								
								// Show win message
								JFrame winFrame = new JFrame();
								JPanel winPanel = new JPanel();
								JLabel winLabel = new JLabel("YOU WIN!");
								JButton winButton = new JButton("PLAY AGAIN?");
								winButton.addActionListener(new playAgainListener(this.gFrame, winFrame));
								winPanel.add(winLabel, BorderLayout.NORTH);
								winPanel.add(winButton, BorderLayout.SOUTH);
								winFrame.add(winPanel);
								winFrame.pack();
								winFrame.setVisible(true);
								winFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
							}
						}
					}
				}
			}
		}
	}
	/**
	 * reveals all the blocks when clicked 
	 */
	public class revealAllListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Blocks[][] blocks = gameP.getGameArray();
			for (int row = 0; row < blocks.length; row++) {
				for (int col = 0; col < blocks[0].length; col++) {
					Blocks selected = blocks[row][col];
					if (!selected.getIsRevealed()) {
						if (selected.getIsMine()) {
							ImageIcon pic = new ImageIcon("mine.jpeg");
							Image img = pic.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
							selected.setIcon(new ImageIcon(img));
						} else {
							int count = 0;
							for (int dy = -1; dy <= 1; dy++) {
								for (int dx = -1; dx <= 1; dx++) {
									if (!(dx == 0 && dy == 0)) {
										int nx = col + dx;//its row and col since its all the blocks rather than just one block
										int ny = row + dy;
										if (nx >= 0 && nx < blocks[0].length &&
												ny >= 0 && ny < blocks.length &&
												blocks[ny][nx].getIsMine()) {
											count++;
										}
									}
								}
							}
							ImageIcon imgIcon;
							if (count == 0) {
								imgIcon = new ImageIcon("nothing.jpeg");
							} else {
								imgIcon = new ImageIcon(count + ".jpeg");
							}
							Image img = imgIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
							selected.setIcon(new ImageIcon(img));
							selected.setBorder(null);
							selected.setIsRevealed(true);
							selected.setIsFlagged(false);
						}
					}
				}
			}
		}
	}
	/**
	 * used when the player clicks play again on the win or lose window
	 */
	public class playAgainListener implements ActionListener {
		GameFrame gFrame;
		JFrame winFrame;
		/**
		 * This constructor used to send the game frame to this code and the win frame to this code so both can be changed  
		 * @param gf the game frame
		 * @param wf the win frame(even though it is also the lose frame)
		 */
		public playAgainListener(GameFrame gf, JFrame wf) {
			gFrame = gf;
			winFrame = wf;
		}
		public void actionPerformed(ActionEvent e) {
			correctFlags = 0;
			correctReveal = 0;
			this.gFrame.setVisible(false);
			this.winFrame.setVisible(false);
			GameFrame newGame = new GameFrame(); // Start new game
		}
	}
}
