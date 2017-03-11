package structure.avl.internal;

/**
 * Node of AVL balanced tree
 */
class Node<K, V> {
    Node left, right, parent;
    K key;
    V value;
    int height;

    Node() {
        this.left = null;
        this.right = null;
        this.key = null;
        this.value = null;
        this.parent = null;
        this.height = 0;
    }

    Node(Node p, K key, V value) {
        this.key = key;
        this.value = value;
        this.parent = p;
        this.left = null;
        this.right = null;
        this.height = 1;
    }

    Node(Node p, K key, V value, Node l, Node r) {
        this(p, key, value);
        this.left = l;
        this.right = r;
    }

    public int getHeight() {
        return height;
    }

    public void fixHeight() {
        this.height = Math.max(left.height, right.height) + 1;
    }

    public K getKey() {
        return this.key;
    }

    public V getValue() {
        return this.value;
    }
}
