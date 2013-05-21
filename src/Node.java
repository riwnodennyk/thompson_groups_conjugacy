import org.apache.commons.math3.fraction.BigFraction;

/**
 * Created with IntelliJ IDEA.
 * User: aindrias
 * Date: 21.05.13
 * Time: 11:38
 * To change this template use File | Settings | File Templates.
 */
public class Node {
    final private BigFraction width;
//    final private BigFraction slope;
    private final BigFraction height;

//    public Node(BigFraction width, BigFraction slope) {
//        this.width = width;
//        this.slope = slope;
//        this.height = slope.multiply(width);
//    }

    public Node(BigFraction width, BigFraction height) {
        this.width = width;
//        this.slope = height.divide(width);
        this.height = height;
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

//    public BigFraction getSlope() {
//        return slope;
//    }
//
//    public BigFraction getSlope() {
//        return BigFraction.TWO.pow(slope);
//    }

}
