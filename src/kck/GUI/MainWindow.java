package kck.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import kck.models.Goal;
import kck.models.Character;
import kck.models.Question;
import kck.models.Sentence;
import kck.prolog.PrologManager;
import sun.security.util.Length;

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
    private List<JLabel> map;
    private List<String> history;
    private int historyIndex = 0;
    private final int DELAY_TIME = 5;
    public static int timer1 = 0;
    public static int timer2 = 0;
    private int goalX;
    private int goalY;
    private boolean goalReached = false;
    
    private final int DISTANCE = 128;
    private final int VIEW_RANGE = ((int) java.lang.Math.sqrt(2)*DISTANCE)+ 10;
    private final int LABEL_COUNT = 20;
    private final int MAX_ROWS = 4;
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
        
        startNewGame();
        for (int i=0; i < goals.size();i++) System.err.println(goals.get(i).getName());
    }
    
    
    private void initFields() {
        goals = new ArrayList<>();
        exclude = new ArrayList<>();
        icons = new ArrayList<>();
        history = new ArrayList<>();
        map = new ArrayList<>();
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
    
    private void initMap(){
        int count = 0;
        ImageIcon icon = null;
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 7; j++){
                JLabel newLabel = new JLabel();
                newLabel.setBounds(0,0, ICON_HEIGHT, ICON_WIDTH);
                switch (i % 2){
                    case 0:
                        switch(j % 2){
                            case 0:
                                icon = createImageIcon("/kck/GUI/IMG/MapPathCros.png", "");
                                break;
                            case 1:
                                icon = createImageIcon("/kck/GUI/IMG/MapPathUp.png", "");
                                break;                        
                        }
                        break;
                    case 1:
                        switch(j % 2){
                            case 0:
                                icon = createImageIcon("/kck/GUI/IMG/MapPathSide.png", "");
                                break;
                            case 1:
                                icon = createImageIcon("/kck/GUI/IMG/MapPathGrass.png", "");
                                break;                        
                        }
                        break;
                }
                newLabel.setIcon(icon);
                newLabel.setVisible(true);
                newLabel.setLocation(i*64, j*64);
                testLayer1.add(newLabel);  
                testLayer1.setLayer(newLabel, 1);
            }
        }
    }
    
    private void initGoal(){
        int number;
        int X = character.getX();
        int Y = character.getY();
        while(true)
        {
            number = randInt(0, goals.size()-1);
            goalX = goals.get(number).getX();        
            goalY = goals.get(number).getY();            
            
            if( X == goalX || X + DISTANCE == goalX || X - DISTANCE == goalX){
                if (Y == goalY || Y + DISTANCE == goalY || Y - DISTANCE == goalY){
                } else {
                    break;
                }
            } else {
                break;
            }
        }                         
        userGoal.setText("Twoim celem jest dojście do " + pm.getLocalizedGoal(goals.get(number).getName(), PrologManager.WordCase.GENITIVE));       
    }

    private void reachedGoal(){
        if (!goalReached){
           if (character.getX() == goalX && character.getY() == goalY){
                int selectedoption = JOptionPane.showOptionDialog(testLayer1,"Dotarłeś do celu, chcesz zacząć nową gre?","Dotarłeś!!!",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,null,null);
                if (selectedoption == JOptionPane.YES_OPTION){
                    startNewGame();
                } else {
                    goalReached = true;
                    userGoal.setText("Aby rozpocząć nową grę kliknij przycisk \"Nowa gra\"");
                }
            }
        }
    }

    private void randomIcons() {
        ArrayList<Integer> randIcons = randomIntegers(1, Goal.NAMES.length - 1, LABEL_COUNT - 1);
        goals.clear();
        character = new Character("Character", icons.get(0), testLayer1.getWidth()-64, testLayer1.getHeight()-64);
        for(int i = 0; i < LABEL_COUNT - 1; i++) {
            String name = Goal.NAMES[randIcons.get(i)];
            goals.add(new Goal(name, icons.get(i+1)));
        }      
    }

    private void addIcons() {
        for(int i = 0; i < LABEL_COUNT; i++) {
            String labelName = "goal" + String.valueOf(i);
            JLabel newLabel = new JLabel(labelName);
            newLabel.setText(labelName);
            testLayer1.add(newLabel);
            newLabel.setBounds(0,0, ICON_HEIGHT, ICON_WIDTH);
            testLayer1.setLayer(newLabel, (i == 0 ? 10 : 5)); // agent 10, inne 5
            icons.add(newLabel);
        }
    }

    private void removeIcons() {
        testLayer1.removeAll();
        testLayer1.repaint();
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

        testButton.setText("Nowa Gra");
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
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(testButton, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
                    .addComponent(userInput)
                    .addComponent(userGoal))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(testLayer1, javax.swing.GroupLayout.PREFERRED_SIZE, 612, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(testLayer1, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private Goal getGoalFromName(String name) {
        for(int i = 0; i < goals.size(); i++) {
            if(goals.get(i).getName().equalsIgnoreCase(name)) {
                return goals.get(i);
            }
        }
        return null;
    }
    
    private boolean goalExist(Goal goal) {
        if(goal != null) {
            return true;
        }
        return false;
    }
    
    private void checkCharacterViewRange() {
        for(Goal g : goals) {
            if(Math.abs(difMove(character.getX(), g.getX())) < VIEW_RANGE 
                    && Math.abs(difMove(character.getY(), g.getY())) < VIEW_RANGE
                    && character.canSee(g)) {
                g.setInViewRange(true);
            } else {
                g.setInViewRange(false);
            }
        }
    }     
    
    private void userInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userInputActionPerformed

        checkCharacterViewRange();
        String input = userInput.getText();
        Sentence sentence = pm.getSentenceResult(input);
        inputLog = inputLog + "\nU: " + input;
        Goal goal = getGoalFromName(sentence.getGoal());
        if (sentence.isCorrect()){ 
            if(!sentence.getDirection().isEmpty()) {
                character.setTurnSide(sentence.getDirection());
                checkCharacterViewRange();
            }
            if(sentence.getApproach().equalsIgnoreCase("straight")){
              character.moveToDirection(character.getTurnSide(), DISTANCE); 
            } else if (sentence.getMove().equalsIgnoreCase("walk") && !sentence.getDirection().isEmpty() && sentence.getGoal().isEmpty()){
                character.moveToDirection(sentence.getDirection(), DISTANCE);       //np idź na zachód
                timer.start();
                inputLog = inputLog + "\n" + userInput.getText();
            } else if (sentence.getMove().equalsIgnoreCase("turn") && !sentence.getDirection().equalsIgnoreCase(null) && sentence.getGoal().isEmpty() && sentence.getApproach().isEmpty()){
                character.setTurnSide(sentence.getDirection());                      // np. skręć w lewo; skręć na zachód
            } else if (goalExist(goal) && sentence.getMove().equalsIgnoreCase("walk") && sentence.getApproach().isEmpty()){                               //jeśli zdanie nie załapało się wyżej to sprawdza czy cel istnieje
                if(goal.isInViewRange()) {
                    setCharacterMove(goal);
                    userOutput.setText(inputLog);
                    if (!sentence.getDirection().isEmpty()){                            // sprawdza czy w zdaniu jest kierunek
                        character.setTurnSide(sentence.getDirection());                 //ustawia kierunek
                    }
                    if (character.canSee()){                                            //sprawdza czy agent widzi cel
                        character.moveStraightToGoal(); //start timera
                        timer.start();
                    } else {
                        inputLog = inputLog + "\nA: \"" + userInput.getText() + "\" - nie widzę celu"; 
                        //TODO: czy ten else sie kiedys wykonuje?
                    } 
                } else {
                    inputLog = inputLog + "\nA: \"" + userInput.getText() + "\" - nie widzę " + pm.getLocalizedGoal(goal.getName(), PrologManager.WordCase.GENITIVE);
                    inputLog += "\nA: " + "Powiedz mi, jak mam do " + pm.getLocalizedGoal(goal.getName(), PrologManager.WordCase.KIND) + " dojść";
                }
            } else if(sentence.getApproach() != null) {
                if(!sentence.getGoal().isEmpty()) {
                    Goal g = getGoalFromName(sentence.getGoal());
                    if(g.isInViewRange()) {
                        if(!sentence.getDirection().isEmpty()) {
                            character.setGoal(g, sentence.getDirection(), sentence.getApproach());
                            character.setTurnSide(sentence.getDirection());
                        } else {
                            character.setGoal(g);
                            character.setTurnSide();
                            character.setCurvePoints(null, sentence.getApproach());
                        }
                        character.turnToGoal();
                        timer.start();
                    } else {
                        inputLog = inputLog + "\nA: \"" + userInput.getText() + "\" - nie widzę " + pm.getLocalizedGoal(goal.getName(), PrologManager.WordCase.GENITIVE);
                        inputLog += "\nA: " + "Powiedz mi, jak mam do " + pm.getLocalizedGoal(goal.getName(), PrologManager.WordCase.KIND) + " dojść";
                    }
                } else {
                    if(!sentence.getDirection().isEmpty()) {
                        character.setTurnSide(sentence.getDirection());
                        character.setCurvePoints(sentence.getDirection(), sentence.getApproach());
                        character.turnToGoal();
                        timer.start();
                    }
                }
            } else {
                inputLog = inputLog + "\nA: \"" + userInput.getText() + "\" - nie ma takiego celu"; 
            }
        } else {
            Question question = pm.getQuestionResult(input);
            if(question.isCorrect()) {
                if(question.getCzas().equalsIgnoreCase("what") && question.getOrz().equalsIgnoreCase("see")) {
                    inputLog += "\nA: Obiekty, które widzę to:\n";
                    for(Goal g : goals) {
                        if(g.isInViewRange()) {
                            inputLog += "\t" + pm.getLocalizedGoal(g.getName(), PrologManager.WordCase.NOMINATIVE) + "\n";
                        }
                    }
                }
            } else {
                inputLog = inputLog + "\nA: \"" + userInput.getText() + "\" - nie rozumiem polecenia";
            }
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
        startNewGame();
    }//GEN-LAST:event_testButtonMouseClicked

    private void startNewGame() {
        removeIcons();         
        initMap();
        addIcons();
        randomIcons();
        randomIconsLocation();
        initGoal();  
        addGoalHover();
        goalReached = false;
    }

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
        if(rowCount > MAX_ROWS) rowCount = MAX_ROWS;                                      // max ilosc wierszy = 5
        int columnCount = (int) Math.ceil(icons.size()/(double)rowCount);   // wyznacza ilosc kolumn
        //System.out.println(rowCount + " " + columnCount);
        int x = 0, y =0, icon = 0;
        
        for(int i = 0; i < columnCount; i++) {
            for(int j = 0; j < rowCount; j++) {
                //System.err.println(x + " " + y);
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
        
        private void addGoalHover() {
        goals.stream().forEach((g) -> {
            g.addMouseListener();
        });
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

    private void setCharacterMove(Goal goal) {
        character.setMoveX(difMove(character.getX(), goal.getX()));
        character.setMoveY(difMove(character.getY(), goal.getY()));
    }
}
