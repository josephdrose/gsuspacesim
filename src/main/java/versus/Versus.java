package versus;

import spacesim.SpaceSim;

public class Versus {

	public static void main(String[] args) throws Exception {
		int north_wins=0;
		int south_wins=0;
		int draws=0;
		
		long start_ms=System.currentTimeMillis();
		for(int x=0; x<1000; x++){
	    	SpaceSim s = new SpaceSim();
	    	
	    	//north using drools planner values
	    	//added hard-coded drl file for south
	    	int r = s.simulate(610, 35, 825, 220);
	    	
	    	if(r==SpaceSim.DRAW){
				draws++;
			}
			else if(r==SpaceSim.NORTH_WIN){
				north_wins++;
			}
			else if(r==SpaceSim.SOUTH_WIN){
				south_wins++;
			}
	    	
	    	System.out.println(north_wins+" / "+south_wins+" / "+draws);
		}
		long duration=(System.currentTimeMillis()-start_ms)/1000;
		
		System.out.println("Time for 1k runs: "+duration);
		System.out.println("Final score:");
    	System.out.println(north_wins+" / "+south_wins+" / "+draws);
	}
}
