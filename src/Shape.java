import java.awt.*;
import java.awt.image.BufferedImage;

public class Shape {

    private char identifyer;

    private BufferedImage block;  //variable that carries the block image color of each block

    private int[][] coord; //variable that holds the coordinates of the shape

    private Board board; //variable that allows access to the board

    private int deltaX = 0; // variable that changes the x position of the x

    private int x, y; //variables that determine the position of the shape

    private int normalSpeed = 600 , fastSpeed = 60, currentSpeed; //variables that determine the speed of the drop

    private long time , lastTime; //variables that get current time to help the shape drop slower

    private boolean collision = false, checkingX;


    public Shape(){ //default constructor
        this.block = null;
        this.coord = null;
        this.board = null;
        this.identifyer = 'I';

        x = 4;
        y = 0;

        currentSpeed = normalSpeed;
        time = 0; //time is set to 0
        lastTime = System.currentTimeMillis(); //last time is set to current time in millisecond
    }

    public Shape(BufferedImage block, int[][]coord, char identifyer,Board board){ //main constructor

        this.block = block;
        this.coord = coord;
        this.board = board;
        this.identifyer = identifyer;

        x = 4;
        y = 0;

        currentSpeed = normalSpeed;
        time = 0; //time is set to 0
        lastTime = System.currentTimeMillis(); //last time is set to current time in millisecond

    }


    public void update(){ //method that updates the position of the shape

        time += System.currentTimeMillis() - lastTime; //time becomes a difference between milliseconds

        lastTime = System.currentTimeMillis(); // last time is updated



        if(collision){ //if a collision happens go here

            //nested for loops that puts the values int the board depending on the shape
            for (int rows = 0; rows < coord.length; rows++) { //changing the y direction

                for (int col = 0; col < coord[rows].length; col++) {//changing int he x direction

                    if(coord[rows][col] != 0){ //changing the values of the board depending on where the shape landed

                        if(identifyer == 'I') { //if the shape is I put a 1 in that certain location of the board
                            board.getBoard()[y + rows][x + col] = 1;
                        }
                        else if(identifyer == 'Z') { //if the shape is z put a 2 in that certain location of the board
                            board.getBoard()[y + rows][x + col] = 2;
                        }
                        else if(identifyer == 'S') { //if the shape is s put a 3 in that certain location of the board
                            board.getBoard()[y + rows][x + col] = 3;
                        }
                        else if(identifyer == 'J') { //if the shape is j put a 4 in that certain location of the board
                            board.getBoard()[y + rows][x + col] = 4;
                        }
                        else if( identifyer == 'L') {//if the shape is l put a 5 in that certain location of the board
                            board.getBoard()[y + rows][x + col] = 5;
                        }
                        else if(identifyer == 'T') {//if the shape is t put a 6 in that certain location of the board
                            board.getBoard()[y + rows][x + col] = 6;
                        }
                        else if(identifyer == 'O') {//if the shape is o put a 7 in that certain location of the board
                            board.getBoard()[y + rows][x + col] = 7;
                        }

                    }

                }

            }

            chekingLine();

            board.setNewShape(); //setting a new piece

        }



        //first condition is the x left upper most square plus a change in one plus the length of the shape is less than or equal to 10
        //second condition is the x left square plus the change in position is grater than or equal to 0
        if(x + deltaX + coord[0].length <= 10 && x + deltaX >= 0) {


            //nested for loop that checks if the shape has collided
            for (int row = 0; row < coord.length; row++) {  //moving y direction

                for (int col = 0; col < coord[0].length; col++) { //moving x direction

                    if(coord[row][col] !=0){ //if the current coordinate value is not 0 go here

                        if(board.getBoard()[y + row ][x + col + deltaX] != 0){ //if the following value in the board is not a 0 collision happens
                            checkingX = false; //if there is a shape there already we are prohibiting the side movement tot that position
                        }

                    }

                }

            }

            if(checkingX) { //checking to see if were allowed to move to that position
                x += deltaX; //the current x position becomes itself plus the change in x
            }
        }


        if(y + coord.length + 1 <= 25){ //if statement that bounds the shape to stop falling


            //nested for loop that checks if the shape has collided
            for (int row = 0; row < coord.length; row++) {  //moving y direction

                for (int col = 0; col < coord[0].length; col++) { //moving x direction

                    if(coord[row][col] !=0){ //if the current coordinate value is not 0 go here

                        if(board.getBoard()[y + row + 1][x + col] != 0){ //if the following value in the board is not a 0 collision happens
                            collision = true; //collision in the y direction becomes true
                        }

                    }

                }

            }



            if (time > currentSpeed) { //if the time is greater the normal speed move the shape down
                y++;
                time = 0; // re setting time to 0
            }

        }
        else{ //if shape cant fall anymore collision becomes true
            collision = true;
        }

        deltaX = 0; //change in x is reseated to 0
        checkingX = true; //enabling the movement to the sides

    }

    private void chekingLine(){

        int height = board.getBoard().length -1 ; //setting variable height

        for (int i = height; i >  0; i--) { //i starts at the bottom and moves to the top of the array

            int count = 0;  //variable count that determines if we've completed a line

            for (int j = 0; j < board.getBoard()[0].length; j++) {  //j moves along the row

                if(board.getBoard()[i][j] != 0){ //if the element in that certain section of the board is not 0 increase count

                    count++;
                }

                board.getBoard()[height][j] = board.getBoard()[i][j]; //the values of the board at the bottom move or not depending if the height was decreased
                                                                        //if the height wasn't deceased the tiles move down
                                                                        // if the height was decreased the tiles stay in the spot
                                                                        //the height needs to move along with the i to not remove tiles
            }

            if(count < board.getBoard()[0].length){ //if the count is less meaning there is no complete line
                                                    //subtract the height and it moves along with i
                height--;
            }

        }

    }

    public void render(Graphics g){

        //double for loop that draws the shapes
        for (int row = 0; row < coord.length; row++) { //first loop that determines the rows

            for (int col = 0; col < coord[row].length; col++) { //second loop that determines the columns

                if(coord[row][col] == 1){ //if condition determining to draw

                    g.drawImage(block, col * board.getBlocksSize() + x * board.getBlocksSize(),
                            row * board.getBlocksSize() + y * board.getBlocksSize(), null);

                }
            }
            
        }
        
    }
    public void setDeltaX(int deltaX){ //method that gets the change in x
        this.deltaX = deltaX;
    }

    public void speedingUp(){ //method that dictates the shape to move faster down

        currentSpeed = fastSpeed;
    }
    public void normalSpeed(){ //method that dictates the shape to slow down
        currentSpeed = normalSpeed;
    }

    public void rotate(){


        if(collision){ //if a collision happens do not rotate
            return;
        }

        int[][] rotatedMatrix = null; //creating blank matrix

        rotatedMatrix = getReverse(getTranspose(coord)); //setting blank matrix as the reverse of the transverse of the current matrix

        if(x + rotatedMatrix[0].length > 10 || y + rotatedMatrix.length > 25){ //if statement that checks if the rotation is out of bound

            return;
        }


        for (int row = 0; row < rotatedMatrix.length ; row++) {

            for (int col = 0; col < rotatedMatrix[0].length; col++) {

                if(board.getBoard()[y + row][x + col ] != 0){
                    return;
                }

            }

        }

        coord = rotatedMatrix; //setting the current matrix as the new rotated matrix





    }

    private int[][] getTranspose(int[][] matrix){ //method that gets the transpose of the shape


        //new matrix that has the number of rows to be the same as the number of columns in the given matrix
        //and the number of columns to be the number of row from the old matrix
        int[][] transposeMatrix = new int[matrix[0].length][matrix.length];

        for (int i = 0; i < matrix.length; i++) { //nested for loops that go through the given matrix

            for (int j = 0; j < matrix[0].length ; j++) {

                transposeMatrix[j][i] = matrix[i][j]; //setting each value of old matrix to the transverse version of the new transverse matrix

            }

        }

        return transposeMatrix;

    }

    private int[][] getReverse(int[][] matrix){ //method that reverses the transpose of the shape

        int middle = matrix.length / 2; //getting middle of matrix

        for (int i = 0; i < middle; i++) { //for loop that reverses the matrix

            int [] m = matrix[i]; //storing the first row temporally

            matrix[i] = matrix[matrix.length - i -1]; //setting first row as the last row

            matrix[matrix.length - i -1] = m; //setting the last row as the first row
        }

        return matrix;
    }

    public void deepCopy(Shape copiedShape){ //deep copy method

        this.block = copiedShape.block;
        this.coord = copiedShape.coord;
        this.board = copiedShape.board;
        this.identifyer = copiedShape.identifyer;
    }
    public int [][] getCoord(){
        return this.coord;
    }

}
