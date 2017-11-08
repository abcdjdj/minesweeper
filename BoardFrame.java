import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BoardFrame extends JFrame {

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

        public void newGame() {
                getContentPane().remove(boardPanel);
                boardPanel = new Board();
                getContentPane().add(boardPanel);
                revalidate();
                repaint();
        }
}
