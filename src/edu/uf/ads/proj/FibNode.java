package edu.uf.ads.proj;

/**
 * Created by NIK-MSI on 11/7/2016.
 */
public class FibNode {
    String hashtag;
    int value;
    FibNode parent;
    FibNode child;
    FibNode right;
    FibNode left;
    int degree;
    boolean childCut =false;

    /**
     *
     * @param hashtag
     * @param value
     */
    public FibNode(String hashtag, int value) {
        this.hashtag = hashtag;
        this.value = value;
        right = this;
        left = this;
        this.degree = 0;
        this.childCut = false;
    }

    /**
     * Get the hashtag name corresponding to the Fibonacci heap
     * @return
     */
    public String getHashtag() {
        return hashtag;
    }

    /**
     * Get Fibonacci node value
     * @return Fibonacci node value
     */
    public int getValue() {
        return value;
    }

    /**
     * Set Fibonacci node value
     * @param value new value to be set.
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Inserts the given node in the circular linked list
     * @param node Fibonacci Node that needs to be inserted
     */
    public void insertInCircularList(FibNode node){
        node.right = this;
        node.left = this.left;
        this.left = node;
        node.left.right = node;
    }

    /**
     * Tells whether the given Fibonacci node has a parent
     * @return Boolean value indicating whether the node has a parent
     */
    public boolean hasParent(){
        return parent!=null;
    }

    /**
     * Tells whether the given node has Children
     * @return Boolean value indicating whether the node has children
     */
    public boolean hasChildren(){
        return child != null;
    }

    /**
     * Tells whether the given node has Siblings
     * @return Boolean value indicating whether the node has siblings
     */
    public boolean hasSiblings(){
        return this.left != this;
    }

    /**
     * Tells whether the given node is part of the child pointer of the node's parent
     * @return Boolean value indicating whether the node is part of the child pointer of the node's parent
     */
    public boolean isChildLink(){
        return parent.child == this;
    }

    /**
     * Unlinks the node from its siblings if any
     */
    public void unlinkFromSiblings(){
        left.right = right;
        right.left = left;
    }

    /**
     * Unlinks the node from its parent
     */
    public void unlinkFromParent(){
        if(this.parent!=null){
            // changing the child of parent if needed
            if (isChildLink()) {
                if (!hasSiblings())
                    parent.child = null;
                else {
                    unlinkFromSiblings();
                    parent.child = left;
                }
            } else {
                unlinkFromSiblings();
            }
            parent.degree--;
            parent = null;
            childCut = false;
            left = right = this;
        }
    }

    /**
     * Links the node to the given parent node
     * @param parent Parent Fibonacci node that linked a parent to this node
     */
    public void link(FibNode parent) {
        left.right = right;
        right.left = left;
        this.parent = parent;
        if (!parent.hasChildren()) {
            parent.child = this;
            right = this;
            left = this;
        } else {
            left = parent.child;
            right = parent.child.right;
            parent.child.right = this;
            right.left = this;
        }
        parent.degree++;
        childCut = false;
    }

    @Override
    public String toString() {
        return hashtag+":"+value;
    }
}
