package group244.zaicev.com;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class for working with AVL Tree of any type
 *
 * @param <Type> Type which you want to use in your Binary Tree
 */
public class BinaryTree<Type extends Comparable<Type>> implements Iterable<Type> {
    private Node<Type> root = null;
    private int size = 0;

    /**
     * Adds a value to the tree
     * @param value - value, wich you want to add
     */
    public void add(Type value) {
        if (isEmpty()) {
            root = new group244.zaicev.com.Node<>(value);
            size++;
            return;
        }

        boolean isAdded = add(root, value);
        if (isAdded) {
            size++;
        }
    }

    /**
     * realize adding node
     *
     * @param node root of your subtree
     * @param value new value
     * @return false, if this value is contains in the tree, else true
     */
    private boolean add(Node<Type> node, Type value) {
        if (node.getValue().equals(value)) {
            return false;
        }

        if (value.compareTo(node.getValue()) > 0) {
            if (node.getRight() == null) {
                node.setRight(new Node<>(value));
                node.getRight().setParent(node);
                return true;
            }
            return add(node.getRight(), value);
        } else {
            if (node.getLeft() == null) {
                node.setLeft(new Node<>(value));
                node.getLeft().setParent(node);
                return true;
            }
            return add(node.getLeft(), value);
        }
    }

    /**
     * Checks if the value is in the tree
     * @param value value, wich you want to check
     * @return true, if contained, else false
     */
    public boolean isContains(Type value) {
        return (root != null) && (isContains(root, value));
    }

    /**
     * Checks if the tree contains this value
     *
     * @param node the root of your subtree
     * @param value which you want to check
     * @return true, if tree contains this value, else false
     */
    private boolean isContains(Node<Type> node, Type value) {
        if (node == null) {
            return false;
        }

        if (node.getValue().equals(value)) {
            return true;
        }

        if (value.compareTo(node.getValue()) > 0) {
            return isContains(node.getRight(), value);
        } else {
            return isContains(node.getLeft(), value);
        }
    }

    /**
     * Removes a value from a tree
     * @param value - value, wich you want to remove
     */
    public void remove(Type value) {
        remove(root, value);
    }

    /**
     * Removes node from tree
     * @param node start searching for delete value
     * @param value value, wich you want to delete
     */
    private void remove(group244.zaicev.com.Node<Type> node, Type value) {
        if (node == null) {
            return;
        }

        if (node.getValue().equals(value)) {
            removeNode(node);
            return;
        }

        if (value.compareTo(node.getValue()) > 0) {
            remove(node.getRight(), value);
        } else {
            remove(node.getLeft(), value);
        }
        size--;
    }

    /**
     * Removes node from tree
     * @param node node, wich you want to remove
     */
    private void removeNode(group244.zaicev.com.Node<Type> node) {
        group244.zaicev.com.Node<Type> tempNode;

        if ((node.getRight() != null) && (node.getLeft() != null)) {
            tempNode = node.getLeft();

            group244.zaicev.com.Node<Type> temp = tempNode;
            while (temp.getRight() != null) {
                temp = temp.getRight();
            }
            temp.setRight(node.getRight());
            node.getRight().setParent(temp);
        } else {
            if (node.getLeft() != null) {
                tempNode = node.getLeft();
            } else {
                tempNode = node.getRight();
            }
        }

        if (node.getParent() == null) {
            root = tempNode;
        } else {
            if (node.equals(node.getParent().getLeft())) {
                node.getParent().setLeft(tempNode);
            } else {
                node.getParent().setRight(tempNode);
            }
        }

        if (tempNode != null) {
            tempNode.setParent(node.getParent());
        }
    }

    /**
     * @return true if tree is Empty, else false
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * @return Iterator for binary tree
     */
    @Override
    public Iterator<Type> iterator() {
        return new BinaryTreeIterator();
    }

    /**
     * @return binary tree size
     */
    public int getSize() {
        return size;
    }


    /**
     * Class for working with Binary Tree iterator of any type
     */
    public class BinaryTreeIterator implements Iterator<Type> {
        private group244.zaicev.com.Node<Type> current;
        private group244.zaicev.com.Node<Type> next;

        /**
         * Constructor for Iterator
         */
        BinaryTreeIterator() {
            current = null;
            next = root;

            while ((next != null) && (next.getLeft() != null)) {
                next = next.getLeft();
            }
        }

        /**
         * Checks if there is next node
         */
        @Override
        public boolean hasNext() {
            return next != null;
        }

        /**
         * Returns value next node
         */
        @Override
        public Type next()  {
            if (next == null) {
                throw new NoSuchElementException();
            }
            current = next;
            next = getNext();
            return current.getValue();
        }

        /**
         * Returns next node
         */
        private group244.zaicev.com.Node<Type> getNext() {
            if (next.getRight() != null) {
                group244.zaicev.com.Node<Type> temp = next.getRight();
                while (temp.getLeft() != null) {
                    temp = temp.getLeft();
                }
                return temp;
            }

            group244.zaicev.com.Node<Type> temp = next;
            while ((temp.getParent() != null) && (temp.equals(temp.getParent().getRight()))) {
                temp = temp.getParent();
            }

            return temp.getParent();
        }
    }
}

