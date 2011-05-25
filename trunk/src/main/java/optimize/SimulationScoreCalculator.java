package optimize;

import org.drools.planner.core.score.DefaultSimpleScore;
import org.drools.planner.core.score.Score;
import org.drools.planner.core.score.calculator.AbstractScoreCalculator;

public class SimulationScoreCalculator extends  AbstractScoreCalculator {
	private static final long serialVersionUID = 1L;
	private int score;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Score calculateScore() {
        return DefaultSimpleScore.valueOf(score);
    }

    public void simulate(Variable v) {
    	if(v.value==295)
    		score=0;
    	else if (v.value > 295)
    		score=-1*(v.value-295);
    	else
    		score=v.value-295;
    	
    	System.out.println("Asked to simulate for "+v.value);
    }
}