import org.apache.commons.math3.fraction.BigFraction;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: aindrias
 * Date: 21.05.13
 * Time: 13:05
 * To change this template use File | Settings | File Templates.
 */
public class Utils {
    public static boolean equivalent(List<DeltaTuple> first, List<DeltaTuple> second) {
        //TODO
        return true;
    }

    public static boolean isPowerOfTwo(long x) {
        return (x & (x - 1)) == 0;
    }

    public static boolean isPowerOrTwo(BigFraction fraction) {
        if (fraction.compareTo(BigFraction.ONE) == 1) {
            return (fraction.getDenominatorAsInt() == 1) && Utils.isPowerOfTwo(fraction.getNumeratorAsLong());
        } else {
            return (fraction.getNumeratorAsInt() == 1) && Utils.isPowerOfTwo(fraction.getDenominatorAsLong());
        }
    }

    static BigFraction slope(Node prevNode, Node nextNode) {
        return nextNode.getHeight().subtract(prevNode.getHeight())
                .divide(nextNode.getWidth().subtract(prevNode.getWidth()));
    }

    static BigFraction slopeAfter(List<Node> nodes, int index) {
        Node prevNode = nodes.get(index);
        Node nextNode = nodes.get(index + 1);

        return slope(prevNode, nextNode);
    }

    public static Node calculateXYIntersectedNode(Node prevNode, Node nextNode) {
        if (prevNode.getSignature() == nextNode.getSignature() || prevNode.getSignature() == Signature.MIDDLE
                || nextNode.getSignature() == Signature.MIDDLE) {
            throw new IllegalArgumentException("Nodes should lay on the different sides of y=x, but instead node1 is " + prevNode +
                    " and node2 is " + nextNode);
        }

        BigFraction x = (
                (prevNode.getWidth().subtract(prevNode.getHeight()))
                        .divide(
                                slope(prevNode, nextNode).subtract(BigFraction.ONE)
                        )
        ).add(prevNode.getWidth());
        return new Node(x, x);

    }
}
