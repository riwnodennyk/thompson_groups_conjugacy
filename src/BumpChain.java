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
    List<BumpDomain> bumpDomains;

    public BumpChain() {
        //TODO
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
        //TODO
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    BigFraction getWidth() {
        //TODO
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    List<Integer> getSignatures() {
        List<Integer> signatures = new ArrayList<Integer>();
        for (BumpDomain bumpDomain : bumpDomains) {
            Integer bumpDomainSignature = bumpDomain.getSignature();
            if (bumpDomainSignature == 1 || bumpDomainSignature == -1) {
                signatures.add(bumpDomainSignature);
            } else {
                throw new IllegalStateException(BumpDomain.class.getSimpleName() + "'s " + bumpDomain + " signature is " + bumpDomainSignature
                        + ", but only -1 or 1 are expected. " + ZeroStubSection.class.getSimpleName() + " to be used for 0 signature.");
            }
        }

        return signatures;
    }

    public DeltaTuple getDelta() {
        //TODO
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
