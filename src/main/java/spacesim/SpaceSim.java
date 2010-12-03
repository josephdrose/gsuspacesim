package spacesim;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;

public class SpaceSim extends JPanel {
	private static final long serialVersionUID = 1L;

	//TODO: These variables could really be named better
	Ship n, s;
	ExpertSystem nes, nmes, ses, smes;
	Missile nmissile, smissile;
	Timer t;
	public Exit e;
	BufferedImage shipimg, missileimg;
	double shipimg_half, missileimg_half;
	AffineTransform tf;
	ArrayList<Boom> booms;
	
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
    	ArrayList<String> statuses=new ArrayList();
    	if(n!=null) {
    		String [] nstatus={
	            "North Ship X: "+n.x, 
	            "North Ship Y: "+n.y,
	            "North Ship DX: "+n.dx,
	            "North Ship DY: "+n.dy,
	            "North Ship ED:"+n.ed
			};
    		statuses.addAll(Arrays.asList(nstatus));
    	}
    	if(s!=null) {
    		String [] sstatus={
				"South Ship X: "+s.x,
	            "South Ship Y: "+s.y,
	            "South Ship DX: "+s.dx,
	            "South Ship DY: "+s.dy,
	            "South Ship ED:"+s.ed
    		};
    		statuses.addAll(Arrays.asList(sstatus));
    	}    	
    	int offset=15;
    	for(int x=0; x<statuses.size(); x++) {
    		g.drawString(statuses.get(x), 0, getHeight()-(offset*statuses.size())+offset*x);
    	}
    		
		AffineTransform ot=g.getTransform();
		g.setTransform(tf);
		
    	//Draw ships
    	if(n!=null) {
    		drawImage(g, shipimg, n.x, n.y, n.angle, shipimg_half);
    	}
    	if(s!=null) {
    		drawImage(g, shipimg, s.x, s.y, s.angle, shipimg_half);
    	}
    	
    	//Draw missiles
    	if(nmissile!=null) {
    		drawImage(g, missileimg, nmissile.x, nmissile.y, nmissile.angle, missileimg_half);
    	}
		if(smissile!=null) {
			drawImage(g, missileimg, smissile.x, smissile.y, smissile.angle, missileimg_half);
		}

		//draw booms
		g.setColor(Color.red);
		for(int x=0; x<booms.size(); x++) {
			Boom b=booms.get(x);
			g.fillOval((int)b.x, (int)b.y, (int)b.r, (int)b.r);
			b.r-=.1;
			if(b.r<=0) {
				booms.remove(x);
			}
		}

    	g.setTransform(ot);
	}
	
	public SpaceSim() throws Exception {
		//load up the expert systems
		nes = new ExpertSystem("joe.drl");
		nmes = new ExpertSystem("joe_missile.drl");
		ses = new ExpertSystem("darnell.drl");
		smes = new ExpertSystem("darnell_missile.drl");
		
		//opponents North and South		
		n = new Ship(0, 150, -90);
		s = new Ship(0, -150, 90);

		//why can't we all just get along?
		n.enemyShip=s;
		s.enemyShip=n;
		booms=new ArrayList<Boom>();
		
		//timer
		t = new Timer();
		
		//exit listener
		e=new Exit();
		
		//load images
        shipimg=ImageIO.read(new File("ship.png"));
        missileimg=ImageIO.read(new File("missile.png"));
        
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
			//move ships
			if(n!=null) {
				n.move();
			}
			if(s!=null) {
			 s.move();
			}
			
			//update ship status from expert system
			if(n!=null) {
				nes.go(n);
			}
			if(s!=null) {
				ses.go(s);
			}
			
			//move missiles, update with expert systems
			if(nmissile!=null){
				nmissile.move();
				nmes.go(nmissile);
				if(nmissile.boom) {
					booms.add(new Boom(nmissile.x, nmissile.y, nmissile.explosionRadius));
					nmissile=null;
				}
			}
			if(smissile!=null){
				smissile.move();
				smes.go(smissile);
				if(smissile.boom) {
					booms.add(new Boom(smissile.x, smissile.y, smissile.explosionRadius));
					smissile=null;
				}
			}
			
	    	//Did we request a fired missile?
			if(n!=null&&n.fireMissile&&n.missiles>0){
				n.missiles--;
				nmissile=new Missile(n.x, n.y, n.angle, n.dx, n.dy);
				nmissile.enemyShip=s;
				if(smissile!=null) {
					nmissile.enemyMissile=smissile;
				}
			}
			if(s!=null&&s.fireMissile&&s.missiles>0){
				s.missiles--;
				smissile=new Missile(s.x, s.y, s.angle, s.dx, s.dy);
				smissile.enemyShip=n;
				if(nmissile!=null) {
					smissile.enemyMissile=nmissile;
				}
			}
			
			//Detect booms colliding with ships, missiles
			for(int x=0; x<booms.size(); x++) {
				Boom b=booms.get(x);
				if(n!=null&&Util.distance(b.x, b.y, n.y, n.y)<b.r) {
					n=null;
				}
				if(s!=null&&Util.distance(b.x, b.y, s.y, s.y)<b.r) {
					s=null;
				}
				if(nmissile!=null&&Util.distance(b.x, b.y, nmissile.y, nmissile.y)<b.r) {
					nmissile=null;
				}
				if(smissile!=null&&Util.distance(b.x, b.y, smissile.y, smissile.y)<b.r) {
					smissile=null;
				}
			}
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