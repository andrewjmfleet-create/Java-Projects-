/*******
 * @author Andrew Fleet
 * @dueDate Wed May 21
 * @description This is the class for my Queue which I use in my game, this is the same class from the simulation I did for project 3.
 */
import java.io.*;
import java.util.Scanner;

public class Queue {
	/**
	 * This is the Node class for the binary tree that we created
	 */
    private class Node {
        Blocks data;
        Node next;
        /**
		 * This is the constructor method for the node class in the Binary Tree
		 * @param d data that the node stores
		 * @param n next node on the queue
		 */
        private Node(Blocks d, Node n) {
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
    public void add(Blocks N) {
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
    public Blocks remove() {
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

}