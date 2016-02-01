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
    private static final String HOUSE = "/kck/GUI/house.png";
    private static final String MOUNTAIN = "/kck/GUI/mountain.png";

    public final void setLabel(JLabel label) {
        this.label = label;
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
            case "house":
                this.iconPath = HOUSE;
                break;
            case "mountain":
                this.iconPath = MOUNTAIN;
                break;
            default:
                this.iconPath = PLACEHOLDER;
        }
        ImageIcon icon = createImageIcon(iconPath, this.name);
        this.label.setIcon(icon);
    } 
        
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
        this.label.setIcon(icon);
        setLabel(label);
    }
    

    
}
