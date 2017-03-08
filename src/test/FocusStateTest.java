package test;

import Assign2.FocusNode;
import Assign2.FocusState;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Max on 3/1/2017.
 */
public class FocusStateTest {

    FocusState fs2, fs4;

    @Before
    public void setUp() throws Exception {
        fs2 = new FocusState(true);
        fs4 = new FocusState(false);
    }

    @Test
    public void twoPlayerinit(){
        System.out.println(fs2);
    }

    @Test
    public void fourPlayerinit(){
        System.out.println(fs4);
    }

    @Test
    public void move(){
        System.out.println(fs4);
        int captured = fs4.move(3,3,1,1, 1,FocusState.Teams.Green);
        System.out.println(fs4);
        assertEquals(0, captured);
        captured = fs4.move(3,2,2,2, 3,FocusState.Teams.Green);
        System.out.println(fs4);
        assertEquals(0, captured);
        captured = fs4.move(5,2,3,3, 4,FocusState.Teams.Green);
        System.out.println(fs4);
        assertEquals(0, captured);
        captured = fs4.move(2,2,4,4, 2,FocusState.Teams.Green);
        System.out.println(fs4);
        assertEquals(0, captured);
        captured = fs4.move(2,6,5,5, 1,FocusState.Teams.Green);
        System.out.println(fs4);
        assertEquals(1, captured);
        captured = fs4.move(2,1,5,1, 1,FocusState.Teams.Green);
        System.out.println(fs4);
        captured = fs4.move(2,0,3,1, 2,FocusState.Teams.Green);
        System.out.println(fs4);
    }

    @Test
    public void getPossibleMoves(){
        for(FocusNode node : fs2.getPossibleMoves(FocusState.Teams.Green)){
            System.out.println(node);
        }
        System.out.println("There are " + fs2.getPossibleMoves(FocusState.Teams.Green).size() + " possible nodes");
    }
}
