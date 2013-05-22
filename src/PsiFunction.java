import org.apache.commons.math3.fraction.BigFraction;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: aindrias
 * Date: 21.05.13
 * Time: 12:56
 * To change this template use File | Settings | File Templates.
 */
public class PsiFunction {
    private final BigFraction lambda;
    private final Map<BigFraction, BigFraction> map;
    private final BigFraction r;

    public PsiFunction(BigFraction lambda, BigFraction r, Map<BigFraction, BigFraction> map) {
        this.lambda = lambda;
        this.map = map;
        this.r = r;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PsiFunction that = (PsiFunction) o;

//        if (!lambda.equals(that.lambda)) return false;
        if (!getRealMap().equals(that.getRealMap())) return false;
//        if (!r.equals(that.r)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = lambda.hashCode();
        result = 31 * result + map.hashCode();
        result = 31 * result + r.hashCode();
        return result;
    }

    @Override

    public String toString() {
        return "PsiFunction{" +
                "lambda=" + lambda +
                ", map=" + map +
                ", r=" + r +
                ", realMap = " +
                getRealMap().toString() +
                "}";


    }

    Map<Double, BigFraction> getRealMap() {
        Map<Double, BigFraction> realMap = new HashMap<Double, BigFraction>();
        for (BigFraction key : map.keySet()) {
            Double realKey = Math.log(key.divide(r).doubleValue()) / Math.log(lambda.doubleValue());
            realMap.put(realKey, map.get(key));
        }
        return realMap;
    }
}
