package group244.zaicev.com;

import org.junit.Assert;
import org.junit.Test;


import java.util.Iterator;
import java.util.NoSuchElementException;
public class BinaryTreeTest {

    private BinaryTree<Integer> tree = new BinaryTree<>();

    @Test
    public void addTest() {
        tree.add(1);
        Assert.assertEquals(tree.getSize(), 1);
        tree.add(4);
        Assert.assertEquals(tree.getSize(), 2);
        tree.add(1);
        Assert.assertEquals(tree.getSize(), 2);
        tree.add(3);
        Assert.assertEquals(tree.getSize(), 3);
        tree.add(-3);
        Assert.assertEquals(tree.getSize(), 4);
        tree.add(-2);
        Assert.assertEquals(tree.getSize(), 5);
    }

    @Test
    public void isContains() {

        Assert.assertFalse(tree.isContains(1));
        tree.add(1);
        Assert.assertTrue(tree.isContains(1));

        Assert.assertFalse(tree.isContains(2));
        tree.add(2);
        Assert.assertTrue(tree.isContains(2));

        Assert.assertFalse(tree.isContains(0));
        tree.add(0);
        Assert.assertTrue(tree.isContains(0));
    }

    @Test
    public void isEmpty() {
        Assert.assertTrue(tree.isEmpty());
        tree.add(1);
        Assert.assertFalse(tree.isEmpty());
    }

    @Test
    public void removeFirstTest() {
        tree.add(1);
        tree.add(2);
        Assert.assertTrue(tree.isContains(2));
        tree.remove(2);
        Assert.assertFalse(tree.isContains(2));

        tree.remove(1);
        Assert.assertTrue(tree.isEmpty());

        tree.remove(1);
    }


    @Test
    public void removeSecondTest() {
        tree.add(0);
        tree.add(-3);
        tree.add(-5);
        tree.add(-1);
        tree.add(4);
        tree.add(3);
        tree.add(5);
        tree.add(2);
        tree.add(1);

        tree.remove(0);
        tree.remove(4);
        tree.remove(2);
        tree.remove(3);
    }

    @Test(expected = NoSuchElementException.class)
    public void iteratorNoThisElementExceptionTest() {
        tree.add(0);

        Iterator<Integer> iterator = tree.iterator();
        iterator.next();
        iterator.next();
    }

    @Test
    public void iteratorHasNextTest() {
        Iterator<Integer> iterator = tree.iterator();

        Assert.assertFalse(iterator.hasNext());
    }

    @Test
    public void iteratorNextTest() {
        tree.add(0);
        tree.add(-3);
        tree.add(-5);
        tree.add(-1);
        tree.add(4);
        tree.add(3);
        tree.add(5);
        tree.add(2);

        Iterator<Integer> iterator = tree.iterator();

        int buff = iterator.next();
        for (int i = 0; i < tree.getSize() - 1; i++) {
            int temp = iterator.next();
            Assert.assertTrue(buff <= temp);
            buff = temp;
        }
    }

    @Test
    public void foreachTest() {
        tree.add(0);
        tree.add(-3);
        tree.add(-5);
        tree.add(-1);
        tree.add(4);
        tree.add(3);
        tree.add(5);
        tree.add(2);

        int size = 0;
        for(Integer ignored : tree) {
            size++;
        }

        Assert.assertEquals(tree.getSize(), size);
    }
}