package Assign2;

import java.util.ArrayList;
import java.util.Scanner;

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
        for(int i = 0; i < playerNum; i ++){
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
                
            }
            else{
                players.add(new FocusPlayer(FocusState.Teams.values()[i]));
            }
        }
        return players;
    }

    public static void main(String[] args){
        ArrayList<FocusPlayer> players = new ArrayList<>();
        FocusNode currNode, playerMove;
        boolean twoPlayers = true, validResponse = false;
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
    }
}
