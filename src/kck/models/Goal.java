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
public class Goal {
    
    // TODO: add imgs path
    private static final String CHURCH = "";
    private static final String PLACEHOLDER = "";
    
    private String name;
    private int x;
    private int y;
    private String iconPath;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public Goal(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
        switch(name) {
            case "church":
                this.iconPath = CHURCH;
                break;
            default:
                this.iconPath = PLACEHOLDER;
        }
    }

    @Override
    public String toString() {
        return "Goal{" + "name=" + name + ", x=" + x + ", y=" + y + ", iconPath=" + iconPath + '}';
    }
}
