public class Tree {
    private Integer value;
    private Tree leftNode;
    private Tree rightNode;

    public Tree(Integer value, Tree leftNode, Tree rightNode) {
        this.value = value;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Tree getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(Tree leftNode) {
        this.leftNode = leftNode;
    }

    public Tree getRightNode() {
        return rightNode;
    }

    public void setRightNode(Tree rightNode) {
        this.rightNode = rightNode;
    }

    @Override
    public String toString() {
        return "Tree{" +
                "value=" + value +
                ", leftNode=" + leftNode +
                ", rightNode=" + rightNode +
                '}';
    }
}
