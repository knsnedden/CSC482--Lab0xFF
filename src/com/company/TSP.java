package com.company;

import java.util.ArrayList;
import java.util.List;

public class TSP {

    // O(N^2*log2(N))
    public static int[] TspGreedy(float[][] arr, int n){
        int[] result = new int[n+1];
        int counter = 1, i = 0, j = 0;
        float min = Integer.MAX_VALUE;
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

        return result;
    }

    public static void GreedyCorrectness(){
        System.out.println("Generating circular graph of size 40 with radius 100...");
        float[][] graph1 = Generate.generateRandomCircularGraphCostMatrix(40,100);
        System.out.println("Calculating smallest distance between vertices around the circle...");
        double stepAngle = (Math.PI * 2)/40;
        float[][] coords = new float[2][2];
        for (int i = 0; i < 2; ++i){
            coords[i][0] = (float)(100 * Math.sin(i * stepAngle));
            coords[i][1] = (float)(100 * Math.cos(i * stepAngle));
        }
        double x = Math.pow(coords[0][0] - coords[1][0],2);
        double y = Math.pow(coords[0][1] - coords[1][1],2);
        float dist = (float)(Math.sqrt(x+y));
        System.out.printf("Smallest distance is: %.2f\n", dist);
        System.out.println("The path between the vertices in the result of the greedy algorithm should equal this distance.");
        System.out.println("Testing...");
        int[] result = TspGreedy(graph1,40);
        boolean accurate = true;
        for (int i = 0; i < 39; ++i){
            float a = graph1[result[i]][result[i+1]];
            if ((int)a != (int)dist){
                accurate = false;
            }
        }
        System.out.println("Does it work? " + accurate);
        System.out.println();
        System.out.println("Generating circular graph of size 60 with radius 100...");
        float[][] graph2 = Generate.generateRandomCircularGraphCostMatrix(60,100);
        System.out.println("Calculating smallest distance between vertices around the circle...");
        stepAngle = (Math.PI * 2)/60;
        for (int i = 0; i < 2; ++i){
            coords[i][0] = (float)(100 * Math.sin(i * stepAngle));
            coords[i][1] = (float)(100 * Math.cos(i * stepAngle));
        }
        x = Math.pow(coords[0][0] - coords[1][0],2);
        y = Math.pow(coords[0][1] - coords[1][1],2);
        dist = (float)(Math.sqrt(x+y));
        System.out.printf("Smallest distance is: %.2f\n", dist);
        System.out.println("The path between the vertices in the result of the greedy algorithm should equal this distance.");
        System.out.println("Testing...");
        result = TspGreedy(graph2,60);
        accurate = true;
        for (int i = 0; i < 59; ++i){
            float a = graph2[result[i]][result[i+1]];
            if ((int)a != (int)dist){
                accurate = false;
            }
        }
        System.out.println("Does it work? " + accurate);

    }

    public static ArrayList<int[]> permutations = new ArrayList<int[]>();

    //O(n!)
    public static int[] TspBrute(float[][] arr, int size){
        int[] bestTour = new int[size];

        int[] nums = new int[size];
        for (int i = 0; i < size; ++i){
            nums[i] = i;
        }
        permute(nums, 1, size-1);

        float minSum = Integer.MAX_VALUE;
        float x = 0;

        for (int i = 0; i < permutations.size(); ++i){
            for (int j = 0; j < size-1; ++j){
                x += arr[permutations.get(i)[j]][permutations.get(i)[j+1]];
            }
            x += arr[permutations.get(i)[size-1]][0];
            if (x < minSum){
                minSum = x;
                bestTour = permutations.get(i);
            }
            x = 0;
        }

        System.out.println(minSum);


        permutations.clear();
        return bestTour;
    }

    public static void permute(int[] nums, int l, int r){
        if (l == r){
            permutations.add(nums.clone());
        }

        for (int i = l; i <= r; ++i){
            nums = swap(nums, l, i);
            permute(nums, l+1, r);
            nums = swap(nums, l, i);
        }
    }

    public static int[] swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
        return nums;
    }


}
