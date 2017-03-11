package structure.bst.internal;
/*
    * This is an implementation of Balaced Search tree
 */

public class BST<K extends Comparable, V> {

    protected class Node {
        private Node left;
        private Node right;
        private K key;
        private V value;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getLeft() {
            return left;
        }

        public void set(Node node) {
            this.key = node.getKey();
            this.value = node.getValue();
        }

        public V getValue() {
            return value;
        }
    }

    private Node root;

    public Node add(K key, V value) {
        Node newNode = new Node(key, value);
        if (root == null)
            root = newNode;
        else
            return insert(root, newNode);
        return null;
    }

    private Node insert(Node parent, Node child) {
        if (child.getKey().compareTo(parent.getKey()) > 0)
            if (parent.getRight() == null) {
                parent.setRight(child);
            } else
                return insert(parent.getRight(), child);
        else if (child.getKey().compareTo(parent.getKey()) < 0)
            if (parent.getLeft() == null) {
                parent.setLeft(child);
            } else
                return insert(parent.getLeft(), child);
        else {
            Node temp = parent;
            parent.set(child);
            return temp;
        }
        return null;
    }

    public Node search(V value, Node current) {
        if (current == null) return null;

        if (current.getValue() == value)
            return current;
        Node left = search(value, current.getLeft());
        Node right = search(value, current.getRight());
        if (left != null) return left;
        if (right != null) return right;
        return null;
    }


    public void traversal() {
        Node node = root;
        inorder(root);
    }

    private void inorder(Node node) {
        if (node == null) return;
        inorder(node.getLeft());
        System.out.println("Node with Key " + node.getKey() + " has value " + node.getValue());
        inorder(node.getRight());
    }

    public int getHeight() {
        if (root == null) return 0;
        int h = 0;
        return height(root, h);
    }

    private int height(Node node, int h) {
        if (node == null) return h;
        h++;
        int left = height(node.getLeft(), h);
        int right = height(node.getRight(), h);
        return Math.max(left, right);
    }

}