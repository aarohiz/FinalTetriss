import info.gridworld.actor.*;
import info.gridworld.grid.*;
import java.util.ArrayList;
import java.awt.Color;
import info.gridworld.grid.Grid;
import info.gridworld.actor.Rock;
import info.gridworld.grid.BoundedGrid;
import java.util.Random;
public class TetrisGame {
    public static ActorWorld world = new ActorWorld(new BoundedGrid(19, 10));
    public static TetrisBlock currentBlock;
    public static int score;
    public static Grid<Actor> gr; 
    public static void main(String[] args) {
        
        nextTetrisBlock();
        //needed code for keyboard event handling
        java.awt.KeyboardFocusManager.getCurrentKeyboardFocusManager()
        .addKeyEventDispatcher(new java.awt.KeyEventDispatcher() {
                public boolean dispatchKeyEvent(java.awt.event.KeyEvent event) {
                    String key = javax.swing.KeyStroke.getKeyStrokeForEvent(event).toString();
                    if (key.equals("pressed UP"))
                        currentBlock.rotate();
                    if (key.equals("pressed RIGHT"))
                        currentBlock.moveRight();
                    if (key.equals("pressed DOWN"))
                        currentBlock.act();
                    if (key.equals("pressed LEFT"))
                        currentBlock.moveLeft();
                    world.show();
                    return true;
                }
            });
        world.show();
    }

    /**
     * Calls removeCompleteRows and chooses a new TetrisBlock at random
     */
    public static void nextTetrisBlock() {
        gr = TetrisGame.world.getGrid(); 
        removeCompleteRows();
        score +=3;
        if (gr.get(new Location(0, 5)) != null )
        { 
            javax.swing.JOptionPane.showMessageDialog(null, "Score: " 
                + TetrisGame.score, "GAME OVER!", 0); 
            System.exit(0); 
        } 
        TetrisBlock randomBlock = new TetrisBlock();
        //choose random block
        int randNum = (int)(Math.random()*4)+1; 
        
        switch(randNum){
            case 1:
            randomBlock = new TetrisBlockO();
            break;
            case 2:
            randomBlock = new TetrisBlockL();
            break;
            case 3:
            randomBlock = new TetrisBlockI();
            break;
            case 4:
            randomBlock = new TetrisBlockZ();
            break;
        }
        currentBlock = randomBlock;

    }

    /**
     * checks each row 1 through 18 (skip row 0) for full rows
     * if a row is full, then remove the actor from each cell in that row
     * and ask each actor located above the just deleted row to act and
     * update the score++
     */
    public static void removeCompleteRows() 
    {
        int columnsFilled = 0; 
        Grid grid = world.getGrid(); 
       
       Object x;
        //loops through rows only after each column has finished 
        for(int row = 18; row >= 0; row--) {  //needed >= 
            columnsFilled = 0;    //need to reinitialize this every iteration 
            for(int col = 0; col <= grid.getNumCols() - 1; col++) { //needed <= 

                if (grid.get(new Location(row,col)) != null) { 
                    columnsFilled++; 
                } 
            } 
            if (columnsFilled == 10) { 
                for(int col = 0; col <= grid.getNumCols(); col++)
                { 
                    if(grid.isValid(new Location(row,col)))
                    {
                        world.remove(new Location(row,col)); 
                    }

                } 
                columnsFilled =0; 
                score+=10;
                for(int test = row; test >= 0; test --)
                {
                    for (int colTest = 0; colTest <10; colTest ++)
                    {
                        if( grid.get(new Location (test, colTest)) != null){
                            x = grid.get(new Location (test, colTest));
                            grid.remove(new Location (test, colTest));
                            grid.put(new Location (test + 1, colTest), x);
                            
                } 
            } 

        }
    }
}
}
}