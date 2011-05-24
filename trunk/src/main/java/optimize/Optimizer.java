package optimize;

import org.drools.planner.config.XmlSolverConfigurer;
import org.drools.planner.core.Solver;

public class Optimizer {
	public static void main(String [] args) {
		XmlSolverConfigurer configurer = new XmlSolverConfigurer(); 
		configurer.configure("/optimize/config.xml"); 
		Solver solver = configurer.buildSolver();
		
		//optimizing fire distance
		solver.setStartingSolution(new VariableSolution(new Variable(310)));
		solver.solve(); 
		VariableSolution bestSolution = (VariableSolution) solver.getBestSolution();
		
		System.out.println("Done; how does "+bestSolution+" sound?");
	}
}