package optimize;

import java.util.ArrayList;
import java.util.List;

import org.drools.planner.core.localsearch.LocalSearchSolverScope;
import org.drools.planner.core.localsearch.StepScope;
import org.drools.planner.core.localsearch.decider.Decider;
import org.drools.planner.core.move.Move;
import org.drools.planner.core.move.factory.MoveFactory;
import org.drools.planner.core.solution.Solution;

public class VariableMoveFactory implements MoveFactory {
	public List<Move> createMoveList(Solution solution) {
		ArrayList<Move> newmoves=new ArrayList<Move>();
		
		newmoves.add(new VariableMove(1, ((VariableSolution)solution).variables.get(0)));
		newmoves.add(new VariableMove(-1, ((VariableSolution)solution).variables.get(0)));
		
		return newmoves;
	}

	public void setDecider(Decider decider) {
		
	}

	public void beforeDeciding(StepScope stepScope) {
		
	}

	public void solvingEnded(LocalSearchSolverScope localSearchSolverScope) {
		
	}

	public void solvingStarted(LocalSearchSolverScope localSearchSolverScope) {
		
	}

	public void stepDecided(StepScope stepScope) {
		
	}

	public void stepTaken(StepScope stepScope) {
		
	}
}
