package optimize;

import java.util.ArrayList;
import java.util.Collection;

import org.drools.planner.core.score.Score;
import org.drools.planner.core.score.SimpleScore;
import org.drools.planner.core.solution.Solution;

public class VariableSolution implements Solution {
	public ArrayList<Variable> variables=new ArrayList<Variable>();
	public SimpleScore score;
	
	public VariableSolution(Variable start_solution) {
		variables.add(start_solution);
	}
	
	public VariableSolution() {
		
	}
	
	public Solution cloneSolution() {
		VariableSolution s = new VariableSolution();
		s.setScore(score);
		s.variables=new ArrayList<Variable>();
		s.variables.add(this.variables.get(0));
		return s;
	}

	public Collection<? extends Object> getFacts() {
		return variables;
	}

	public SimpleScore getScore() {
		return score;
	}

	public void setScore(Score score) {
		this.score=(SimpleScore)score;
	}
	
	public String toString(){
		return new Integer(variables.get(0).value).toString();
	}
}
