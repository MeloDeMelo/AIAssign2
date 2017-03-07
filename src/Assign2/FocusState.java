package Assign2;

import java.util.ArrayList;
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
                board[i][k] = new Stack<>();
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

        fourVerticle(0,4,Teams.Yellow);
        fourVerticle(1,4,Teams.Blue);
        fourVerticle(2,4,Teams.Yellow);
        fourVerticle(3,4,Teams.Blue);
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

    public int move(int x, int y, int number, int distance, int direction, Teams team){
        if((!getStackAtPosition(x,y).isEmpty()) && (number <= getStackSize(x,y))) {
            if ((withinBounds(x, y)) && (getStackAtPosition(x, y).peek() == team)) {
                switch (direction) {
                    case 1: //up
                        if (withinBounds(x, y - distance)) {
                            return pushPop(x, y, x, y - distance, distance, number);
                        }
                    case 2: //down
                        if (withinBounds(x, y + distance)) {
                            return pushPop(x, y, x, y + distance, distance, number);
                        }
                    case 3: //right
                        if (withinBounds(x + distance, y)) {
                            return pushPop(x, y, x + distance, y, distance, number);
                        }
                    case 4: //left
                        if (withinBounds(x - distance, y)) {
                            return pushPop(x, y, x - distance, y, distance, number);
                        }
                }
            }
        }
        return -1;
    }

    private int pushPop(int startX, int startY, int endX, int endY, int distance, int number){
        Stack<Teams> inbetween = new Stack<>();
        Stack<Teams> captured = new Stack<>();
        int capturedNum;

        int size = getStackSize(endX,endY);
        if(size + number >= 5){
            for(int i = 0; i < size; i ++)
                captured.push(board[endX][endY].pop());
            for(int i = 0; i < 5-number; i ++)
                board[endX][endY].push(captured.pop());
        }

        capturedNum = captured.size();

        for(int i = 0; i < number; i ++)
            inbetween.push(board[startX][startY].pop());
        for(int i = 0; i < number; i ++)
            board[endX][endY].push(inbetween.pop());

        return capturedNum;
    }

    public ArrayList<FocusNode> getPossibleMoves(Teams team){
        ArrayList<FocusNode> possibleNodes = new ArrayList<>();
        for(int i = 0; i < 8; i ++){
            for (int k = 0; k < 8; k ++){
                if((withinBounds(k, i)) && (!getStackAtPosition(k,i).isEmpty()) && (getStackAtPosition(k,i).peek() == team)){
                    possibleNodes.addAll(movesAtPosition(k,i,team));
                }
            }
        }
        return possibleNodes;
    }

    private ArrayList<FocusNode> movesAtPosition(int x, int y, Teams team){
        ArrayList<FocusNode> possibleNodes = new ArrayList<>();
        int possibleDistance = getStackSize(x,y);
        for(int m = 1; m <= 4; m ++) {//direction
            for (int i = 1; i <= possibleDistance; i++) {//number
                for (int k = 1; k <= possibleDistance; k++) {//distance
                    possibleNodes.add(new FocusNode(x, y, m, i, k, team));
                }
            }
        }
        return possibleNodes;
    }

    public boolean checkWin(Teams team){
        for(int k = 0; k < 8; k ++){
            for (int i = 0; i < 8; i ++){
                if (withinBounds(i,k))
                    if (getStackAtPosition(i,k).peek() != team)
                        return false;
            }
        }
        return true;
    }

    public String toString(){
        String bored = "    (0) (1) (2) (3) (4) (5) (6) (7)\n";
        for(int k = 0; k < 8; k ++){
            for (int i = -1; i < 8; i ++){
                if(i == -1)
                    bored +=  "("+ k + ")";
                else if(!withinBounds(i,k))
                    bored += "    ";
                else if(getStackSize(i,k) == 0)
                    bored += " 0  ";
                else
                    bored += " " + getStackSize(i,k) + "" + getStackAtPosition(i,k).peek() + " ";
            }
            bored +="\n";
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
