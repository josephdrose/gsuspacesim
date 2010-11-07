package spacesim;

import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;

import spacesim.Ship;
import spacesim.ExpertSystem;

public class SpaceSim extends JPanel {
	private static final long serialVersionUID = 1L;
	Ship n, s;
	ExpertSystem nes, ses;
	Timer t;
	public Exit e;

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
    	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);

    	g2.setPaint(Color.gray);
    	int x = 5;
    	int y = 7;

    	//g2.setStroke(stroke);
    	g2.draw(new Ellipse2D.Double(x, y, 200, 200));
    	g2.drawString("Ellipse2D", x, 250);
	}
	
	public SpaceSim() throws Exception {
		//load up the expert systems
		nes = new ExpertSystem("joe.drl");
		ses = new ExpertSystem("darnell.drl");
		
		//opponents North and South		
		n = new Ship();
		s = new Ship();

		//timer
		t = new Timer();
		
		//exit listener
		e=new Exit();
	}

	public void start() {
		//start the timer immediately, fire every second
	    t.schedule(new Tick(), 0, 1000);
	    
	    //TODO: infinite while loop for drawing
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
			
			System.out.println("North speed is now "+n.dx);
			System.out.println("South speed is now "+s.dx);
		}
	}
	
	public class Exit extends WindowAdapter {
		public void windowClosing(WindowEvent event) {
			System.exit(0);
		}
	}
	
	//program entry point
	public static void main(String [] args) throws Exception{				
		SpaceSim s = new SpaceSim();
		JFrame frame = new JFrame("Space Combat Simulator");
	    s.setBackground(new Color(0, 0, 0));
	    frame.setSize(300, 400);
	    frame.setContentPane(s);
	    frame.addWindowListener(s.e);
	    frame.setVisible(true);

	    s.start();
	}
}