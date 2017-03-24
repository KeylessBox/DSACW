import java.math.BigInteger;

/**
 * Created by AndreiM on 12/21/2016.
 */
public class AVLNode {
    public BigInteger value;
    public AVLNode left;
    public AVLNode right;
    public AVLNode parent;
    public int balance;

    public AVLNode () {
        left = null;
        right = null;
        parent = null;
        value = null;
        balance = 0;
    }
    public AVLNode (BigInteger value){
        left = null;
        right = null;
        parent = null;
        this.value = value;
        balance = 0;
    }
    public String toString() {
        return "" + value;
    }
}
