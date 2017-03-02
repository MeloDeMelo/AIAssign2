package Assign2;

import java.util.Random;
import java.util.Stack;

/**
 * Created by Max on 3/1/2017.
 */
public class FocusState {

    Stack<Teams>[][] board;
    boolean twoPlayer;
    Random random;

    public FocusState(boolean twoPlayer){
        random = new Random();
        board = new Stack[8][8];
        init();
        this.twoPlayer = twoPlayer;
        if(twoPlayer)
            twoPlayerinit();
        else
            fourPlayerinit();
    }

    public FocusState(FocusState fs){
        this.twoPlayer = fs.getTwoPlayer();

        for(int i = 0; i < 8; i ++){
            for (int k = 0; k < 8; k++){
                this.board[i][k] = fs.getStackAtPosition(i,k);
            }
        }
    }

    private void init(){
        for(int i = 0; i < 8; i++){
            for(int k = 0; k < 8; k ++){
                board[i][k] = new Stack<Teams>();
            }
        }
    }

    private void twoPlayerinit(){
        int player1Counter = 0, player2Counter = 0;
        for(int i = 1; i < 7; i ++){
            for (int k = 1; k < 7; k++){
                if((random.nextBoolean()) && (player1Counter < 18)){
                    player1Counter ++;
                    board[i][k].push(Teams.Red);
                }
                else if (player2Counter < 18){
                    player2Counter ++;
                    board[i][k].push(Teams.Green);
                }
                else{
                    board[i][k].push(Teams.Red);
                }
            }
        }
    }

    private void fourPlayerinit(){
        fourHorizontal(0,0,Teams.Blue);
        fourHorizontal(0,1,Teams.Green);
        fourHorizontal(0,2,Teams.Blue);
        fourHorizontal(0,3,Teams.Green);

        fourVerticle(4,0,Teams.Red);
        fourVerticle(5,0,Teams.Green);
        fourVerticle(6,0,Teams.Red);
        fourVerticle(7,0,Teams.Green);

        fourHorizontal(4,4,Teams.Yellow);
        fourHorizontal(4,5,Teams.Red);
        fourHorizontal(4,6,Teams.Yellow);
        fourHorizontal(4,7,Teams.Red);

        fourVerticle(4,4,Teams.Yellow);
        fourVerticle(5,4,Teams.Blue);
        fourVerticle(6,4,Teams.Yellow);
        fourVerticle(7,4,Teams.Blue);
    }

    private void fourVerticle(int x, int y, Teams team){
        for(int i = 0; i < 4; i ++){
            if(withinBounds(x, y+i))
                board[x][y+i].push(team);
        }
    }

    private void fourHorizontal(int x, int y, Teams team){
        for(int i = 0; i < 4; i ++){
            if(withinBounds(x+i, y))
                board[x+i][y].push(team);
        }
    }

    private boolean withinBounds(int x, int y){
        if((y == 0) && ( (x == 0) || (x == 1) || (x == 6) || (x == 7) ))
            return false;
        else if((y == 1) && ( (x == 0) || (x == 7) ))
            return false;
        else if((y == 6) && ( (x == 0) || (x == 7) ))
            return false;
        else if((y == 7) && ( (x == 0) || (x == 1) || (x == 6) || (x == 7) ))
            return false;
        else if( (x > -1) && (x < 8) && (y > -1) && (y < 8) )
            return true;
        else
            return false;
    }

    public void move(){

    }

    //public ArrayList<Assign2.FocusState> getPossibleMoves(){

    //}

    //public int checkWin(){

    //}

    public String toString(){
        String bored = "";
        int x = 0, y = 0;
        for(int i = 0; i < 15; i ++){
            for (int k = 0; k < 40; k ++){
                if(i%2 == 0){
                    if(k%5 == 0){
                        bored += "|";
                    }else{
                        if(k%5 == 2)
                            if(withinBounds(x,y))
                                bored += getStackSize(x,y);
                            else
                                bored += "X";
                        if(k%5 == 3) {
                            if(withinBounds(x,y)) {
                                if (getStackSize(x, y) == 0)
                                    bored += " ";
                                else
                                    bored += getStackAtPosition(x, y).peek();
                            }
                            x ++;
                        }
                        else
                            bored += " ";
                    }
                }else{
                    if(k%5 == 0){
                        bored += " ";
                    }
                    else{
                        bored += "-";
                    }
                }
            }
            y++;
            x = 0;
            bored += "\n";
        }
        return bored;
    }

    public boolean getTwoPlayer(){
        return twoPlayer;
    }

    public Stack<Teams>[][] getBoard(){
        return board;
    }

    public int getStackSize(int x, int y){
        return board[x][y].size();
    }

    public Stack<Teams> getStackAtPosition(int x, int y){
        return (Stack<Teams>) board[x][y].clone();
    }

    public enum Teams{
        Red("R"),
        Green("G"),
        Yellow("Y"),
        Blue("B");

        String icon;
        Teams(String icon){
            this.icon = icon;
        }
        public String toString(){
            return icon;
        }
    }
}
