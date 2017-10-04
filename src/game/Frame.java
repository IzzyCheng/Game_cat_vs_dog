package game;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;
import java.awt.GridLayout;
import java.awt.event.*;
import java.awt.Color;
import java.awt.*;

public class Frame extends JFrame {
	static Boolean isClick = false;
	static Boolean turn = false;
	static Boolean wind_change = false;
	static Boolean wolf_hit = false;
	static Boolean USA_hit = false;
	static double Vy = 0, Vx = 0;
	static int power = 0;
	static int wind_speed = 0;
	static int left_throw = 0;
	static int right_throw = 0;
	static int wolf_blood = 100;
	static int man_blood = 100;
	
    private JLabel fishlabel1 = null;
	private JProgressBar strength = new JProgressBar(0, 100);
	private Timer timer = null;
	private TimerTask task = null;
	Random gen = new Random();
	
	public Frame () {
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		wind_speed = gen.nextInt(41)-20;
		
		//Set Background picture
	    ImageIcon background = new ImageIcon("back.png");
	    JLabel bgLabel = new JLabel(background);
        bgLabel.setBounds(0, 0, 800, 600);
	    JPanel imagePanel = (JPanel) getContentPane();
        imagePanel.setOpaque(false);
        getLayeredPane().add(bgLabel, new Integer(Integer.MIN_VALUE));
        //end Background
        
        //Health
        JProgressBar cat_health = new JProgressBar(0, 100){
        	@Override
        	protected void paintComponent(Graphics g) {
        	  Graphics2D g2d = (Graphics2D) g;
        	  g2d.scale(-1, 1); //Flips over y-axis
        	  g2d.translate(-getWidth(), 0);
        	  super.paintComponent(g2d);
        	}
        };
        cat_health.setSize(275, 11);
        cat_health.setLocation(42, 50);
        cat_health.setStringPainted(false);
        cat_health.setBackground(Color.RED);
        cat_health.setForeground(Color.WHITE);
        cat_health.setValue(100);
        add(cat_health);
        
        JProgressBar dog_health = new JProgressBar(0, 100);
        dog_health.setSize(305, 11);
        dog_health.setLocation(441, 50);
        dog_health.setStringPainted(false);
        dog_health.setForeground(Color.RED);
        dog_health.setValue(100);
        add(dog_health);
        //end Health
        
        //wind
        JProgressBar left_wind = new JProgressBar(0, 20){
        	@Override
        	protected void paintComponent(Graphics g) {
        	  Graphics2D g2d = (Graphics2D) g;
        	  g2d.scale(-1, 1); //Flips over y-axis
        	  g2d.translate(-getWidth(), 0);
        	  super.paintComponent(g2d);
        	}
        };
        left_wind.setSize(45, 9);
        left_wind.setLocation(334, 126);
        left_wind.setBackground(Color.BLUE);
        left_wind.setForeground(Color.YELLOW);
        if (wind_speed<0)
        	left_wind.setValue(-wind_speed);
        else
        	left_wind.setValue(0);
        add(left_wind);
        JProgressBar right_wind = new JProgressBar(0, 20);
        right_wind.setSize(45, 9);
        right_wind.setLocation(379, 126);
        right_wind.setBackground(Color.YELLOW);
        right_wind.setForeground(Color.BLUE);
        right_wind.setValue(wind_speed);      
        add(right_wind);
        
        Icon left_arrow = new ImageIcon("wind_left.png");
        JLabel left_wind_arrow = new JLabel(left_arrow);
        left_wind_arrow.setSize(25, 12);
        left_wind_arrow.setLocation(339, 110);
        add(left_wind_arrow);
        if(wind_speed < 0)
        	left_wind_arrow.setVisible(true);
        else if(wind_speed >= 0)
        	left_wind_arrow.setVisible(false);
        Icon right_arrow = new ImageIcon("wind_right.png");
        JLabel right_wind_arrow = new JLabel(right_arrow);
        right_wind_arrow.setSize(25, 12);
        right_wind_arrow.setLocation(396, 110);
        add(right_wind_arrow);
        if(wind_speed > 0)
        	right_wind_arrow.setVisible(true);
        else if(wind_speed <= 0)
        	right_wind_arrow.setVisible(false);
        //end wind
        
        //Button
		JPanel panel = new JPanel();	//left
		JPanel panel2 = new JPanel();	//right
		panel.setSize(300, 60);
		panel.setLocation(7, 75);
		panel.setOpaque(false);
		panel2.setSize(300, 60);
		panel2.setLocation(450, 75);
		panel2.setOpaque(false);
		JButton[] left_btn = new JButton[5];
		JButton[] right_btn = new JButton[5];
		ImageIcon[] btn_image = new ImageIcon[5];
		for (int i = 0; i<5; i++)
			btn_image[i] = new ImageIcon(i+".png");
		for (int i = 0; i<4; i++) {
			left_btn[i] = new JButton(btn_image[i+1]);
			right_btn[i] = new JButton(btn_image[i+1]);
			left_btn[i].setBorder(null);
			left_btn[i].setBorderPainted(false);
			left_btn[i].setContentAreaFilled(false);
			left_btn[i].setOpaque(false);
			right_btn[i].setBorder(null);
			right_btn[i].setBorderPainted(false);
			right_btn[i].setContentAreaFilled(false);
			right_btn[i].setOpaque(false);
			panel.add(left_btn[i]);
			panel2.add(right_btn[i]);
		}
		add(panel);
		add(panel2);
		left_btn[0].addActionListener(new ActionListener(){ 			//bomb
			public void actionPerformed(ActionEvent e) {
				if (!turn) {
				left_throw = 1;	left_btn[0].setVisible(false);
				}}	
			});
		left_btn[1].addActionListener(new ActionListener(){ 			//wind
			public void actionPerformed(ActionEvent e) {
				if (!turn) {
				wind_speed = 0;
				left_wind.setValue(0);	right_wind.setValue(0);	
				left_btn[1].setVisible(false);
				}}
			});
		left_btn[2].addActionListener(new ActionListener(){ 			//health
			public void actionPerformed(ActionEvent e) {   
				if (!turn) {
				man_blood+=20;
				turn = true;
				cat_health.setValue(man_blood);
				left_btn[2].setVisible(false);
				}}
			});
		left_btn[3].addActionListener(new ActionListener(){ 			//double
			public void actionPerformed(ActionEvent e) {
				if (!turn) {
				left_throw = 4;  
				left_btn[3].setVisible(false);
				}}	
			});
		
		right_btn[0].addActionListener(new ActionListener(){ 
			public void actionPerformed(ActionEvent e) {
				if (turn) {
				right_throw = 1; right_btn[0].setVisible(false);
				}}
			});
		right_btn[1].addActionListener(new ActionListener(){ 
			public void actionPerformed(ActionEvent e) {
				if (turn) {
				wind_speed = 0;  
				left_wind.setValue(0);	right_wind.setValue(0);	
				right_btn[1].setVisible(false);
				}}
			});
		right_btn[2].addActionListener(new ActionListener(){ 
			public void actionPerformed(ActionEvent e) {
				if (turn) {
				wolf_blood+=20;
				turn = false;
				dog_health.setValue(wolf_blood);
				right_btn[2].setVisible(false);
				}}
			});
		right_btn[3].addActionListener(new ActionListener(){ 
			public void actionPerformed(ActionEvent e) {
				if (turn) {
				right_throw = 4;
				right_btn[3].setVisible(false);
				}}	
			});
		//end Button
		
		//End Button
		JPanel panel_end = new JPanel();
		ImageIcon exit_picture = new ImageIcon("marvel.jpg");
		JButton exit_btn = new JButton(exit_picture);
		panel_end.setSize(100,50);
		panel_end.setLocation(330,150);
		panel_end.setOpaque(false);
		exit_btn.setBorder(null);
		exit_btn.setBorderPainted(false);
		exit_btn.setContentAreaFilled(false);
		exit_btn.setOpaque(false);
		panel_end.add(exit_btn);
		add(panel_end);
		
		End_listener exit_listener = new End_listener();
		exit_btn.addActionListener(exit_listener);
		//end End Button
		
        //Left Man - USA
        ImageIcon USA_picture = new ImageIcon("USA_normal.gif");
        JButton USA_btn = new JButton(USA_picture);
        USA_btn.setSize(150, 150);
        USA_btn.setLocation(10, 390);
		USA_btn.setBorder(null);
		USA_btn.setBorderPainted(false);
		USA_btn.setContentAreaFilled(false);
        USA_btn.setOpaque(false);
        add(USA_btn);
        
        JPanel USA_hitted_panel = new JPanel();
        ImageIcon USA_hitted_picture = new ImageIcon("USA_laugh.gif");	
        JLabel USA_hitted_label = new JLabel(USA_hitted_picture);
        USA_hitted_panel.add(USA_hitted_label);
        USA_hitted_panel.setSize(150, 150);
        USA_hitted_panel.setLocation(10, 390);
        USA_hitted_panel.setLayout(new GridLayout());
        USA_hitted_panel.setOpaque(false);
        add(USA_hitted_panel);
        USA_hitted_panel.setVisible(false);
        //end USA
        
        //Right Man Wolf
        ImageIcon wolf_picture = new ImageIcon("wolf_normal.gif");
        JButton wolf_btn = new JButton(wolf_picture);
        wolf_btn.setSize(150, 150);
        wolf_btn.setLocation(600, 390);
        wolf_btn.setOpaque(false);
        wolf_btn.setBorder(null);
        wolf_btn.setBorderPainted(false);
        wolf_btn.setContentAreaFilled(false);
        add(wolf_btn);
        
        JPanel wolf_hitted_panel = new JPanel();
        ImageIcon wolf_hitted_picture = new ImageIcon("wolf_laugh.gif");
        JLabel wolf_hitted_label = new JLabel(wolf_hitted_picture);
        wolf_hitted_panel.add(wolf_hitted_label);
        wolf_hitted_panel.setSize(150, 150);
        wolf_hitted_panel.setLocation(600, 390);
        wolf_hitted_panel.setLayout(new GridLayout());
        wolf_hitted_panel.setOpaque(false);
        add(wolf_hitted_panel);
        wolf_hitted_panel.setVisible(false);
        //end Wolf
		
        //Throw
        JPanel fishpanel1 = new JPanel();
		Timer throwtimer = new Timer();
		TimerTask throwing = new TimerTask() {
			public void run() {
				if (isClick && fishpanel1.getY()<450 && (!wolf_hit && !USA_hit)) {
					if (fishpanel1.getY()>370 && fishpanel1.getY()<540 && fishpanel1.getX()>580 && fishpanel1.getX()<700 && turn) {
						wolf_hit = true;
						if (fishpanel1.getX()>625 && fishpanel1.getX()<645) {
							wolf_blood -=40; if(left_throw==1) wolf_blood -=40;}
						else if ((fishpanel1.getX()>600 && fishpanel1.getX()<=625) || (fishpanel1.getX()>=655 && fishpanel1.getX()<680)) {
							wolf_blood -=20; if(left_throw==1) wolf_blood -=20;}
						else { wolf_blood -=10; if(left_throw==1) wolf_blood -=10;}
					}
					else if (fishpanel1.getY()>370 && fishpanel1.getY()<540 && fishpanel1.getX()>-10 && fishpanel1.getX()<110 && !turn) {
						USA_hit = true;
						if (fishpanel1.getX()>45 && fishpanel1.getX()<65) {
							man_blood -=40; if(right_throw==1) man_blood -=40;}
						else if ((fishpanel1.getX()>20 && fishpanel1.getX()<=45) || (fishpanel1.getX()>=65 && fishpanel1.getX()<90)) {
							man_blood -=20; if(right_throw==1) man_blood -=20;}
						else { man_blood -=10; if (right_throw==1) man_blood -=10;}
					}
					if (wolf_hit) {
						if (wolf_blood <= 0) {
							Ending endframe = new Ending();
							endframe.set_cap_Win(true);
							endframe.showview();
							setVisible(false);
						}
						fishpanel1.setVisible(false);
						dog_health.setValue(wolf_blood);
						dog_health.setVisible(true);
						wolf_btn.setVisible(false);
						wolf_hitted_panel.setVisible(true);
						try {	Thread.sleep(2000);	} catch(Exception e) { }
					}
					else if (USA_hit) {
						if (man_blood <= 0) {
							Ending endframe = new Ending();
							endframe.set_wol_Win(true);
							endframe.showview();
							setVisible(false);
						}
						fishpanel1.setVisible(false);
						cat_health.setValue(man_blood);
						cat_health.setVisible(true);
						USA_btn.setVisible(false);
						USA_hitted_panel.setVisible(true);
						try {	Thread.sleep(2000); } catch(Exception e) { }
					}
					wolf_btn.setVisible(true);
					wolf_hitted_panel.setVisible(false);
					USA_btn.setVisible(true);
					USA_hitted_panel.setVisible(false);
					move(fishpanel1);
				}
				else if (fishpanel1.getY()>=450 && !wind_change) {
					wind_speed = gen.nextInt(41)-20;
					if (wind_speed<0) {
						left_wind.setValue(-wind_speed);
						right_wind.setValue(0);
					}
					else {
						right_wind.setValue(wind_speed);
						left_wind.setValue(0);
					}
					wind_change = true;
				}
			}
		};
		throwtimer.schedule(throwing, 0, 10);
		//end Throw
		
		//Strength
		USA_btn.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (!turn) {
				power = 0;
				strength.setVisible(true);
		        if (timer == null)
		        	timer = new Timer();
		        if (task == null)
		        	task = new TimerTask() {
		    			public void run() {
		    				if (power == 100) power = 0;
		    				power += 5;
		    				strength.setSize(100, 11);
		    		    	strength.setLocation(150, 400);
		    		    	strength.setStringPainted(false);
		    		    	strength.setBackground(Color.RED);
		    		    	strength.setForeground(Color.WHITE);
		    		    	strength.setValue(power);
		    		  	    add(strength);
		    		  	    setLayout(null);
		    		  	    setVisible(true);
		    			}
		    		};
		    	if (timer != null && task != null)
		    		timer.schedule(task, 0, 100);
		        setLayout(null);
		        setVisible(true);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (!turn) {
				Vx = power/2;
				Vy = -power;
				fishlabel1 = new JLabel(btn_image[left_throw]);
				fishpanel1.removeAll();
				fishpanel1.add(fishlabel1);
				fishpanel1.setSize(90, 60);
				fishpanel1.setLocation(150, 325);
				fishpanel1.setOpaque(false);
				fishpanel1.setVisible(true);
				add(fishpanel1);
				turn = true;	if(left_throw == 4) turn = false;
				left_throw = 0;
				isClick = true;
				wind_change = false;
				wolf_hit = USA_hit = false;
				System.out.println(wind_speed);
				if (timer != null) {
					timer.cancel();
					timer = null;
				}
				if (task != null) {
					task.cancel();
					task = null;
				}
				strength.setVisible(false);
				}
			}
		});
		wolf_btn.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (turn) {
				power = 0;
				strength.setVisible(true);
		        if (timer == null)
		        	timer = new Timer();
		        if (task == null)
		        	task = new TimerTask() {
		    			public void run() {
		    				if (power == 100) power = 0;
		    				power += 5;
		    				strength.setSize(100, 11);
		    		    	strength.setLocation(600, 400);
		    		    	strength.setStringPainted(false);
		    		    	strength.setBackground(Color.RED);
		    		    	strength.setForeground(Color.WHITE);
		    		    	strength.setValue(power);
		    		  	    add(strength);
		    		  	    setLayout(null);
		    		  	    setVisible(true); 
		    			}
		    		};
		    	if (timer != null && task != null)
		    		timer.schedule(task, 0, 100);
		        setLayout(null);
		        setVisible(true);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (turn) {
				Vx = -power/2;
				Vy = -power;
				ImageIcon wolfpic = new ImageIcon("wolf_weapon.png");
				if (right_throw == 1)
					fishlabel1 = new JLabel(btn_image[1]);
				else
					fishlabel1 = new JLabel(wolfpic);
				fishpanel1.removeAll();
				fishpanel1.add(fishlabel1);
				fishpanel1.setSize(90, 60);
				fishpanel1.setLocation(600, 325);
				fishpanel1.setOpaque(false);
				fishpanel1.setVisible(true);
				add(fishpanel1);
				turn = false;	if(right_throw == 4) turn = true;
				right_throw = 0;
				isClick = true;
				wind_change = false;
				wolf_hit = USA_hit = false;
				System.out.println(wind_speed);
				if (timer != null) {
					timer.cancel();
					timer = null;
				}
				if (task != null) {
					task.cancel();
					task = null;
				}
				strength.setVisible(false);
				}
			}
		});
		//end Strength
		
		setLayout(null);
		setVisible(true);
		
	//Frame
	}
	public static void move(JPanel e) {
		double t = 0.1;
		int x, y;
		x=(int)(e.getX() + (Vx + wind_speed)*t);
		y=(int)(e.getY() + Vy*t + 9.8*t*t/2);
		Vy=Vy+9.8*t;
		e.setLocation(x, y);
	}
}