import java.io.*;
import java.util.*;

/**
 * @author Tre' Jeter
 * UF ID#: 1946-9876
 * Email: t.jeter@ufl.edu
 */
public class avltree {

        /**
         * Class representation of a node of the AVL Tree
         */
        public class Node {
                int nodeKey;
                int nodeHeight;
                Node leftChild;
                Node rightChild;

                /**
                 * Depicting of a Node
                 * @param key A key value helps distinguish many attributes of a Node
                 */
                public Node(int key) {
                        this.nodeKey = key;
                        this.nodeHeight = 1;
                        this.leftChild = null;
                        this.rightChild = null;
                }

                /**
                 * Get a specific key value
                 * @return The desired key value
                 */
                public int getKey() {
                        return this.nodeKey;
                }
        }

        Node root;

        /**
         * Returns the height of the AVL Tree
         * @param point A specific node
         * @return Height of AVL Tree from that specific point
         */
        int height(Node point) {
                if(point == null) {
                        return 0;
                }
                return point.nodeHeight;
        }

        /**
         * Right rotates a subtree
         * @param point A specific node
         * @return New root of the AVL Tree after a right rotate
         */
        Node rightRotate(Node point) {
                Node node = point.leftChild;
                Node node2 = node.rightChild;

                //Right rotation
                node.rightChild = point;
                point.leftChild = node2;

                //Height of AVL Tree must be updated
                point.nodeHeight = Math.max(height(point.leftChild), height(point.rightChild)) + 1;
                node.nodeHeight = Math.max(height(node.leftChild), height(node.rightChild)) + 1;

                //Return the new root of the AVL Tree
                return node;
        }

        /**
         * Left rotates a subtree
         * @param point A specific node
         * @return New root of the AVL Tree after a left rotate
         */
        Node leftRotate(Node point) {
                Node node = point.rightChild;
                Node node2 = node.leftChild;

                //Left rotation
                node.leftChild = point;
                point.rightChild = node2;

                //Height of AVL Tree must be updated
                point.nodeHeight = Math.max(height(point.leftChild), height(point.rightChild)) + 1;
                node.nodeHeight = Math.max(height(node.leftChild), height(node.rightChild)) + 1;

                //Return the new root of the AVL Tree
                return node;
        }

        /**
         * Balance the AVL Tree after operations
         * @param point A specific node
         * @return A balanced version of the previous AVL Tree
         */
        int balanceTree(Node point) {
                if(point == null) {
                        return 0;
                }
                return height(point.leftChild) - height(point.rightChild);
        }

        /**
         * Inserting a node with binary search tree insertion
         * Updating the AVL Tree's height
         * Balancing the AVL Tree
         * @param point A specific node
         * @param key Any given key to insert
         * @return The updated AVL Tree after an item is inserted
         */
        Node insert(Node point, int key) {
                //Binary Search Tree Insertion
                if(point == null) {
                        return (new Node(key));
                }

                //Key in left-subtree
                if(key < point.nodeKey) {
                        point.leftChild = insert(point.leftChild, key);
                }

                //Key in right-subtree
                else if(key > point.nodeKey) {
                        point.rightChild = insert(point.rightChild, key);
                }

                //Handles duplicate keys
                else {
                        return point;
                }

                //Updating the height of the node
                point.nodeHeight = Math.max(height(point.leftChild), height(point.rightChild)) + 1;

                //Setting the balance factor of the node
                int balance = balanceTree(point);

                //Left Left Case for unbalanced AVL Tree
                if(balance > 1 && key < point.leftChild.nodeKey) {
                        return rightRotate(point);
                }

                //Right Right Case for unbalanced AVL Tree
                if(balance < -1 && key > point.rightChild.nodeKey) {
                        return leftRotate(point);
                }

                //Left Right Case for unbalanced AVL Tree
                if(balance > 1 && key > point.leftChild.nodeKey) {
                        point.leftChild = leftRotate(point.leftChild);
                        return rightRotate(point);
                }

                //Right Left Case for unbalanced AVL Tree
                if(balance < -1 && key < point.rightChild.nodeKey) {
                        point.rightChild = rightRotate(point.rightChild);
                        return leftRotate(point);
                }

                //Return the node
                return point;
        }

        /**
         * If a non-empty Binary Search Tree exists, return the node with minimum key
         * @param point A specific node
         * @return Node with minimum key
         */
        Node minNodeKey(Node point) {
                Node current = point;

                //Find left-most child node
                while(current.leftChild != null) {
                        current = current.leftChild;
                }
                return current;
        }

        /**
         * Deleting a node with binary search tree deletion
         * Updating the AVL Tree's height
         * Balancing the AVL Tree
         * @param point A specific node
         * @param key Any given key to delete
         * @return The updated AVL Tree after an item is deleted
         */
        Node delete(Node point, int key) {
                //Binary Search Tree Deletion
                if(point == null) {
                        return point;
                }

                //Key in left-subtree
                if(key < point.nodeKey) {
                        point.leftChild = delete(point.leftChild, key);
                }

                //Key in right-subtree
                else if(key > point.nodeKey) {
                        point.rightChild = delete(point.rightChild, key);
                }

                //Key matches the root nodes key --> node to be deleted
                else {
                        //Node with one or no children
                        if((point.leftChild == null) || (point.rightChild == null)) {
                                Node temp = null;

                                if(temp == point.leftChild) {
                                        temp = point.rightChild;
                                }

                                else {
                                        temp = point.leftChild;
                                }

                                //In the case of no children
                                if(temp == null) {
                                        temp = point;
                                        point = null;
                                }

                                //In the case of one child
                                else {
                                        point = temp;
                                }
                        }

                        /*
                         * A node with two children should return
                         * the smallest in the right-subtree
                         */
                        else {
                                Node temp = minNodeKey(point.rightChild);
                                point.nodeKey = temp.nodeKey;
                                point.rightChild = delete(point.rightChild, temp.nodeKey);
                        }
                }

                //In the case that the AVL Tree only has one node, return that node
                if(point == null) {
                        return point;
                }

                //Updating the height of the node
                point.nodeHeight = Math.max(height(point.leftChild), height(point.rightChild)) + 1;

                //Setting the balance factor of the node
                int balance = balanceTree(point);

                //Left Left Case for unbalanced AVL Tree
                if(balance > 1 && balanceTree(point.leftChild) >= 0) {
                        return rightRotate(point);
                }

                //Left Right Case for unbalanced AVL Tree
                if(balance > 1 && balanceTree(point.leftChild) < 0) {
                        point.leftChild = leftRotate(point.leftChild);
                        return rightRotate(point);
                }

                //Right Right Case for unbalanced AVL Tree
                if(balance < -1 && balanceTree(point.rightChild) <= 0) {
                        return leftRotate(point);
                }

                //Right Left Case for unbalanced AVL Tree
                if(balance < -1 && balanceTree(point.rightChild) > 0) {
                        point.rightChild = rightRotate(point.rightChild);
                        return leftRotate(point);
                }
                return point;
        }

        /**
         * Searching for the existence of a key in the AVL Tree
         * @param key Any given key to be found (or not)
         * @return A found key, null if the requested key is not found
         */
        public String search(int key) {
                Node node = this.root;
                while(node != null) {
                        //Found key
                        if(node.nodeKey == key) {
                                return String.valueOf(node.getKey());
                        }
                        else if(node.nodeKey < key) {
                                node = node.rightChild;
                        }
                        else {
                                node = node.leftChild;
                        }
                }
                //Key not found
                return null;
        }

        /**
         * Helper Function for rangeSearch()
         * Given a list of keys, node, and two key values, add
         * key values between the two parameter key values to the
         * list of keys recursively
         * @param keys List of key values
         * @param node A specific node
         * @param key1 Any given key value as the starting point of a range search
         * @param key2 Any given key value as the ending point of a range search
         */
        public void rangeSearchHelp(List<Integer> keys, Node node, int key1, int key2) {
                if(node == null) {
                        return;
                }
                if(node.nodeKey > key1) {
                        rangeSearchHelp(keys, node.leftChild, key1, key2);
                }
                if(node.nodeKey >= key1 && node.nodeKey <= key2) {
                        keys.add(node.nodeKey);
                }
                if(node.nodeKey < key2) {
                        rangeSearchHelp(keys, node.rightChild, key1, key2);
                }
        }

        /**
         * Given a node and two key values, return the the values (inclusive)
         * between the two key values given
         * @param node A specific node
         * @param key1 Any given key value as the starting point of a range search
         * @param key2 Any given key value as the ending point of a range search
         * @return Range of keys found, if found
         */
        public String rangeSearch(Node node, int key1, int key2) {
                while(node!= null) {
                        String str = "";
                        ArrayList<Integer> keys = new ArrayList<Integer>();
                        rangeSearchHelp(keys, node, key1, key2);
                        str = keys.toString().replace("[", "").replace("]", "");
                        return str;
                }
                return null;
        }


        /**
         * Main function for reading the text file of instructions
         * @param args Parameter of main function
         * @throws FileNotFoundException In case a file isn't found, throw an exception
         */
        public static void main(String [] args) throws FileNotFoundException {
                PrintWriter out = new PrintWriter("output_file.txt");
                avltree tree = new avltree();
                String value = "";
                String replace = "";
                String [] splitting;
                if(args.length > 0) {
                        File file = new File(args[0]);
                        try
                        {
                                Scanner scan = new Scanner(file);
                                //Continuously scan the file line-by-line
                                while (scan.hasNext() && scan.hasNextLine()) {
                                        //Capture each value on each line and run them through the conditions
                                        value = scan.next();

                                        //Initialize a new AVL Tree
                                        if(value.equalsIgnoreCase("Initialize()")) {
                                                tree = new avltree();
                                        }

                                        //Insert a new key into the AVL Tree
                                        else if(value.contains("Insert")) {
                                                replace = value.replaceAll("\\(", " ").replaceAll("\\)", "");
                                                splitting = replace.split(" ");
                                                tree.root = tree.insert(tree.root, Integer.parseInt(splitting[1]));
                                        }

                                        //Delete a key from the AVL Tree
                                        else if(value.contains("Delete")) {
                                                replace = value.replaceAll("\\(", " ").replaceAll("\\)", "");
                                                splitting = replace.split(" ");
                                                tree.root = tree.delete(tree.root, Integer.parseInt(splitting[1]));
                                        }

                                        //Search for key values within a certain range
                                        else if(value.contains("Search") && value.contains("(") && value.contains(",") && value.contains(")")) {
                                                replace = value.replaceAll("\\(", " ").replaceAll("\\)", "").replaceAll("\\,", " ");
                                                splitting = replace.split(" ");
                                                out.println(tree.rangeSearch(tree.root, Integer.parseInt(splitting[1]), Integer.parseInt(splitting[2])));
                                        }

                                        //Search for a specific key value
                                        else if(value.contains("Search")) {
                                                replace = value.replaceAll("\\(", " ").replaceAll("\\)", "");
                                                splitting = replace.split(" ");
                                                out.println(tree.search(Integer.parseInt(splitting[1])));
                                        }
                                }
                                scan.close();
                        }
                        catch (FileNotFoundException error) {
                                error.printStackTrace();
                        }
                }
                out.close();
        }
}