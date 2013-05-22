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
    private BumpDomain inversed;
    private BumpDomain simpleBumpDomainToTest;

    @Before
    public void setUp() throws Exception {
        ArrayList<Node> nodes = new ArrayList<Node>();
        nodes.add(new Node(new BigFraction(0, 16), new BigFraction(0, 16)));
        nodes.add(new Node(new BigFraction(1, 16), new BigFraction(2, 16)));
        nodes.add(new Node(new BigFraction(3, 16), new BigFraction(6, 16)));
        nodes.add(new Node(new BigFraction(4, 16), new BigFraction(7, 16)));
        nodes.add(new Node(new BigFraction(6, 16), new BigFraction(8, 16)));
        nodes.add(new Node(new BigFraction(8, 16), new BigFraction(9, 16)));
        nodes.add(new Node(new BigFraction(10, 16), new BigFraction(13, 16)));
        nodes.add(new Node(new BigFraction(16, 16), new BigFraction(16, 16)));

        bumpDomainToTest = new ThompsonElement(nodes).getBumpDomainAt(0);

        inversed = bumpDomainToTest.inverse();


        ArrayList<Node> simpleNodes = new ArrayList<Node>();
        simpleNodes.add(new Node(new BigFraction(0, 4), new BigFraction(0, 4)));
        simpleNodes.add(new Node(new BigFraction(1, 4), new BigFraction(2, 4)));
        simpleNodes.add(new Node(new BigFraction(2, 4), new BigFraction(3, 4)));
        simpleNodes.add(new Node(new BigFraction(4, 4), new BigFraction(4, 4)));
        simpleBumpDomainToTest = new BumpDomain(simpleNodes);

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

    @Test
    public void testInverse() throws Exception {

        assertEquals(new BigFraction(1, 32),
                inversed.function(new BigFraction(1, 16)));

        assertEquals(new BigFraction(6, 16),
                inversed.function(new BigFraction(8, 16)));

        assertEquals(new BigFraction(16, 16),
                inversed.function(new BigFraction(16, 16)));
    }

    @Test
    public void testGetInitialSlope() throws Exception {
        assertEquals(1, bumpDomainToTest.getSignature());
        assertEquals(-1, bumpDomainToTest.inverse().getSignature());

        assertEquals(BigFraction.TWO, bumpDomainToTest.getInitialSlope());
        assertEquals(BigFraction.ONE_HALF, bumpDomainToTest.inverse().getInitialSlope());
    }

    @Test
    public void testLastFirstSegments() throws Exception {
        assertEquals(new BigFraction(3, 16), bumpDomainToTest.getFirstSegmentXEnd());
        assertEquals(new BigFraction(10, 16), bumpDomainToTest.getLastSegmentXStart());


        assertEquals(new BigFraction(6, 16), inversed.getFirstSegmentXEnd());
        assertEquals(new BigFraction(13, 16), inversed.getLastSegmentXStart());
    }

    @Test
    public void testPhi() throws Exception {
        assertEquals(new BigFraction(2, 1), bumpDomainToTest.phi(new BigFraction(3, 16)));
        assertEquals(bumpDomainToTest.phi(new BigFraction(9, 16)), bumpDomainToTest.phi(new BigFraction(3, 16)));


        assertEquals(new BigFraction(1, 1), simpleBumpDomainToTest.phi(new BigFraction(3, 16)));
    }

    @Test
    public void testPAsterisk() throws Exception {
        assertEquals(new BigFraction(3, 16), bumpDomainToTest.getPAsterisk());


        assertEquals(new BigFraction(1, 4), simpleBumpDomainToTest.getPAsterisk());
    }

    @Test
    public void testR() throws Exception {
        assertEquals(new BigFraction(3, 32), bumpDomainToTest.getR());


        assertEquals(new BigFraction(1, 8), simpleBumpDomainToTest.getR());
    }

    @Test
    public void testIsPowerOfTwo() throws Exception {
        assertEquals(true, Utils.isPowerOrTwo(new BigFraction(1, 2)));
        assertEquals(false, Utils.isPowerOrTwo(new BigFraction(5, 2)));
        assertEquals(true, Utils.isPowerOrTwo(new BigFraction(1, 1024)));
        assertEquals(true, Utils.isPowerOrTwo(new BigFraction(512, 1024)));
        assertEquals(false, Utils.isPowerOrTwo(new BigFraction(513, 1024)));
        assertEquals(true, Utils.isPowerOrTwo(new BigFraction(512, 1)));

        assertEquals(new Node(new BigFraction(7, 3), new BigFraction(7, 3)),
                Utils.calculateXYIntersectedNode(
                        new Node(new BigFraction(2, 1), new BigFraction(1, 1))
                        ,
                        new Node(new BigFraction(3, 1), new BigFraction(5, 1))
                ));
    }

    @Test
    public void testGetPsiFunction() throws Exception {
        assertEquals(new BigFraction(3, 32), bumpDomainToTest.getPsiFunction());
    }

    @Test
    public void testPsi() throws Exception {
        assertEquals(new BigFraction(2, 1), bumpDomainToTest.getPsiFunction());

//        assertEquals(new BigFraction(1, 1), simpleBumpDomainToTest.phi(new BigFraction(3, 16)));
    }
}
