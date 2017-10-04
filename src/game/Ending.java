package game;

import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;

public class Ending extends JFrame implements ActionListener{
	private boolean wol_win = false;
	private boolean cap_win = false;
	private JLabel bgLabel;

	public void showview() {
		setSize(800, 600);
		
		if(wol_win) {
			ImageIcon bg = new ImageIcon("wolf_win.png");
			bgLabel = new JLabel(bg);
		}
		else if(cap_win) {
			ImageIcon bg = new ImageIcon("USA_win.png");
			bgLabel = new JLabel(bg);
		}
		
		bgLabel.setBounds(0, 0, 800, 600);
	    JPanel imagePanel = (JPanel) getContentPane();
	    setBackground(Color.WHITE);
        imagePanel.setOpaque(false);
        getLayeredPane().add(bgLabel, new Integer(Integer.MIN_VALUE));
        setResizable(false);
		setVisible(true);
	}
	
	/* check that who won the game */
	public void set_wol_Win(boolean test) {
		if(test)
			wol_win = true;
	}
	public void set_cap_Win(boolean test) {
		if(test)
			cap_win = true;
	}
	
	public void actionPerformed(ActionEvent e) {
		
	}
	
}
