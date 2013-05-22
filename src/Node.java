import org.apache.commons.math3.fraction.BigFraction;

/**
 * Created with IntelliJ IDEA.
 * User: aindrias
 * Date: 21.05.13
 * Time: 11:38
 * To change this template use File | Settings | File Templates.
 */
public class Node implements Comparable {
    final private BigFraction width;
    final private BigFraction height;
    final private int signature;

    public Node(BigFraction width, BigFraction height) {
        if (width == null || height == null) {
            throw new IllegalStateException();
        }
        this.width = width;
        this.height = height;
        this.signature = getHeight().compareTo(getWidth());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (!height.equals(node.height)) return false;
        if (!width.equals(node.width)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = width.hashCode();
        result = 31 * result + height.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Node{" +
                "x=(" + width +
                "), y=(" + height +
                ")}";
    }

    public Node inverse() {
        return new Node(height, width);
    }

    public BigFraction getWidth() {
        return width;
    }

    public BigFraction getHeight() {
        return height;
    }

    public int getSignature() {
        return signature;
    }

    public boolean isDiadic() {
        return Utils.isPowerOfTwo(width.getDenominatorAsInt()) &&
                Utils.isPowerOfTwo(height.getDenominatorAsInt());
    }

    @Override
    public int compareTo(Object o) {
        return getWidth().compareTo(((Node) o).getWidth());
    }
}
