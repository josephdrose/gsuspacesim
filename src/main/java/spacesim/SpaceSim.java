package spacesim;

import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;

import spacesim.Ship;
import spacesim.ExpertSystem;

public class SpaceSim extends JPanel {
	private static final long serialVersionUID = 1L;
	Ship n, s;
	ExpertSystem nes, ses;
	Timer t;
	public Exit e;
	BufferedImage shipimg, missileimg;
	
	public static final int screen_width=640, screen_height=640;
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
    	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
    	
    	//clear background
    	g.setColor(Color.gray);
    	g.fillRect(0, 0, getWidth(), getHeight());
    	
    	//draw status text
    	g.setColor(Color.black);
    	String [] statuses={
            "North Ship X: "+n.x, 
            "North Ship Y: "+n.y,
            "North Ship DX: "+n.dx,
            "North Ship DY: "+n.dy,
            "South Ship X: "+s.x,
            "South Ship Y: "+s.y,
            "South Ship DX: "+s.dx,
            "South Ship DY: "+s.dy
    	};
    	
    	int offset=15;
    	for(int x=0; x<statuses.length; x++)
    		g2.drawString(statuses[x], 0, getHeight()-(offset*statuses.length)+offset*x);

    	//Draw ships
        g.drawImage(shipimg, n.scaleX(screen_width), n.scaleY(screen_height), null);
        g.drawImage(shipimg, s.scaleX(screen_width), s.scaleY(screen_height), null);
	}
	
	public SpaceSim() throws Exception {
		//load up the expert systems
		nes = new ExpertSystem("joe.drl");
		ses = new ExpertSystem("darnell.drl");
		
		//opponents North and South		
		n = new Ship(50000, 0, 270);
		s = new Ship(50000, 90000, 90);

		//timer
		t = new Timer();
		
		//exit listener
		e=new Exit();
		
		//load images
        shipimg=ImageIO.read(new File("ship.png"));
        missileimg=ImageIO.read(new File("ship.png"));
	}

	public void start() {
		//start animation and movement timers
	    t.schedule(new Tick(), 0, 1000);
	    t.schedule(new Paint(), 0, 33);
	}
	
	public void stop() {
		nes.end();
		ses.end();		
	}
	
	//called every second
	public class Tick extends TimerTask {
		public void run() {
			//update ship status from expert system
			nes.go(n);
			ses.go(s);
			
			//move ship appropriately
			n.move();
			s.move();
		}
	}
	
	public class Paint extends TimerTask {
		public void run() {
			repaint();
		}
	}
	
	public class Exit extends WindowAdapter {
		public void windowClosing(WindowEvent event) {
			System.exit(0);
		}
	}
	
	//program entry point
	public static void main(String [] args) throws Exception{				
		//create simulator
		SpaceSim s = new SpaceSim();

		//set up gui parameters
		JFrame frame = new JFrame("Space Combat Simulator");
	    s.setBackground(new Color(0, 0, 0));
	    frame.setSize(screen_width, screen_height);
	    frame.setContentPane(s);
	    frame.addWindowListener(s.e);
	    frame.setVisible(true);
	    
	    //start simulator
	    s.start();
	}
}