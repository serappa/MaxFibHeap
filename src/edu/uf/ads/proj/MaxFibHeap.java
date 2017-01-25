package edu.uf.ads.proj;

import java.util.NoSuchElementException;
import java.lang.Math;

/**
 * Created by NIK-MSI on 11/7/2016.
 */
public class MaxFibHeap {
    private FibNode maximum = null;
    private int n;

    public MaxFibHeap(){
        maximum = removeAllElements();
    }

    /**
     *
     * @return
     */
    public FibNode removeAllElements(){
        maximum = null;
        n = 0 ;
        return maximum;
    }

    /**
     * Insert a new Fibonacci Node into the heap
     * @param newNode fibonacci Node
     * @return newly inserted fibonacci Node
     */
    public FibNode insert(FibNode newNode){
        newNode.parent = null;
        newNode.left = newNode;
        newNode.right = newNode;
        newNode.childCut = false;

        if(maximum!=null){
            maximum.insertInCircularList(newNode);
            if(newNode.value > maximum.value){
                maximum = newNode;
            }
        }else{
            maximum = newNode;
        }
        n++;
        return newNode;
    }

    /**
     * Insert a value along with a mapped hashtag into the fibonacci heap
     * @param hashtag
     * @param value
     * @return newly inserted fibonacci node
     */
    public FibNode insert(String hashtag, int value){
        FibNode newNode = new FibNode(hashtag, value);
        return insert(newNode);
    }


    /**
     * Removes that maximum element from the Max Fibonacci heap
     * @return Fibonacci node corresponding to the max element in the heap.
     */
    public FibNode removeMax(){
        if(maximum == null)
            return null;
        //set the max node to be removed to null
        if(maximum.hasChildren()){
            // Traverse through the child level and set all child's parent to null
            FibNode childOfMax = maximum.child;
            int degree = maximum.degree;

            for(int i=0;i<degree;i++){
                FibNode next = childOfMax.right;
                childOfMax.unlinkFromParent();
                insert(childOfMax);
                n--;
                childOfMax = next;
            }
        }
        FibNode retVal = maximum;
        FibNode maxNeighbor = maximum.right;
        if(maximum == maxNeighbor){
            maximum.unlinkFromSiblings();
            maximum = null;
        }
        else{
            maximum.unlinkFromSiblings();
            maximum = maxNeighbor;
            pairwiseMerge();
        }
        n--;
        return retVal;

    }

    /**
     * Perform pairwiseMerge to merge all the nodes that have same degree until all nodes at root level have distinct degrees
     */
    private void pairwiseMerge(){
        double height_f = Math.log(n)/Math.log(2);
        int height = (int) Math.ceil(height_f)+1;
        height = 45;
        //Array to hold degrees
        FibNode[] arrayDegrees = new FibNode[height];
        FibNode start = maximum;
        FibNode node = maximum;
        do {
            FibNode x = node;
            // Because x might be moved, save its sibling now.
            FibNode nextNode = node.right;
            int d = x.degree;
            while (arrayDegrees[d] != null) {
                // Make one of the nodes a child of the other.
                FibNode y = arrayDegrees[d];
                if (x.value < y.value) {
                    //Swap nodes
                    FibNode temp = y;
                    y = x;
                    x = temp;
                }
                if (y == start) {
                    // Because removeMax() arbitrarily assigned the max
                    // reference, we have to ensure we do not miss the
                    // end of the root node list.
                    start = start.right;
                }
                if (y == nextNode) {
                    // If we wrapped around we need to check for this case.
                    nextNode = nextNode.right;
                }
                // Node y disappears from root list.
                y.link(x);

                // We've handled this degree, go to next one.
                arrayDegrees[d] = null;
                d++;
            }

            // Save this node for later when we might encounter another
            // of the same degree.
            arrayDegrees[d] = x;
            // Move forward through list.
            node = nextNode;
        } while (node != start);

        // The node considered to be max may have been changed above.
        maximum = start;
        // Find the maximum key again.
        for (FibNode a : arrayDegrees) {
            if (a != null && (a.value > maximum.value)) {
                maximum = a;
            }
        }
    }

    /**
     * Increase the key for a given Fibonacci node to the given new value
     * @param node Fibonacci node whose key should be increased
     * @param newValue value that needs to be updated
     * @return Fibonacci Node corresponding to the maximum element in the Fibonacci Node
     */
    public FibNode increaseKey(FibNode node, int newValue) {
        if (node.parent == null) {
            node.setValue(newValue);
            if (newValue > maximum.getValue())
                maximum = node;
        } else {
            if (node.parent.getValue() >= newValue) {
                node.setValue(newValue);
            } else {
                node.setValue(newValue);
                FibNode nodeParent = node.parent;
                node.unlinkFromParent();
                insert(node);
                n--;
                // check Child Cut Values
                cascadeCut(nodeParent);

            }
        }
        return maximum;
    }

    /**
     * Cascade cut the node and add the node at root level until childCut is false
     * @param node Fibonacci node to start the cascadeCut
     */
    private void cascadeCut(FibNode node) {
        while (node.hasParent() && node.childCut == true) {
            FibNode parentNode = node.parent;
            node.unlinkFromParent();
            insert(node);
            node = parentNode;
            n--;
        }
        if (node.hasParent())
            node.childCut = true;

    }


    /**
     * Return maximum valued node in the Fibonacci node
     * @return Fibonacci node corresponding to the max element in the heap
     */
    public FibNode maximumValue(){
        if (!isEmpty()) {
            return maximum;
        }
        throw new NoSuchElementException("No elements in the heap");
    }

    /**
     * Return a boolean value indicating whether the heap is empty
     * @return boolean value indication whether the heap is empty
     */
    public boolean isEmpty(){
        return maximum ==null;
    }

}




