import org.apache.commons.math3.fraction.BigFraction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: aindrias
 * Date: 21.05.13
 * Time: 11:39
 * To change this template use File | Settings | File Templates.
 */
public class BumpDomain {
//    final List
//            <BigFraction> xs;
//
//    final List
//            <BigFraction> ys;

    final List
            <Node> nodes;

    //    public BumpDomain(List<BigFraction> xs, List<BigFraction> ys) {
//        this.xs = xs;
//        this.ys = ys;
//        if (!isValid()) {
//            throw new IllegalStateException("Not a valid bump domain.");
//        }
//    }
    public BumpDomain(List<Node> nodes) {
        this.nodes = nodes;
        validate();
    }

    BigFraction slopeAfter(int index) {
        Node prevNode = nodes.get(index);
        Node nextNode = nodes.get(index + 1);

        return nextNode.getHeight().subtract(prevNode.getHeight())
                .divide(nextNode.getWidth().subtract(prevNode.getWidth()));
    }

    BigFraction function(BigFraction x) {
        for (int nodeIndex = 0; nodeIndex < nodes.size(); nodeIndex++) {
            Node node = nodes.get(nodeIndex);
            BigFraction nodeWidth = node.getWidth();

            boolean demandedArgumentNotLessThanNodeWidth = x.compareTo(nodeWidth) > -1;
            if (demandedArgumentNotLessThanNodeWidth) {
                // just iterate later
            } else {
                Node prevNode = nodes.get(nodeIndex - 1);
                BigFraction curentSegmentAddition = slopeAfter(nodeIndex - 1).multiply(x.subtract(prevNode.getWidth()));
                return prevNode.getHeight().
                        add(curentSegmentAddition);
            }
        }
        return nodes.get(nodes.size()-1).getHeight();
    }

    BigFraction inverse_function(BigFraction x) {
        for (int nodeIndex = 0; nodeIndex < nodes.size(); nodeIndex++) {
            Node node = nodes.get(nodeIndex);
            BigFraction nodeWidth = node.getHeight();

            boolean demandedArgumentNotLessThanNodeWidth = x.compareTo(nodeWidth) > -1;
            if (demandedArgumentNotLessThanNodeWidth) {
                // just iterate later
            } else {
                Node prevNode = nodes.get(nodeIndex - 1);
                BigFraction curentSegmentAddition = slopeAfter(nodeIndex - 1).reciprocal()
                        .multiply(x.subtract(prevNode.getHeight()));
                return prevNode.getWidth().
                        add(curentSegmentAddition);
            }
        }
        return nodes.get(nodes.size()-1).getWidth();
    }

    BigFraction function(BigFraction x, int power) {
        BigFraction y = x;
        if (power == 0) {
            return y;
        } else if (power > 0) {

            for (int powerIndex = 0; powerIndex < power; power++) {
                y = function(y);
            }
            return y;
        } else {
            //if power < 0
            for (int powerIndex = 0; powerIndex > power; power--) {
                y = inverse_function(y);
            }
        }
        return y;
    }

    BumpDomain inverse() {
        List<Node> inversedNodes = new ArrayList<Node>();
        for (Node node : nodes) {
            inversedNodes.add(node.inverse());
        }
        return new BumpDomain(inversedNodes);
    }

    BigFraction getHeight() {
        return nodes.get(nodes.size() - 1).getHeight();
    }

    BigFraction getWidth() {
        return nodes.get(nodes.size() - 1).getWidth();
    }

    Integer getSignature() {
        return slopeAfter(0).compareTo(BigFraction.ONE);
    }

    private void validate() {
        boolean isValid = true;
        isValid = isValid && getWidth().equals(getHeight());
        Node firstNode = nodes.get(0);
        isValid = isValid && firstNode.getHeight().equals(firstNode.getWidth());
        if (!isValid) {
            throw new IllegalStateException("Not a valid bump domain. Should begin with first node laying on x=y.");
        }
        for (int nodeIndex = 1; nodeIndex < nodes.size() - 1; nodeIndex++) {
            Node node = nodes.get(nodeIndex);
            if (getSignature() == 1) {
                isValid = isValid && (node.getHeight().compareTo(node.getWidth()) == 1);
            } else if (getSignature() == -1) {
                isValid = isValid && (node.getWidth().compareTo(node.getHeight()) == 1);
            } else {
                isValid = false;
            }
        }

        if (!isValid) {
            throw new IllegalStateException("Not a valid bump domain. Should begin with first node laying on x=y.");
        }
    }

    public BigFraction getInitialSlope() {
        return slopeAfter(0);
    }

    /**
     * Should not be called with initial or final nodes.
     *
     * @param x
     * @return
     */
    private BigFraction function_asterisk(BigFraction x) {
//        if (x.equals(BigFraction.ZERO) || x.equals(getHeight())) {
//            throw new IllegalArgumentException("X should be from (a,b), where 'a' is the start of bumpDomain and" +
//                    "b is it's end point.");
//        }
        BigFraction lastX = BigFraction.ZERO;

        int foundNodeIndex = Collections.binarySearch(nodes, new Node(x, BigFraction.ZERO), new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.getWidth().compareTo(o2.getWidth());
            }
        });
        if (foundNodeIndex >= 0) {
            return slopeAfter(foundNodeIndex).divide(slopeAfter(foundNodeIndex - 1));
        } else {
            return BigFraction.ONE;
        }
    }

    BigFraction phi() {
        BigFraction result = BigFraction.ONE;
//        for (int power = 0; true; power++) {
//
//        }
        return result;
    }

    public PsiFunction getPsiFunction() {
        //TODO
        return null;
    }
}
