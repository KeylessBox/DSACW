import java.io.*;
import java.math.BigInteger;


/**
 * Created by AndreiM on 12/21/2016.
 * Does exactly what I understand the Coursework asks.
 */
public class CWTest {
    public static void main(String[] args) {

        String fileName = "results.txt";
        /**
         * Builds an empty AVL Tree
         */
        AVLTree tree = new AVLTree();
        /**
         * Building the means to write to the "fileName" file
         */
        try (BufferedWriter outToFile = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(fileName), "utf-8"))) {
            /**
             * Writes to file the UobNumber and the chosen data structure
             */
            String msg = "" + CalculateKeys.getUobNumber() + "\n";
            outToFile.write(msg);
            outToFile.write("TREE\n");
            /**
             * Calculates the keys and writes to file the format the coursework asks
             */
            String currentLine = CalculateKeys.calculate();
            outToFile.write("data " + currentLine + "\n");
            // System.out.println(currentLine);
            outToFile.write("AVL trace ");
            /**
             * Splits the keys into numbers
             */
            String[] keys  = currentLine.split(", ");
            /**
             * Inserts every key into the tree and writes to file a task log
             */
            for (int i=0; i< 16; i++) {
                 outToFile.write(tree.insert(new BigInteger(keys[i])));
                 //System.out.println(tree.insert(new BigInteger(keys[i])));
            }

            // System.out.println(CalculateKeys.sort(currentLine));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
