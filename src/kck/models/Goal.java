package kck.models;

import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.event.MouseInputListener;
import kck.prolog.PrologManager;


public class Goal extends Object {
    
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
    private static final String SIGN = "/kck/GUI/IMG/sign.png";    
    private static final String PALM = "/kck/GUI/IMG/palm.png";    
    private static final String BARRELS = "/kck/GUI/IMG/barrels.png";    
    private static final String CACTUS = "/kck/GUI/IMG/cactus.png";
    private static final String SNOWMAN = "/kck/GUI/IMG/snowman.png";    
    private static final String WELL = "/kck/GUI/IMG/well.png";    
    private static final String TRUNK = "/kck/GUI/IMG/trunk.png";       
    private static final String RAILS = "/kck/GUI/IMG/rails.png";          
    private static final String TUNNEL = "/kck/GUI/IMG/tunnel.png";
    
    
    
    public static final String[] NAMES = {
        "character",
        "lamp",
        "church",
        "tree",
        "stone",
        "house",
        "mountain",
        "fountain",
        "stadium",
        "tunnel",
        "bench",
        "sign",
        "monument",
        "car",
        "rails",
        "graveyard",
        "palm",
        "barrels",
        "cactus",
        "snowman",
        "well",
        "trunk"
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
            case "sign":
                this.iconPath = SIGN;
                break;
            case "palm":
                this.iconPath = PALM;
                break;
            case "barrels":
                this.iconPath = BARRELS;
                break;
            case "snowman":
                this.iconPath = SNOWMAN;
                break;
            case "cactus":
                this.iconPath = CACTUS;
                break;
            case "well":
                this.iconPath = WELL;
                break;
            case "trunk":
                this.iconPath = TRUNK;
                break;
            case "rails":
                this.iconPath = RAILS;
                break;
            case "tunnel":
                this.iconPath = TUNNEL;
                break;
            default:
                this.iconPath = "";
                this.label.setText(name);
        }
        ImageIcon icon = createImageIcon(iconPath, this.name);
        this.label.setIcon(icon);
        this.label.addMouseListener(showNameListener);
    } 
        
    public Goal(String name, JLabel label) {
        super(name, label);
        setLabel(label);
    }
    
    private MouseInputListener showNameListener = new MouseInputListener() {
        PrologManager pm = new PrologManager();
            @Override
            public void mouseEntered(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                label.setIconTextGap(-64);
                label.setText("<html><div style=\"background-color: white; font-size: 10px; text-align: center\">" + pm.getLocalizedGoal(name, PrologManager.WordCase.NOMINATIVE) + "</div></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setText("");
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
}
