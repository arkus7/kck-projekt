package kck.models;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class Object {
    
    
    protected String name;
    protected int x;
    protected int y;
    protected JLabel label;
    protected String iconPath;

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

    public Object(String name, JLabel label) {
        this.name = name.toLowerCase();
        this.x = label.getX();
        this.y = label.getY();
        this.label = label;
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
