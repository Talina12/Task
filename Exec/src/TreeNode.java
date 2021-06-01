public class TreeNode {
    public TreeNode left;
    public TreeNode right;
    public int data;

    public TreeNode(int data) {
        this.data = data;
    }

    public void addLeft(int data){
        this.left = new TreeNode(data);
    }

    public TreeNode getRight() {
        return right;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void addRight(int data){
        this.right = new TreeNode( data);
    }
}

