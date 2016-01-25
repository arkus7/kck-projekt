/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kck.models;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import kck.GUI.MainWindow;

/**
 *
 * @author arkus
 */
public class Goal {
    
    // TODO: add imgs path
    private static final String CHURCH = "/kck/GUI/7.png";
    private static final String PLACEHOLDER = "/kck/GUI/7.png";
    private static final String CHARACTER = "/kck/GUI/8.png";
    private static final String LAMP = "/kck/GUI/8.gif";
    
    private String name;
    private int x;
    private int y;
    private JLabel label;
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

    public Goal(String name, JLabel label) {
        this.name = name;
        this.x = label.getX();
        this.y = label.getY();
        this.label = label;
        switch(name) {
            case "church":
                this.iconPath = CHURCH;
                break;
            default:
                this.iconPath = PLACEHOLDER;
        }
        /*
        ImageIcon icon = new ImageIcon("/src" + iconPath);
        System.out.println(icon.toString());
        this.label.setIcon(icon);
        */
    }
    
    public void setLocation(int x, int y) {
        this.label.setLocation(x, y);
        this.x = label.getX();
        this.y = label.getY();
    }

    @Override
    public String toString() {
        return "Goal{" + "name=" + name + ", x=" + x + ", y=" + y + ", iconPath=" + iconPath + '}';
    }
}
