/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kck.models;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 *
 * @author Shirru
 */
public class Character extends Object {
    private int moveX,moveY;
    private final int DELAY_TIME = 5;
    
    
    ActionListener straightToGoal = new ActionListener() {
        public void actionPerformed(ActionEvent evt) { // po tym kod który ma się wykonać co odstęp czasu          
        //testButton.setEnabled(false);
        //userInput.setEnabled(false);
        System.out.println(moveX+ " " + moveY);
            if (moveX != 0){
                if (moveX > 0){
                    moveX = moveX - 1;
                    setLocation(x - 1, y);
                }
                if (moveX < 0){
                    moveX = moveX + 1;
                    setLocation(x + 1, y);
                }
            }
          
            if (moveY != 0){
                if (moveY > 0){
                  moveY = moveY - 1;
                  setLocation(x, y - 1);
                 }
                if (moveY < 0){
                  moveY = moveY + 1;
                  setLocation(x, y + 1);
                }
            }
          
            if (moveY == 0 && moveX == 0){ //aktualnie idzie to Y celu testowego moveY ==0 && moveX == 0
              //testButton.setEnabled(true);
              //userInput.setEnabled(true);
              ((Timer)evt.getSource()).stop();          //zatrzymuje timer
            }
        }
    };
    
   
    
    public void moveStraightToGoal(){
        new Timer(DELAY_TIME, straightToGoal).start();
    }
    
    
    
    
    
    
    public int getMoveX() {
        return moveX;
    }

    public void setMoveX(int x) {
        this.moveX = x;
    }
    public int getMoveY() {
        return moveY;
    }

    public void setMoveY(int y) {
        this.moveY = y;
    }
    
    public Character(String name, JLabel label) {
        super(name, label);
    }
    

}
    
