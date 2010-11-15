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
	
	public static final int screen_width=640, screen_height=640;
	
	public void drawImage(Graphics2D g, BufferedImage img, int x, int y, double angle, double half){
		g.translate(x, y);
    	g.drawImage(img, AffineTransform.getRotateInstance(Math.toRadians(angle-90.0), half, half), null);
    	g.translate(-x,-y);
	}
	
	public void paint(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
    	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
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
    		g.drawString(statuses[x], 0, getHeight()-(offset*statuses.length)+offset*x);

    	//Draw ships
    	drawImage(g, shipimg, n.scaleX(screen_width), n.scaleY(screen_height), n.angle, shipimg_half);
    	drawImage(g, shipimg, s.scaleX(screen_width), s.scaleY(screen_height), s.angle, shipimg_half);
    }
	
	public SpaceSim() throws Exception {
		//load up the expert systems
		nes = new ExpertSystem("joe.drl");
		ses = new ExpertSystem("darnell.drl");
		
		//opponents North and South		
		n = new Ship(50000, 100000, 270);
		s = new Ship(50000, 0, 90);

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
	}

	public void start() {
		//start animation and movement timers
	    t.schedule(new Tick(), 0, 100);
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
	    frame.setContentPane(s);
	    frame.addWindowListener(s.e);
	    frame.setVisible(true);
	    
	    //start simulator
	    s.start();
	}
}