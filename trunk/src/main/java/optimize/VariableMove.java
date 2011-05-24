package optimize;

import org.drools.WorkingMemory;
import org.drools.planner.core.move.Move;
import org.drools.runtime.rule.FactHandle;

public class VariableMove implements Move {
	int delta;
	Variable variable;
	
	public VariableMove(int delta, Variable variable) {
		this.delta=delta;
		this.variable=variable;
	}
	
	public Move createUndoMove(WorkingMemory workingMemory) {
		return new VariableMove(delta*-1, variable);
	}

	public void doMove(WorkingMemory workingMemory) {
		FactHandle variableHandle=workingMemory.getFactHandle(variable);
		variable.value+=delta;
		workingMemory.update(variableHandle, variable);
	}

	public boolean isMoveDoable(WorkingMemory workingMemory) {
		return true;
	}
}
