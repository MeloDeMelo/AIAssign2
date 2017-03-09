package Assign2;


import Assign2.FocusState.Teams;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

/**
 * Created by Max on 3/6/2017.
 */
public class FocusPlayer {
    private boolean computer;
    private Teams team;
    private Teams[] notTeams;
    private int capturedPieces;
    private int lookDepth;
    private Heuristic heuristic;

    public FocusPlayer(Teams team){
        this.computer = false;
        this.lookDepth = 0;
        this.heuristic = null;
        this.capturedPieces = 0;
        this.team = team;
        notTeams = null;
    }

    public FocusPlayer(Teams team, Heuristic heuristic, int lookDepth){
        this.computer = true;
        this.capturedPieces = 0;
        this.lookDepth = lookDepth;
        this.heuristic = heuristic;
        this.team = team;
        decideNotTeams();
    }

    private void decideNotTeams(){
        int teamValue = -1;
        notTeams = new Teams[3];
        for (int i = 0; i < 4; i ++){
            if(Teams.values()[i] == team)
                teamValue = i;
        }
        teamValue++;
        for (int i = 0; i < 3; i++){
            notTeams[i] = Teams.values()[teamValue];
            if(teamValue + 1 >= 4)
                teamValue = 0;
            else
                teamValue++;
        }
    }

    public Teams[] getNotTeams(){
        return notTeams;
    }

    private int calculateHeuristic(FocusNode node){
        switch(heuristic){
            case First:
                return firstHeuristic(node);
            case Second:
                return 0;
            case third:
                return 0;
            case fourth:
                return 0;
            default:
                return -1;
        }
    }

    private int firstHeuristic(FocusNode node){
        int stacksControlled = 0;
        for(int i = 0; i < 8; i ++){
            for(int k = 0; k < 8; k ++){
                if(node.getState().withinBounds(k, i)) {
                    if (!node.getState().getStackAtPosition(k, i).isEmpty()) {
                        if (node.getState().getStackAtPosition(k, i).peek() == team)
                            stacksControlled++;
                    }
                }
            }
        }
        return stacksControlled;
    }

    public FocusNode play(FocusState currState){
        if(computer){
            return computerPlay(currState);
        }
        else{
            return humanPlay(currState);
        }
    }

    private FocusNode computerPlay(FocusState initialState){
        System.out.println("Computer " + team + ": thinking...");
        FocusNode finalDecision = null;
        int bestStrength = maxValue(new FocusNode(initialState), Integer.MIN_VALUE, Integer.MAX_VALUE, 1);
        for(FocusNode node : getPossibleMoves(team, new FocusNode(initialState))){
            if(calculateHeuristic(node) == bestStrength)
                finalDecision = node;
            else if(finalDecision == null)
                finalDecision = node;
        }
        return finalDecision;
    }

    private ArrayList<FocusNode> getPossibleMoves(Teams team, FocusNode parent){
        ArrayList<FocusNode> moves = parent.getState().getPossibleMoves(team);
        for(FocusNode node : moves){
            node.setParentNode(parent);
        }
        Collections.shuffle(moves);
        return moves;
    }

    private int maxValue(FocusNode state, int alpha, int beta, int currDepth){
        if((currDepth > lookDepth) || (state.getState().checkWin()))
            return calculateHeuristic(state);
        int bestValue = Integer.MIN_VALUE;
        ArrayList<FocusNode> possibleMoves = getPossibleMoves(team, state);
        for(FocusNode node : possibleMoves){
            bestValue = Math.max(bestValue, minValue(node, alpha, beta, currDepth+1));
            if(bestValue >= beta)
                return bestValue;
            alpha = Math.max(alpha, bestValue);
        }
        return bestValue;
    }

    private int minValue(FocusNode state, int alpha, int beta, int currDepth){
        if((currDepth > lookDepth) || (state.getState().checkWin()))
            return calculateHeuristic(state);
        int bestValue = Integer.MAX_VALUE;
        Teams enemyTeam;
        boolean nextMax;
        if(state.getState().getTwoPlayer()) {
            enemyTeam = (team == Teams.Red) ? Teams.Green : Teams.Red;
            nextMax = true;
        }
        else{
            if(currDepth % 4 == 2){
                enemyTeam = notTeams[0];
                nextMax = false;
            }
            else if(currDepth % 4 == 3){
                enemyTeam = notTeams[1];
                nextMax = false;
            }
            else{
                enemyTeam = notTeams[2];
                nextMax = true;
            }
        }
        ArrayList<FocusNode> possibleMoves = getPossibleMoves(enemyTeam, state);
        for(FocusNode node : possibleMoves){
            if(nextMax)
                bestValue = Math.min(bestValue, maxValue(node, alpha, beta, currDepth+1));
            else
                bestValue = Math.min(bestValue, minValue(node, alpha, beta, currDepth+1));
            if(bestValue <= alpha)
                return bestValue;
            beta = Math.min(beta, bestValue);
        }
        return bestValue;
    }

    private FocusNode humanPlay(FocusState currState){
        ArrayList<Integer> input;
        Scanner in = new Scanner(System.in);
        boolean validResponse = false;
        int x = -1, y = -1 , number = 0, direction = 0, distance = 0;

        System.out.println("Please enter the coordinate of the stack you would like to move (separated by a space)");
        System.out.println("You are colour: " + team + ", you have captured " + capturedPieces + " pieces.");
        while(!validResponse){
            input = new ArrayList<>();
            String response = getStringInput(in);
            for (String s : response.split("\\s")) {
                input.add(Integer.parseInt(s));
            }
            if(input.size() >= 2) {
                x = input.get(0);
                y = input.get(1);
                if((currState.withinBounds(x,y)) && (currState.getStackAtPosition(x,y).peek() == team))
                    validResponse = true;
            }
            if(!validResponse)
                System.out.println("Invalid move");
        }

        validResponse = false;
        System.out.println("Please enter how many you would like to move, how far they will go and in what direction");
        System.out.println("1 is up, 2 is down, 3 is right and 4 is left");
        while(!validResponse){
            input = new ArrayList<>();
            String response2 = getStringInput(in);
            for (String s : response2.split("\\s")) {
                input.add(Integer.parseInt(s));
            }
            if(input.size() >= 3) {
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

    public Teams getTeam(){
        return team;
    }

    public int getCapturedPieces(){
        return capturedPieces;
    }

    public void addCaptured(int captured){
        capturedPieces += captured;
    }

    public boolean computerPlayer(){
        return computer;
    }

    public enum Heuristic{
        First,
        Second,
        third,
        fourth
    }
}
