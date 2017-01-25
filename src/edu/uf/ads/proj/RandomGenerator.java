package edu.uf.ads.proj;

import java.io.*;
import java.util.*;

/**
 * Created by hsitas444 on 11/12/2016.
 */
public class RandomGenerator {
    public static void main(String args[]){
        int uniqueStringCount = 10000000;
        final String hash = "#";
        final String stop = "stop";
        final String space = " ";
        Set<String> set = new HashSet<>();
        while(set.size()<uniqueStringCount){
            set.add(UUID.randomUUID().toString().substring(0,10));
        }
        List<String> uniqueStringList = new ArrayList<>(set);
        Random rand = new Random();
        try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter( new FileOutputStream("sampleInput_10e6.txt")))){
            for(int i=0;i<uniqueStringCount;i++){
                String line = uniqueStringList.get(i);
                writer.append(hash).append(line).append(space).append(Integer.toString(rand.nextInt(21)+1)).append(System.lineSeparator());
                if(i%100 ==0){
                    //1% of the times incraseKey is called
                    line = uniqueStringList.get(rand.nextInt(i+1));
                    //Add key such that increasekey is called
                    writer.append(hash).append(line).append(space).append(Integer.toString(rand.nextInt(21)+1)).append(System.lineSeparator());
                }
                if(i% 100000 == 0 && i > 21){
                    writer.append(Integer.toString(rand.nextInt(21)+1)).append(System.lineSeparator());
                }
            }
            writer.write(stop);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
