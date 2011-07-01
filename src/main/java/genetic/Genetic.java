package genetic;

import java.util.Collections;
import java.util.Vector;

public class Genetic {
	private static int NUM_CHROMOSOMES = 100;
	private static int FITNESS_THRESHOLD = 100;
	private static int MAX_RUNS=5;
	public static final int RUNS_PER_SCORE=10;
	private static float MUTATE = (float) .05;
	private Vector<Chromosome> population = new Vector<Chromosome>();;
	
	public Genetic(){
		for(int i = 0; i < NUM_CHROMOSOMES; ++i){
			population.add(new Chromosome(
				(int)(1000.0*Math.random()),  //0-1000 
				(int)(359.0*Math.random()),   //0-360 
				(int)(1000.0*Math.random()),  //0-1000 
				(int)(500.0*Math.random())    //0-500 
				)
			);
		}
	}
	
	public void start() throws Exception{
		boolean done=false;
		int runs=1;
		while(!done) {
			Collections.sort(population);
			Chromosome best = (Chromosome) population.lastElement();
			
			done = best.fitness() >= FITNESS_THRESHOLD;
			
			if(done||runs>MAX_RUNS){
				System.out.println("Solution: " + best);	
				break;
			}
			else
				generateNewPopulation();
			
			runs++;
		}
	}
	
	private void generateNewPopulation(){
		Vector<Chromosome> temp = new Vector<Chromosome>();
		
		for(int i = 0; i < population.size()/2; ++i){
			Chromosome p1 = selectParent();
			Chromosome p2 = selectParent();
			temp.add(cross(p1, p2));
			temp.add(cross(p2, p1));
		}
		
		population.clear();
		population.addAll(temp);
	}
	
	private Chromosome selectParent(){
		int index=(int)Math.ceil(Math.pow(Math.random()*Math.pow((double)NUM_CHROMOSOMES, 3), .333333333333))-1;
		return (Chromosome)population.get(index);
	}
	
	private Chromosome cross(Chromosome parent1, Chromosome parent2){
		Chromosome offspring = new Chromosome(parent1.fire_distance, parent1.enemy_offset, parent2.return_fire_distance, 
			parent2.missile_avoid_threshold);
		
		if(shouldMutate())
			mutate(offspring);
		
		return offspring;
	}
		
	private boolean shouldMutate(){
		return (Math.random()*100.0 <= MUTATE);
	}
	
	private void mutate(Chromosome offspring){
		offspring.fire_distance=(int)(1000.0*Math.random());  			//0-1000 
		offspring.enemy_offset=(int)(359.0*Math.random());   			//0-360 
		offspring.return_fire_distance=(int)(1000.0*Math.random());  	//0-1000 
		offspring.missile_avoid_threshold=(int)(500.0*Math.random());   //0-500 
	}
	
	public static void main(String[] args) throws Exception {
		Genetic s = new Genetic();
		s.start();
	}
}