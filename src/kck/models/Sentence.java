package kck.models;


public class Sentence {
    protected String move; 
    protected String goal;
    protected String direction;
    protected String approach;

    public Sentence() {
    }

    public Sentence(String move, String goal, String direction, String approach) {
        this.move = move;
        this.goal = goal;
        this.direction = direction;
        this.approach = approach;
    }

    public String getApproach() {
        return approach;
    }

    public String getGoal() {
        return goal;
    }

    public String getDirection() {
        return direction;
    }

    public String getMove() {
        return move;
    }

    @Override
    public String toString() {
        return "Sentence{" + "move=" + move + ", goal=" + goal + ", direction=" + direction + ", approach=" + approach + '}';
    }
    
    public boolean isCorrect() {
        if(move != null) return true;
        return false;
    }
}
