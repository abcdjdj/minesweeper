import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/* MenuPanel is the panel that contains two buttons - New Game and Help
 * It exists in the North region of the BoardFrame. Also responsible for
 * reading the contents of rules.txt */
public class MenuPanel extends JPanel {
        private JButton newGame, help;
        /* Save a reference to the BoardFrame object,
         * so that newGame() can be called from here */
        private BoardFrame frame;
        private String helpMsg;

        MenuPanel(BoardFrame frame) {
                MenuListener ml = new MenuListener();

                newGame = new JButton("New Game");
                newGame.addActionListener(ml);

                help = new JButton("Help");
                help.addActionListener(ml);

                add(newGame);
                add(help);

                new Thread("Rules Thread") {
                        @Override
                        public void run() {
                                helpMsg="";
                                try (FileReader fr = new FileReader("rules.txt")){
                                        int c;
                                        while((c=fr.read())!=-1)
                                                helpMsg+=(char)c;
                                } catch(FileNotFoundException ex) {
                                        helpMsg = "Error! Could not locate rules file";
                                } catch(IOException ex) {
                                        helpMsg = "Error! IOException occured";
                                }
                        }
                }.start();

                this.frame = frame;
        }

        class MenuListener implements ActionListener {
                @Override
                public void actionPerformed(ActionEvent e) {
                        if(e.getSource().equals(newGame)) {
                                frame.newGame();
                        } else if(e.getSource().equals(help)) {
                                JOptionPane.showMessageDialog(frame, helpMsg);
                        }
                }
        }
}
