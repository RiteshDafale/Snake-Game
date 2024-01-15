package snakegame;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.time;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.time;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.AbstractAction;
import javax.swing.Timer;

public class Gamepanel extends JPanel  implements ActionListener, KeyListener {

    int xPos[] = {25, 50, 75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450, 475, 500, 525, 550, 575, 600, 625, 650, 675, 700, 725, 750, 775, 800, 825, 850};
    int yPos[] = {75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450, 475, 500, 525, 550, 575, 600, 625};
    // above  array enemy ka leya hai
    private ImageIcon snakeTitle = new ImageIcon(getClass().getResource("images/snaketitle.jpg"));
    private ImageIcon snakeLeft = new ImageIcon(getClass().getResource("images/leftmouth.png"));
    private ImageIcon snakeRight = new ImageIcon(getClass().getResource("images/rightmouth.png"));
    private ImageIcon snakeUp = new ImageIcon(getClass().getResource("images/upmouth.png"));
    private ImageIcon snakeDown = new ImageIcon(getClass().getResource("images/downmouth.png"));
    private ImageIcon snakeImage = new ImageIcon(getClass().getResource("images/snakeimage.png"));
    private ImageIcon enemyImage = new ImageIcon(getClass().getResource("images/enemy.png"));
    // images 
    Timer timer;
    int Delay = 100;  //timer ke speed or snake ke speed
    int enemyX, enemyY;
    boolean left = false;
    boolean right = true;
    boolean up = false;
    boolean down = false;  // snake ka mouth true matlab use taraf mouth rahaga
    int a=1;

    int[] snakeXlength = new int[750];   // snake ke maximum size
    int[] snakeYlength = new int[750];
    int lengthofSnake = 3;   // snake ke minmum length
    Random random = new Random(); // to generate random number 
    int moves = 0;
    boolean gameOver = false;
    int score;

    public Gamepanel() {
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);
        timer = new Timer(Delay, this);
        timer.start();
        this.addKeyListener(this);
        newEnemy();   // enemy function 
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.green);    // border color
        g.drawRect(24, 10, 854, 55);
//        g.drawRect(a, a, WIDTH, HEIGHT);
        g.drawRect(24, 74, 851, 576);
        snakeTitle.paintIcon(this, g, 25, 11);  // not understood
//        snakeTitle.paintIcon(this, g, WIDTH, Delay);
        g.setColor(Color.black);
        g.fillRect(25, 75, 850, 575);
        if (moves == 0) {      // initially game start hoga tab snake ka position 
            snakeXlength[0] = 100;
            snakeXlength[1] = 75;
            snakeXlength[2] = 50;

            snakeYlength[0] = 100;
            snakeYlength[1] = 100;
            snakeYlength[2] = 100;
            moves++;
        }  
        if (left) {  // image ke position 
            snakeLeft.paintIcon(this, g, snakeXlength[0], snakeYlength[0]);
        }
        if (right) {
            snakeRight.paintIcon(this, g, snakeXlength[0], snakeYlength[0]);
        }
        if (up) {
            snakeUp.paintIcon(this, g, snakeXlength[0], snakeYlength[0]);
        }
        if (down) {
            snakeDown.paintIcon(this, g, snakeXlength[0], snakeYlength[0]);
        }

        for (int i = 1; i < lengthofSnake; i++) {
            snakeImage.paintIcon(this, g, snakeXlength[i], snakeYlength[i]);   // 
        }
        enemyImage.paintIcon(this, g, enemyX, enemyY);
//            g.setFont(new Font("Arial",Font.BOLD,20));
//            g.drawString("Score = "+score,750,30);
              
           

        if (gameOver) {  // game over hona ka bad 
            g.setColor(Color.white);
            g.setFont(new Font("Algerian", Font.BOLD, 20));
            g.drawString("Game Over", 150, 200);
            g.drawString("Press Space to Restart", 150, 100);
        }
        g.setColor(Color.GREEN);  // game ka wakh score dekhaga
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.drawString("Score=" + score, 750, 30);//
        g.drawString("Length=" + lengthofSnake, 750, 50);
        g.dispose();   // nhi samza
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = lengthofSnake; i > 0; i--) {
            snakeXlength[i] = snakeXlength[i - 1];
            snakeYlength[i] = snakeYlength[i - 1];
        }
        if (left) {
            
            snakeXlength[0] = snakeXlength[0] - 25;
        }
        if (right) {
            snakeXlength[0] = snakeXlength[0] + 25;
        }
        if (up) {
            snakeYlength[0] = snakeYlength[0] - 25;
        }
        if (down) {
            snakeYlength[0] = snakeYlength[0] + 25;
        }
        
        
         
        if (snakeXlength[0] > 850) {
            snakeXlength[0] = 25;
        }

        if (snakeXlength[0] < 25) {
            snakeXlength[0] = 850;
        }

        if (snakeYlength[0] > 625) {
            snakeYlength[0] = 75;
        }

        if (snakeYlength[0] < 75) {
            snakeYlength[0] = 625;
        }
        collidesWithEnemy();  // function call hoga jab snake enemy sa collide hoga
        collidesWithBody(); // function call hoga jab snake khud ka body sa collide hoga
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {  // if space click hoge to game restart hoga
            restart();
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT && !right) {
            left = true;
            right = false;
            up = false;
            down = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && !left) {
            left = false;
            right = true;
            up = false;
            down = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP && !down) {
            left = false;
            right = false;
            up = true;
            down = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN && !up) {
            left = false;
            right = false;
            up = false;
            down = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private void newEnemy() {
        enemyX = xPos[random.nextInt(34)];
        enemyY = yPos[random.nextInt(23)];
        for (int i = lengthofSnake; i > 0; i--) {
            if (snakeXlength[i] == enemyX && snakeYlength[i] == enemyY) {
                newEnemy();
            }
        }
    }

    private void collidesWithEnemy() {
        if (snakeXlength[0] == enemyX && snakeYlength[0] == enemyY) {
            lengthofSnake++;
            newEnemy();
            score = score + 100;
            if(score == 1500)
            {
                timer.stop();
                gameOver = true;
            }
        }
    }

    private void collidesWithBody() {
        for (int i = lengthofSnake; i > 0; i--) {
            if (snakeXlength[0] == snakeXlength[i] && snakeYlength[0] == snakeYlength[i]) {
                timer.stop();
                gameOver = true;
            }
        }
    }

    private void restart() {
        lengthofSnake = 3;
        right = true;
        left = false;
        up = false;
        down = false;
        score = 0;
        timer.start();
        gameOver = false;
        moves = 0;
        repaint();
    }
}
