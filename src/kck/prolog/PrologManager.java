/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kck.prolog;

import kck.models.Sentence;
import org.jpl7.Query;
/**
 *
 * @author arkus
 */
public class PrologManager {
    private final String MOVE = "move(";
    private final String DIR = "dir(";
    private final String GOAL = "goal(";
    private final String BRACKET_END = ")";
    
    public Sentence getResult(String sentence) { 
        String[] words = sentence.split(" ");
        String query = "zdanie(X, [" + String.join(",", words) + "], [])";
        // TODO: add JPL to get this code working
        if(Query.hasSolution(query)) {
            Query q = new Query(query);
            String queryResult = q.oneSolution().get("X").toString();
            return new Sentence(getMove(queryResult), getDirection(queryResult), getGoal(queryResult), getApproach(queryResult));
        }
        return new Sentence(); // Delete this
    }
    
    protected String getMove(String sentence) {
        if(sentence.contains(MOVE)) {
            int moveStart = sentence.indexOf(MOVE) + MOVE.length();
            int moveEnd = sentence.indexOf(BRACKET_END, moveStart);
            return sentence.substring(moveStart, moveEnd);
        }
        return "";
    }
    
    protected String getDirection(String sentence) {
        if(sentence.contains(DIR)) {
            int dirStart = sentence.indexOf(DIR) + DIR.length();
            int dirEnd = sentence.indexOf(BRACKET_END, dirStart);
            return sentence.substring(dirStart, dirEnd);
        }
        return "";
    }
    
    protected String getGoal(String sentence) {
        if(sentence.contains(GOAL)) {
            int goalStart = sentence.indexOf(GOAL) + GOAL.length();
            int goalEnd = sentence.indexOf(BRACKET_END, goalStart);
            return sentence.substring(goalStart, goalEnd);
        }
        return "";
    }

    protected String getApproach(String sentence) {
        //TODO: get approach from sentence
        return "";
    }
}
