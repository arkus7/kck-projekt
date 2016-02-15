/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kck.models;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
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
    private static final String CHURCH = "/kck/GUI/IMG/church.png";
    private static final String PLACEHOLDER = "/kck/GUI/IMG/error.png";
    private static final String LAMP = "/kck/GUI/IMG/lamp.png";
    private static final String TREE = "/kck/GUI/IMG/tree.png";
    private static final String STONE = "/kck/GUI/IMG/stone.png";
    private static final String HOUSE = "/kck/GUI/IMG/house.png";
    private static final String MOUNTAIN = "/kck/GUI/IMG/mountain.png";
    private static final String MONUMENT = "/kck/GUI/IMG/statue.png";
    private static final String FOUNTAIN = "/kck/GUI/IMG/fountain.png";
    private static final String STADIUM = "/kck/GUI/IMG/stadium.png";
    private static final String CAR = "/kck/GUI/IMG/car.png";
    private static final String BENCH = "/kck/GUI/IMG/bench.png";
    private static final String GRAVEYARD = "/kck/GUI/IMG/graveyard.png";
    
    public static final String[] NAMES = {
        "character",
        "lamp",
        "lake",
        "church",
        "tree",
        "stone",
        "house",
        "mountain",
        "fountain",
        "stadium",
        "tunnel",
        "bench",
        "river",
        "busstop",
        "monument",
        "car",
        "rails",
        "graveyard"
    };

    public final void setLabel(JLabel label) {
        this.label = label;
        switch(this.name) {
            case "church":
                this.iconPath = CHURCH;
                break;
            case "stone":
                this.iconPath = STONE;
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
            case "monument":
                this.iconPath = MONUMENT;
                break;
            case "fountain":
                this.iconPath = FOUNTAIN;
                break;
            case "stadium":
                this.iconPath = STADIUM;
                break;
            case "bench":
                this.iconPath = BENCH;
                break;
            case "car":
                this.iconPath = CAR;
                break;
            case "graveyard":
                this.iconPath = GRAVEYARD;
                break;
            default:
                this.iconPath = "";
                this.label.setText(name);
        }
        ImageIcon icon = createImageIcon(iconPath, this.name);
        System.out.println(icon.toString());
        this.label.setIcon(icon);
    } 
        
    public Goal(String name, JLabel label) {
        super(name, label);
        setLabel(label);
    }
    

    
}
