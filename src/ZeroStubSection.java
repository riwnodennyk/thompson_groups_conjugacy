import org.apache.commons.math3.fraction.BigFraction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: aindrias
 * Date: 21.05.13
 * Time: 12:01
 * To change this template use File | Settings | File Templates.
 */
public class ZeroStubSection extends Section {
    private final BigFraction size;

    ZeroStubSection(BigFraction size) {
        this.size = size;
    }

    @Override
    BigFraction getHeight() {
        return size;
    }

    @Override
    BigFraction getWidth() {
        return size;
    }

    @Override
    List<Integer> getSignatures() {
        List<Integer> signature = new ArrayList<Integer>();
        signature.add(0);
        return signature;
    }

}
