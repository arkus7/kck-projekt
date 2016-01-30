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
public class Goal extends Object {
    
    // TODO: add imgs path
    private static final String CHURCH = "/kck/GUI/church.png";
    private static final String PLACEHOLDER = "/kck/GUI/error.png";
    private static final String CHARACTER = "/kck/GUI/char.png";
    private static final String LAMP = "/kck/GUI/lamp.png";
    private static final String TREE = "/kck/GUI/tree.png";
    private static final String STONE = "/kck/GUI/stone.png";

    public Goal(String name, JLabel label) {
        super(name, label);
        switch(this.name) {
            case "church":
                this.iconPath = CHURCH;
                break;
            case "stone":
                this.iconPath = STONE;
                break;
            case "character":
                this.iconPath = CHARACTER;
                break;
            case "tree":
                this.iconPath = TREE;
                break;
            case "lamp":
                this.iconPath = LAMP;
                break;
            default:
                this.iconPath = PLACEHOLDER;
        }
        ImageIcon icon = createImageIcon(iconPath, this.name);
        System.out.println(icon.toString());
        this.label.setIcon(icon);
    }
    

    @Override
    public String toString() {
        return "Goal{" + "name=" + name + ", x=" + x + ", y=" + y + ", iconPath=" + iconPath + '}';
    }
    
    protected ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
}
