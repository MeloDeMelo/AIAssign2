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
        int playerNum = (twoPlayers)? 2 : 4, depth = 0;
        boolean validResponse = false, computer = false;
        String response;
        ArrayList<FocusPlayer> players = new ArrayList<>();
        Heuristic heuristic = null;
        for(int i = 0; i < playerNum; i ++){
            validResponse = false;
            System.out.println("Is player " + (i+1) + " a computer?");
            while (!validResponse) {
                response = getStringInput();
                if (response.toLowerCase().equals("yes")) {
                    computer = true;
                    validResponse = true;
                } else if (response.toLowerCase().equals("no")) {
                    computer = false;
                    validResponse = true;
                }
            }
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

    public static void main(String[] args){
        ArrayList<FocusPlayer> players = new ArrayList<>();
        FocusPlayer winningPlayer = null;
        FocusNode currNode, playerMove;
        boolean twoPlayers = true, validResponse, gameWon = false;
        String response;
        FocusGame fG = new FocusGame();

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

        while(!gameWon){
            for(FocusPlayer player : players){
                System.out.println(currNode.getState());
                playerMove = player.play(currNode.getState());
                playerMove.setParentNode(currNode);
                player.addCaptured(playerMove.getCaptured());
                if(playerMove.getCaptured() > 0)
                    System.out.println("You have captured " + playerMove.getCaptured() + " pieces.");
                if((playerMove.getState().checkWin(player.getTeam())) || (player.getCapturedPieces() >= 5)) {
                    gameWon = true;
                    winningPlayer = player;
                    break;
                }
                System.out.println();
                currNode = playerMove;
            }
        }

        System.out.println("Congrats " + winningPlayer.getTeam() + ", you won!");
    }
}
