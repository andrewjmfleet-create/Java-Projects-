/*******
 * @author Andrew Fleet
 * @dueDate Wed Jan 29
 * @description This is the class for my Queue which I use in my simulation.
 */
import java.io.*;
import java.util.Scanner;

public class Queue {
	/**
	 * This is the Node class for the binary tree that we created
	 */
    private class Node {
        String data;
        Node next;
        /**
		 * This is the constructor method for the node class in the Binary Tree
		 * @param d data that the node stores
		 * @param n next node on the queue
		 */
        private Node(String d, Node n) {
            data = d;
            next = n;
        }
    }

    private Node front;
    private Node back;

    /**
     * Check if it is empty
     * @return the result of the check
     */
    public boolean isEmpty() {
        return front == null;  // If front is null, the queue is empty
    }

    /**
     * Adding the string to the back of the queue
     * @param N the string being added
     */
    public void add(String N) {
        Node newNode = new Node(N, null);
        if (isEmpty()) {
            front = newNode;  // If the queue is empty, both front and back are the same
        } else {
            back.next = newNode;  // Link the current back node to the new node
        }
        back = newNode;  // Update the back pointer to the new node
    }

   /**
    * removing the string from the front of the queue
    * @return the string being removed
    */
    public String remove() {
        if (isEmpty()) {
            System.out.println("Queue is empty.");
            return null;  // Return null if the queue is empty
        }
        Node d = front;  // Save the data to return it
        front = front.next;  // Move the front pointer to the next node

        // If the queue becomes empty after removing, set back to null
        if (front == null) {
            back = null;
        }

        return d.data;  // Return the removed item
    }

    /**
     * method to print the queue
     */
    public void printQueue() {
        if (isEmpty()) {
            System.out.println("Queue is empty.");
            return;
        }

        Node currentNode = front;
        System.out.print("Queue: ");
        while (currentNode != null) {
            System.out.print(currentNode.data + " ");
            currentNode = currentNode.next;
        }
        System.out.println();  // Print a new line at the end
    }

    /**
     * method of writing the queue to a file
     * @param filename name of file it is being written to
     */
    public void writeToFile(String filename) {
        try {
        	FileWriter writer = new FileWriter(filename);
        	PrintWriter pw = new PrintWriter(writer);
            Node currentNode = front;
            while (currentNode != null) {
            	pw.println(currentNode.data); 
                currentNode = currentNode.next;
            }
            pw.close();
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file " );
        }
    }

    /**
     * method of reading the queue to a file
     * @param filename name of file it is being read to
     */
    public void readFromFile(String filename) {
    	
        try {
        	FileReader fr = new FileReader(filename);
        	Scanner scanner = new Scanner(fr);
        	while (scanner.hasNext()) {
                this.add(scanner.nextLine());  
            }
            fr.close();
        } catch (IOException e) {
            System.out.println("An error occurred while reading from the file ");
        }
    }

    
    public static void main(String[] args) {
    }
}