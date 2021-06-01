import java.util.*;

public class Tree {
    public static void main(String[] ars){
        
        TreeNode root = new TreeNode(1);
        root.addLeft(2);
        root.addRight(3);
        root.getLeft().addLeft(4);
        root.getLeft().addRight(5);
        root.getRight().addLeft(6);
        root.getRight().addRight(7);
        root.getRight().getLeft().addLeft(8);
        root.getRight().getLeft().addRight(9);
        root.getRight().getRight().addLeft(10);
        printTree(root);
        reverseTree(root);
        System.out.println();
        printTree(root);
    }
    public static void reverseTree(TreeNode root){
        /*TreeNode temp;
        temp = root.left;
        root.left = root.right;
        root.right = temp;*/
        if (!Objects.isNull(root.left ))
            reverseTree(root.left);
        if (!Objects.isNull(root.right))
            reverseTree(root.right);
        
    }

    public static void printTree(TreeNode root){
         System.out.print(root.data);
        //System.out.println();

        if (!Objects.isNull(root.left )){
            printTree(root.left);
        }
        //System.out.print(root.data);
        if (!Objects.isNull(root.right)){
            printTree(root.right);}
       // System.out.print(root.data);
    }
    }

