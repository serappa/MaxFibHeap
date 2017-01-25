package edu.uf.ads.proj;

import java.io.*;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by NIK-MSI on 11/10/2016.
 */
public class hashtagcounter {
    public static void main(String[] args) {
        if(args.length < 1) {
            System.out.println("Invalid input");
            System.out.println("$java hashtagcounter file-name");
            System.exit(1);
        }
        String filename = args[0];
        MaxFibHeap fheap = new MaxFibHeap();
        Map<String, FibNode> map = new Hashtable<>();
        //Initializing the tree
        String line = "";

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)))){
            do{
                line = reader.readLine();
                if(line.startsWith("#")){
                    String[] hashtag = line.split(" ");
                    FibNode node = map.get(hashtag[0]);
                    if(node == null ){
                        FibNode max = fheap.insert(hashtag[0],Integer.valueOf(hashtag[1]));
                        map.put(hashtag[0],max);
                    }
                    else{
                        int currentValue =  node.value;
                        fheap.increaseKey(node, currentValue+Integer.valueOf(hashtag[1]));
                    }
                }
                else if(line.equalsIgnoreCase("stop")){
                    System.out.println("Exiting");
                    System.exit(1);
                }
                else{
                    int queryValue = Integer.valueOf(line);
                    FibNode[] removedNodes = new FibNode[queryValue];
                    for(int i = 0; i<queryValue; i++){
                        FibNode removed_max = fheap.removeMax();
                        removedNodes[i] = removed_max;
                        if(i!=0){
                            System.out.print(","+removedNodes[i]);
                        }
                        else{
                            System.out.print(removedNodes[i]);
                        }
                    }
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    for(int i = 0; i<queryValue; i++){
                        if(removedNodes[i]!= null)
                            fheap.insert(removedNodes[i]);
                    }
                }
            }while(true);
        } catch(NumberFormatException e){
            System.out.println("Failed to parse Number in command: "+ line);
        } catch (IOException e){
            System.out.println("Failed to read Input Stream");
        }


    }
}
