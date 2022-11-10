## Description
----------------
An AVL tree is a self-balancing Binary Search Tree (BST) where the difference between heights of left and right subtrees cannot be more than one for all nodes. In this project, we’re asked to develop and test a small AVL Tree (i.e., the entire tree resides in main memory). The data is given in the form key with no duplicates and we we’re required to implement an AVL Tree to store the key values. This implementation should support the following operations:

1.	Initialize (): create a new AVL tree

2.	Insert (key)

3.	Delete (key)

4.	Search (key): returns the key if present in the tree else NULL

5.	Search (key1, key2): returns keys that are in the range key1 ≤ key ≤ key2

### *For more information, refer to the pdf file named description*

## To Compile and Run:
-----------------------
- make (javac -g avltree.java)

- java avltree [name_of_file] (i.e., avl.txt, test1.txt,...etc.)


## To Remove Class Files:
--------------------------
- make clean ($RM *.class)
