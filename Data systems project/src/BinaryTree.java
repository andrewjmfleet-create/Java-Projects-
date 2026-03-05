/*******
 * @author Andrew Fleet(with help from Flint and class work)
 * @dueDate Wed Jan 29
 * @description This is the class for my Binary Tree which I use in my simulation.
 */
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class BinaryTree {

	/**
	 * This is the Node class for the binary tree that we created
	 */
	private class Node {
		String data;
		Node left;
		Node right;
		/**
		 * This is the constructor method for the node class in the Binary Tree
		 * @param data data that the node stores
		 */
		public Node(String data) {
			this.data = data;
			this.left = null;
			this.right = null;
		}
	}
	private Node root; // Root of the binary tree
	/**
	 * constructor of the binary tree
	 */
	public BinaryTree() {
		root = null; // Initially, the tree is empty
	}
	/**
	 * Method which inserts the info in the right spot
	 * @param s the string being inserted
	 */
	public void insert(String s) {
		if (root == null) {
			root = new Node(s);
		} else {
			Node currentNode = root;
			Node parent = root;
			while (currentNode != null) {
				if (s.compareTo(currentNode.data) < 0) {
					parent = currentNode;
					currentNode = currentNode.left;
				} else {
					parent = currentNode;
					currentNode = currentNode.right;
				}
			}
			if (s.compareTo(parent.data) < 0) {
				parent.left = new Node(s);
			} else {
				parent.right = new Node(s);
			}
		}
	}
	/**
	 * method which search's for the value 
	 * @param value value being searched
	 * @return if the value if there or not
	 */
	public boolean search(String value) {
		Node current = root;

		while (current != null) {
			if (current.data.equals(value)) {
				return true;
			} else if (value.compareTo(current.data) < 0) {
				current = current.left;
			} else {
				current = current.right;
			}
		}

		return false;
	}
	/**
	 * method for deleting a value from the tree
	 * Method partially written by flint and myself
	 * @param value value being deleted
	 */
	public void delete(String value) {
		Node parent = null;
		Node current = root;

		while (current != null && !current.data.equals(value)) {
			parent = current;
			if (value.compareTo(current.data) < 0) {
				current = current.left;
			} else {
				current = current.right;
			}
		}

		if (current == null) {
			return;
		}

		// Case 1: Node to delete has no children (is a leaf)
		if (current.left == null && current.right == null) {
			if (parent == null) {
				root = null;
			} else if (parent.left == current) {
				parent.left = null;
			} else {
				parent.right = null;
			}
			return;
		}

		// Case 2: Node to delete has only one child
		if (current.left == null || current.right == null) {
			Node child;
			if (current.left != null) {
				child = current.left;
			} else {
				child = current.right;
			}

			if (parent == null) {
				root = child;
			} else if (parent.left == current) {
				parent.left = child;
			} else {
				parent.right = child;
			}
			return;
		}

		// Case 3: Node to delete has two children
		Node successorParent = current;
		Node successor = current.right;

		// Find the smallest value in the right subtree
		while (successor.left != null) {
			successorParent = successor;
			successor = successor.left;
		}

		// Copy successor data to current node
		current.data = successor.data;

		// Delete the successor (which must have at most one child)
		if (successorParent == current) {
			successorParent.right = successor.right;
		} else {
			successorParent.left = successor.right;
		}
	}
	/**
	 * printing in order method
	 */
	public void printInOrder() {
		// Start the recursion at the root
		printInOrderHelper(root);
	}
	/**
	 * recursive method which supports the print in order method
	 * @param current which ever node it is on currently
	 */
	private void printInOrderHelper(Node current) {
		if(current==null) {
			//nothing happens if it is null
		}else {
			printInOrderHelper(current.left);
			System.out.println(current.data);
			printInOrderHelper(current.right);
		}
	}
	/**
	 * gets the height of the Binary Tree
	 * @return
	 */
	public int getHeight() {
		return getHeightHelper(root)+1;
	}
	/**
	 * this method supports the get height method by doing all the work
	 * @param current which ever node it is currently on
	 * @return the value of height
	 */
	private int getHeightHelper(Node current) {
		if (current == null) {
			return -1;
		}
		int lh = getHeightHelper(current.left);
		int rh = getHeightHelper(current.right);
		if (rh>lh) {
			return rh + 1;//plus one because it starts at 0
		} else {
			return lh + 1;//plus one because it starts at 0
		}
	}
	/**
	 * this is the non recursive version of get height which I do not use
	 * @return the value of the height
	 */
	public int getHeightNonRecursion() {
	    if (root == null) {
	        return 0; // An empty tree has a height of 0
	    }

	    Node current = root;
	    int maxHeight = 0;     // maximum depth 
	    int currentDepth = 0;  // current depth 

	    while (current != null) {
	        if (current.left == null) {
	            // No left subtree = move to the right + count this level
	            currentDepth++;
	            if (currentDepth > maxHeight) {
	                maxHeight = currentDepth; //Update maxHeight 
	            }
	            current = current.right;
	        } else {
	            // Find the in-order predecessor of the current node
	            Node predecessor = current.left;
	            int tempDepth = 1; // Count levels in the left subtree
	            while (predecessor.right != null && predecessor.right != current) {
	                predecessor = predecessor.right;
	                tempDepth++;
	            }

	            if (predecessor.right == null) {
	                //temp link to the current node + move left
	                predecessor.right = current;
	                currentDepth++;
	                current = current.left;
	            } else {
	                // Break temp link + move right
	                predecessor.right = null;
	                if (currentDepth > maxHeight) {
	                    maxHeight = currentDepth; // maxHeight update
	                }
	                currentDepth -= tempDepth; // Move back
	                current = current.right;
	            }
	        }
	    }

	    return maxHeight;
	}
	/**
	 * this prints the binary tree to a file in order
	 * @param filename name of file it is printed to
	 */
	public void printInOrderToFile(String filename) {
	    try {
	        FileWriter writer = new FileWriter(filename);
	        PrintWriter pw = new PrintWriter(writer);
	        printInOrderRecursive(root, pw);
	        pw.close();
	    } catch (IOException e) {
	        System.out.println("An error occurred while writing to the file: " + e.getMessage());
	    }
	}
	/**
	 * this is the helper for the last method it is doing most of the work(recursive)
	 * @param node node it is currently on
	 * @param pw print writer
	 */
	private void printInOrderRecursive(Node node, PrintWriter pw) {
	    if (node == null) {
	    }else {
	    	printInOrderRecursive(node.left, pw); // Recursively traverse the left subtree
		    pw.println(node.data); // Print the data of the current node
		    printInOrderRecursive(node.right, pw); // Recursively traverse the right subtree
	    }
	    
	    
	}
	/**
	 * reading in from the file it printed to method
	 * @param filename which ever file it is reading in from
	 */
	public void readFromFile(String filename) {
		try {
			FileReader fr = new FileReader(filename);
			Scanner scanner = new Scanner(fr);

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				this.insert(line);  
			}
			scanner.close();
			fr.close();
		} catch (IOException e) {
			System.out.println("An error occurred while reading from the file: " + e.getMessage());
		}
	}
	/**
	 * this is the combined operations method that actually does the combined operations that is talked about in the main method
	 */
	public void actualCombinedOperations() {
		// Insert elements into the tree

		this.insert("pear");
		this.insert("apple");
		this.insert("watermelon");
		this.insert("orange");
		this.insert("banana");
		this.insert("zucchini");

		// Create an array of Queue objects based on the tree height
		int height = this.getHeight();
		Queue[] queueList = new Queue[height];

		// Initialize each queue
		for (int i = 0; i < height; i++) {
			queueList[i] = new Queue();
		}

		// Store nodes level-wise in the queues
		storeNodesByLevel(this.root, queueList, 0);

		// Print and write each queue to a file
		for (int i = 0; i < height; i++) {
			System.out.println("Level " + (i+1) + ": ");
			queueList[i].printQueue();  // Print queue contents
		}
	}	
	/**
	 * this is the recursive method which the combined operations method calls to sort the tree into queues
	 * @param node node it is currently on
	 * @param queueList array list of queues
	 * @param level which level it is currently on
	 */
	private void storeNodesByLevel(Node node, Queue[] queueList, int level) {
	    if (node != null) {  
	        if (level < queueList.length) {
	            queueList[level].add(node.data);  // Add node data to the corresponding level queue
	            
	            // Continue with left and right children only if within bounds
	            storeNodesByLevel(node.left, queueList, level + 1);
	            storeNodesByLevel(node.right, queueList, level + 1);
	        } else {
	            System.out.println("Failed to do");
	        }
	    }
	}
	public static void main(String[] args) {
		
		
	}
}