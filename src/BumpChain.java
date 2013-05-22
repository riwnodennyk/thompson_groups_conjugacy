import org.apache.commons.math3.fraction.BigFraction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: aindrias
 * Date: 21.05.13
 * Time: 11:38
 * To change this template use File | Settings | File Templates.
 */
public class BumpChain extends Section {
    final List<BumpDomain> bumpDomains;

    public BumpChain() {
        bumpDomains = new ArrayList<BumpDomain>();

        if (!isValid()) {
            throw new IllegalStateException("Not a valid bump domain.");
        }
    }

    List<BumpDomain> getBumpDomains() {
        return bumpDomains;
    }

    private boolean isValid() {
        //TODO
        return true;  //To change body of created methods use File | Settings | File Templates.
    }

    @Override
    BigFraction getHeight() {
        BigFraction sum = BigFraction.ZERO;
        for (BumpDomain bumpDomain : bumpDomains) {
            sum.add(bumpDomain.getHeight());
        }
        return sum;
    }

    @Override
    BigFraction getWidth() {
        BigFraction sum = BigFraction.ZERO;
        for (BumpDomain bumpDomain : bumpDomains) {
            sum.add(bumpDomain.getWidth());
        }
        return sum;
    }

    @Override
    List<Integer> getSignatures() {
        List<Integer> signatures = new ArrayList<Integer>();
        for (BumpDomain bumpDomain : bumpDomains) {
            Integer bumpDomainSignature = bumpDomain.getSignature();
            if (bumpDomainSignature == Signature.TOP || bumpDomainSignature == Signature.BOTTOM) {
                signatures.add(bumpDomainSignature);
            } else {
                throw new IllegalStateException(BumpDomain.class.getSimpleName() + "'s " + bumpDomain + " signature is " + bumpDomainSignature
                        + ", but only -1 or 1 are expected. " + ZeroStubSection.class.getSimpleName() + " to be used for 0 signature.");
            }
        }

        return signatures;
    }

    public DeltaTuple getDelta() {
        for(BumpDomain bumpDomain : bumpDomains)
        {

        }
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void addBumpDomain(BumpDomain bumpDomain) {
        bumpDomains.add(bumpDomain);
    }

    @Override
    public String toString() {
        return "BumpChain{" +
                "bumpDomains=" + bumpDomains +
                '}';
    }
}
