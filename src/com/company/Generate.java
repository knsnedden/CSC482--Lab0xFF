package com.company;

import java.util.Random;

public class Generate {
    public static int[][] generateRandomCostMatrix(int size, int maxCost){
        int[][] arr = new int[size][size];
        Random newRand = new Random();
        int rand;

        for (int i = 0; i < size; ++i){
            for (int j = i; j < size; ++j){
                if (i == j){
                    arr[i][j] = 0;
                    arr[j][i] = 0;
                }else {
                    rand = newRand.nextInt(maxCost);
                    arr[i][j] = rand;
                    arr[j][i] = rand;
                }

            }
        }

        return arr;
    }
}
