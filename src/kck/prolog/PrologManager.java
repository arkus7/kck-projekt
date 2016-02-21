package kck.prolog;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import kck.models.Question;
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
    private final String SERVER_URL = "http://46.101.96.206:5000/";
    private final String SENTENCE_URL = "sentence";
    private final String FIRST_WORD = "?w=";
    private final String NEXT_WORD = "&w=";
    private final String GOAL_URL = "goal";
    private final String GOAL_NAME = "?name=";
    private final String GOAL_CASE = "&case=";
    private final String QUESTION_URL = "question";
    private boolean initialized = false;
    
    public static enum WordCase {
        NOMINATIVE,
        GENITIVE,
        KIND
    }

    public PrologManager() {
        initJPL();
        checkAndUploadFile();
    }
    
    public String getLocalizedGoal(String goalName, WordCase wordCase) {
        String caseStr;
        switch(wordCase) {
            case NOMINATIVE: caseStr = "mian";
                break;
            case KIND: caseStr = "rodzaj";
                break;
            default:
            case GENITIVE: caseStr = "dop";
        }
        if(initialized) {
            String query = "cel(" + caseStr + ", " + goalName + ", Y, []).";
            Query q = new Query(query);
            if(q.hasSolution()) {
                String solution = q.oneSolution().get("Y").toString();
                return solution.replaceAll("[^a-z]+", "");
            }
            return null;
        } else {
            try {
                URL url = new URL(SERVER_URL + GOAL_URL + GOAL_NAME + goalName + GOAL_CASE + caseStr);
                URLConnection con = url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                String result = in.readLine();
                return result;
            } catch (MalformedURLException ex) {
                Logger.getLogger(PrologManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(PrologManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    public Sentence getSentenceResult(String sentence) { 
        sentence = normalizeSentence(sentence);
        String[] words = sentence.split(" ");
        if(initialized) {
            String query = "zdanie(X, [" + String.join(",", words) + "], []).";
                System.out.println("Query: " + query);
                Query q = new Query(query);
                if(q.hasSolution()) {
                    String queryResult = q.oneSolution().get("X").toString();
                    System.out.println("Result: " + queryResult);
                    return getSentenceFromResult(queryResult);
                }
            } else {
                try {
                    URL url = new URL(SERVER_URL + SENTENCE_URL + FIRST_WORD + String.join(NEXT_WORD, words));
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
    
    public Question getQuestionResult(String sentence) {
        boolean question = sentence.contains("?");
        sentence = normalizeSentence(sentence);
        String[] words = sentence.split(" ");
        if(initialized) {
            String query = "(X, [" + String.join(",", words) + "], []).";
            query = "pytanie" + query;
            System.out.println("Query: " + query);
            Query q = new Query(query);
            if(q.hasSolution()) {
                System.out.println(q.oneSolution().get("X").toString());
                String result =  q.oneSolution().get("X").toString();
                return new Question(result);
            }
            return new Question();
        } else {
            try {
                URL url = new URL(SERVER_URL + QUESTION_URL + FIRST_WORD + String.join(NEXT_WORD, words));
                URLConnection con = url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                String result = in.readLine();
                return new Question(result);
            } catch(IOException ex) {
                
            }
            return new Question();
        }
    }

    private void initJPL() {
        try {
            initialized = JPL.init(new String[] {"swipl", "-g", "true", "-nosignals", "src/kck/prolog/kck2.pl"});
        } catch(Throwable e) {
            initialized = false;
        }
    }
    
        private void checkAndUploadFile() {
        try {
            URL url = getClass().getResource("sync.sh");
            File file = new File(url.getPath());
            file.setExecutable(true);
            ProcessBuilder pb = new ProcessBuilder("./sync.sh");
            pb.directory(new File(file.getParent()));
            Process p = pb.start();
            System.err.println("Synced prolog files succesfully.");
        } catch (IOException | NullPointerException ex) {
            System.err.println("Couldn't sync prolog files. " + ex.getMessage());
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
                .replace("?", "")
                .replaceAll("[^a-z ]+", "");
    }
    
    protected Sentence getSentenceFromResult(String result) {
        try {
            return new Sentence(getMove(result), getGoal(result), getDirection(result), getApproach(result));
        } catch(NullPointerException ex) {
            return new Sentence();
        }
    }
}
