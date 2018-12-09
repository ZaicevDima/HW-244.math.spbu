package group244.zaicev.com;

/**
 * Class for working with Binary Tree node any Types
 *
 * @param <Type> Type, which you want to use
 */
public class Node<Type extends Comparable<Type>> {
    private Type value;
    private Node<Type> left;
    private Node<Type> right;
    private Node<Type> parent;

    /**
     * Constructor AVL Tree node
     *
     * @param value  new node value
     */
    Node(Type value) {
        this.value = value;
        this.parent = null;
        this.left = null;
        this.right = null;
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

    /**
     * Checks if the tree contains this value
     *
     * @param value which you want to check
     * @return true, if tree contains this value, else false
     */
    public boolean isContains(Type value) {
        if (this.value == value) {
            return true;
        } else {
            if (this.value.compareTo(value) > 0) {
                return this.getLeft() != null && this.left.isContains(value);
            } else {
                return this.getRight() != null && this.right.isContains(value);
            }
        }
    }

    /**
     * realize adding node
     *
     * @param value new value
     */
    public boolean add(Type value) {
        boolean isAdded = false;
        if (value.compareTo(this.getValue()) > 0) {
            if (this.getRight() == null) {
                this.setRight(new Node<>(value));
                this.getRight().setParent(this);
                return true;
            }
            isAdded = this.getRight().add(value);
        } else if (value.compareTo(this.getValue()) < 0){
            if (this.getLeft() == null) {
                this.setLeft(new Node<>(value));
                this.getLeft().setParent(this);
                return true;
            }
            isAdded = this.getLeft().add(value);
        }
        return isAdded;
    }
}
