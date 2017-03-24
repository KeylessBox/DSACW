import java.math.BigInteger;
import java.util.ArrayList;

/**
 * The tree class
 * Created by AndreiM on 12/21/2016.
 */
public class AVLTree {
    private AVLNode root;

    /**
     * Constructor for empty tree
     */
    public AVLTree () {
        root = null;
    }

    /**
     * Insert a value(key) into the tree. Builds a node with this value
     * @param data = node.value
     * @return task log
     */
    public String insert(BigInteger data) {
        AVLNode n = new AVLNode(data);
        return insertAVL(this.root, n);
    }

    /**
     * Inserts the current node into the tree
     * @param parentNode
     * @param currentNode
     * @return task log
     */
    public String insertAVL(AVLNode parentNode, AVLNode currentNode) {
        /**
         * Checks if tree empty. If yes, the new node becomes the root of the tree
         */
        if (parentNode==null) {
            this.root = currentNode;
            return ("I" + currentNode.value + " ");

        }
        /**
         * If the tree is not empty, it inserts the node somewhere in the tree
         */
        else {
            /**
             * if the value of the current node is smaller than its current parent candidate(or ancestor), the node becomes left child.
             */
            if (currentNode.value.compareTo(parentNode.value) == -1 ) {
                /**
                 * if current parent does not have a left child, then the currentNode is going to be the one. Checks for any balancing
                 * and returns task log
                 */
                if (parentNode.left == null) {
                    parentNode.left = currentNode;
                    currentNode.parent = parentNode;
                    String response = recursiveBalance(parentNode);
                    return  "I" + currentNode.value + "L" + parentNode.value + " " + response + " ";
                }
                /**
                 * else, the search continues
                 */
                else {
                    return insertAVL(parentNode.left, currentNode);
                }
            }
            /**
             * if the value of the current node is bigger than of its parent candidate(ancestor) the node looks to the right
             */
            else if (currentNode.value.compareTo(parentNode.value) == 1) {
                /**
                 * if parent candidate does not have a right child, then currentNode becomes one. Plus checks for any balancing
                 * and returns task log
                 */
                if (parentNode.right == null) {
                    parentNode.right = currentNode;
                    currentNode.parent = parentNode;
                    String response = recursiveBalance(parentNode);
                    return "I" + currentNode.value + "R" + parentNode.value+ " " + response + " ";
                }
                /**
                 * else the search continues
                 */
                else {
                    return insertAVL(parentNode.right, currentNode);
                }
            }
            return "";
        }
    }

    /**
     * Checks for the currentNode's parent sub-tree's balance and if need, balances the tree
     * rotation1 and rotation2 variables tell how many rotations with its parent need to be done
     * @param parentNode
     * @return task log (if need to balance)
     */
    public String recursiveBalance(AVLNode parentNode) {
        setBalance(parentNode);
        int balance = parentNode.balance;
        boolean rotation1 = false;
        boolean rotation2 = false;

        if (balance == -2) {
            rotation1 = true;
            if (height(parentNode.left.left) >= height(parentNode.left.right)) {
                parentNode = rotateRight(parentNode);
            }
            else {
                parentNode = doubleRotateLeftRight(parentNode);
                rotation2 = true;
            }
        }
        else if (balance == 2){
            rotation1 = true;
            if (height(parentNode.right.right) >= height(parentNode.right.left)) {
                parentNode = rotateLeft(parentNode);

            }
            else {
                parentNode = doubleRotateRightLeft(parentNode);
                rotation2 = true;
            }
        }
        if (rotation1 && !rotation2) {
            return "R" + parentNode.value + recursiveBalance((parentNode));
        } else if (rotation1 && rotation2) {
            return "R" + parentNode.value + " R" + parentNode.value + recursiveBalance((parentNode));
        }
        else {
            if (parentNode.parent != null) {
                return recursiveBalance((parentNode.parent));
            }
            else {
                this.root = parentNode;
                return "";
            }
        }
    }

    /**
     * Removes a specific key
     * @param value
     */
    public void remove (BigInteger value) {
        removeAVL(this.root, value);
    }

    /**
     * Find the node with the specific value
     * @param nodeToRemove
     * @param data
     */
    public void removeAVL(AVLNode nodeToRemove, BigInteger data) {
        if (nodeToRemove==null) {
            return;
        }
        else {
            if (nodeToRemove.value.compareTo(data) == 1) {
                removeAVL(nodeToRemove.left, data);
            }
            else if (nodeToRemove.value.compareTo(data) == -1) {
                removeAVL(nodeToRemove.right, data);
            }
            else if (nodeToRemove.value.compareTo(data) == 0){
                removeFoundNode(nodeToRemove);
            }
        }
    }

    /**
     * Remove the node with the specific value
     * @param nodeToRemove
     */
    public void removeFoundNode(AVLNode nodeToRemove) {
        AVLNode r;

        if (nodeToRemove.left == null || nodeToRemove.right == null) {
            if (nodeToRemove.parent == null) {
                this.root = null;
                nodeToRemove = null;
                return;
            }
            r = nodeToRemove;
        }
        else {
            r = successor(nodeToRemove);
            nodeToRemove.value = r.value;
        }
        AVLNode p;
        if (r.left != null) {
            p = r.left;
        }
        else {
            p = r.right;
        }

        if (p!= null) {
            p.parent = r.parent;
        }

        if (r.parent == null) {
            this.root = p;
        } else {
            if (r==r.parent.left) {
                r.parent.left = p;
            }
            else {
                r.parent.right = p;
            }
            recursiveBalance(r.parent);
        }
        r = null;
    }

    /**
     *
     * @param node
     * @return
     */
    public AVLNode rotateLeft (AVLNode node) {
        AVLNode v = node.right;
        v.parent = node.parent;

        node.right = v.left;

        if (node.right != null) {
            node.right.parent = node;
        }

        v.left = node;
        node.parent = v;

        if (v.parent!= null) {
            if (v.parent.right == node) {
                v.parent.right = v;
            }
            else if (v.parent.left == node) {
                v.parent.left = v;
            }
        }
        setBalance(node);
        setBalance(v);

        return v;
    }

    public AVLNode rotateRight(AVLNode n) {

        AVLNode v = n.left;
        v.parent = n.parent;

        n.left = v.right;

        if(n.left!=null) {
            n.left.parent=n;
        }

        v.right = n;
        n.parent = v;


        if(v.parent!=null) {
            if(v.parent.right==n) {
                v.parent.right = v;
            } else if(v.parent.left==n) {
                v.parent.left = v;
            }
        }

        setBalance(n);
        setBalance(v);

        return v;
    }

    public AVLNode doubleRotateLeftRight(AVLNode u) {
        u.left = rotateLeft(u.left);
        return rotateRight(u);
    }

    public AVLNode doubleRotateRightLeft(AVLNode u) {
        u.right = rotateRight(u.right);
        return rotateLeft(u);
    }

    public AVLNode successor(AVLNode q) {
        if(q.right!=null) {
            AVLNode r = q.right;
            while(r.left!=null) {
                r = r.left;
            }
            return r;
        } else {
            AVLNode p = q.parent;
            while(p!=null && q==p.right) {
                q = p;
                p = q.parent;
            }
            return p;
        }
    }

    private int height(AVLNode currentNode) {
        if(currentNode==null) {
            return -1;
        }
        if(currentNode.left==null && currentNode.right==null) {
            return 0;
        } else if(currentNode.left==null) {
            return 1+height(currentNode.right);
        } else if(currentNode.right==null) {
            return 1+height(currentNode.left);
        } else {
            return 1+maximum(height(currentNode.left),height(currentNode.right));
        }
    }

    private int maximum(int a, int b) {
        if(a>=b) {
            return a;
        } else {
            return b;
        }
    }

    private void setBalance(AVLNode current) {
        current.balance = height(current.right)-height(current.left);
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void makeEmpty() {
        root = null;
    }
    public BigInteger getRoot() {
        return root.value;
    }


}
