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
    FocusPlayer human;
    FocusNode fn;

    @Before
    public void setUp() throws Exception{
        fs = new FocusState(true);
        human = new FocusPlayer(FocusState.Teams.Green);
        fn = null;
    }

    @Test
    public void humanMove(){
        System.out.println(fs);
        fn = human.play(fs);
        System.out.println(fn);
    }

}
