import org.apache.commons.math3.fraction.BigFraction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: aindrias
 * Date: 20.05.13
 * Time: 18:59
 * To change this template use File | Settings | File Templates.
 */
public class ThompsonElement {
    private final List<Section> sections;
    private final List<BumpChain> bumpChains;
    private final List<BumpDomain> bumpDomains;

    public ThompsonElement(String s) {
        //TODO   parse string
        sections = new ArrayList<Section>();


        bumpChains = new ArrayList<BumpChain>();
        for (Section section : sections) {
            if (section instanceof BumpChain) {
                bumpChains.add((BumpChain) section);
            }
        }

        bumpDomains = new ArrayList<BumpDomain>();
        for (BumpChain bumpChain : bumpChains) {
            bumpDomains.addAll(bumpChain.getBumpDomains());
        }

        isValid();
    }

    boolean isValid() {
        //TODO
        BigFraction height = BigFraction.ZERO;
        BigFraction width = BigFraction.ZERO;
        for (Section section : sections) {
            height.add(section.getHeight());
            width.add(section.getWidth());
        }
        return height.equals(BigFraction.ONE) &&
                width.equals(BigFraction.ONE);
    }

    public Conjugacy isConjugantTo(ThompsonElement secondElement) {
        if (!sigmaOne().equals(secondElement.sigmaOne())) {
            return Conjugacy.SIGMA_ONE_CHECK_FAILED;
        }
        if (!sigmaTwo().equals(secondElement.sigmaTwo())) {
            return Conjugacy.SIGMA_TWO_CHECK_FAILED;
        }
        if (!sigmaThree().equals(secondElement.sigmaThree())) {
            return Conjugacy.SIGMA_THREE_CHECK_FAILED;
        }
        if (!Utils.equivalent(delta(), secondElement.delta())) {
            return Conjugacy.DELTA_CHECK_FAILED;
        }
        return Conjugacy.SIGMA_ONE_CHECK_FAILED;
    }

    /**
     * @return List of integers {-1, 0, 1}, each of them is corresponding to the bump domain.
     */
    private List<Integer> sigmaOne() {
        List<Integer> sigmaOne = new ArrayList<Integer>();
        for (Section section : sections) {
            sigmaOne.addAll(section.getSignatures());
        }
        return sigmaOne;
    }

    private List<BigFraction> sigmaTwo() {
        List<BigFraction> sigmaTwo = new ArrayList<BigFraction>();
        for (BumpDomain bumpDomain : bumpDomains) {
            sigmaTwo.add(bumpDomain.getInitialSlope());
        }
        return sigmaTwo;
    }

    private List<PsiFunction> sigmaThree() {
        List<PsiFunction> sigmaThree = new ArrayList<PsiFunction>();
        for (BumpDomain bumpDomain : bumpDomains) {
            sigmaThree.add(bumpDomain.getPsiFunction());
        }
        return sigmaThree;
    }

    private List<DeltaTuple> delta() {
        List<DeltaTuple> delta = new ArrayList<DeltaTuple>();
        for (BumpChain bumpChain : bumpChains) {
            delta.add(bumpChain.getDelta());
        }
        return delta;
    }


    public enum Conjugacy {
        SIGMA_ONE_CHECK_FAILED, SIGMA_TWO_CHECK_FAILED,
        SIGMA_THREE_CHECK_FAILED, DELTA_CHECK_FAILED,
        SUCCESS
    }
}
