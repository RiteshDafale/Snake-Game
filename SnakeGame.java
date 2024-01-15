
package snakegame;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class SnakeGame extends JFrame  {
    {
        this.setBounds(10,20,905,700);
        Gamepanel panel = new Gamepanel();
        panel.setBackground(Color.DARK_GRAY);
        this.add(panel);
        this.setLocationRelativeTo(null);   // screen center ma atte hai
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    
    public static void main(String[] args) {
        SnakeGame obj = new SnakeGame();
    }
    
}
