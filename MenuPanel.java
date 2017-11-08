import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class MenuPanel extends JPanel {
        private JButton newGame, help;
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

                new Thread() {
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
