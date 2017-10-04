package game;

import javax.swing.*;

import java.awt.Color;
import java.awt.Window;
import java.awt.event.*;
import java.util.Random;

public class Index extends JFrame implements ActionListener{

	private JButton enter_btn;
	Random gen = new Random();
	private int ran;
	private JLabel bgLabel;
	
	public static void main(String[] args) {
		Index frame = new Index();
		frame.setVisible(true);
	}
	
	public Index() {
		super("title");
		ran = gen.nextInt(5) + 1;
		
		setSize(800, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		
		/* Background */
		if(ran == 1) {
			ImageIcon bg = new ImageIcon("index_bg.jpg");
			bgLabel = new JLabel(bg);
		}
		else if(ran == 2) {
			ImageIcon bg = new ImageIcon("index_bg3.png");
			bgLabel = new JLabel(bg);
		}
		else if(ran == 3) {
			ImageIcon bg = new ImageIcon("index_bg4.jpg");
			bgLabel = new JLabel(bg);
		}
		else if(ran == 4) {
			ImageIcon bg = new ImageIcon("index_bg5.jpg");
			bgLabel = new JLabel(bg);
		}
		else if(ran == 5) {
			ImageIcon bg = new ImageIcon("index_bg6.jpg");
			bgLabel = new JLabel(bg);
		}
        bgLabel.setBounds(0, 0, 800, 600);
	    JPanel imagePanel = (JPanel) getContentPane();
	    setBackground(Color.BLACK);
        imagePanel.setOpaque(false);
        getLayeredPane().add(bgLabel, new Integer(Integer.MIN_VALUE));
		
        /* Play Button */
		Icon fight = new ImageIcon("fight.png");
		enter_btn = new JButton(fight);
		enter_btn.setSize(250, 122);
		enter_btn.setLocation(270, 340);
		enter_btn.setOpaque(false);
		enter_btn.setContentAreaFilled(false);
		enter_btn.setBorderPainted(false);
		add(enter_btn);
		enter_btn.addActionListener(this);
		setResizable(false);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == enter_btn) {
			Frame play = new Frame();
			play.setVisible(true);
			this.setVisible(false);
		}
	}

}
