package com.company;

import java.util.Random;
import java.lang.Math;

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

    public static float[][] generateRandomEuclideanCostMatrix(int size, int maxX, int maxY){
        float[][] cost = new float[size][size];
        int[][] coord = new int[size][2];
        Random newRand = new Random();
        int rand;
        float result;

        // create random coordinates
        for (int i = 0; i < size; ++i){
            rand = newRand.nextInt(maxX);
            coord[i][0] = rand;
            rand = newRand.nextInt(maxY);
            coord[i][1] = rand;
        }

        // calculate the costs
        for (int i = 0; i < size; ++i){
            for (int j = i; j < size; ++j){
                double x = Math.pow(coord[i][0] - coord[j][0],2);
                double y = Math.pow(coord[i][1] - coord[j][1],2);
                result = (float)(Math.sqrt(x+y));
                cost[i][j] = result;
                cost[j][i] = result;
            }
        }

        return cost;
    }
}
