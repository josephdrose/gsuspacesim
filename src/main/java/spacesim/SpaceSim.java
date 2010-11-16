package spacesim;

import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
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
	double shipimg_half, missileimg_half;
	AffineTransform tf;
	
	public static final int screen_width=500, screen_height=522; //offset height 22px for dialog title
	
	public void drawImage(Graphics2D g, BufferedImage img, double x, double y, double angle, double half){
		AffineTransform t=new AffineTransform();
		//x and y here are backwards because of the tf angle offset
		t.translate(y, x);
		t.rotate(Math.toRadians(180-angle));
		t.translate(-half, -half);
		g.drawImage(img, t, null);
	}
	
	public void paint(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
    	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    	
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
    		g.drawString(statuses[x], 0, getHeight()-(offset*statuses.length)+offset*x);

		AffineTransform ot=g.getTransform();
		g.setTransform(tf);
		
    	//Draw ships
    	drawImage(g, shipimg, n.x, n.y, n.angle, shipimg_half);
    	drawImage(g, shipimg, s.x, s.y, s.angle, shipimg_half);
    
    	g.setTransform(ot);
	}
	
	public SpaceSim() throws Exception {
		//load up the expert systems
		nes = new ExpertSystem("joe.drl");
		ses = new ExpertSystem("darnell.drl");
		
		//opponents North and South		
		n = new Ship(0, 150, -90);
		s = new Ship(0, -150, 90);

		//timer
		t = new Timer();
		
		//exit listener
		e=new Exit();
		
		//load images
        shipimg=ImageIO.read(new File("ship.png"));
        missileimg=ImageIO.read(new File("ship.png"));
        
        //set half sizes
        shipimg_half=shipimg.getWidth()/2.0;
        missileimg_half=missileimg.getWidth()/2.0;
        
        //set transform to convert from render to real world coordinate system
        //java renders 0 degrees as "south" instead of "east"
        tf = new AffineTransform();
        tf.translate(screen_width/2, screen_height/2);
        tf.rotate(Math.toRadians(-90));
	}

	public void start() {
		//start animation and movement timers
	    t.schedule(new Tick(), 0, 10);
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

		//set up gui
		JFrame frame = new JFrame("Space Combat Simulator");
	    s.setBackground(new Color(0, 0, 0));
	    frame.setSize(screen_width, screen_height);
	    frame.setResizable(false);
	    frame.setContentPane(s);
	    frame.addWindowListener(s.e);
	    frame.setVisible(true);
	    
	    //start simulator
	    s.start();
	}
}