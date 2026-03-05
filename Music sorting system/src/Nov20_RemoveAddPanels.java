import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Nov20_RemoveAddPanels {
	JFrame frame;
	JPanel p2;
	JPanel p3;
	JPanel p4;
	boolean p2IsHere=true;
    public static void main(String[] args) {
    	new Nov20_RemoveAddPanels();
    }
    public Nov20_RemoveAddPanels() {
    	JPanel p1 = new JPanel();
    	p2 = new JPanel();
    	p3 = new JPanel();
    	p4 = new JPanel();
    	JLabel l1 = new JLabel("idk");
    	JLabel l2 = new JLabel("idk");
    	JLabel l3 = new JLabel("idk");
    	JButton b1 = new JButton("Add/Remove Panel 2");
    	JButton b2 = new JButton("Add/Remove Panel 3");
    	JButton b3 = new JButton("Add/Remove Panel 4");
    	b1.addActionListener(new Panel1Listener());
    	p2.add(l1);
    	p3.add(l2);
    	p4.add(l3);
    	new BorderLayout();
		p1.add(b1, BorderLayout.EAST);
    	p1.add(b2,BorderLayout.WEST);
    	p1.add(b3,BorderLayout.CENTER);
    	
    	
    	
    	frame = new JFrame();
    	frame.add(p1, BorderLayout.NORTH);
    	frame.add(p2,BorderLayout.SOUTH);
    	frame.add(p3,BorderLayout.EAST);
    	frame.add(p4,BorderLayout.WEST);
    	frame.pack();
    	frame.setVisible(true);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    }
    public class Panel1Listener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(p2IsHere) {
				frame.remove(p2);
				p2IsHere=false;
			}else {
				frame.add(p2,BorderLayout.SOUTH);
				p2IsHere=true;
			}
			frame.revalidate();
			frame.repaint();
		}
    	
    }
}