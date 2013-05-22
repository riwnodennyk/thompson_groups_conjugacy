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
    private List<Section> sections;
    private List<BumpChain> bumpChains;  // never set, should be overriden from sections
    private List<BumpDomain> bumpDomains;  // never set, should be overriden from sections

    {
        sections = new ArrayList<Section>();
    }

    public ThompsonElement(List<Node> nodes) {
        List<Node> goodNodes = optimizeNodes(nodes);
        apply(goodNodes);

    }

    Section getSectionAt(int index) {
        return sections.get(index);
    }

    BumpDomain getBumpDomainAt(int index) {
        return bumpDomains.get(index);
    }

    int getSectionsCount() {
        return sections.size();
    }

    private List<Node> optimizeNodes(List<Node> nodes) {
        int previousNodeSignature = Signature.JUST_STARTED;
        for (int nodeIndex = 0; nodeIndex < nodes.size(); nodeIndex++) {
            if (nodeIndex < nodes.size() - 2 &&
                    Utils.slopeAfter(nodes, nodeIndex).equals(Utils.slopeAfter(nodes, nodeIndex + 1))) {
                nodes.remove(nodeIndex + 1);
                continue;
            }

            Node node = nodes.get(nodeIndex);
            int nodeSignature = node.getSignature();
            switch (nodeSignature) {
                case Signature.BOTTOM:
                    if (previousNodeSignature == Signature.TOP) {
                        nodes.add(nodeIndex, Utils.calculateXYIntersectedNode(nodes.get(nodeIndex - 1), node));
                        nodeIndex++;
                    }
                    break;
                case Signature.MIDDLE:
                    // nothing to do
                    break;
                case Signature.TOP:
                    if (previousNodeSignature == Signature.BOTTOM) {
                        nodes.add(nodeIndex, Utils.calculateXYIntersectedNode(nodes.get(nodeIndex - 1), node));
                        nodeIndex++;
                    }
                    break;
                default:
                    assert false;
            }
            previousNodeSignature = nodeSignature;

        }
        return nodes;
    }

    public void addSection(Section section) {
        sections.add(section);
    }

    public void apply(List<Node> nodes) {
        sections = new ArrayList<Section>();
        Node previousNode = null;

        BumpChain currentBumpChain = new BumpChain();
        BumpDomain currentBumpDomain = new BumpDomain();

        for (int nodeIndex = 0; nodeIndex < nodes.size(); nodeIndex++) {
            Node currentNode = nodes.get(nodeIndex);
            if(previousNode != null && currentNode.compareTo(previousNode) < 1)
            {
                throw new IllegalStateException("Nodes' x should in increasing order.");
            }
            switch (currentNode.getSignature()) {
                case Signature.BOTTOM:
                    if (previousNode == null) {
                        assert false;
                    } else if (previousNode.getSignature() == Signature.MIDDLE) {
                        currentBumpDomain.addNode(previousNode);
                        currentBumpDomain.addNode(currentNode);
                    } else if (previousNode.getSignature() == Signature.BOTTOM) {
                        currentBumpDomain.addNode(currentNode);
                    } else {
                        assert false;
                    }
                    break;
                case Signature.MIDDLE:
                    if (previousNode == null) {
                        // nothing to do
                    } else if (previousNode.getSignature() == Signature.MIDDLE) {
                        assert currentBumpChain.getWidth().equals(BigFraction.ZERO);
                        addSection(new ZeroStubSection(currentNode.getHeight().subtract(previousNode.getHeight())));
                    } else {
                        currentBumpDomain.addNode(currentNode);
                        currentBumpChain.addBumpDomain(currentBumpDomain);
                        if (currentNode.isDiadic()) {
                            addSection(currentBumpChain);
                            currentBumpChain = new BumpChain();
                        }
                        currentBumpDomain = new BumpDomain();
                    }
                    break;
                case Signature.TOP:
                    if (previousNode == null) {
                        assert false;
                    } else if (previousNode.getSignature() == Signature.MIDDLE) {
                        currentBumpDomain.addNode(previousNode);
                        currentBumpDomain.addNode(currentNode);
                    } else if (previousNode.getSignature() == Signature.TOP) {
                        currentBumpDomain.addNode(currentNode);
                    } else {
                        assert false;
                    }
                    break;
                default:
                    assert false;
            }
            previousNode = currentNode;
        }
        init();
    }

    public void init() {
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

    @Override
    public String toString() {

        if (sections.size() == 0) {
            return "ThompsonElement with empty sections list.";
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (Section section : sections) {
            stringBuilder.append("\nSection #" + sections.indexOf(section) + "\n " + section);
        }
        return stringBuilder.toString();
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
        //TODO
//        if (!Utils.equivalent(delta(), secondElement.delta())) {
//            return Conjugacy.DELTA_CHECK_FAILED;
//        }
        return Conjugacy.SUCCESS;
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

    public void visualize() {

        System.out.printf("\n\n\n" + this);

        System.out.printf("\n\n Count of Bump Chains is " + bumpChains.size());
        System.out.printf("\n Count of Bump Domains is " + bumpDomains.size());

        System.out.printf("\n\n >>> Sigma 1 <<<\n" + this.sigmaOne());
        System.out.printf("\n\n >>> Sigma 2 <<<\n" + this.sigmaTwo());
        System.out.printf("\n\n >>> Sigma 3 <<<\n");
        int bumpDomainIndex = 0;
        for (PsiFunction psiFunction : this.sigmaThree()) {
            bumpDomainIndex++;
            System.out.printf("\n > For Bump Domain #" +
                    bumpDomainIndex +
                    ":\n" + psiFunction);

        }
        //TODO
//        System.out.printf("\n\n >>> Delta <<<\n" + this.delta() + "\n");
    }


    public enum Conjugacy {
        SIGMA_ONE_CHECK_FAILED, SIGMA_TWO_CHECK_FAILED,
        SIGMA_THREE_CHECK_FAILED, DELTA_CHECK_FAILED,
        SUCCESS
    }
}
