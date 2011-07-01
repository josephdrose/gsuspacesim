package genetic;

import java.lang.Comparable;

import spacesim.SpaceSim;

public class Chromosome implements Comparable{
	public int fire_distance, enemy_offset, return_fire_distance, missile_avoid_threshold;
	private int fitness=99999;
	
	public Chromosome(int fire_distance, int enemy_offset, int return_fire_distance, int missile_avoid_threshold){
		this.fire_distance=fire_distance;
		this.enemy_offset=enemy_offset;
		this.return_fire_distance=return_fire_distance;
		this.missile_avoid_threshold=missile_avoid_threshold;
 	}
	
	public int compareTo(Object compare) {
		Chromosome o = (Chromosome) compare;
		try {
			return this.fitness()-o.fitness();
		} 
		catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public int fitness() throws Exception{
		System.out.println("Testing " + this);
		
		if(fitness==99999){
			fitness=0;
			for(int x=0; x<Genetic.RUNS_PER_SCORE; x++){
		    	SpaceSim s = new SpaceSim();
		    	int r = s.simulate(fire_distance, enemy_offset, return_fire_distance, missile_avoid_threshold);
		    	
		    	if(r==SpaceSim.DRAW){
					fitness-=5;
				}
				else if(r==SpaceSim.NORTH_WIN){
					fitness+=5;
				}
				else if(r==SpaceSim.SOUTH_WIN){
					fitness-=20;
				}
	    	}
		}
		return fitness;
	}
	
	public boolean equals(Object compare){
		Chromosome o =(Chromosome)compare;
		return fire_distance==o.fire_distance&&enemy_offset==o.enemy_offset&&return_fire_distance==o.return_fire_distance&&
			missile_avoid_threshold==o.missile_avoid_threshold;
	}
	
	public String toString(){
		return "Chromosome ("+fitness+"): " + fire_distance + " - " + enemy_offset + " - " + return_fire_distance + " - "+ missile_avoid_threshold;
	}	
}