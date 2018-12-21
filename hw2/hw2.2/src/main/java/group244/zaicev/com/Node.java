package group244.zaicev.com;

/**
 * Class for working with Binary Tree node any Types
 *
 * @param <Type> Type, which you want to use
 */
public class Node<Type> {
    private Type value;
    private Node<Type> left = null;
    private Node<Type> right = null;
    private Node<Type> parent = null;

    /**
     * Constructor AVL Tree node
     *
     * @param value  new node value
     */
    Node(Type value) {
        this.value = value;
    }

    /**
     * Returns the left node
     */
    public Node<Type> getLeft() {
        return this.left;
    }

    /**
     * Returns the right node
     */
    public Node<Type> getRight() {
        return this.right;
    }

    /**
     * Returns the parent node
     */
    public Node<Type> getParent(){
        return this.parent;
    }

    /**
     * Returns the node value
     */
    public Type getValue() {
        return  this.value;
    }

    /**
     * Changes the left node
     *
     * @param node new left node
     */public void setLeft(Node<Type> node) {
        this.left = node;
    }

    /**
     * Changes the right node
     *
     * @param node new right node
     */
    public void setRight(Node<Type> node) {
        this.right = node;
    }

    /**
     * Changes the parent node
     *
     * @param node new parent node
     */
    public void setParent(Node<Type> node){
        this.parent = node;
    }
}
