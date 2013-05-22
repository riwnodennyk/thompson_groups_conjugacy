import org.apache.commons.math3.fraction.BigFraction;

import java.io.IOException;
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
//        thompsonOne().visualize();
//        thompsonTwo().visualize();
//        thompsonElementExample().visualize();
        compare(thompsonB(), thompsonC());


    }

    private static void compare(ThompsonElement one, ThompsonElement second) {
        one.visualize();
        second.visualize();
        boolean isConjugate = one.isConjugantTo(second)
                .equals(ThompsonElement.Conjugacy.SUCCESS);
        if (isConjugate) {
            System.out.print("\nIS conjugate.");
        } else {
            System.out.print("\nNOT conjugate.");
        }
    }

    private static ThompsonElement inputElement() throws IOException {


//        BufferedReader buff = new BufferedReader(
//                new InputStreamReader(System.in));
//        System.out.print("\nWhat's your name? ");
//        System.out.flush();
//        String s = buff.readLine();
//        System.out.printf("Hello, %s!", s);
//        return new ThompsonElement(s);
        return null;
    }

    static ThompsonElement thompsonOne() {

        ArrayList<Node> nodes = new ArrayList<Node>();
        nodes.add(new Node(new BigFraction(0, 16), new BigFraction(0, 16)));
        nodes.add(new Node(new BigFraction(1, 16), new BigFraction(2, 16)));
        nodes.add(new Node(new BigFraction(3, 16), new BigFraction(6, 16)));
        nodes.add(new Node(new BigFraction(4, 16), new BigFraction(7, 16)));
        nodes.add(new Node(new BigFraction(6, 16), new BigFraction(8, 16)));
        nodes.add(new Node(new BigFraction(8, 16), new BigFraction(9, 16)));
        nodes.add(new Node(new BigFraction(10, 16), new BigFraction(13, 16)));
        nodes.add(new Node(new BigFraction(16, 16), new BigFraction(16, 16)));

        return new ThompsonElement(nodes);
    }

    static ThompsonElement thompsonTwo() {

        ArrayList<Node> nodes = new ArrayList<Node>();
        nodes.add(new Node(new BigFraction(0, 4), new BigFraction(0, 4)));
        nodes.add(new Node(new BigFraction(1, 4), new BigFraction(2, 4)));
        nodes.add(new Node(new BigFraction(2, 4), new BigFraction(3, 4)));
        nodes.add(new Node(new BigFraction(4, 4), new BigFraction(4, 4)));

        return new ThompsonElement(nodes);
    }

    static ThompsonElement thompsonA() {
        ArrayList<Node> nodes = new ArrayList<Node>();
        nodes.add(new Node(new BigFraction(0, 4), new BigFraction(0, 4)));
        nodes.add(new Node(new BigFraction(2, 4), new BigFraction(1, 4)));
        nodes.add(new Node(new BigFraction(3, 4), new BigFraction(2, 4)));
        nodes.add(new Node(new BigFraction(4, 4), new BigFraction(4, 4)));

        return new ThompsonElement(nodes);
    }

    static ThompsonElement thompsonB() {
        ArrayList<Node> nodes = new ArrayList<Node>();
        nodes.add(new Node(new BigFraction(0, 4), new BigFraction(0, 4)));
        nodes.add(new Node(new BigFraction(2, 4), new BigFraction(2, 4)));
        nodes.add(new Node(new BigFraction(6, 8), new BigFraction(5, 8)));
        nodes.add(new Node(new BigFraction(7, 8), new BigFraction(6, 8)));
        nodes.add(new Node(new BigFraction(8, 8), new BigFraction(8, 8)));

        return new ThompsonElement(nodes);
    }

    static ThompsonElement thompsonC() {
        ArrayList<Node> nodes = new ArrayList<Node>();
        nodes.add(new Node(new BigFraction(0, 4), new BigFraction(0, 4)));
        nodes.add(new Node(new BigFraction(3, 4), new BigFraction(3, 4)));
        nodes.add(new Node(new BigFraction(14, 16), new BigFraction(13, 16)));
        nodes.add(new Node(new BigFraction(15, 16), new BigFraction(14, 16)));
        nodes.add(new Node(new BigFraction(16, 16), new BigFraction(16, 16)));

        return new ThompsonElement(nodes);
    }

    static ThompsonElement thompsonElementExample() {
        ArrayList<Node> nodes = new ArrayList<Node>();
        nodes.add(new Node(new BigFraction(0, 32), new BigFraction(0, 32)));
        nodes.add(new Node(new BigFraction(1, 32), new BigFraction(2, 32)));
        nodes.add(new Node(new BigFraction(3, 32), new BigFraction(6, 32)));
        nodes.add(new Node(new BigFraction(4, 32), new BigFraction(7, 32)));
        nodes.add(new Node(new BigFraction(6, 32), new BigFraction(8, 32)));
        nodes.add(new Node(new BigFraction(8, 32), new BigFraction(9, 32)));
        nodes.add(new Node(new BigFraction(10, 32), new BigFraction(13, 32)));
        nodes.add(new Node(new BigFraction(16, 32), new BigFraction(16, 32)));

        nodes.add(new Node(new BigFraction(20, 32), new BigFraction(20, 32)));
        nodes.add(new Node(new BigFraction(22, 32), new BigFraction(24, 32)));
        nodes.add(new Node(new BigFraction(26, 32), new BigFraction(25, 32)));
        nodes.add(new Node(new BigFraction(27, 32), new BigFraction(27, 32)));
        nodes.add(new Node(new BigFraction(32, 32), new BigFraction(32, 32)));

        return new ThompsonElement(nodes);
    }
}
