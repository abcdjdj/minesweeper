import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/* Top level JFrame container. Also responsible for initiating a new game */
public class BoardFrame extends JFrame {

        /* Save the reference to our Board object so that it
         * can be removed from the BoardFrame when newGame() is called */
        private Board boardPanel;
        BoardFrame(String title) {
                super(title);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setSize(270, 330);

                add(new MenuPanel(this), BorderLayout.NORTH);
                boardPanel = new Board();
                add(boardPanel, BorderLayout.CENTER);

                setResizable(false);
                setVisible(true);
        }

        /* Initiating a new game is as simple as just removing the board panel
         * from our BoardFrame, creating a new Board() object and adding it
         * back to our BoardFrame */
        public void newGame() {
                remove(boardPanel);
                boardPanel = new Board();
                add(boardPanel);
                revalidate();
                repaint();
        }
}
