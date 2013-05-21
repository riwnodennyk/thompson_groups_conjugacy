import org.apache.commons.math3.fraction.BigFraction;

import java.util.ArrayList;
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
        if (!isValid()) {
            throw new IllegalStateException("Not a valid bump domain.");
        }
    }

    BigFraction slopeAfter(int index) {
        Node prevNode = nodes.get(index);
        Node nextNode = nodes.get(index + 1);

        return nextNode.getHeight().subtract(prevNode.getHeight())
                .divide(nextNode.getWidth().subtract(prevNode.getWidth()));
    }

    BigFraction function(BigFraction x) {
//        BigFraction y = BigFraction.ZERO;
//        BigFraction lastX = BigFraction.ZERO;

        for (int nodeIndex = 0; nodeIndex < nodes.size(); nodeIndex++) {
            Node node = nodes.get(nodeIndex);
            BigFraction nodeWidth = node.getWidth();

//            BigFraction extraX = x.subtract(nodeWidth);
            boolean demandedArgumentNotLessThanNodeWidth = x.compareTo(nodeWidth) > -1;
            if (demandedArgumentNotLessThanNodeWidth) {
                // just iterate later
            } else {
                Node prevNode = nodes.get(nodeIndex - 1);
                BigFraction curentSegmentAddition = slopeAfter(nodeIndex - 1).multiply(x.subtract(prevNode.getWidth()));
                return prevNode.getHeight().
                        add(curentSegmentAddition);
            }
//            else
//            {
//                y = y.add(node.getHeight());
//                lastX = lastX.add(nodeWidth);
//            }
        }
        return nodes.get(nodes.size()).getHeight();
    }

    BigFraction inverse_function(BigFraction x) {
        BigFraction y = BigFraction.ZERO;
        BigFraction lastX = BigFraction.ZERO;

        for (Node node : nodes) {
            BigFraction nodeWidth = node.getHeight();
            BigFraction extraX = x.subtract(lastX);
            if (extraX.compareTo(nodeWidth) == -1) {
                y = y.add(node.getSlope().reciprocal().multiply(extraX));
                return y;
            } else {
                y = y.add(node.getWidth());
                lastX = lastX.add(nodeWidth);
            }
        }
        assert y.equals(getHeight()) && y.equals(getWidth());
        return y;
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
//        BigFraction totalBumpDomainHeight = BigFraction.ZERO;
//        for (Node node : nodes) {
//            totalBumpDomainHeight.add(node.getHeight());
//        }
//        return totalBumpDomainHeight;
        return nodes.get(nodes.size() - 1).getHeight();
    }

    BigFraction getWidth() {
//        BigFraction totalBumpDomainWidth = BigFraction.ZERO;
//        for (Node node : nodes) {
//            totalBumpDomainWidth.add(node.getWidth());
//        }
        return nodes.get(nodes.size() - 1).getWidth();
    }

    Integer getSignature() {
        return getInitialSlope().compareTo(BigFraction.ONE);
    }

    private boolean isValid() {
        return getWidth().equals(getHeight());
    }

    public BigFraction getInitialSlope() {
        return slopeAfter(0);
    }

    private BigFraction function_asterisk(BigFraction x) {
//        if (x.equals(BigFraction.ZERO) || x.equals(getHeight())) {
//            throw new IllegalArgumentException("X should be from (a,b), where 'a' is the start of bumpDomain and" +
//                    "b is it's end point.");
//        }
        BigFraction lastX = BigFraction.ZERO;
        for (int nodeIndex = 0; nodeIndex < nodes.size(); nodeIndex++) {
            if (.){
                return nodes.get(nodeIndex).getSlope().divide(nodes.get(nodeIndex - 1).getSlope());
            }else{
                return BigFraction.ONE;
            }
        }


    }

    BigFraction phi() {
        BigFraction result = BigFraction.ONE;
        for (int power = 0; true; power++) {

        }
        return result;
    }

    public PsiFunction getPsiFunction() {
        //TODO
        return null;
    }
}
