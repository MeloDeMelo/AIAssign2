package Assign2;


import static Assign2.FocusPlayer.Heuristic.*;

/**
 * Created by Max on 3/6/2017.
 */
public class FocusPlayer {
    private boolean computer;
    private int capturedPieces;
    private int lookDepth;
    private Heuristic heuristic;

    public FocusPlayer(){
        this.computer = false;
        this.lookDepth = 0;
        this.heuristic = null;
        this.capturedPieces = 0;
    }

    public FocusPlayer(Heuristic heuristic, int lookDepth){
        this.computer = true;
        this.capturedPieces = 0;
        this.lookDepth = lookDepth;
        this.heuristic = heuristic;
    }

    public FocusNode play(FocusState currState){
        if(computer){
            return computerPlay(currState);
        }
        else{
            return humanPlay(currState);
        }
    }

    private FocusNode computerPlay(FocusState currState){

    }

    private FocusNode humanPlay(FocusState currState){

    }

    public int getCapturedPieces(){
        return capturedPieces;
    }

    public enum Heuristic{
        First,
        Second,
        third,
        fourth
    }
}
