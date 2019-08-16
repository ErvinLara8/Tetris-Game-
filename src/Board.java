import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Board extends JPanel implements KeyListener {

    private BufferedImage blocks; //Class Variable of blocks image

    private final int blocksSize = 30; //variable that determines the size of each block

    private final int boardWidth = 10, boardHeight = 25; //variables that determine the height and width of the board

    private int[][] board = new int[boardHeight][boardWidth]; //board variable that will hold the elements

    private Shape [] shapes = new Shape[7]; //variable that will hold the shapes

    private Shape currentShape; //shape being used variable

    private Timer timer; // timer variable that controls the loops of the game

    private final int FPS = 60; //the frames per second

    private final int delay = 1000/FPS; //the delay of the timer

    private boolean gameOver = false;


    //shape arrays variables
    private int[][] IShape =
            {       {1, 1, 1, 1}    } ;

    private int[][] ZShape =
            {       {1, 1, 0},
                    {0, 1, 1}       } ;
    private int[][] SShape =
            {       {0, 1, 1},
                    {1, 1, 0}       } ;
    private int[][] JShape =
            {       {1, 1, 1},
                    {0, 0, 1}       } ;
    private int[][] LShape =
            {       {1, 1, 1},
                    {1, 0, 0}       } ;
    private int[][] TShape =
            {       {1, 1, 1},
                    {0, 1, 0}       } ;
    private int[][] OShape =
            {       {1, 1},
                    {1, 1}       } ;



    public Board(){ //constructor

        //getting image of blocks
        try {
            blocks = ImageIO.read(Board.class.getResource("TetrisBlocks.png"));
        }catch (IOException e){
            e.printStackTrace();
        }


        //creating repainting loop with timer
        timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //every loop the timer will call the update and repaint method
                //to check move the shape and repaint it in its proper position
                update();
                repaint();
            }
        });

        timer.start(); //starting the timer.


        //creating the Shapes and storing them in an array

        shapes[0] = new Shape(blocks.getSubimage(0,0,blocksSize,blocksSize), IShape, 'I',this); //I - Shape
        shapes[1] = new Shape(blocks.getSubimage(blocksSize,0,blocksSize,blocksSize),ZShape,'Z' ,this); //Z - Shape
        shapes[2] = new Shape(blocks.getSubimage(blocksSize * 2,0,blocksSize,blocksSize), SShape, 'S', this); //S - Shape
        shapes[3] = new Shape(blocks.getSubimage(blocksSize * 3,0,blocksSize,blocksSize), JShape, 'J', this); //J - Shape
        shapes[4] = new Shape(blocks.getSubimage(blocksSize * 4,0,blocksSize,blocksSize), LShape, 'L',this); //L - Shape
        shapes[5] = new Shape(blocks.getSubimage(blocksSize * 5,0,blocksSize,blocksSize), TShape, 'T',this); //T - Shape
        shapes[6] = new Shape(blocks.getSubimage(blocksSize * 6,0,blocksSize,blocksSize), OShape, 'O',this); //O - Shape


        setNewShape();


    }

    public void setNewShape(){ //method to generate new shape
        Random r = new Random(); //creating random generator

        int randomNum = r.nextInt(7); //creating random number

        Shape newShape = new Shape(); //creating newShape variable

        newShape.deepCopy(shapes[randomNum]); //copying all the variables values in the randomly chooses variable from the shapes array

        currentShape = newShape; //current shape becomes the new shape

        for (int row = 0; row < currentShape.getCoord().length; row++) {

            for (int col = 0; col < currentShape.getCoord()[row].length ; col++) {

                if(currentShape.getCoord()[row][col] != 0){

                    if(board[row][col + 3] != 0 ){

                        gameOver= true;

                    }

                }

            }

        }

    }

    public void update(){ //board update method that updates the position of the shape
        currentShape.update();
        if(gameOver){
            timer.stop();
        }
    }

    public void paintComponent(Graphics g){//method that paints the components of the board.

        super.paintComponents(g);

        currentShape.render(g);

        //nested for loops that draw the COLLIDED shapes into the board
        for (int row = 0; row < board.length; row++) { //changing in the y direction

            for (int col = 0; col < board[row].length; col++) { //changing in the x direction

                if(board[row][col] != 0 ){ //if the value at this position og the board matrix is not 0 go here

                    if(board[row][col] == 1) { //if the value is one paint red
                        g.drawImage(blocks.getSubimage(0, 0, blocksSize, blocksSize), col * blocksSize, row * blocksSize, null);
                    }
                    else if(board[row][col] == 2) { //if the value is 2 paint orange
                        g.drawImage(blocks.getSubimage(blocksSize, 0, blocksSize, blocksSize), col * blocksSize, row * blocksSize, null);
                    }
                    else if(board[row][col] == 3) { //if the value is 3 paint yellow
                        g.drawImage(blocks.getSubimage(blocksSize*2, 0, blocksSize, blocksSize), col * blocksSize, row * blocksSize, null);
                    }
                    else if(board[row][col] == 4) { //if the value is 4 paint green
                        g.drawImage(blocks.getSubimage(blocksSize*3, 0, blocksSize, blocksSize), col * blocksSize, row * blocksSize, null);
                    }
                    else if(board[row][col] == 5) { //if the value is 5 paint light blue
                        g.drawImage(blocks.getSubimage(blocksSize*4, 0, blocksSize, blocksSize), col * blocksSize, row * blocksSize, null);
                    }
                    else if(board[row][col] == 6) { //if the value is 6 paint purple
                        g.drawImage(blocks.getSubimage(blocksSize*5, 0, blocksSize, blocksSize), col * blocksSize, row * blocksSize, null);
                    }
                    else if(board[row][col] == 7) { // is the value is 7 paint dark blue
                        g.drawImage(blocks.getSubimage(blocksSize*6, 0, blocksSize, blocksSize), col * blocksSize, row * blocksSize, null);
                    }
                }
            }

        }


        for (int i = 0; i <= boardHeight; i++) { //drawing the horizontal lines

            g.drawLine(0, i*blocksSize, boardWidth*blocksSize, i*blocksSize);
        }

        for (int i = 0; i <= boardWidth; i++) { //drawing the vertical lines

            g.drawLine(i*blocksSize, 0, i*blocksSize, boardHeight * blocksSize);
        }


    }

    public int getBlocksSize() {
        return blocksSize;
    }
    public int[][] getBoard(){
        return board;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_LEFT){ //key listener that moves the shape tot he left
            currentShape.setDeltaX(-1);
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT){ //key listener that moves the shape tot he right
            currentShape.setDeltaX(1);
        }

        if(e.getKeyCode() == KeyEvent.VK_DOWN){ //key to move the shape down faster
            currentShape.speedingUp();
        }
        if(e.getKeyCode() == KeyEvent.VK_UP){ //key to rotate the shape
            currentShape.rotate();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_DOWN){ //if the down key is released, the shape will slow down
            currentShape.normalSpeed();
        }
    }
}
