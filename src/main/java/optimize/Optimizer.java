package optimize;

import org.drools.planner.config.XmlSolverConfigurer;
import org.drools.planner.core.Solver;
import org.drools.planner.core.localsearch.DefaultLocalSearchSolver;

public class Optimizer {
	public static final int RUNS_PER_SCORE=10;
	public static final int STEP=25;
	
	public static int VARY_FIRE_DISTANCE=0;           //25
	public static int VARY_ENEMY_OFFSET=1;            //1
	public static int VARY_RETURN_FIRE_DISTANCE=2;    //25
	public static int VARY_MISSILE_AVOID_THRESHOLD=3; //10
	
	public static int MODE=VARY_FIRE_DISTANCE;
	
	public static void main(String [] args) {
		XmlSolverConfigurer configurer = new XmlSolverConfigurer(); 
		configurer.configure("/optimize/config.xml"); 
		Solver solver = configurer.buildSolver();
		((DefaultLocalSearchSolver) solver).setScoreCalculator(new SimulationScoreCalculator());
		solver.setStartingSolution(new VariableSolution(new Variable(310)));
		solver.solve(); 
		VariableSolution bestSolution = (VariableSolution) solver.getBestSolution();
		
		System.out.println("Done; how does "+bestSolution+" sound?");
	}
}