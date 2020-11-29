package com.company;

import java.util.ArrayList;
import java.util.List;

public class TSP {

    // O(N^2*log2(N))
    public static int[] TspGreedy(float[][] arr, int n){
        int[] result = new int[n+1];
        int counter = 1, i = 0, j = 0;
        float min = Integer.MAX_VALUE, sum = 0;
        List<Integer> visited = new ArrayList<>();

        visited.add(0); // visit home node
        result[0] = 0;

        // go through cost matrix
        while(i < n && j < n){
            if (counter >= n){
                break;
            }

            if (i != j && !visited.contains(j)){ // if it hasn't been visited and if the cost is a min, store cost
                if (arr[i][j] < min){
                    min = arr[i][j];
                    result[counter] = j;
                }
            }

            ++j;
            // look at all paths from i
            if (j == n){
                sum += min;
                min = Integer.MAX_VALUE;
                visited.add(result[counter]);
                j = 0;
                i = result[counter];
                counter++;
            }

        }

        i = result[counter-1];

        for (j = 0; j < n; ++j){
            if (i != j && arr[i][j] < min){
                min = arr[i][j];
                result[counter] = j;
            }
        }
        sum += min;

        //testing purposes
        System.out.println(sum);

        return result;
    }
}
