import org.apache.commons.math3.fraction.BigFraction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: aindrias
 * Date: 20.05.13
 * Time: 18:59
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main(String[] argv) throws IOException {
//        ThompsonElement firstElement = inputElement();
//        ThompsonElement secondElement = inputElement();
//
//        ThompsonElement.Conjugacy isConjugant = firstElement.isConjugantTo(secondElement);
//
//        System.out.printf("\nThese two elements of the Thompson group are %sCONJUGANT",
//                isConjugant == ThompsonElement.Conjugacy.SUCCESS ? " " : "NOT ");

        ArrayList<Node> list = new ArrayList<Node>();
        list.add(new Node(BigFraction.THREE_QUARTERS, BigFraction.ONE_QUARTER));
        list.add(new Node(BigFraction.ONE_QUARTER, BigFraction.THREE_QUARTERS));
        BumpDomain bump
                = new BumpDomain(list);

        System.out.printf( "\n" + bump.function(BigFraction.ONE_FIFTH));
        System.out.printf( "\n" + bump.inverse_function(BigFraction.ONE_FIFTH));
        System.out.printf( "\n" + bump.inverse_function(BigFraction.ONE_HALF));
    }

    private static ThompsonElement inputElement() throws IOException {


        BufferedReader buff = new BufferedReader(
                new InputStreamReader(System.in));
        System.out.print("\nWhat's your name? ");
        System.out.flush();
        String s = buff.readLine();
        System.out.printf("Hello, %s!", s);
        return new ThompsonElement(s);
    }
}
