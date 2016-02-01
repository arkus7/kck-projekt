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
import kck.GUI.MainWindow;

/**
 *
 * @author Shirru
 */
public class Character extends Object {
    private static final String C1N = "/kck/GUI/c1N.png";
    private static final String C1E = "/kck/GUI/c1E.png";
    private static final String C1S = "/kck/GUI/c1S.png";
    private static final String C1W = "/kck/GUI/c1W.png";
    //private static final String CHURCH = "/kck/GUI/church.png";
        
    private int moveX,moveY;
    private final int DELAY_TIME = 5;
    private String turnSide = "north";
    
    public boolean canSee (){
        System.out.println(moveX + " " + moveY + " " + turnSide);
        switch (this.turnSide){
            case "north":
                if ((moveX >= 0 && moveY >= 0) || (moveX <= 0 && moveY >= 0)){
                    if (moveY == 0  && moveX > 0 ) {
                        this.label.setIcon(createImageIcon(C1W, this.name));
                        this.turnSide = "west";
                    } else if (moveY == 0  && moveX < 0 ){
                        this.label.setIcon(createImageIcon(C1E, this.name));
                        this.turnSide = "east";
                    }
                    return true;
                }
                break;
            case "west":
            case "sw":
            case "nw":
                if ((moveX >= 0 && moveY >= 0) || (moveX >= 0 && moveY <= 0)){
                    if (moveX == 0  && moveY > 0 ) {
                        this.label.setIcon(createImageIcon(C1N, this.name));
                        this.turnSide = "north";
                    } else if (moveX == 0  && moveY < 0 ) {
                        this.label.setIcon(createImageIcon(C1S, this.name));
                        this.turnSide = "south";
                    }
                    return true;
                }
                break;
            case "south":
                if ((moveX >= 0 && moveY <= 0) || (moveX <= 0 && moveY <= 0)){
                    if (moveY == 0  && moveX > 0 ) {
                        this.label.setIcon(createImageIcon(C1W, this.name));
                        this.turnSide = "west";
                    } else if (moveY == 0  && moveX < 0 ){
                        this.label.setIcon(createImageIcon(C1E, this.name));
                        this.turnSide = "east";
                    }
                    return true;
                }
                break;
            case "east":
            case "se":
            case "ne":
                if ((moveX <= 0 && moveY >= 0) || (moveX <= 0 && moveY <= 0)){
                    if (moveX == 0  && moveY > 0 ) {
                        this.label.setIcon(createImageIcon(C1N, this.name));
                        turnSide = "north";
                    } else if (moveX == 0  && moveY < 0 ){
                        this.label.setIcon(createImageIcon(C1S, this.name));
                        turnSide = "south";
                    }                    
                    return true;   
                }
                break;
            }
        return false;
    }

    public String getTurnSide() {
        return turnSide;
    }
    
    public String nextSide(String dir){
        switch (dir){
            case "left":
                switch (this.turnSide){
                    case "north":
                    case "nw":
                    case "ne":
                        return "west";
                    case "west":
                        return "south";
                    case "south":
                    case "se":
                    case "sw":
                        return "east";
                    case "east":
                        return "north";
                    default:
                        return "north";
                }
            case "right":
                switch (this.turnSide){
                    case "north":
                    case "nw":
                    case "ne":
                        return "east";
                    case "east":
                        return "south";
                    case "south":
                    case "se":
                    case "sw":
                        return "west";
                    case "west":
                        return "north";
                    default:
                        return "north";
                }
        }
        return dir;
    }

    public void setTurnSide(String turnSide) {
        System.out.println("Przed : "+ turnSide);
        System.out.println("przed : " +this.turnSide); 
        this.turnSide = nextSide(turnSide);
        System.out.println("po : " +this.turnSide);        
        switch(this.turnSide.toLowerCase()) {
            case "west":
            case "sw":
                this.iconPath = C1W;
                break;            
            case "east":
            case "ne":
                this.iconPath = C1E;
                break;
            case "north":
            case "nw":
                this.iconPath = C1N;
                break;
            case "back":
            case "south":
            case "se":
                this.iconPath = C1S;
                break;
            default:
                this.iconPath = C1N;
        }
        ImageIcon icon = createImageIcon(iconPath, this.name);
        System.out.println(icon.toString());
        this.label.setIcon(icon);
    }
    
    public void moveToDirection(String direction, int lenght){
        switch (direction)
        {
            case "north":
                moveX = 0;
                moveY = lenght;
                break;
            case "nw":
                moveX = lenght;
                moveY = lenght;
                break;
            case "west":
                moveX = lenght;
                moveY = 0;
                break;
            case "sw": 
                moveX = lenght;
                moveY = -lenght;
                break;
            case "south":
                moveX = 0;
                moveY = -lenght;
                break;
            case "se":
                moveX = -lenght;
                moveY = -lenght;
                break;
            case "east":
                moveX = -lenght;
                moveY = 0;
                break;
            case "ne": 
                moveX = -lenght;
                moveY = lenght;
                break;
            default:
                moveX = 0;
                moveY = 0;
        }      
        MainWindow.timer1 = Math.abs(moveX);
        MainWindow.timer2 = Math.abs(moveY);
        this.setTurnSide(direction);
        moveStraightToGoal();
    }
    
     public void moveStraightToGoal(){
        MainWindow.timer1 = Math.abs(moveX);
        MainWindow.timer2 = Math.abs(moveY);
        new Timer(DELAY_TIME, straightToGoal).start();
    }
    
    
    ActionListener straightToGoal = new ActionListener() {
        public void actionPerformed(ActionEvent evt) { // po tym kod który ma się wykonać co odstęp czasu          
//        System.out.println(moveX+ " " + moveY);
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
              ((Timer)evt.getSource()).stop(); 
              //zatrzymuje timer
            }
        }
    };
    
    ActionListener softToGoal = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
        //wywoływany kod tutaj
        
        
        }};
    
    ActionListener sharpToGoal = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
        //wywoływany kod tutaj
        }};
   
    
   
    
    
    
    
    
    
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
    
    public void setLabel(JLabel label) {
        this.label = label;
        ImageIcon icon = createImageIcon("/kck/GUI/c1N.png", this.name);
        System.out.println(icon.toString());
        this.label.setIcon(icon);
    } 
    
    
    public Character(String name, JLabel label) {
        super(name, label);
        ImageIcon icon = createImageIcon("/kck/GUI/c1N.png", this.name);
        System.out.println(icon.toString());
        this.label.setIcon(icon);
    }
    

}
    
