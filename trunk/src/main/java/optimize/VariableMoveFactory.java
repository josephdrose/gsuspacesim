package optimize;

import java.util.ArrayList;
import java.util.List;

import org.drools.planner.core.move.Move;
import org.drools.planner.core.move.factory.CachedMoveFactory;
import org.drools.planner.core.solution.Solution;

public class VariableMoveFactory extends CachedMoveFactory {
	public List<Move> createCachedMoveList(Solution solution) {
		ArrayList<Move> newmoves=new ArrayList<Move>();
		
		for(int x=1; x<11; x++){
			newmoves.add(new VariableMove(1*x, ((VariableSolution)solution).variables.get(0)));
			newmoves.add(new VariableMove(-1*x, ((VariableSolution)solution).variables.get(0)));
		}
		
		return newmoves;
	}
}
