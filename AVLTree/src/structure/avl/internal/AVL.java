package structure.avl.internal;

import java.util.function.BiConsumer;

/**
 * Created by rom on 17.10.16.
 */
public class AVL<K extends Comparable, V>{
    int size;
    Node<K, V> root;
    private Node<K, V> NILL = new Node();

    public AVL() {
        root = NILL;
        size = 0;
    }

    // Search node with the key begin from node p
    private Node search(Node p, K key) {
        if (isExternal(p))
            return p;                       // if node p is external return it
        else if (key == p.key)
            return p;
        else if (key.compareTo(p.key) < 0)
            if (p.left == NILL)             //  key smaller than key of p and p has not left child
                return p;                   //  return p
            else
                return search(p.left, key); //  else continue search in left subtree
        else
            if (p.right == NILL)
                return p;
            else
                return search(p.right, key);

    }

    // returns true if node is leaf or hasn't children
    private boolean isExternal(Node n) {
        if (n == NILL) return true;             // is leaf
        if (n.left == NILL && n.right == NILL)  // is node without children
            return true;
        else
            return false;                       // node has at least one child
    }

    public V put(K key, V value) {
        if (isEmpty()) {
            root = new Node(null, key, value, NILL, NILL);  // store at root node if tree is empty
            size++;
            return null;
        } else {
            Node node = search(root, key);
            if (key.compareTo(node.key) == 0) {             // if founded node with equals tree - replace it
                V temp = (V)node.value;
                node.value = value;
                return temp;
            } else {
                expandExternal(node, key, value);           // if node with equals key does not exist - expand node
                return null;                                // with preferable position
            }
        }
    }

    // get the value of node for given key
    public V get(K key) {
        Node n = search(root, key);
        if (n.key != key)  return null;
        return (V)n.value;
    }

    private void expandExternal(Node p, K key, V value) {
        Node child = new Node(p, key, value, NILL, NILL);
        if (p.left == NILL && p.right == NILL)              // node has not children
            p.height++;
        if (key.compareTo(p.key) < 0) {
            p.left = child;
        } else {
            p.right = child;                                // key might be only bigger - store child as right
        }
        insRebalance(child);                                // rebalance hook
        size++;
    }

    // Check and rebalance tree  after insert if it is necessary
    private void insRebalance(Node n) {
        if (n.height > getSibling(n).height)
            rebalance(n);
    }

    // returns sibling of given tree
    private Node getSibling(Node n) {
        if (n == n.parent.left)
            return n.right;
        else
            return n.left;
    }


    // Delete node with given key and returns value from this node
    public V delete(K key) {
        Node p = search(root, key);
        if (p.key != key) return null;      // if key doesn't found
        V temp = (V)p.value;
        if (isExternal(p))
            drop(p);                        // node has not children
        else {
            if (p.left == NILL)
                p = p.right;                // node has not left child
            else if (p.right == NILL)
                p = p.left;                 // node has not tight child
            else {
                Node replacement;
                if (p.left.height < p.right.height)  // follow the height of children's heights choose the replacement
                    replacement = successor(p);
                else
                    replacement = predecessor(p);
                p.key = replacement.key;
                p.value = replacement.value;
                drop(replacement);
            }
        }
        delRebalance(p);                // hook for rebalance
        return temp;
    }

    // Physical remove of node
    private void drop(Node node) {
        if (node == node.parent.left)
            node.parent.left = NILL;
        else
            node.parent.right = NILL;
    }

    private void delRebalance(Node n) {
        if (n != NILL && n != root)
            if (n.height < getSibling(n).height)
                rebalance(n);
    }

    // Fix height of all subtrees and if some subtree in path unbalanced - execute restructuring
    private void rebalance(Node p) {
        while(p != null) {
            if (!isBalanced(p)) {
                Node y = biggerChild(p);
                Node x = biggerChild(y);
                p = triNodeRestructure(x);
                p.left.fixHeight();
                p.right.fixHeight();
            }
            p.fixHeight();
            p = p.parent;
        }
    }

    // returns the heighest child of node
    private Node biggerChild(Node p) {
        // if height of children is different - peek the highest
        if (p.left.height > p.right.height)
            return p.left;
        else if (p.left.height < p.right.height)
            return p.right;
        // if height of both children equals
        else if (p == root)            // p is root - does not matter what is returned
            return p.left;
        else if (p == p.parent.left)
            return p.left; // node is the left child of it parent
        else
            return p.right; // node is the right child of it parent
    }

    // Check balance of subtree with root at p
    private boolean isBalanced(Node p) {
        if (Math.abs(p.left.height - p.right.height) <= 1)
            return true;
        else
            return false;
    }


    // swap node p with it child
    private void relink (Node p, Node child, boolean left) {
        if (child != NILL) {
            child.parent = p;
        }
        if (left)
            p.left = child;
        else
            p.right = child;
    }


    // execute single rotation from child position and make it parent
    public void rotate(Node child) {
        Node parent = child.parent;
        Node grand = parent.parent;
        if (grand == null) {        // grand not exist. Replace parent by child and
            root = child;
            child.parent = null;
        } else
            if (parent == grand.left)
                relink(grand, child, true);
            else
                relink(grand, child, false);

        if (child == parent.right) {            // if child is right parent's child
            relink(parent, child.left, false);  // replace left descendant of child and parent
            relink(child, parent, true);        // and then replace parent and child
        } else {
            relink(parent, child.right, true);  // child is left parent's child
            relink(child, parent, false);
        }
    }

    public Node triNodeRestructure(Node child) {
        Node parent = child.parent;
        Node grandParent = parent.parent;
        if ((child == parent.right)                     // At the same time parent and child
                == (parent == grandParent.right)) {     // the same children of their parent
            rotate(parent);                             // do single rotation
            return parent;
        } else {
            rotate(child);                              // neeaded double rotation
            rotate(child);
            return child;
        }
    }


    public boolean isEmpty() {
        return size == 0;
    }

    public int height() {
        return root.getHeight();
    }

    // travers tree in visitor's scenario
    public void traverse(BiConsumer<K, V> visitor) {
        if (isEmpty()) throw new IllegalArgumentException();
        Node node = minimum(root);
        while(node != null && node != NILL) {
            visitor.accept((K)node.getKey(), (V)node.getValue());
            node = successor(node);
        }
    }

    // executes travers with given visitor for given interval
    public void traverse(BiConsumer visitor, K from, K to) {
        Node node = search(root, from);
        if (node == NILL) throw new IllegalArgumentException();
        while(node != null &&
                node != NILL && to.compareTo(node.getKey()) >= 0) {
            visitor.accept((K)node.getKey(), (V)node.getValue());
            node = successor(node);
        }
    }

    private Node predecessor(Node node) {
        Node x = node;
        if (x.left != NILL)
            return maximum(x.left);
        Node y = x.parent;
        while ( y != null && y != NILL && y.left == x) {
            x = y;
            y = y.parent;
        }
        return y;
    }

    private Node successor(Node n) {
        Node x = n;
        if (x.right != NILL)
            return minimum(x.right);
        Node y = x.parent;
        while ( y != null && y != NILL && y.right == x) {
            x = y;
            y = y.parent;
        }
        return y;
    }

    // returns node with smallest key in subtree of given node
    public Node minimum(Node node) {
        Node p = node;
        while(p.left != NILL) {
            p = p.left;
        }
        return p;
    }

    // returns node with biggest key in subtree of given node
    public Node maximum(Node node) {
        Node p = node;
        while(p.right != NILL) {
            p = p.right;
        }
        return p;
    }

}
