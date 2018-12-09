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
            root = new Node<>(value);
            size++;
            return;
        }

        boolean isAdded = root.add(value);
        if (isAdded) {
            size++;
        }
    }

    /**
     * Checks if the value is in the tree
     * @param value value, wich you want to check
     * @return true, if contained, else false
     */
    public boolean isContains(Type value) {
        return (root != null) && (root.isContains(value));
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
    private void remove(Node<Type> node, Type value) {
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
    private void removeNode(Node<Type> node) {
        Node<Type> tempNode;

        if ((node.getRight() != null) && (node.getLeft() != null)) {
            tempNode = node.getLeft();

            Node<Type> temp = tempNode;
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
        private Node<Type> current;
        private Node<Type> next;

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
        private Node<Type> getNext() {
            if (next.getRight() != null) {
                Node<Type> temp = next.getRight();
                while (temp.getLeft() != null) {
                    temp = temp.getLeft();
                }
                return temp;
            }

            Node<Type> temp = next;
            while ((temp.getParent() != null) && (temp.equals(temp.getParent().getRight()))) {
                temp = temp.getParent();
            }

            return temp.getParent();
        }
    }
}

