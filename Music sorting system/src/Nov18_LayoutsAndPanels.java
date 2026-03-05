import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Nov18_LayoutsAndPanels {
	JLabel numbers;
	double num1;
	double num2;
	boolean plus=false;
	boolean subtract=false;
	boolean divide=false;
	boolean multiply=false;

	public static void main(String[] args) {

		/*
		 * Java Layout Managers:
		 * 1. Grid Layout
		 * 	  - You chose the number of rows and columns
		 * 	  - Components are added one row at a time.
		 * 	  - Makes all the components the same size.
		 * 	  - Lines up with 2D arrays - you can't access one specific location.
		 * 2. Border Layout
		 *	  - North, east, south, west, center
		 *	  - If areas are not used, the other components
		 *		expand to take up that space
		 *	  -You can specify the area you want to 
		 * 		add components to.
		 * 	  - Area will resize to fit your component
		 * 3. Box Layout
		 * 	  - Either a horizontal row or a vertical columns
		 * 	  - New BoxLayout(Container c, int axis)
		 * 		axis is either X_AXIS or Y_AXIS
		 * 4. Flow Layout (Default on JPanels)
		 * 	  -Adds components in line from left to right
		 * 	   before moving to the next components 
		 * 	   reorganize when window resized
		 * 	   WE RARELY WANT TO USE THIS
		 * 
		 * 
		 */

		new Nov18_LayoutsAndPanels();
	}

	public Nov18_LayoutsAndPanels() {
		//Makes a frame
		JFrame calc = new JFrame();

		//Make 3 panels
		//1 - numeric key pad - grid layout with - 3/4 - buttons
		//2 - operations - box layout vertical - 4 buttons
		//3 - one label

		JPanel numberPanel = new JPanel(new GridLayout(4,3));
		int counter=9;
		JButton[][] keyPad = new JButton[4][3];
		for(int row = 0; row<keyPad.length; row++) {
			for(int col = keyPad[row].length-1; col>=0; col--) {
				if(counter>=0) {
					String c = Integer.toString(counter);
					keyPad[row][col]= new JButton(c);
					keyPad[row][col].addActionListener(new CalcListener());
					counter--;
				}else if(counter ==-1) {
					keyPad[row][col]= new JButton(".");
					counter--;
					keyPad[row][col].addActionListener(new CalcListener());
				}else {
					keyPad[row][col]= new JButton("=");
					keyPad[row][col].addActionListener(new EqualListener());
				}

			}
		}
		for(int row = 0; row<keyPad.length; row++) {
			for(int col = 0; col<keyPad[row].length; col++) {
				numberPanel.add(keyPad[row][col]);
			}
		}
		JPanel operationsPanel = new JPanel();
		BoxLayout b = new BoxLayout(operationsPanel,BoxLayout.Y_AXIS);
		operationsPanel.setLayout(b);
		JButton ac = new JButton("ac");
		JButton div = new JButton("/");
		JButton mult= new JButton("*");
		JButton sub = new JButton("-");
		JButton add = new JButton("+");
		ac.addActionListener(new ClearListener());
		div.addActionListener(new OperationListener());
		mult.addActionListener(new OperationListener());
		sub.addActionListener(new OperationListener());
		add.addActionListener(new OperationListener());
		
		operationsPanel.add(ac);
		operationsPanel.add(div);
		operationsPanel.add(mult);
		operationsPanel.add(sub);
		operationsPanel.add(add);

		JPanel screen = new JPanel();
		numbers = new JLabel("0");
		screen.add(numbers);
		calc.add(numberPanel);
		calc.add(operationsPanel,BorderLayout.EAST);
		calc.add(screen,BorderLayout.NORTH);
		calc.pack();
		calc.setVisible(true);
		calc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public class CalcListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			JButton btn = (JButton) e.getSource();
			String currentText = numbers.getText();
			String buttonText = btn.getText();

			// Append the button's text to the current label text
			if (currentText.equals("0")) {
				// Replace the default "0" with the button's text
				numbers.setText(buttonText);
			} else {
				// Append the new text to the existing label
				numbers.setText(currentText + buttonText);
			}
		}
	}
	public class ClearListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			numbers.setText("");
		}
	}
	public class EqualListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			num2 = Double.parseDouble(numbers.getText());
			double answer =0;
			if(plus) {
				answer = num1+num2;
			}else if(subtract) {
				answer = num1-num2;
			}else if(divide&& num2!=0) {
				answer = num1/num2;
			}else if(multiply){
				answer = num1*num2;
			}
			numbers.setText(Double.toString(answer));
		}
	}
	public class OperationListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			JButton b = (JButton)e.getSource();
			char op = b.getText().charAt(0);
			plus = false;
			subtract = false;
			divide = false;
			multiply = false;
			switch(op) {
			case'+':
				plus=true;
				break;
			case'-':
				subtract = true;
				break;
			case'*':
				multiply = true;
				break;
			case '/':
				divide = true;
				break;
			}
			num1 = Double.parseDouble(numbers.getText());
			numbers.setText("");
		}
	}
}
