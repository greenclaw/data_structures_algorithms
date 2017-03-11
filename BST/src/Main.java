import structure.bst.internal.BST;

/**
 * Created by rom on 01.11.16.
 */
public class Main {

    public static void main(String[] args) {


        BST<String, String> tree = new BST();
        tree.add("3", "three");
        tree.add("1", "one");
        tree.add("2", "two");
        tree.add("4", "four");
        tree.add("5", "five");
        tree.add("6", "six");
        tree.add("7", "seven");
        tree.traversal();
        System.out.println("Height of the tree " + tree.getHeight());
        System.out.println(Factor.getFactorial(6));
    }
}
