package spacesim;

import spacesim.Ship;
import spacesim.ExpertSystem;

public class SpaceSim {
	public static void main(String [] args) throws Exception{
		//load up the expert systems
		ExpertSystem nes = new ExpertSystem("joe.drl");
		ExpertSystem ses = new ExpertSystem("darnell.drl");
		
		//opponents North and South		
		Ship n = new Ship();
		Ship s = new Ship();
		
		for(int i=0; i<15; i++){
			//update ship status from expert system
			nes.go(n);
			ses.go(s);
			
			n.move();
			s.move();
			
			System.out.println("North speed is now "+n.speed);
			System.out.println("South speed is now "+s.speed);
		}
		
		//end
		nes.end();
		ses.end();
	}
}