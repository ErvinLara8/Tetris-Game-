import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

public class Window extends JFrame { //extending Jframe

    public static final int WIDTH = 500, HEIGHT = 775;
    private final Color BACKGROUND = new Color(153,153,255);
    private JFrame window; //Creating Jframe class variable

    private Board board; // Creating class variable board




    public Window(){ //Constructor

        //window conditions
        window = new JFrame("Tetris");
        window.setDefaultCloseOperation(EXIT_ON_CLOSE);
        window.setSize(WIDTH,HEIGHT);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setBackground(BACKGROUND);


        //creating a new board
        board = new Board();

        //adding the board to the window
        window.add(board);

        //adding the keyListener to the window
        window.addKeyListener(board);


        window.setVisible(true);

    }

    public static void main(String[] args){

        new Window();

    }


}
