import org.apache.commons.math3.fraction.BigFraction;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: aindrias
 * Date: 21.05.13
 * Time: 19:44
 * To change this template use File | Settings | File Templates.
 */
public class BumpDomainTest {
    private BumpDomain bumpDomainToTest;

    @Before
    public void setUp() throws Exception {
        ArrayList<Node> nodes = new ArrayList<Node>();
        nodes.add(new Node(new BigFraction(0, 16), new BigFraction(0, 16)));
        nodes.add(new Node(new BigFraction(1, 16), new BigFraction(2, 16)));
        nodes.add(new Node(new BigFraction(3, 16), new BigFraction(6, 16)));
        nodes.add(new Node(new BigFraction(4, 16), new BigFraction(7, 16)));
        nodes.add(new Node(new BigFraction(6, 16), new BigFraction(8, 16)));
        nodes.add(new Node(new BigFraction(8, 16), new BigFraction(9, 16)));
        nodes.add(new Node(new BigFraction(10, 16), new BigFraction(15, 16)));
        nodes.add(new Node(new BigFraction(16, 16), new BigFraction(16, 16)));
        bumpDomainToTest = new BumpDomain(nodes);
    }

    @Test
    public void testFunctionX() throws Exception {
        assertEquals(new BigFraction(1, 16),
                bumpDomainToTest.function(new BigFraction(1, 32)));

        assertEquals(new BigFraction(8, 16),
                bumpDomainToTest.function(new BigFraction(6, 16)));

        assertEquals(new BigFraction(16, 16),
                bumpDomainToTest.inverse_function(new BigFraction(16, 16)));
    }

    @Test
    public void testInverse_functionX() throws Exception {
        assertEquals(new BigFraction(1, 32),
                bumpDomainToTest.inverse_function(new BigFraction(1, 16)));

        assertEquals(new BigFraction(6, 16),
                bumpDomainToTest.inverse_function(new BigFraction(8, 16)));

        assertEquals(new BigFraction(16, 16),
                bumpDomainToTest.inverse_function(new BigFraction(16, 16)));
    }

//    @Test
//    public void testGetInitialSlope() throws Exception {
//
//    }
}
