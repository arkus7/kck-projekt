package kck.models;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;
import kck.GUI.MainWindow;


public class Character extends Object {
    private static final String C1N = "/kck/GUI/IMG/c1N.png";
    private static final String C1E = "/kck/GUI/IMG/c1E.png";
    private static final String C1S = "/kck/GUI/IMG/c1S.png";
    private static final String C1W = "/kck/GUI/IMG/c1W.png";
        
    private int moveX,moveY,tempX,tempY;
    private final int DELAY_TIME = 5;
    private int maxX, maxY;
    private String turnSide = "south";
    private Goal goal;
    private Point endPoint;
    private List<Point> curvePoints;
    private final int DISTANCE = 128;
    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal, String direction, String approach) {
        this.goal = goal;
        this.generateCurvePoints(direction, approach);
    }

    public List<Point> getCurvePoints() {
        return curvePoints;
    }

    public void setCurvePoints(String direction, String approach) {
        this.generateCurvePoints(direction, approach);
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }
    
    public boolean canSee (){
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
    
    public boolean canSee (Goal goal){
        int toMoveX = this.getX() - goal.getX();
        int toMoveY = this.getY() - goal.getY();
        switch (this.turnSide){
            case "north":
                if ((toMoveX >= 0 && toMoveY >= 0) || (toMoveX <= 0 && toMoveY >= 0)){
                    return true;
                }
                break;
            case "west":
            case "sw":
            case "nw":
                if ((toMoveX >= 0 && toMoveY >= 0) || (toMoveX >= 0 && toMoveY <= 0)){
                    return true;
                }
                break;
            case "south":
                if ((toMoveX >= 0 && toMoveY <= 0) || (toMoveX <= 0 && toMoveY <= 0)){
                    return true;
                }
                break;
            case "east":
            case "se":
            case "ne":
                if ((toMoveX <= 0 && toMoveY >= 0) || (toMoveX <= 0 && toMoveY <= 0)){             
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
        //System.out.println("Przed : "+ turnSide);
        //System.out.println("przed : " +this.turnSide); 
        this.turnSide = nextSide(turnSide);
        //System.out.println("po : " +this.turnSide);        
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
        this.label.setIcon(icon);
    }
    
    public void setTurnSide() {
        if(goal != null) {
            int moveX = this.x - goal.x;
            int moveY = this.y - goal.y;
            
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
                }
                break;
            }
        }
    }
    
    public void moveToDirection(String direction, int lenght){
        direction = nextSide(direction);
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
        if(x - moveX < 0 || y - moveY < 0 || x - moveX >= maxX || y - moveY >= maxY){            
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
        tempX = moveX;
        tempY = moveY;
        new Timer(DELAY_TIME, straightToGoal).start();
    }
    
    public void turnToGoal() {
        new Timer(DELAY_TIME, turnToGoalTimer).start();
    }
    
    
    ActionListener straightToGoal = new ActionListener() {
        public void actionPerformed(ActionEvent evt) { // po tym kod który ma się wykonać co odstęp czasu          
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
    
    ActionListener turnToGoalTimer = new ActionListener() {
        int k = 0;
        public void actionPerformed(ActionEvent evt) {
            int x = getX();
            int y = getY();
            setLocation(curvePoints.get(k).x, curvePoints.get(k).y);
            if(k < curvePoints.size() - 1) {
                k++;
                MainWindow.timer1 = curvePoints.size();
            } else {
                if(goal != null) {
                    setLocation(endPoint.x, endPoint.y);
                }
                if(MainWindow.timer1 > 0) {
                    MainWindow.timer1 = 0;
                }
                k = 0;
                goal = null;
                ((Timer)evt.getSource()).stop();
            }
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
    
    public JLabel getLabel(){
        return this.label;
    }
    
    public void setMoveY(int y) {
        this.moveY = y;
    }
    
    public void setLabel(JLabel label) {
        this.label = label;
        ImageIcon icon = createImageIcon("/kck/GUI/IMG/c1S.png", this.name);
        this.label.setIcon(icon);
    } 
 
    public Character(String name, JLabel label, int maxX, int maxY) {
        super(name, label);
        ImageIcon icon = createImageIcon("/kck/GUI/IMG/c1S.png", this.name);
        this.label.setIcon(icon);
        this.maxX = maxX;
        this.maxY = maxY;
    }
    
    private void generateCurvePoints(String direction, String type) {
        List<Point> points = new ArrayList<>();
        Point start = new Point(this.x, this.y);
        Point end;
        Point controlOffset = new Point();
        int offset = (type.contains("sh") ? DISTANCE : DISTANCE/2);
        if(goal != null) {
            end = new Point(goal.getX(), goal.getY());
        } else if(!direction.isEmpty()) {
            switch(direction) {
                case "north":
                end = new Point(start.x, start.y - DISTANCE);
                break;
            case "nw":
                end = new Point(start.x - DISTANCE, start.y - DISTANCE);
                break;
            case "west":
                end = new Point(start.x - DISTANCE, start.y);
                break;
            case "sw": 
                end = new Point(start.x - DISTANCE, start.y + DISTANCE);
                break;
            case "south":
                end = new Point(start.x, start.y + DISTANCE);
                break;
            case "se":
                end = new Point(start.x + DISTANCE, start.y + DISTANCE);
                break;
            case "east":
                end = new Point(start.x + DISTANCE, start.y);
                break;
            case "ne": 
                end = new Point(start.x + DISTANCE, start.y - DISTANCE);
                break;
            default: 
                end = new Point();
                break;
            }
        } else {
            end = new Point();
        }
        endPoint = end;
        switch(turnSide) {
            case "south":
            case "east":
                offset *= -1;
            break;
        }
        if(Math.abs(end.y - start.y) == 0) {    // poziomy łuk
            controlOffset = new Point((end.x - start.x) / 2, 
                    (type.contains("l") ? offset : - offset));
        } else if(Math.abs(end.x - start.x) == 0) { // pionowy łuk
            controlOffset = new Point((type.contains("r") ? offset : - offset), 
                    (end.y - start.y) / 2);
        } else if(end.y - start.y < 0) { // na polnoc
            if(end.x - start.x > 0) { // na wschod
                if(type.contains("r")) {
                    controlOffset = new Point(offset, 0);   // prawy
                } else {
                    controlOffset = new Point(0, - offset); // lewy
                }
            } else if(end.x - start.x < 0) { // na zachod
                if(type.contains("r")) {
                    controlOffset = new Point(0, -offset);  // prawy
                } else {
                    controlOffset = new Point(-offset, 0);  // lewy
                }
            }
        } else if(end.y - start.y > 0) { // na poludnie 
            if(end.x - start.x > 0) { // na wschod
                controlOffset = new Point((type.contains("l")? - offset : offset), 0);
            } else if(end.x - start.x < 0) { // na zachod
                controlOffset = new Point(0, (type.contains("l")? - offset : offset));
            }
        }
        Point controlPoint = new Point(start.x + controlOffset.x, start.y + controlOffset.y);
        for(double t = 0.0; t < 1.0; t+= 0.005) {
            int x = (int) (Math.pow(1 - t, 2) * start.x + 2 * (1 - t) * t * controlPoint.x + Math.pow(t, 2) * end.x);
            int y = (int) (Math.pow(1 - t, 2) * start.y + 2 * (1 - t) * t * controlPoint.y + Math.pow(t, 2) * end.y);
            points.add(new Point(x,y));
        }
        this.curvePoints = points;
    }
}
    
