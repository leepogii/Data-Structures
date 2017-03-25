import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Min Ho Lee
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> implements AVLInterface<T> {

    // Do not make any new instance variables.
    private AVLNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty AVL tree.
     */
    public AVL() {
        root = null;
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public AVL(Collection<T> data) {
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
            root = new AVLNode<>(data);
            size++;
            return;
        }
        root = addHelper(root, data);
    }

    @Override
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data passed is null.");
        }
        AVLNode<T> toReturn = new AVLNode<>(null);
        root = removeHelper(root, toReturn, data);
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

    @Override
    public int depth(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data passed is null.");
        }
        return depthHelper(root, data);
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
    private AVLNode<T> addHelper(AVLNode<T> current, T data) {
        if (current == null) {
            size++;
            return new AVLNode<>(data);
        }
        if (data.equals(current.getData())) {
            current.setHeight(heightHelper(current));
            return current;
        }
        if (data.compareTo(current.getData()) < 0) {
            current.setLeft(addHelper(current.getLeft(), data));
            balanceFactor(current);
            if (Math.abs(current.getBalanceFactor()) == 2) {
                return rotateHelper(current);
            }
            return current;
        } else {
            current.setRight(addHelper(current.getRight(), data));
            balanceFactor(current);
            if (Math.abs(current.getBalanceFactor()) == 2) {
                return rotateHelper(current);
            }
            return current;
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
    private AVLNode<T> removeHelper(AVLNode<T> current, AVLNode<T> toReturn,
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
                AVLNode<T> predParent = predecessor(current.getLeft(),
                        current.getLeft());
                if (predParent == current.getLeft()
                        && predParent.getRight() == null) {
                    current.setData(predParent.getData());
                    current.setLeft(current.getLeft().getLeft());
                } else {
                    current.setData(predParent.getRight().getData());
                    predParent.setRight(predParent.getRight().getLeft());
                }
                if (current == root) {
                    balanceFactor(current);
                    if (Math.abs(current.getBalanceFactor()) == 2) {
                        return rotateHelper(current);
                    }
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
            balanceFactor(current);
            if (Math.abs(current.getBalanceFactor()) == 2) {
                return rotateHelper(current);
            }
            return current;
        } else {
            current.setRight(removeHelper(current.getRight(), toReturn, data));
            balanceFactor(current);
            if (Math.abs(current.getBalanceFactor()) == 2) {
                return rotateHelper(current);
            }
            return current;
        }
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
    private T getHelper(AVLNode<T> current, T data) {
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
     * Helper method for preorder()
     * It adds data to the list when it is the right location
     *
     * @param current current node
     * @param preOrder the list where data will be added
     */
    private void preorderTraverse(AVLNode<T> current, ArrayList<T> preOrder) {
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
    private void postorderTraverse(AVLNode<T> current,
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
    private void inorderTraverse(AVLNode<T> current, ArrayList<T> inOrder) {
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
    private void levelorderHelper(AVLNode<T> root, ArrayList<T> levelOrder) {
        Queue<AVLNode<T>> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            AVLNode<T> front = queue.peek();
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
     * Helper method rotateLeft.
     * Rotate left when parent's balance factor is -2
     * and child's is -1 or 0
     * @param current the parent's node
     * @return new parent's node
     */
    private AVLNode<T> rotateLeft(AVLNode<T> current) {
        AVLNode<T> right = current.getRight();
        current.setRight(right.getLeft());
        right.setLeft(current);
        balanceFactor(right);
        return right;
    }
    /**
     * Helper method rotateRight.
     * Rotate right when parent's balance factor is 2
     * and child's is 1 or 0
     * @param current the parent's node
     * @return new parent's node
     */
    private AVLNode<T> rotateRight(AVLNode<T> current) {
        AVLNode<T> left = current.getLeft();
        current.setLeft(left.getRight());
        left.setRight(current);
        balanceFactor(left);
        return left;
    }
    /**
     * Helper method rotateLeftRight.
     * Rotate left and right when parent's balance factor is 2
     * and child's is -1
     * @param current the parent's node
     * @return new parent's node
     */
    private AVLNode<T> rotateLeftRight(AVLNode<T> current) {
        current.setLeft(rotateLeft(current.getLeft()));
        return rotateRight(current);
    }
    /**
     * Helper method rotateRightLeft.
     * Rotate left when parent's balance factor is -2
     * and child's is 1
     * @param current the parent's node
     * @return new parent's node
     */
    private AVLNode<T> rotateRightLeft(AVLNode<T> current) {
        current.setRight(rotateRight(current.getRight()));
        return rotateLeft(current);
    }
    /**
     * Helper method for height()
     * It calculates what the height of the tree is.
     * @param current current node
     * @return the height of the tree
     */
    private int heightHelper(AVLNode<T> current) {
        if (current == null) {
            return -1;
        }
        return 1 + Math.max(heightHelper(current.getLeft()),
                heightHelper(current.getRight()));
    }

    /**
     * Helper method for rotation
     * Rotate when absolute value of parent's balance factor is 2.
     * @param current the parent's node
     * @return new parent's node
     */
    private AVLNode<T> rotateHelper(AVLNode<T> current) {
        if (current.getBalanceFactor() == 2) {
            if (current.getLeft().getBalanceFactor() == 1
                    || current.getLeft().getBalanceFactor() == 0) {
                return rotateRight(current);
            }
            if (current.getLeft().getBalanceFactor() == -1) {
                return rotateLeftRight(current);
            }
        }
        if (current.getBalanceFactor() == -2) {
            if (current.getRight().getBalanceFactor() == -1
                    || current.getRight().getBalanceFactor() == 0) {
                return rotateLeft(current);
            }
            if (current.getRight().getBalanceFactor() == 1) {
                return rotateRightLeft(current);
            }
        }
        return current;
    }
    /**
     * Helper method for depth()
     * It calculates the depth of where the data is.
     * @param current current node
     * @param data the data to search in the tree
     * @return the depth of where the data is.
     */
    private int depthHelper(AVLNode<T> current, T data) {
        int depth = 1;
        while (current != null) {
            if (data.equals(current.getData())) {
                return depth;
            }
            if (data.compareTo(current.getData()) < 0) {
                depth++;
                current = current.getLeft();
            } else {
                depth++;
                current = current.getRight();
            }
        }
        throw new NoSuchElementException("There is no such data in the tree");
    }

    /**
     * Helper method to set height and balance factor
     * @param current current node to be set
     */
    private void balanceFactor(AVLNode<T> current) {
        if (current.getLeft() != null) {
            current.getLeft().setHeight(heightHelper(current.getLeft()));
            current.getLeft().setBalanceFactor(
                    heightHelper(current.getLeft().getLeft())
                    - heightHelper(current.getLeft().getRight()));
        }
        if (current.getRight() != null) {
            current.getRight().setHeight(heightHelper(current.getRight()));
            current.getRight().setBalanceFactor(
                    heightHelper(current.getRight().getLeft())
                    - heightHelper(current.getRight().getRight()));
        }
        current.setHeight(heightHelper(current));
        current.setBalanceFactor(heightHelper(current.getLeft())
                - heightHelper(current.getRight()));
    }
    /**
     * To find predecessor.
     *
     * @param current current node
     * @param prev parent node of predecessor
     * @return node which holds next smallest data than current data
     */
    private AVLNode<T> predecessor(AVLNode<T> current, AVLNode<T> prev) {
        if (current.getRight() == null) {
            return prev;
        }
        return predecessor(current.getRight(), current);
    }
    /**
     * THIS METHOD IS ONLY FOR TESTING PURPOSES.
     * DO NOT USE IT IN YOUR CODE
     * DO NOT CHANGE THIS METHOD
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        return root;
    }
}
