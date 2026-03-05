/*******
 * @author Andrew Fleet
 * @dueDate Wed Jan 29
 * @description This is my main class which has all the parts and methods needed for making my Data Structure Simulator
 */
import java.io.*;
import java.util.Scanner;

public class DataStructureSimulator {
	static BinaryTree bst = new BinaryTree();
	static Queue queue = new Queue();
	
	public static void main(String[] args) {
		simulation();
	}
	/**
	 * This is all the different methods combined into one method and then done in the main method
	 */
	public static void simulation() {
		queue.readFromFile("queue_data.txt");
		bst.readFromFile("bst_data.txt");
		Scanner scanner = new Scanner(System.in);
		boolean running = true;

		while (running) {
			System.out.println("\nSelect the data structure to simulate:");
			System.out.println("1. Family Tree (BST)");
			System.out.println("2. Coffee Shop Queue");
			System.out.println("3. Explanation: Why a Family Tree is a BST");
			System.out.println("4. Explanation: Why a Queue is Best for a Coffee Shop Line");
			System.out.println("5. Binary Tree Visulization");
			System.out.println("6. Combined Operations");
			System.out.println("7. Exit");
			System.out.print("Choose an option: ");

			int choice = -1;  // Initialize choice to an invalid value
	        try {
	            choice = scanner.nextInt();
	            scanner.nextLine();  // Consume the newline character
	        } catch (Exception e) {    
	            scanner.nextLine();  // Clear the buffer
	        }
			

			if (choice == 1) {
				simulateFamilyTree(scanner);
			} else if (choice == 2) {
				simulateQueue(scanner);
			} else if (choice == 3) {
				explainFamilyTree();
			} else if (choice == 4) {
				explainQueue();
			} else if (choice == 5) {
				basicBinaryTreeVisualization(scanner);
			} else if (choice == 6) {
				combinedOperationsExpo();
			} else if (choice == 7) {
				running = false;
				System.out.println("Exiting the simulator. Goodbye!");
				queue.writeToFile("queue_data.txt");
				bst.printInOrderToFile("bst_data.txt");;
			} else {
				System.out.println("Invalid option. Please choose again.");
			}
		}
	}
	/**
	 * this class is used to simulate the family tree using a binary tree
	 * @param scanner Scanner used throughout(I was to lazy to keep making them)
	 */
	public static void simulateFamilyTree(Scanner scanner) {
	    boolean running = true;
	    while (running) {
	        System.out.println("Family Tree Simulation:");
	        System.out.println("1. Add family member");
	        System.out.println("2. Remove family member");
	        System.out.println("3. Search for family member");
	        System.out.println("4. Display family tree");
	        System.out.println("5. Back to main menu");
	        System.out.print("Choose an option: ");

	        int choice = -1;  
	        try {
	            choice = scanner.nextInt();
	            scanner.nextLine(); 
	        } catch (Exception e) {
	            System.out.println("Invalid input. Please enter a valid number.");
	            scanner.nextLine(); 
	        }

	        if (choice == 1) {
	            System.out.println("Adding a family member:");
	            System.out.println("In a BST, new members are added based on their order,");
	            System.out.println("with smaller values going to the left and larger values to the right.");
	            System.out.print("Enter a family member's name to add: ");
	            String data = scanner.nextLine();
	            bst.insert(data);
	        } else if (choice == 2) {
	            System.out.println("Removing a family member:");
	            System.out.println("When a member is removed, the BST rearranges itself to maintain order,");
	            System.out.println("which ensures proper structure of the tree.");
	            System.out.print("Enter a family member's name to remove: ");
	            String data = scanner.nextLine();
	            bst.delete(data);
	        } else if (choice == 3) {
	            System.out.println("Searching for a family member:");
	            System.out.println("A BST allows quick searches by checking whether a value is smaller or larger,");
	            System.out.println("navigating left or right accordingly.");
	            System.out.print("Enter a family member's name to search for: ");
	            String data = scanner.nextLine();
	            if (bst.search(data)) {
	                System.out.println("Family member found in the tree.");
	            } else {
	                System.out.println("Family member not found in the tree.");
	            }
	        } else if (choice == 4) {
	            System.out.println("Displaying the family tree:");
	            System.out.println("The tree is displayed in sorted order (in-order traversal).");
	            System.out.println("Family tree contents: ");
	            bst.printInOrder();
	        } else if (choice == 5) {
	            running = false;
	            bst.printInOrderToFile("bst_data.txt");
	        } else {
	            System.out.println("Invalid option. Please choose again.");
	        }
	    }
	}
	/**
	 * this class is made to simulate a line in a coffee shop 
	 * @param scanner Scanner used throughout(I was to lazy to keep making them)
	 */
	public static void simulateQueue(Scanner scanner) {
		boolean running = true;
		while (running) {
			System.out.println("Coffee Shop Queue Simulation:");
			System.out.println("1. Add customer to the queue");
			System.out.println("2. Serve customer from the queue");
			System.out.println("3. Display queue");
			System.out.println("4. Back to main menu");
			System.out.print("Choose an option: ");
			int choice = -1;  // Initialize choice to an invalid value
	        try {
	            choice = scanner.nextInt();
	            scanner.nextLine();  // Consume the newline character
	        } catch (Exception e) {
	            System.out.println("Invalid input. Please enter a valid number.");
	            scanner.nextLine();  // Clear the buffer
	        }

			if (choice == 1) {
				System.out.println("Adding to a Queue is very simple:");
				System.out.println("Queues use the first in first out principal meaning");
				System.out.println("when you enter in a name it goes to the back of the queue.");
				System.out.println("Furthermore you can only add to the back of the queue and no other places");
				System.out.print("Enter customer name: ");
				String customer = scanner.nextLine();
				queue.add(customer);
				queue.printQueue();
			} else if (choice == 2) {
				System.out.println("Removing from a Queue is very simple:");
				System.out.println("The system just removes which ever name you entered first");
				queue.remove();
				queue.printQueue();
			} else if (choice == 3) {
				queue.printQueue();
			} else if (choice == 4) {
				running = false;
			} else {
				System.out.println("Invalid option. Please choose again.");
			}
		}
	}
	/**
	 * This method is made for the user to visualize the making of a binary tree
	 * @param scanner Scanner used throughout(I was to lazy to keep making them)
	 */
	public static void basicBinaryTreeVisualization(Scanner scanner) {
		System.out.println();
		System.out.println("If you were to input numbers into a binary tree(BST), the tree sorts them in a specific way");
		System.out.println("Lets say you first inputed 5");
		System.out.println("The BST would look like:");
		System.out.println("5");
		System.out.println("Then you inputed 7:");
		System.out.println("5");
		System.out.println(" \\");
		System.out.println("   7");
		System.out.println("Then you inputed 2:");
		System.out.println("  5");
		System.out.println(" / \\");
		System.out.println("2   7");
		System.out.println();
		System.out.println("Now you try out some numbers (not 0)");
	
		
		int num1=0;
		int num2= 0;
		int num3= 0;
		do {
			System.out.println("Input your root(first number):");
			try {
				num1=scanner.nextInt();
			} catch (Exception e) {
	            System.out.println("Invalid input. Please enter a valid number.");
	            scanner.nextLine();  // Clear the buffer
	            num1 =0;
	        }
		}while(num1==0);
		do {
			System.out.println("Input your second number:");
			try {
				num2=scanner.nextInt();
			} catch (Exception e) {
	            System.out.println("Invalid input. Please enter a valid number.");
	            scanner.nextLine();  // Clear the buffer
	            num2=0;
	        }
		}while(num2==0);
		do {
			System.out.println("Input your third number:");
			try {
				num3=scanner.nextInt();
			} catch (Exception e) {
	            System.out.println("Invalid input. Please enter a valid number.");
	            scanner.nextLine();  // Clear the buffer
	            num3 =0;
	        }
		}while(num3==0);
		
		
		if(num2>num1) {
			if(num1>num3) {
				System.out.println("Here is your BST:");
				System.out.println("  "+num1);
				System.out.println(" / \\");
				System.out.println(num3+"   "+num2);
			}else if(num3>num2) {
				System.out.println("Here is your BST:");
				System.out.println(num1);
				System.out.println("\\");
				System.out.println(num2);
				System.out.println("  \\");
				System.out.println(num3);
			}else{
				System.out.println("Here is your BST:");
				System.out.println(num1);
				System.out.println("\\");
				System.out.println(num2);
				System.out.println("  /");
				System.out.println(num3);
			}
		}else if(num2<num1) {
			if(num1<num3) {
				System.out.println("Here is your BST:");
				System.out.println("  "+num1);
				System.out.println(" / \\");
				System.out.println(num2+"   "+num3);
			}else if(num2>num3) {
				System.out.println("Here is your BST:");
				System.out.println("    "+num1);
				System.out.println("   /");
				System.out.println("  "+num2);
				System.out.println(" /");
				System.out.println(num3);
			}else{
				System.out.println("Here is your BST:");
				System.out.println("    "+num1);
				System.out.println("   /");
				System.out.println("  "+num2);
				System.out.println("   \\");
				System.out.println("   "+num3);
			}
		}
		
		
		
	}
	/**
	 * This is the explanation of why the binary tree was used
	 */
	public static void explainFamilyTree() {
		System.out.println("Explanation: Why a Family Tree is a BST:");
		System.out.println("A Binary Search Tree (BST) is a good choice for representing a family tree because:");
		System.out.println("1. The hierarchical structure of a family tree is naturally suited to a tree.");
		System.out.println("2. BSTs allow efficient search, insertion, and deletion operations, which are important when");
		System.out.println("   managing family members, adding new ones, or removing them.");
		System.out.println("3. In a BST, family members can be ordered in a way that allows for quick retrieval of ancestor");
		System.out.println("   and descendant relationships, as well as efficient traversal to visualize family lines.");
		System.out.println();
	}

	/**
	 * explanation on why the queue was used
	 */
	public static void explainQueue() {
		System.out.println();
		System.out.println("Explanation: Why a Queue is Best for a Coffee Shop Line:");
		System.out.println("A Queue is the best data structure for a coffee shop line because:");
		System.out.println("1. A queue follows the FIFO (First In, First Out) principle, meaning the first customer to");
		System.out.println("   enter the queue will be the first one to be served (this is how lines work in real life).");
		System.out.println("2. This is useful in situations where a simple line management system is needed.");
		System.out.println("3. In this situation customer who are served are immiediatly removed from the front line");
		System.out.println("   making the queue a natural fit for this use case.");
		System.out.println();
	}
	/**
	 * Gives the user of an example of the ways you can combine the operations of different data structures
	 */
	public static void combinedOperationsExpo() {
	    System.out.println("It is possible to actually combine two data structures to");
	    System.out.println("simplify the operations of one.");
	    System.out.println();
	    System.out.println("For example, it is easy to traverse a Binary Tree using a Queue.");
	    System.out.println();
	    System.out.println("Here's how it works:");
	    System.out.println("1. Start by adding the root of the tree to the queue.");
	    System.out.println("2. While the queue is not empty:");
	    System.out.println("   a) Remove the front node from the queue.");
	    System.out.println("   b) Process the node (Ex: print its value).");
	    System.out.println("   c) Add the node's left and right children to the queue (if they exist).");
	    
	    
	    System.out.println("In this way, we combine the hierarchical structure of a Binary Tree");
	    System.out.println("with the sequential processing of a Queue.");
	    
	   System.out.println("Here is a demonstration with a Binary tree with the words, pear, apple, watermelon, orange, banana, and zucchini in it ");
	   BinaryTree bst1 = new BinaryTree();
	   bst1.insert("pear");
	   bst1.insert("apple");
	   bst1.insert("watermelon");
	   bst1.insert("orange");
	   bst1.insert("banana");
	   bst1.insert("zucchini");
	   bst1.actualCombinedOperations();
	}
	

}