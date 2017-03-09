package test;

import Assign2.FocusNode;
import Assign2.FocusPlayer;
import Assign2.FocusState;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Max on 3/8/2017.
 */
public class FocusPlayerTest {

    FocusState fs;
    FocusPlayer human, comp;
    FocusNode fn;

    @Before
    public void setUp() throws Exception{
        fs = new FocusState(true);
        human = new FocusPlayer(FocusState.Teams.Green);
        comp = new FocusPlayer(FocusState.Teams.Red, FocusPlayer.Heuristic.First, 1);
        fn = null;
    }

    @Test
    public void humanMove(){
        System.out.println(fs);
        fn = human.play(fs);
        System.out.println(fn);
    }

    @Test
    public void decideNotTeams(){
        System.out.println(comp.getNotTeams()[0]);
        System.out.println(comp.getNotTeams()[1]);
        System.out.println(comp.getNotTeams()[2]);
    }

}
