import org.apache.commons.math3.fraction.BigFraction;

import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: aindrias
 * Date: 21.05.13
 * Time: 12:01
 * To change this template use File | Settings | File Templates.
 */
public abstract class Section {
    abstract BigFraction getHeight();

    abstract BigFraction getWidth();

    abstract List<Integer> getSignatures();

}
