/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kck.models;

import java.awt.Graphics;
import java.awt.Point;
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
    private int moveX,moveY;
    private int halfX, halfY;
    private Point startPoint;
    private Point endPoint;
    private double actualT = 0.0;
    
    private final static int TURN_LEFT = 1;
    private final static int TURN_RIGHT = 2;

    public void setActualT(double actualT) {
        this.actualT = actualT;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }
    private final int DELAY_TIME = 5;
    
    
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
    
    ActionListener softLeftToGoal = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent evt) {
            //wywoływany kod tutaj
            double k = 0.001;
            double maxT = 1;
            Point arcPoint = new Point();
            if(moveX > 0) { // w lewo
//                arcPoint = new Point((startPoint.x + endPoint.x)/2 + halfY, (startPoint.y + endPoint.y)/2 + halfX);
                arcPoint = new Point(0,256);
                int newX = (int) (Math.pow(maxT - actualT, 2) * getX() + 2 * (maxT - actualT) * actualT * arcPoint.x + Math.pow(actualT, 2) * endPoint.x);
                System.err.println("double newX = " + String.valueOf(Math.pow(maxT - actualT, 2) * getX() + 2 * (maxT - actualT) * actualT * arcPoint.x + Math.pow(actualT, 2) * endPoint.x));
                System.err.println("int newX = " + newX);
                int newY = (int) (Math.pow(maxT - actualT, 2) * getY() + 2 * (maxT - actualT) * actualT * arcPoint.y + Math.pow(actualT, 2) * endPoint.y);
                setLocation(newX, newY);
                actualT += k;
                System.err.println("arcPoint = " + (arcPoint));
            }
            if(moveX < 0) { // w prawo
                
            }
            System.out.println(".actionPerformed()" + actualT);
            if(actualT > maxT) {
                actualT = 0;
                System.err.println("startPoint = " + startPoint);
                System.err.println("arcPoint = " + (arcPoint));
                System.err.println("endPoint = " + endPoint);
                ((Timer)evt.getSource()).stop();
            }
        }};
    
    ActionListener sharpToGoal = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent evt) {
        //wywoływany kod tutaj
        }};
   
    
    public void moveStraightToGoal(){
        new Timer(DELAY_TIME, straightToGoal).start();
    }
    
    public void moveSoftToGoal() {
        new Timer(DELAY_TIME, softLeftToGoal).start();
    }
    
    public int getMoveX() {
        return moveX;
    }

    public void setMoveX(int x) {
        this.moveX = x;
        this.halfX = x/2;
    }
    public int getMoveY() {
        return moveY;
    }

    public void setMoveY(int y) {
        this.moveY = y;
        this.halfY = y/2;
    }
    
    public void setLabel(JLabel label) {
        this.label = label;
        ImageIcon icon = super.createImageIcon("/kck/GUI/char.png", this.name);
        this.label.setIcon(icon);
    } 
    
    
    public Character(String name, JLabel label) {
        super(name, label);
        ImageIcon icon = super.createImageIcon("/kck/GUI/char.png", this.name);
        this.label.setIcon(icon);
    }
    

}
    
