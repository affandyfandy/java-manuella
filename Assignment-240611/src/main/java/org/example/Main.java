package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Assignment 3
        System.out.println("Assignment 3");
        List<Integer> list0 = new ArrayList<Integer>(Arrays.asList(1,4,3,-6,5,4));
        int maxNum = list0.get(0);
        for(int k = 0; k < list0.size(); k++) {
            if (list0.get(k) > maxNum) {
                maxNum = list0.get(k);
            }
        }

        int secMaxNum = list0.get(0);
        for(int k = 0; k < list0.size(); k++) {
            if (list0.get(k) > secMaxNum &&  list0.get(k) != maxNum) {
                secMaxNum = list0.get(k);
            }
        }

        List listIdx = new ArrayList<Integer>();
        for(int k = 0; k < list0.size(); k++){
            if (list0.get(k) == secMaxNum){
                listIdx.add(k);
            }
        }
        System.out.println(listIdx);

        // Assignment 4
        System.out.println("Assignment 4");
        List<Integer> list = new ArrayList<Integer>(Arrays.asList(1,2,3,-6,5,0));
        int i;
        int j = 0;
        for(i = 0; i < list.size(); i++){
            int sum = list.get(i);
            j = i+1;
            while (j < list.size() && sum != 0){
                sum += list.get(j);
                j += 1;
            }
            if (sum == 0){
                System.out.println("i: " + i);
                System.out.println("j: " + (j-1));
                break;
            }
        }

    }
}