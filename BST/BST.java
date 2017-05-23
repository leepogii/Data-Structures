import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.NoSuchElementException;
import java.util.LinkedList;
/**
 * Binary Search Tree
 * @author Min Ho Lee
 * @version 1.0
 */
public class BST<T extends Comparable<? super T>> implements BSTInterface<T> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private BSTNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty BST
     */
    public BST() {
        root = null;
    }

    /**
     * Initializes the BST with the data in the Collection. The data in the BST
     * should be added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element
     * in data is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException(
                    "data in the collection is null");
        }
        for (T e: data) {
            if (e == null) {
                throw new IllegalArgumentException(
                        "data in the collection is null");
            }
            add(e);
        }
    }

    @Override
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data passed is null.");
        }
        if (root == null) {
            root = new BSTNode<>(data);
            size++;
            return;
        }
        addHelper(root, data);
    }

    @Override
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data passed is null.");
        }
        BSTNode<T> toReturn = new BSTNode<>(null);
        removeHelper(root, toReturn, data);
        return toReturn.getData();
    }

    @Override
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data passed is null.");
        }
        return getHelper(root, data);
    }

    @Override
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data passed is null.");
        }
        try {
            return data.equals(get(data));
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public List<T> preorder() {
        ArrayList<T> preOrder = new ArrayList<>();
        if (root != null) {
            preorderTraverse(root, preOrder);
        }
        return preOrder;
    }

    @Override
    public List<T> postorder() {
        ArrayList<T> postOrder = new ArrayList<>();
        if (root != null) {
            postorderTraverse(root, postOrder);
        }
        return postOrder;
    }

    @Override
    public List<T> inorder() {
        ArrayList<T> inOrderList = new ArrayList<>();
        if (root != null) {
            inorderTraverse(root, inOrderList);
        }
        return inOrderList;
    }

    @Override
    public List<T> levelorder() {
        ArrayList<T> levelOrder = new ArrayList<>();
        if (root != null) {
            levelorderHelper(root, levelOrder);
        }
        return levelOrder;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public int height() {
        return heightHelper(root);
    }

    /**
     * Helper method for get()
     * Returns the data in the tree matching the parameter passed in.
     *
     * @throws java.util.NoSuchElementException if the data is not found
     * @param current current node
     * @param data the data to search for in the tree
     * @return the data matched if found
     */
    private T getHelper(BSTNode<T> current, T data) {
        if (current == null) {
            throw new NoSuchElementException(
                    "There is no such data in the tree");
        }
        if (data.equals(current.getData())) {
            return current.getData();
        }
        if (data.compareTo(current.getData()) < 0) {
            return getHelper(current.getLeft(), data);
        } else {
            return getHelper(current.getRight(), data);
        }
    }

    /**
     * Helper method for remove()
     * Remove the node which holds the matched data.
     *
     * @throws java.util.NoSuchElementException if the data is not found
     * @param current current node
     * @param toReturn to store the data from the removed node
     * @param data the data to be deleted if found
     * @return the current node for recursion
     */
    private BSTNode<T> removeHelper(BSTNode<T> current, BSTNode<T> toReturn,
                                    T data) {
        if (current == null) {
            throw new NoSuchElementException(
                    "There is no such data in the tree");
        }
        if (data.equals(current.getData())) {
            size--;
            toReturn.setData(current.getData());
            if (current.getLeft() == null && current.getRight() == null) {
                if (current == root) {
                    clear();
                    return null;
                }
                return null;
            } else if (current.getLeft() != null
                    && current.getRight() != null) {
                current.setData(predecessor(current.getLeft(),
                        current.getLeft()).getData());
                if (predecessor(current.getLeft(),
                        current.getLeft()) == current.getLeft()) {
                    current.setData(predecessor(current.getLeft(),
                            current.getLeft()).getData());
                    current.setLeft(current.getLeft().getLeft());
                } else {
                    current.setData(predecessor(current.getLeft(),
                            current.getLeft()).getRight().getData());
                    predecessor(current.getLeft(),
                            current.getLeft()).setRight(
                            predecessor(current.getLeft(),
                            current.getLeft()).getRight().getLeft());
                }
                return current;

            } else if (current.getLeft() != null) {
                if (current == root) {
                    root = current.getLeft();
                }
                return current.getLeft();
            } else {
                if (current == root) {
                    root = current.getRight();
                }
                return current.getRight();
            }
        }
        if (data.compareTo(current.getData()) < 0) {
            current.setLeft(removeHelper(current.getLeft(), toReturn, data));
            return current;
        } else {
            current.setRight(removeHelper(current.getRight(), toReturn, data));
            return current;
        }
    }

    /**
     * Helper method for add()
     * Traverse until current finds the correct spot and add the data.
     * Update whenever it traverses.
     *
     * @param current current node
     * @param data the data to be added only if no same data in the tree.
     * @return the current node used for recursion
     */
    private BSTNode<T> addHelper(BSTNode<T> current, T data) {
        if (current == null) {
            size++;
            return new BSTNode<>(data);
        }
        if (data.equals(current.getData())) {
            return current;
        }
        if (data.compareTo(current.getData()) < 0) {
            current.setLeft(addHelper(current.getLeft(), data));
            return current;
        } else {
            current.setRight(addHelper(current.getRight(), data));
            return current;
        }
    }

    /**
     * To find predecessor.
     *
     * @param current current node
     * @param prev parent node of predecessor
     * @return node which holds next smallest data than current data
     */
    private BSTNode<T> predecessor(BSTNode<T> current, BSTNode<T> prev) {
        if (current.getRight() == null) {
            return prev;
        }
        return predecessor(current.getRight(), current);
    }

    /**
     * Helper method for preorder()
     * It adds data to the list when it is the right location
     *
     * @param current current node
     * @param preOrder the list where data will be added
     */
    private void preorderTraverse(BSTNode<T> current, ArrayList<T> preOrder) {
        preOrder.add(current.getData());
        if (current.getLeft() != null) {
            preorderTraverse(current.getLeft(), preOrder);
        }
        if (current.getRight() != null) {
            preorderTraverse(current.getRight(), preOrder);
        }
    }
    /**
     * Helper method for postorder()
     * It adds data to the list when it is the right location
     *
     * @param current current node
     * @param postorder the list where data will be added
     */
    private void postorderTraverse(BSTNode<T> current,
                                   ArrayList<T> postorder) {
        if (current.getLeft() != null) {
            postorderTraverse(current.getLeft(), postorder);
        }
        if (current.getRight() != null) {
            postorderTraverse(current.getRight(), postorder);
        }
        postorder.add(current.getData());
    }
    /**
     * Helper method for inorder()
     * It adds data to the list when it is the right location
     *
     * @param current current node
     * @param inOrder the list where data will be added
     */
    private void inorderTraverse(BSTNode<T> current, ArrayList<T> inOrder) {
        if (current.getLeft() != null) {
            inorderTraverse(current.getLeft(), inOrder);
        }
        inOrder.add(current.getData());
        if (current.getRight() != null) {
            inorderTraverse(current.getRight(), inOrder);
        }
    }

    /**
     * Helper method for levelorder()
     * Add the data in the list in level order
     * @param root root of the tree
     * @param levelOrder the list where data will be added
     */
    private void levelorderHelper(BSTNode<T> root, ArrayList<T> levelOrder) {
        Queue<BSTNode<T>> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            BSTNode<T> front = queue.peek();
            if (front.getLeft() != null) {
                queue.add(front.getLeft());
            }
            if (front.getRight() != null) {
                queue.add(front.getRight());
            }
            levelOrder.add(front.getData());
            queue.remove();
        }
    }

    /**
     * Helper method for height()
     * It calculates what the height of the tree is.
     * @param current current node
     * @return the height of the tree
     */
    private int heightHelper(BSTNode<T> current) {
        if (current == null) {
            return -1;
        }
        return 1 + Math.max(heightHelper(current.getLeft()),
                heightHelper(current.getRight()));
    }

    /**
     * THIS METHOD IS ONLY FOR TESTING PURPOSES.
     * DO NOT USE IT IN YOUR CODE
     * DO NOT CHANGE THIS METHOD
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        return root;
    }
}
