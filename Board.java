import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Board extends JPanel implements BoardConstantsInterface
{

    Cell board[][];
    private Image[] image;
    private int remainingMines;
    private boolean gameOver;
    private JLabel status, timer;
    private long startTime;

    public int getImageType(Cell cell) {
            if(gameOver) {
                    if(cell.isFlagged()) {
                            if(cell.isMine())
                                return IMG_FLAGGED;
                            else
                                return IMG_FLAGGED_WRONG;
                    } else {
                            return cell.getCount();
                    }
            }
            if(cell.isFlagged())
                return IMG_FLAGGED;
            else if(cell.isCovered())
                return IMG_COVERED;
            else
                return cell.getCount();
    }

    Board() {
         board = BoardGenerator.generateBoard();

         remainingMines = 0;
         for(Cell[] i : board)
                for(Cell j : i)
                        remainingMines += (j.isMine())?1:0;

         image = new Image[IMAGES_NUMBER];
         for(int i= 0;i<IMAGES_NUMBER;i++) {
             String path = "img/j" + i + ".gif";
             image[i] = new ImageIcon(path).getImage();
         }

         gameOver = false;
         setLayout(new BorderLayout());

         status = new JLabel("New game started, Mine Count : " + remainingMines);
         timer = new JLabel("Time Elapsed : ");
         startTime = System.currentTimeMillis();
         JPanel southPanel = new JPanel();
         southPanel.setLayout(new BorderLayout());
         southPanel.add(status, BorderLayout.NORTH);
         southPanel.add(timer, BorderLayout.SOUTH);
         add(southPanel, BorderLayout.SOUTH);

         new Thread("Timer Thread") {
                 @Override
                 public void run() {
                         while(!gameOver) {
                                 long diff =  (System.currentTimeMillis()-startTime);
                                 diff = diff/1000; //convert to seconds

                                 String msg = "Time Elapsed : ";
                                 if(diff/60 < 10)
                                        msg+="0";
                                 msg+= (diff)/60 + ":";
                                 if(diff%60 < 10)
                                        msg+="0";
                                 msg+=(diff%60);
                                 timer.setText(msg);
                                 try{
                                         Thread.sleep(1000);
                                 } catch(InterruptedException ex) {}
                                 southPanel.repaint();
                         }
                 }
         }.start();

         addMouseListener(new BoardMouseAdapter());
     }

     @Override
     public void paintComponent(Graphics g)
      {
          super.paintComponent(g);
          
          int i,j;
          int covered = 0;
          for(i=0;i<board.length; i++)
          {
              for(j=0;j<board[i].length;j++)
              {
                  if(board[i][j].isCovered())
                        ++covered;

                  int img_index = getImageType(board[i][j]);
                  g.drawImage(image[img_index], j*CELL_SIZE, i*CELL_SIZE, this);
              }
          }
          if(covered==0 && remainingMines==0) {
                  /* At this stage, game can still be lost!!
                  *  Check if everything has been marked properly */
                  boolean lastCheck = true;
                  for(i=0;i<board.length;++i) {
                        for(j=0;j<board[i].length;++j) {
                                if(board[i][j].isFlagged() && !board[i][j].isMine()) {
                                        lastCheck = false;
                                        break;
                                }
                        }
                }
                if(lastCheck)
                        status.setText("Congratulations, you've won!!");
                else
                        status.setText("Game over, you have lost :(");
                gameOver = true;
          }
      }

      class BoardMouseAdapter extends MouseAdapter {

              @Override
              public void mousePressed(MouseEvent e) {
                      int row = e.getY() / CELL_SIZE;
                      int col = e.getX() / CELL_SIZE;

                      if(row >= BOARD_ROWS || col >= BOARD_COLS || gameOver) {
                              return;
                      }
                      Cell cell = board[row][col];

                      if(SwingUtilities.isRightMouseButton(e)) {
                              /* If cell is uncovered, do nothing */
                              if(!cell.isCovered() && !cell.isFlagged())
                                      return;

                              if(!cell.isFlagged()) {
                                      cell.setFlagged(true);
                                      cell.setCovered(false);
                                      /* Set covered to false so that paint()
                                      will properly count the uncovered tiles*/
                                      --remainingMines;
                              } else {
                                      cell.setFlagged(false);
                                      cell.setCovered(true);
                                      ++remainingMines;
                              }
                              status.setText("Remaining Mines : " + remainingMines);
                      } else if(SwingUtilities.isLeftMouseButton(e)) {
                              if(cell.isFlagged() || !cell.isCovered()) {
                                      /*Do Nothing*/
                                      return;
                              } else if(cell.isMine()) {
                                      /* Game over code here. BOOM */
                                      gameOver = true;
                                      status.setText("Game over, you have lost :(");
                                      BoardGenerator.uncoverAll(board);
                              } else {
                                      BoardGenerator.recursiveUncover(board, row, col);
                                      status.setText("Remaining Mines : " + remainingMines);
                              }
                      }
                      repaint();
              }
      }
}
