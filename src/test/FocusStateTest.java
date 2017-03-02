package test;

import Assign2.FocusState;
import org.junit.Test;

/**
 * Created by Max on 3/1/2017.
 */
public class FocusStateTest {

    FocusState fs2, fs4;

    @org.junit.Before
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
}
