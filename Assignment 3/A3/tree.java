//class tree_node {
//    int id;
//    int height;
//    tree_node left, right, parent;
//    customer cst;
//    node_heap node;
//}
//public class tree {//method used in previous assignment of organizational hierarchy
//    tree_node rootAVL;
//
//    int max(int a, int b) {
//        if (a == b) return a;
//        return (a > b) ? a : b;
//    }
//
//    int getHeight(tree_node node) {
//        if (node == null)
//            return 0;
//        return node.height;
//    }
//
//    int balancefactor(tree_node node) {
//        if (node == null)
//            return 0;
//        return getHeight(node.left) - getHeight(node.right);
//    }
//
//    tree_node RightRotate(tree_node y) {
//        tree_node x = y.left;
//        tree_node z = x.right;
//        if (y.parent.right == y) {
//            y.parent.right = x;
//        } else {
//            y.parent.left = x;
//        }
//        x.parent = y.parent;
//        x.right = y;
//        if (y != null) {
//            y.parent = x;
//        }
//        y.left = z;
//        if (z != null) {
//            z.parent = y;
//        }
//        y.height = max(getHeight(y.left), getHeight(y.right)) + 1;
//        x.height = max(getHeight(x.left), getHeight(x.right)) + 1;
//        return x;
//    }
//
//    tree_node LeftRotate(tree_node y) {
//        tree_node x = y.right;
//        tree_node z = x.left;
//        if (y.parent.right == y) {
//            y.parent.right = x;
//        } else {
//            y.parent.left = x;
//        }
//        x.parent = y.parent;
//        x.left = y;
//        if (y != null) {
//            y.parent = x;
//        }
//        y.right = z;
//        if (z != null) {
//            z.parent = y;
//        }
//        y.height = max(getHeight(y.left), getHeight(y.right)) + 1;
//        x.height = max(getHeight(x.left), getHeight(x.right)) + 1;
//        return x;
//    }
//
//    tree_node find_tree(int id) {//to search customer id in the tree
//        tree_node curr = rootAVL;
//        while (curr != null) {
//            if (curr.id < id) {//if id id > than node of tree then move right else left
//                curr = curr.right;
//            } else if (curr.id > id) {
//                curr = curr.left;
//            } else {
//                break;
//            }
//        }
//        return curr;
//    }
//
//    public tree_node insert_tree(int id) {//insert id in the tree
//        tree_node new_node = new tree_node();//creating new node of tree
//        new_node.id = id;
//        tree_node curr = rootAVL;
//        //finding an appropriate position to insert node in the tree
//        if (rootAVL == null) {
//            rootAVL = new_node;
//            return rootAVL;
//        } else {
//            tree_node temp_parent = curr;
//            while (curr != null) {
//                temp_parent = curr;
//                if (curr.id > id) {
//                    curr = curr.left;
//                } else {
//                    curr = curr.right;
//                }
//            }
//            //updating attributes
//            if (temp_parent.left == null && temp_parent.right != null) {
//                temp_parent.left = new_node;
//                new_node.parent = curr;
//                new_node.height = 1;
//                return new_node;
//            }
//            if (temp_parent.right == null && temp_parent.left != null) {
//                temp_parent.right = new_node;
//                new_node.parent = temp_parent;
//                new_node.height = 1;
//                return new_node;
//            }
//            //if temp_parent has no child
//            if (temp_parent.id < new_node.id) {
//                temp_parent.right = new_node;
//                new_node.parent = temp_parent;
//                new_node.height = 1;
//            } else {
//                temp_parent.left = new_node;
//                new_node.parent = temp_parent;
//                new_node.height = 1;
//            }
//            //balancing tree
//            if (new_node.parent.parent == null) {
//                new_node.parent.height++;
//                return new_node;
//            }
//            tree_node temp = new_node;
//            while (temp.parent != null) {
//                temp.height = max(getHeight(temp.right), getHeight(temp.left)) + 1;
//                if (balancefactor(temp) > 1) {
//                    if (getHeight(temp.left.left) >= getHeight(temp.left.right)) {
//                        temp = RightRotate(temp);
//                    } else {
//                        temp.left = LeftRotate(temp.left);
//                        temp = RightRotate(temp);
//                    }
//                }
//                if (balancefactor(temp) < -1) {
//                    if (getHeight(temp.right.left) > getHeight(temp.right.right)) {
//                        temp.right = RightRotate(temp.right);
//                    }
//                    temp = LeftRotate(temp);
//                }
//                temp = temp.parent;
//            }
//        }
//        return new_node;
//    }
//}
class avl_node {
    int id, height;
    avl_node left, right;
    customer cst;
    node_heap node;
}

public class tree {
    avl_node root;

    //function to get height of the tree
    int height(avl_node n) {
        if (n == null)
            return 0;
        return n.height;
    }

    // function to get maximum of two integers
    int max(int a, int b) {
        return (a > b) ? a : b;
    }

    // function to right rotate subtree rooted with y
    avl_node rightRotate(avl_node y) {
        avl_node x = y.left;
        avl_node z = x.right;
        // Perform rotation
        x.right = y;
        y.left = z;
        // Update heights
        y.height = max(height(y.left), height(y.right)) + 1;
        x.height = max(height(x.left), height(x.right)) + 1;
        // Return new root
        return x;
    }

    // function to left rotate subtree rooted with x
    avl_node leftRotate(avl_node x) {
        avl_node y = x.right;
        avl_node z = y.left;
        // Perform rotation
        y.left = x;
        x.right = z;
        // Update heights
        x.height = max(height(x.left), height(x.right)) + 1;
        y.height = max(height(y.left), height(y.right)) + 1;
        // Return new root
        return y;
    }

    // Get Balance factor of node n
    int getBalance(avl_node n) {
        if (n == null)
            return 0;
        return height(n.left) - height(n.right);
    }

    //searching in avl tree
    public avl_node find_tree(int id) {//to search customer id in the tree
        avl_node curr = root;
        while (curr != null) {
            if (curr.id < id) {//if id id > than node of tree then move right else left
                curr = curr.right;
            } else if (curr.id > id) {
                curr = curr.left;
            } else {
                break;
            }
        }
        return curr;
    }


    //inserting in avl tree
    public avl_node insert_tree(avl_node node, int key) {
        if (node == null) {
            avl_node n = new avl_node();
            n.height = 1;
            n.left = n.right = null;
            n.id = key;
            return n;//returning new node
        }
        if (key < node.id)//checking in left subtree
        {
            node.left = insert_tree(node.left, key);
        } else if (key > node.id)//checking in right subtree
        {
            node.right = insert_tree(node.right, key);
        } else // Equal keys not allowed
            return node;

        /* for updating height of this ancestor node */
        node.height = 1 + max(height(node.left), height(node.right));

        /*  find the balance factor of this boss node to check whether this node became unbalanced */
        int balance = getBalance(node);

        // If this node becomes unbalanced, then
        // Left Left Case
        if (balance > 1 && key < node.left.id)
            return rightRotate(node);

        // Right Right Case
        if (balance < -1 && key > node.right.id)
            return leftRotate(node);

        // Left Right Case
        if (balance > 1 && key > node.left.id) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && key < node.right.id) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }
}

