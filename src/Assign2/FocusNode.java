package Assign2;
import Assign2.FocusState.Teams;

/**
 * Created by Max on 3/4/2017.
 */
public class FocusNode {
    private int startX, startY;
    private int direction, number, distance;
    private Teams team;
    private FocusState state;
    private int captured;

    public FocusNode(int startX, int startY, int number, int distance, int direction, Teams team){
        this.startX = startX;
        this.startY = startY;
        this.direction = direction;
        this.distance = distance;
        this.number = number;
        this.team = team;
        this.captured = 0;
    }

    public FocusNode(FocusState state){
        this.state = state;
        this.startX = -1;
        this.startY = -1;
        this.captured = 0;
    }

    public FocusNode(FocusNode node) {
        this.state = node.getState();
        this.startX = node.getStartX();
        this.startY = node.getStartY();
        this.direction = node.getDirection();
        this.distance = node.getDistance();
        this.number = node.getNumber();
        this.captured = node.getCaptured();
    }

    public void setParentNode(FocusNode parent){
        this.state = new FocusState(parent.getState());
        captured = state.move(startX,startY,number,distance,direction,team);
    }

    public FocusState getState(){
        return state;
    }

    public int getCaptured(){
        return captured;
    }

    public int getStartX(){
        return startX;
    }

    public int getStartY(){
        return startY;
    }

    public int getDistance(){
        return distance;
    }

    public int getDirection(){
        return direction;
    }

    public int getNumber(){
        return number;
    }

    public String toString(){
        return "X: " + startX + ", Y: " + startY + ", Direction: "
                + direction + ", distance: " + distance + ", number: " + number;
    }
}
