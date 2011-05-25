package optimize;

import org.drools.planner.config.XmlSolverConfigurer;
import org.drools.planner.core.Solver;
import org.drools.planner.core.localsearch.DefaultLocalSearchSolver;

public class Optimizer {
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