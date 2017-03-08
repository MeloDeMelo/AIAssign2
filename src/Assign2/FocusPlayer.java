package Assign2;


import static Assign2.FocusPlayer.Heuristic.*;
import Assign2.FocusState.Teams;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Max on 3/6/2017.
 */
public class FocusPlayer {
    private boolean computer;
    private Teams team;
    private int capturedPieces;
    private int lookDepth;
    private Heuristic heuristic;

    public FocusPlayer(Teams team){
        this.computer = false;
        this.lookDepth = 0;
        this.heuristic = null;
        this.capturedPieces = 0;
        this.team = team;
    }

    public FocusPlayer(Teams team, Heuristic heuristic, int lookDepth){
        this.computer = true;
        this.capturedPieces = 0;
        this.lookDepth = lookDepth;
        this.heuristic = heuristic;
        this.team = team;
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
        return null;
    }

    private FocusNode humanPlay(FocusState currState){
        ArrayList<Integer> input = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        boolean validResponse = false;
        int x = -1, y = -1 , number = 0, direction = 0, distance = 0;

        System.out.println("Please enter the coordinate of the stack you would like to move (separated by a space)");
        while(!validResponse){
            String response = getStringInput(in);
            for (String s : response.split("\\s")) {
                input.add(Integer.parseInt(s));
            }
            x = input.get(0);
            y = input.get(1);
            if((currState.withinBounds(x,y)) && (currState.getStackAtPosition(x,y).peek() == team))
                validResponse = true;
            else
                System.out.println("Invalid move");
        }

        validResponse = false;
        System.out.println("Please enter how many you would like to move, how far they will go and in what direction");
        System.out.println("1 is up, 2 is down, 3 is right and 4 is left");
        while(!validResponse){
            String response = getStringInput(in);
            for (String s : response.split("\\s")) {
                input.add(Integer.parseInt(s));
            }
            number = input.get(0);
            distance = input.get(1);
            direction = input.get(2);
            if((distance <= currState.getStackSize(x,y)) && (distance > 0)) {
                if((number <= currState.getStackSize(x,y)) && (number > 0)){
                    if(((direction == 1) && (currState.withinBounds(x, y - distance)))
                            || ((direction == 2) && (currState.withinBounds(x, y + distance)))
                            || ((direction == 3) && (currState.withinBounds(x + distance, y)))
                            || ((direction == 4) && (currState.withinBounds(x - distance, y))))
                        validResponse = true;
                }
            }
            if(!validResponse)
                System.out.println("Invalid move");
        }

        return new FocusNode(x, y, number, distance, direction, team);
    }

    private String getStringInput(Scanner in) {
        String response;
        while(!in.hasNextLine()) {
            in.next();
        }
        response = in.nextLine();
        return response;
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
