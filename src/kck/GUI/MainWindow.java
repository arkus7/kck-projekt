/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kck.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import kck.models.Object;
import kck.models.Goal;
import kck.models.Character;
import kck.models.Sentence;
import kck.prolog.PrologManager;

/**
 *
 * @author arkus & Shir
 */
public class MainWindow extends javax.swing.JFrame {
    private String inputLog = "";
    private PrologManager pm = new PrologManager();
    private List<Goal> goals;
    private Character character;
    private List<Integer> exclude;
    private List<JLabel> icons;
    private List<String> history;
    private int historyIndex = 0;
    private final int DELAY_TIME = 5;
    public static int timer1 = 0;
    public static int timer2 = 0;
    private int goalX;
    private int goalY;
    
    private final int DISTANCE = 128;
    private final int VIEW_RANGE = ((int) java.lang.Math.sqrt(2)*DISTANCE)+ 10;
    private final int LABEL_COUNT = 18;
    private final int ICON_HEIGHT = 64;
    private final int ICON_WIDTH = 64;
    private final int KEY_UP = 38;
    private final int KEY_DOWN = 40;
    private final int KEY_ENTER = 10;

    private final Timer timer;

    
    public JLabel testLabel;

    ActionListener inputBlockade = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            testButton.setEnabled(false);
            userInput.setEnabled(false);
            timer1--;
            timer2--;
            //System.out.println(timer1+ " " + timer2);
            if(timer1 <= 0 && timer2 <= 0){
                testButton.setEnabled(true);
                userInput.setEnabled(true);
                userInput.requestFocus();
                reachedGoal();
                ((Timer)evt.getSource()).stop(); 
            }
        }
    };
    
    /**
     * Creates new form NewJFrame
     */
    public MainWindow() {
        initComponents();
        initFields();
        
        timer = new Timer(DELAY_TIME, inputBlockade);
        
        addIcons();
        randomIconsLocation();
        randomIcons();
        initGoal();
    }

    private void initFields() {
        goals = new ArrayList<>();
        exclude = new ArrayList<>();
        icons = new ArrayList<>();
        history = new ArrayList<>();
    }
    
    private void initGoal(){
        int number = randInt(1, goals.size() - 1);
        userGoal.setText("Tówj cel to: " + pm.getLocalizedGoal(goals.get(number).getName()));
        goalX = goals.get(number).getX();
        goalY = goals.get(number).getY();        
    }
    
    private void reachedGoal(){
        if (character.getX() == goalX && character.getY() == goalY){
            System.err.println("cel osiągnięy");
//            final JOptionPane optionPane = new JOptionPane("Dotarłeś do celu. Chcesz rozpocząć nową gre?",JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
//            optionPane.setVisible(true);
           int selectedoption = JOptionPane.showOptionDialog(testLayer1,"Dotarłeś do celu, chcesz zacząć nową gre?","Dotarłeś!!!",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,null,null);
           if (selectedoption == JOptionPane.YES_OPTION){
                removeIcons();
                addIcons();
                randomIcons();
                randomIconsLocation();
                initGoal();
           }
    }
        else System.err.println(character.getX() + " " + goalX  + " " +  character.getY() + " " +  goalY);
    }

    private void randomIcons() {
        ArrayList<Integer> randIcons = randomIntegers(1, Goal.NAMES.length - 1, LABEL_COUNT - 1);
        goals.clear();
        System.out.println(randIcons);
        character = new Character("Character", icons.get(0), testLayer1.getWidth()-64, testLayer1.getHeight()-64);
        for(int i = 0; i < LABEL_COUNT - 1; i++) {
            System.out.println("kck.GUI.MainWindow.randomIcons() randIcons = " + randIcons);
            System.out.println("kck.GUI.MainWindow.randomIcons() i == " + i);
            String name = Goal.NAMES[randIcons.get(i)];
            System.err.println("RANDOM NAME = " + name);
            goals.add(new Goal(name, icons.get(i+1)));
        }      
    }

    private void addIcons() {
        for(int i = 0; i < LABEL_COUNT; i++) {
            String labelName = "goal" + String.valueOf(i);
            testLabel = new JLabel(labelName);
            testLabel.setText(labelName);
            testLayer1.add(testLabel);
            testLabel.setBounds(0,0, ICON_HEIGHT, ICON_WIDTH);
            testLayer1.setLayer(testLabel, (i == 0 ? 10 : 5)); // agent 10, inne 5
            icons.add(testLabel);
        }
    }

    private void removeIcons() {
        testLayer1.removeAll();
        testLayer1.repaint();
        System.err.println("Layer cleared");
        icons.clear();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        userInput = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        userOutput = new javax.swing.JTextArea();
        testLayer1 = new javax.swing.JLayeredPane();
        testButton = new javax.swing.JButton();
        userGoal = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(790, 700));
        setResizable(false);

        userInput.setText("Polecenie: ");
        userInput.setToolTipText("test");
        userInput.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                userInputFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                userInputFocusLost(evt);
            }
        });
        userInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userInputActionPerformed(evt);
            }
        });
        userInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                userInputKeyPressed(evt);
            }
        });

        userOutput.setEditable(false);
        userOutput.setColumns(20);
        userOutput.setRows(5);
        jScrollPane2.setViewportView(userOutput);

        testButton.setText("Reload");
        testButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                testButtonMouseClicked(evt);
            }
        });

        userGoal.setEditable(false);
        userGoal.setEnabled(false);
        userGoal.setFocusable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(testLayer1)
                        .addGap(68, 68, 68))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(testButton, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
                            .addComponent(userInput)
                            .addComponent(userGoal))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(testLayer1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(userGoal, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(userInput)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(testButton, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(38, 38, 38))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    private boolean goalExist(String goal){
        for (int i = 0; i < goals.size() ; i++){
            if (goals.get(i).getName().equalsIgnoreCase(goal) && Math.abs(difMove(character.getX(), goals.get(i).getX())) < VIEW_RANGE && Math.abs(difMove(character.getY(), goals.get(i).getY())) < VIEW_RANGE) {
                character.setMoveX(difMove(character.getX(), goals.get(i).getX()));
                character.setMoveY(difMove(character.getY(), goals.get(i).getY()));          
                System.out.println("X = " + character.getMoveX() + " Y = " + character.getMoveY());
                System.out.println(VIEW_RANGE);
                return true; 
            }
        }       
        return false;
    }
     
    
    private void userInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userInputActionPerformed
     Sentence sentance = pm.getResult(userInput.getText());
     
     if (sentance.isCorrect()){   
        if (sentance.getMove().equalsIgnoreCase("walk") && !sentance.getDirection().isEmpty() && sentance.getGoal().isEmpty()){
            character.moveToDirection(sentance.getDirection(), DISTANCE);       //np idź na zachód
            timer.start();
            inputLog = inputLog + "\n" + userInput.getText();
        } else if (sentance.getMove().equalsIgnoreCase("turn") && !sentance.getDirection().equalsIgnoreCase(null) && sentance.getGoal().isEmpty()){
            character.setTurnSide(sentance.getDirection());                      // np. skręć w lewo; skręć na zachód
            inputLog = inputLog + "\n" + userInput.getText();
        } else if (goalExist(sentance.getGoal())){                               //jeśli zdanie nie załapało się wyżej to sprawdza czy cel istnieje
            userOutput.setText(inputLog);
            if (!sentance.getDirection().isEmpty()){                            // sprawdza czy w zdaniu jest kierunek
                character.setTurnSide(sentance.getDirection());                 //ustawia kierunek
            }
            if (character.canSee()){                                            //sprawdza czy agent widzi cel
                character.moveStraightToGoal(); //start timera
                timer.start();
                inputLog = inputLog + "\n" + userInput.getText();
                userInput.setText("");
            } else{
                inputLog = inputLog + "\n" + userInput.getText() + " - Nie widzę celu"; 
            } 
        } else {
            inputLog = inputLog + "\n" + userInput.getText() + " - nie ma takiego celu"; 
        }
     } else{
         inputLog = inputLog + "\n" + userInput.getText() + " - nie rozumiem polecenia";
     }
        userOutput.setText(inputLog);
        userInput.setText("");

     
        
    }//GEN-LAST:event_userInputActionPerformed

    private void userInputFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_userInputFocusGained
        // TODO add your handling code here:
        userInput.setText("");        
    }//GEN-LAST:event_userInputFocusGained

    private void userInputFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_userInputFocusLost
        // TODO add your handling code here:
        userInput.setText("Polecenie: ");
    }//GEN-LAST:event_userInputFocusLost

    private void testButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_testButtonMouseClicked
        removeIcons();
        addIcons();
        randomIcons();
        randomIconsLocation();
        initGoal();
    }//GEN-LAST:event_testButtonMouseClicked

    private void userInputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_userInputKeyPressed
        // TODO add your handling code here:
        switch(evt.getKeyCode()) {
            case KEY_DOWN:
                if(history.size() > 0 && historyIndex > 0) {
                    userInput.setText(history.get(--historyIndex));
                } else if(history.size() > 0 && historyIndex == 0) {
                    userInput.setText(history.get(0));
               }
                break;
            case KEY_UP:
                if(history.size() - 1 > historyIndex) {
                    userInput.setText(history.get(++historyIndex));
                } else {
                    userInput.setText("");
                }
                break;
            case KEY_ENTER:
                history.add(userInput.getText());
                historyIndex = history.size();
                break;
        }
    }//GEN-LAST:event_userInputKeyPressed

    public void randomIconsLocation() {
        // wylosowanie nowych lokalizacji
        int locationNumber=0; 
        
        //ustawienie lokazliacji w orginalnych miejscach
        int rowCount = (int) Math.ceil(icons.size()/2.0);                   // dzieli ilość ikon na 2
        if(rowCount > 5) rowCount = 5;                                      // max ilosc wierszy = 5
        int columnCount = (int) Math.ceil(icons.size()/(double)rowCount);   // wyznacza ilosc kolumn
        System.out.println(rowCount + " " + columnCount);
        int x = 0, y =0, icon = 0;
        
        for(int i = 0; i < rowCount; i++) {
            for(int j = 0; j < columnCount; j++) {
                System.err.println(x + " " + y);
                icons.get(icon).setLocation(x, y);
                icon++;
                if(icon > icons.size() - 1) {
                    break;
                }
                y += DISTANCE;
            }
            y = 0;
            x += DISTANCE;
        }

        locationNumber = randInt(0, icons.size()-1);                                                      //losowanie liczby z zakresu ilości labeli     
        character = new Character("character", icons.get(locationNumber), testLayer1.getWidth() - 64, testLayer1.getHeight() - 64);     // randomowa pozycja agenta
        exclude.add(locationNumber);        // dodanie wylosowanej liczby do zbioru liczb zuzytych
        character.setLabel(icons.get(locationNumber));                                       //ustawienie postaci nowego labela
        character.setLocation(icons.get(locationNumber).getX(), icons.get(locationNumber).getY());        //ustawienie postaci nowej pozycji
        testLayer1.setLayer(icons.get(locationNumber), 10);                                  //ustawienie postaci nowej pozycji, aby była widoczna nad innymi
        for(int j = 0; j < goals.size(); j++) {                                 //przydzielanie nowej lokalizacji kazdemu celowi po kolei
            while(exclude.contains(locationNumber)){                                         //dopóki nie wylosuje liczby, która jeszcze ani razu sie nie pojawiła
                locationNumber = randInt(0,icons.size() - 1); 
            }
            exclude.add(locationNumber);                                                     //dodanie do zbioru wykloczonych
            goals.get(j).setLabel(icons.get(locationNumber));                                //ustawienie celowi nowego labela
            testLayer1.setLayer(icons.get(locationNumber),5);                                // ustawienie nowego poziomu aby cele były pod postacią
            goals.get(j).setLocation(icons.get(locationNumber).getX(), icons.get(locationNumber).getY()); //ustawienie nowej lokazlicaji
        }
        exclude.clear();                                                        //wyczyszczenie listy wykluczonych liczb
    }
    
    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
    
    public ArrayList<Integer> randomIntegers(int min, int max, int size) {
        System.err.println(min + " " + max + " " +  size);
        System.err.println(Math.abs(max - min));
        if(Math.abs(max - min) + 1 < size) {
            return null;
        }
        ArrayList<Integer> integers = new ArrayList<>();
        ArrayList<Integer> already = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            int rand = randInt(min, max);
            while(already.contains(rand)) {
                rand = randInt(min, max);
            }
            integers.add(rand);
            already.add(rand);
        }
        return integers;
    }
    
    private int difMove(int charPos, int goalPos){ 
        // wyliczenie odległości między puntami na tej samej osi;
        return charPos - goalPos;
    }
        
        
        
        
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }
    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton testButton;
    private javax.swing.JLayeredPane testLayer1;
    private javax.swing.JTextField userGoal;
    private javax.swing.JTextField userInput;
    private javax.swing.JTextArea userOutput;
    // End of variables declaration//GEN-END:variables
}
