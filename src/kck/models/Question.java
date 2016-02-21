/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kck.models;

/**
 *
 * @author arkus
 */
public class Question extends Sentence {
    private String czas;
    private String orz;

    public String getCzas() {
        return czas;
    }

    public void setCzas(String czas) {
        this.czas = czas;
    }

    public String getOrz() {
        return orz;
    }

    public void setOrz(String orz) {
        this.orz = orz;
    }
    
    public Question(String question) {
        if(question.contains("czas(")) {
            int questStart = question.indexOf("czas(") + "czas(".length();
            int questEnd = question.indexOf(")", questStart);
            this.czas =  question.substring(questStart, questEnd);
        }
        if(question.contains("orz(")) {
            int questStart = question.indexOf("orz(") + "orz(".length();
            int questEnd = question.indexOf(")", questStart);
            this.orz =  question.substring(questStart, questEnd);
        }
    }

    public Question() {
    }
    
    @Override
    public boolean isCorrect() {
        return czas != null && orz != null;
    }
}
