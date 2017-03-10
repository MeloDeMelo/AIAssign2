package Assign2;

import java.util.ArrayList;
import java.util.Scanner;
import Assign2.FocusPlayer.Heuristic;

/**
 * Created by Max on 3/8/2017.
 */
public class FocusGame {

    private Scanner in;

    public FocusGame(){
        in = new Scanner(System.in);
    }

    private String getStringInput() {
        String response;
        while(!in.hasNextLine()) {
            in.next();
        }
        response = in.nextLine();
        return response;
    }

    public ArrayList<FocusPlayer> askAboutPlayer(boolean twoPlayers){
        int playerNum = (twoPlayers)? 2 : 4, depth;
        boolean validResponse, computer;
        ArrayList<FocusPlayer> players = new ArrayList<>();
        Heuristic heuristic = null;
        for(int i = 0; i < playerNum; i ++){
            System.out.println("Is player " + (i+1) + " a computer?");
            computer = getYesorNo();
            if(computer){
                System.out.println("How deep would you like this computer to look?");
                while(!in.hasNextInt()) {
                    in.next();
                }
                depth = in.nextInt();

                validResponse = false;
                System.out.println("Which Heuristic would you like the computer to use?");
                int responseInt;
                while (!validResponse) {
                    int k = 0;
                    for (Heuristic p : Heuristic.values()) {
                        System.out.println("\t" + k + ": " + p);
                        k ++;
                    }
                    while(!in.hasNextInt()) {
                        in.next();
                    }
                    responseInt = in.nextInt();
                    if ((responseInt <= Heuristic.values().length + 1) && (responseInt > -1)) {
                        heuristic = Heuristic.values()[responseInt];
                        validResponse = true;
                    }
                }
                players.add(new FocusPlayer(FocusState.Teams.values()[i], heuristic, depth));
            }
            else{
                players.add(new FocusPlayer(FocusState.Teams.values()[i]));
            }
        }
        return players;
    }

    public boolean getYesorNo(){
        boolean validResponse = false, answer = false;
        String response;
        while (!validResponse) {
            response = getStringInput();
            if (response.toLowerCase().equals("yes")) {
                answer = true;
                validResponse = true;
            } else if (response.toLowerCase().equals("no")) {
                answer = false;
                validResponse = true;
            }
        }
        return answer;
    }

    public static void main(String[] args){
        ArrayList<FocusPlayer> players = new ArrayList<>();
        FocusPlayer winningPlayer = null;
        FocusNode currNode, playerMove;
        boolean twoPlayers = true, validResponse, gameWon = false, fivePieceWin;
        String response;
        FocusGame fG = new FocusGame();

        System.out.println("Would you like to play with 5 pieces captured to win?");
        fivePieceWin = fG.getYesorNo();

        System.out.println("How many players would you like to play (2 or 4)?");
        validResponse = false;
        while (!validResponse) {
            response = fG.getStringInput();
            if ((response.equals("2")) || (response.toLowerCase().equals("two"))) {
                twoPlayers = true;
                validResponse = true;
            } else if ((response.equals("4")) || (response.toLowerCase().equals("four"))) {
                twoPlayers = false;
                validResponse = true;
            }
        }

        players.addAll(fG.askAboutPlayer(twoPlayers));
        currNode = new FocusNode(new FocusState(twoPlayers));
        ArrayList<FocusPlayer> playersLost = new ArrayList<>();

        while(!gameWon){
            for(FocusPlayer player : players){
                if(playersLost.contains(player))
                    continue;
                System.out.println(currNode.getState());
                playerMove = player.play(currNode.getState());
                if(playerMove == null){
                    System.out.println("This player could no longer play");
                    playersLost.add(player);
                    continue;
                }
                playerMove.setParentNode(currNode);
                player.addCaptured(playerMove.getCaptured());
                if(player.computerPlayer()){
                    System.out.println("computer " + player.getTeam() + ": has captured " + player.getCapturedPieces() + " piece('s)");
                    System.out.println("computer " + player.getTeam() + ": has moved (" + playerMove.getStartX() + ", " + playerMove.getStartY() + ") " + playerMove.getDistance());
                    System.out.println("computer " + player.getTeam() + ": moved " + playerMove.getNumber() + " piece('s) in the direction: " + playerMove.getDirection());
                }
                else if(playerMove.getCaptured() > 0)
                    System.out.println("You have captured " + playerMove.getCaptured() + " piece('s).");
                if((playerMove.getState().checkWin()) || ((fivePieceWin) && (player.getCapturedPieces() >= 5)) || (playersLost.size() == 3)) {
                    gameWon = true;
                    winningPlayer = player;
                    break;
                }
                System.out.println();
                currNode = new FocusNode(playerMove);
            }
        }

        System.out.println("Congrats " + winningPlayer.getTeam() + ", you won!");
    }
}
