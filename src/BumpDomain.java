import org.apache.commons.math3.fraction.BigFraction;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: aindrias
 * Date: 21.05.13
 * Time: 11:39
 * To change this template use File | Settings | File Templates.
 */
public class BumpDomain {
    List
            <Node> nodes;


    public BumpDomain() {
        this.nodes = new ArrayList<Node>();
    }

    public BumpDomain(List<Node> nodes) {
        this.nodes = nodes;

        init();
    }

    public void init() {
        //We remove extra nodes, when slope before is equal to slope after such one.
        for (int nodeIndex = 1; nodeIndex < nodes.size() - 1; nodeIndex++) {
            if (Utils.slopeAfter(nodes, nodeIndex - 1).equals(Utils.slopeAfter(nodes, nodeIndex))) {
                throw new IllegalStateException("not optimized nodes");
            }
        }

        validate();
    }

    double getAlpha() {
        //TODO
        return 1d;
    }

    double getBeta() {
        //TODO
        return 1d;
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
                BigFraction curentSegmentAddition = Utils.slopeAfter(nodes, nodeIndex - 1).multiply(x.subtract(prevNode.getWidth()));
                return prevNode.getHeight().
                        add(curentSegmentAddition);
            }
        }
        return nodes.get(nodes.size() - 1).getHeight();
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
                BigFraction curentSegmentAddition = Utils.slopeAfter(nodes, nodeIndex - 1).reciprocal()
                        .multiply(x.subtract(prevNode.getHeight()));
                return prevNode.getWidth().
                        add(curentSegmentAddition);
            }
        }
        return nodes.get(nodes.size() - 1).getWidth();
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

    BigFraction getFirstSegmentXEnd() {
        return nodes.get(1).getWidth();
    }

    BigFraction getLastSegmentXStart() {
        return nodes.get(nodes.size() - 2).getWidth();
    }

    BumpDomain inverse() {
        List<Node> inversedNodes = new ArrayList<Node>();
        for (Node node : nodes) {
            inversedNodes.add(node.inverse());
        }
        return new BumpDomain(inversedNodes);
    }

    BigFraction getHeight() {
        return nodes.get(nodes.size() - 1).getHeight().subtract(nodes.get(0).getHeight());
    }

    BigFraction getWidth() {
        return nodes.get(nodes.size() - 1).getWidth().subtract(nodes.get(0).getWidth());
    }

    int getSignature() {
        return Utils.slopeAfter(nodes, 0).compareTo(BigFraction.ONE);
    }

    private void validate() {
        boolean isValid = true;
        isValid = isValid && getWidth().equals(getHeight());
        Node firstNode = nodes.get(0);
        isValid = isValid && firstNode.getHeight().equals(firstNode.getWidth());
        if (!isValid) {
            throw new IllegalStateException("Not a valid bump domain. Should begin with first node laying on x=y.");
        }
        int signature = getSignature();

        for (int nodeIndex = 1; nodeIndex < nodes.size() - 1; nodeIndex++) {
            Node node = nodes.get(nodeIndex);
            if (signature == 1) {
                isValid = isValid && (node.getHeight().compareTo(node.getWidth()) == 1);
            } else if (signature == -1) {
                isValid = isValid && (node.getWidth().compareTo(node.getHeight()) == 1);
            } else {
                isValid = false;
            }
            if (!Utils.isPowerOrTwo(Utils.slopeAfter(nodes, nodeIndex - 1))) {
                throw new IllegalStateException("Not a valid bump domain. Every slope should be a power or 2. " +
                        "Check node #" + nodeIndex + " " + node +
                        " " +
                        ": " + Utils.slopeAfter(nodes, nodeIndex - 1));
            }
        }

        if (!isValid) {
            throw new IllegalStateException("Not a valid bump domain.");
        }
    }

    public BigFraction getInitialSlope() {
        return Utils.slopeAfter(nodes, 0);
    }

    /**
     * Should not be called with initial or final nodes.
     *
     * @param x
     * @return
     */
    private BigFraction functionAsterisk(BigFraction x) {
        if (getSignature() == -1) {
            throw new IllegalStateException("Should apply only for signature == 1");
        }

        int foundNodeIndex = Collections.binarySearch(nodes, new Node(x, BigFraction.ZERO), new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.getWidth().compareTo(o2.getWidth());
            }
        });

        if (foundNodeIndex >= 0) {
            return Utils.slopeAfter(nodes, foundNodeIndex).divide(Utils.slopeAfter(nodes, foundNodeIndex - 1));
        } else {
            return BigFraction.ONE;
        }
    }

    BigFraction phi(final BigFraction x) {
        if (getSignature() == -1) {
            throw new IllegalStateException("Should apply only for signature == 1");
        }

        BigFraction result = BigFraction.ONE;

        BigFraction lastSegmentXStart = getLastSegmentXStart();

        for (BigFraction lastArgument = function(x); !(lastArgument.compareTo(lastSegmentXStart) == 1); lastArgument = function(lastArgument)) {
            result = result.multiply(functionAsterisk(lastArgument));
        }


        BigFraction firstSegmentXEnd = getFirstSegmentXEnd();
        for (BigFraction lastArgument = x; !(lastArgument.compareTo(firstSegmentXEnd) == -1); lastArgument = inverse_function(lastArgument)) {
            result = result.multiply(functionAsterisk(lastArgument));
        }

        return result;
    }

    BigFraction getPAsterisk() {
        if (getSignature() == -1) {
            throw new IllegalStateException("Should apply only for signature == 1");
        }

        for (int nodeIndex = 1; nodeIndex < nodes.size() - 1; nodeIndex++) {
            if (!functionAsterisk(nodes.get(nodeIndex).getWidth()).equals(BigFraction.ONE)) {
                return nodes.get(nodeIndex).getWidth();
            }
        }
        assert false;
        return null;
    }

    BigFraction getInverseToMatchInto(BigFraction start, BigFraction end, BigFraction x) {
        while (true) {
            if (x.compareTo(start) >= 0 && x.compareTo(end) == -1) {
                break;
            }
            x = inverse_function(x);
        }
        return x;
    }

    BigFraction getStartX() {
        return nodes.get(0).getWidth();
    }

    BigFraction getR() {
        return getInverseToMatchInto(getStartX(), getFirstSegmentXEnd(), getPAsterisk());

//        if (getSignature() == -1) {
//            throw new IllegalStateException("Should apply only for signature == 1");
//        }
//        BigFraction firstSegmentXEnd = getFirstSegmentXEnd();
//        BigFraction r = getPAsterisk();
//        while (r.compareTo(firstSegmentXEnd) >= 0) {
//            r = inverse_function(r);
//        }
//        return r;
    }

    private Set<BigFraction> getPsiArguments() {
        Set<BigFraction> arguments = new HashSet<BigFraction>();
        BigFraction r = getR();
        for (int nodeIndex = 1; nodeIndex < nodes.size() - 1; nodeIndex++) {
            arguments.add(getInverseToMatchInto(r, getFirstSegmentXEnd(), nodes.get(nodeIndex).getWidth()));
        }

        return arguments;
    }

    public PsiFunction getPsiFunction() {
        if (getSignature() == -1) {
            return inverse().getPsiFunction();
        }
        Map<BigFraction, BigFraction> map = new HashMap<BigFraction, BigFraction>();
        for (BigFraction x : getPsiArguments()) {
            map.put(x, phi(x));
        }
        return new PsiFunction(getInitialSlope(), getR(), map);
    }

    @Override
    public String toString() {
        return "BumpDomain{" +
                "nodes=" + nodes +
                '}';
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

}
