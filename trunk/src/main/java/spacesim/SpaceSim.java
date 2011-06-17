package spacesim;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;

public class SpaceSim extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final boolean simulate=true;
	
	public static final int NORTH_WIN=0;
	public static final int SOUTH_WIN=1;
	public static final int DRAW=2;
	public static final int UNDECIDED=3;
	
	//tunables
	public static int FIRE_DISTANCE=310;
	public static int ENEMY_OFFSET=20;
	public static int RETURN_FIRE_DISTANCE=650;
	public static int MISSILE_AVOID_THRESHOLD=100;
	
	Ship n, s;
	ExpertSystem nes, nmes, ses, smes;
	Missile nmissile, smissile;
	Timer t;
	public Exit e;
	Image nshipimg, sshipimg, missileimg, starfield;
	double shipimg_half, missileimg_half;
	AffineTransform tf;
	ArrayList<Boom> booms;
	int ticks=0;
	
	public static final int screen_width=600, screen_height=600; //offset height 22px for dialog title
	
	public void drawImage(Graphics2D g, Image img, double x, double y, double angle, double half){
		AffineTransform t=new AffineTransform();
		//x and y here are backwards because of the tf angle offset
		t.translate(y, x);
		t.rotate(Math.toRadians(180-angle));
		t.translate(-half, -half);
		g.drawImage(img, t, this);
	}
	
	public boolean imageUpdate(Image img, int flags, int x, int y, int w, int h) {
		repaint();
		return true;
	}
	
	public void paint(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
    	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    	
    	//clear background
    	g.drawImage(starfield, 0, 0, screen_width, screen_width, this);
    	
    	//draw status text
    	g.setColor(Color.white);
    	ArrayList<String> statuses=new ArrayList<String>();
    	if(n.alive) {
    		String [] nstatus={
	            "North Ship X: "+n.x, 
	            "North Ship Y: "+n.y,
	            "North Ship DX: "+n.dx,
	            "North Ship DY: "+n.dy,
	            "North Ship ED:"+n.ed
			};
    		statuses.addAll(Arrays.asList(nstatus));
    	}
    	if(s.alive) {
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
    	if(n.alive) {
    		drawImage(g, nshipimg, n.x, n.y, n.angle, shipimg_half);
    	}
    	if(s.alive) {
    		drawImage(g, sshipimg, s.x, s.y, s.angle, shipimg_half);
    	}
    	
    	//Draw missiles
    	if(nmissile.alive) {
    		drawImage(g, missileimg, nmissile.x, nmissile.y, nmissile.angle, missileimg_half);
    	}
		if(smissile.alive) {
			drawImage(g, missileimg, smissile.x, smissile.y, smissile.angle, missileimg_half);
		}

		//draw booms
		g.setColor(Color.red);
		for(int x=0; x<booms.size(); x++) {
			Boom b=booms.get(x);
			//x and y here are backwards because of the tf angle offset
			g.fillOval((int)b.y, (int)b.x, (int)b.r, (int)b.r);
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
		Random g=new Random();
		int arena=screen_width-100;
		n = new Ship(g.nextInt(arena)-arena/2, g.nextInt(arena)-arena/2, g.nextInt(359));
		s = new Ship(g.nextInt(arena)-arena/2, g.nextInt(arena)-arena/2, g.nextInt(359));
		nmissile=new Missile();
		smissile=new Missile();
		
		//why can't we all just get along?
		n.enemyShip=s;
		n.enemyMissile=smissile;
		nmissile.enemyShip=s;
		nmissile.enemyMissile=smissile;
		s.enemyShip=n;
		s.enemyMissile=nmissile;
		smissile.enemyShip=n;
		smissile.enemyMissile=nmissile;
		booms=new ArrayList<Boom>();
		
		//timer
		t = new Timer();
		
		//exit listener
		e=new Exit();
		
		//load images
        nshipimg=new ImageIcon("ship.gif").getImage();
        sshipimg=new ImageIcon("ship_red.gif").getImage();
        missileimg=new ImageIcon("missile.gif").getImage();
        starfield=new ImageIcon("starfield.jpg").getImage();
        
        //set half sizes
        shipimg_half=nshipimg.getWidth(this)/2.0;
        missileimg_half=missileimg.getWidth(this)/2.0;
        
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
	
	public int simulate(int fire_distance) {
		//TODO: Set fire distance, have rules pull it;
		
		int r = UNDECIDED;
		while(r==UNDECIDED)
			r=tick();
		
		return r;
	}
	
	public void stop() {
		nes.end();
		ses.end();		
	}
	
	public int tick () {
		//move ships
		n.move();
		s.move();
		
		//update ship status from expert system
		if(n.alive) {
			nes.go(n);
		}
		if(s.alive) {
			ses.go(s);
		}
		
		//move missiles, update with expert systems
		if(nmissile.alive) {
			nmissile.move();
			nmes.go(nmissile);
			if(nmissile.boom) {
				booms.add(new Boom(nmissile.x, nmissile.y, nmissile.explosionRadius));
				nmissile.alive=false;
			}
		}
		if(smissile.alive){
			smissile.move();
			smes.go(smissile);
			if(smissile.boom) {
				booms.add(new Boom(smissile.x, smissile.y, smissile.explosionRadius));
				smissile.alive=false;
			}
		}
		
    	//Did we request a fired missile?
		if(n.fireMissile&&n.missiles>0){
			n.missiles--;
			nmissile.fire(n.x, n.y, n.angle, n.dx, n.dy);
		}
		if(s.fireMissile&&s.missiles>0){
			s.missiles--;
			smissile.fire(s.x, s.y, s.angle, s.dx, s.dy);
		}
		
		//Detect booms colliding with ships, missiles
		for(int x=0; x<booms.size(); x++) {
			Boom b=booms.get(x);
			if(Util.distance(b.x, b.y, n.x, n.y)<b.r) {
				n.alive=false;
			}
			if(Util.distance(b.x, b.y, s.x, s.y)<b.r) {
				s.alive=false;
			}
			if(Util.distance(b.x, b.y, nmissile.x, nmissile.y)<b.r) {
				nmissile.alive=false;
			}
			if(Util.distance(b.x, b.y, smissile.x, smissile.y)<b.r) {
				smissile.alive=false;
			}
		}
		
		//check for exit conditions, draw
		if((ticks>2000)||SpaceSim.simulate&&((n.alive==false&&s.alive==false)||(n.alive==true&&s.alive==true&&
				nmissile.alive==false&&smissile.alive==false&&n.missiles==0&&s.missiles==0))){
			return DRAW;
		}
		//north victory
		if(n.alive==true&&smissile.alive==false&&s.alive==false&&s.missiles==0){
			
			return NORTH_WIN;
		}
		//south victory
		if(s.alive==true&&nmissile.alive==false&&n.alive==false&&n.missiles==0){
			System.out.println("South Wins");
			return SOUTH_WIN;
		}
		
    	ticks++;
    	
    	return UNDECIDED;
	}
	
	//called every second
	public class Tick extends TimerTask {
		public void run() {
			if(ticks%100==0)
				System.out.println("Tick "+ticks);
			
			int r = tick();
			
			if(r==DRAW){
				System.out.println("Draw");
				System.exit(0);
			}
			else if(r==NORTH_WIN){
				System.out.println("North Wins");
				System.exit(0);
			}
			else if(r==SOUTH_WIN){
				System.out.println("South Wins");
				System.exit(0);
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
	    
	    //start simulator in real-time mode
	    s.start();
	}
}