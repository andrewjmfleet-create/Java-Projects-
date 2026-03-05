/*******
 * @author Andrew Fleet
 * @dueDate Wed May 21
 * @description this is the block class of my game which extends the JButton object
 */
import java.awt.Color;
import java.awt.Image;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Blocks extends JButton {
    private boolean isMine;//if it is a mine
    private int x;//x cord
    private int y;//y cord
    private boolean isSelected = false;//if it is selected in the game
    private boolean isRevealed = false;// if it is revealed in the game
    private boolean isFlagged = false;// if it is flagged in the game
    private int dangerLevel = -1;//how close it is to a mine
    /**
     * This is the constructor for the block class assigning values to some 
     * of the needed values for this class
     * @param isMine boolean for if it is a mine or not
     * @param x its x cord
     * @param y its y cord
     */
    public Blocks(boolean isMine, int x, int y) {
        this.isMine = isMine;
        this.x = x;
        this.y = y;

        ImageIcon pic = new ImageIcon("grass.jpeg");//starting image
        Image img = pic.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        this.setIcon(new ImageIcon(img));
    }
    /**
     * Its the get method of isMine
     * @return return the value for isMine
     */
    public boolean getIsMine() {
        return isMine;
    }
    /**
     * it is the class to set if it is selected or not(just sets the border to red)
     * @param selected needs to set it to true or false
     */
    public void setIsSelected(boolean selected) {
        isSelected = selected;
        if (selected) {
            this.setBorder(new LineBorder(Color.RED));
        } else {
            this.setBorder(null);//turns it off when its not selected
        }
    }
    /**
     * it is the get method for isSelected
     * @return this is the value(boolean) for isSelected
     */
    public boolean getIsSelected() {
        return isSelected;
    }
    /**
     * this is the get method for the x-cord
     * @return returns the x cord
     */
    public int getXCoord() {
        return x;
    }
    /**
     * this is the get method for the y-cord
     * @return returns the y cord
     */
    public int getYCoord() {
        return y;
    }
    /**
     * it is the get method for isRevealed
     * @return this is the value(boolean) for isSelected
     */
    public boolean getIsRevealed() {
        return isRevealed;
    }
    /**
     * this is the set method for isRevealed 
     * @param revealed the boolean that isRevealed gonna be set to
     */
    public void setIsRevealed(boolean revealed) {
        this.isRevealed = revealed;
    }
    /**
     * this is the set method for isFlagged 
     * @param flagged the boolean that isFlagged gonna be set to
     */
    public void setIsFlagged(boolean flagged) {
        isFlagged = flagged;
    }
    /**
     * it is the get method for isFlagged
     * @return this is the value(boolean) for isFlagged
     */
    public boolean getIsFlagged() {
        return isFlagged;
    }

    /**
     * it is the get method for dangerLevel
     * @return this is the value(int) for dangerLevel
     */
    public int getDangerLevel() {
        return dangerLevel;
    }
    /**
     * this is the set method for dangerLevel 
     * @param dangerLevel the int that dangerLevel gonna be set to
     */
    public void setDangerLevel(int dangerLevel) {
        this.dangerLevel = dangerLevel;
    }
}