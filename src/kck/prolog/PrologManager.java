///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
//
////// TEST CZY IGNOT W NETBIENIE STARCZY
package kck.prolog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import kck.models.Sentence;
import org.jpl7.JPL;
import org.jpl7.Query;
/**
 *
 * @author arkus
 */
public class PrologManager {
    private final String MOVE = "move(";
    private final String DIR = "dir(";
    private final String GOAL = "goal(";
    private final String APPROACH = "app(";
    private final String BRACKET_END = ")";
    private final String COMMA = ",";
    private final String SERVER_URL = "http://46.101.96.206:5000/?w=";
    private boolean initialized = false;

    public PrologManager() {
        initJPL();
    }
    
    public Sentence getResult(String sentence) { 
        sentence = normalizeSentence(sentence);
        String[] words = sentence.split(" ");
        String query = "zdanie(X, [" + String.join(",", words) + "], []).";
        System.out.println("Query: " + query);
        if(initialized) {
            Query q = new Query(query);
            if(q.hasSolution()) {
                String queryResult = q.oneSolution().get("X").toString();
                System.out.println("Result: " + queryResult);
                return getSentenceFromResult(queryResult);
            }
        } else {
            try {
                URL url = new URL(SERVER_URL + String.join("&w=", words));
                URLConnection con = url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                String result = in.readLine();
                System.err.println("result = " + result);
                return getSentenceFromResult(result);
            } catch (MalformedURLException ex) {
                Logger.getLogger(PrologManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(PrologManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new Sentence();
    }

    private void initJPL() {
        try {
            initialized = JPL.init(new String[] {"swipl", "-g", "true", "-nosignals", "src/kck/prolog/kck2.pl"});
        } catch(Throwable e) {
            initialized = false;
        }
    }
    
    protected String getMove(String sentence) {
        if(sentence.contains(MOVE)) {
            int moveStart = sentence.indexOf(MOVE) + MOVE.length();
            int moveEnd = sentence.indexOf(COMMA, moveStart);
            System.out.println("kck.prolog.PrologManager.getMove(): " + sentence.substring(moveStart, moveEnd));
            return sentence.substring(moveStart, moveEnd);
        }
        return "";
    }
    
    protected String getDirection(String sentence) {
        if(sentence.contains(DIR)) {
            int dirStart = sentence.indexOf(DIR) + DIR.length();
            int dirEnd = sentence.indexOf(BRACKET_END, dirStart);
            System.out.println("kck.prolog.PrologManager.getDirection(): " + sentence.substring(dirStart, dirEnd));
            return sentence.substring(dirStart, dirEnd);
        }
        return "";
    }
    
    protected String getGoal(String sentence) {
        if(sentence.contains(GOAL)) {
            int goalStart = sentence.indexOf(GOAL) + GOAL.length();
            int goalEnd = sentence.indexOf(BRACKET_END, goalStart);
            System.out.println("kck.prolog.PrologManager.getGoal(): " + sentence.substring(goalStart, goalEnd));
            return sentence.substring(goalStart, goalEnd);
        }
        return "";
    }

    protected String getApproach(String sentence) {
        if(sentence.contains(APPROACH)) {
            int appStart = sentence.indexOf(APPROACH) + APPROACH.length();
            int appEnd = sentence.indexOf(BRACKET_END, appStart);
            System.out.println("kck.prolog.PrologManager.getApproach(): " + sentence.substring(appStart, appEnd));
            return sentence.substring(appStart, appEnd);
        }
        return "";
    }
    
    protected String normalizeSentence(String sentence) {
        return sentence.toLowerCase()  
                .trim()                     // returns a copy of the string, with leading and trailing whitespace omitted
                .replaceAll(" +", " ")      // replaces 2 or more spaces with one space
                .replaceAll("[żź]", "z")
                .replaceAll("ą", "a")
                .replaceAll("ł", "l")
                .replaceAll("ę", "e")
                .replaceAll("ó", "o")
                .replaceAll("ś", "s")
                .replaceAll("ć", "c")
                .replaceAll("ń", "n")
                .replaceAll("[^a-z ]+", "");
    }
    
    protected Sentence getSentenceFromResult(String result) {
        return new Sentence(getMove(result), getGoal(result), getDirection(result), getApproach(result));
    }
}
