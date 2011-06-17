package optimize;

import java.util.HashMap;

import org.drools.planner.core.score.DefaultSimpleScore;
import org.drools.planner.core.score.Score;
import org.drools.planner.core.score.calculator.AbstractScoreCalculator;

import spacesim.SpaceSim;

public class SimulationScoreCalculator extends  AbstractScoreCalculator {
	private static final long serialVersionUID = 1L;
	public static HashMap<Integer, Integer> old_scores=new HashMap<Integer, Integer>();
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

    public void simulate(Variable v) throws Exception {
    	System.out.println("Computing score for "+v.value);
    	if(v.value<0) {
    		score=-999999;
    	}
    	else if(old_scores.containsKey(v.value)) {
    		score=(Integer)old_scores.get(v.value);
    	}
    	else {
	    	score=-1;
	    	
	    	for(int x=0; x<Optimizer.RUNS_PER_SCORE; x++){
		    	SpaceSim s = new SpaceSim();
		    	int r = s.simulate(v.value);
		    	
		    	if(r==SpaceSim.DRAW){
					score-=5;
				}
				else if(r==SpaceSim.NORTH_WIN){
					score+=5;
				}
				else if(r==SpaceSim.SOUTH_WIN){
					score-=20;
				}
	    	}
	    	old_scores.put(v.value, score);
    	}
    }
}