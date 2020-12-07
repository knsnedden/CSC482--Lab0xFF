package com.company;

import java.util.ArrayList;
import java.util.List;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class TSP {

    // O(N^2*log2(N))
    public static float greedySum;
    public static int[] TspGreedy(float[][] arr, int n){
        int[] result = new int[n+1];
        int counter = 1, i = 0, j = 0;
        float min = Integer.MAX_VALUE;
        List<Integer> visited = new ArrayList<>();
        greedySum = 0;

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

            if (j == n){
                greedySum += min;
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
        greedySum += min;

        greedySum += arr[result[n-1]][0];


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
    public static float bruteSum = 0;

    //O(n-1!)
    public static int[] TspBrute(float[][] arr, int size){
        bruteSum = 0;
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

        bruteSum = minSum;


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

    public static void BruteCorrectness(){
        for (int i = 5; i < 10; ++i){
            float[][] arr = Generate.generateRandomCircularGraphCostMatrix(i,100);
            System.out.printf("Test: N = %d, Expected cost: %.2f\n", i, i * Generate.adjVCost);
            System.out.println("Sequence and coordinates");
            for (int j = 0; j < i; ++j){
                System.out.printf("           %d          ", Generate.vertices[j]);
            }
            System.out.println();
            for (int j = 0; j < i; ++j){
                System.out.printf("x: %6.2f y: %6.2f | ", Generate.coord[j][0], Generate.coord[j][1]);
            }
            System.out.println();
            System.out.println("Cost matrix");
            System.out.printf("     |");
            for (int j = 0; j < i; ++j){
                System.out.printf("    %d     |", j);
            }
            System.out.println();
            for (int j = 0; j < i; ++j){
                System.out.printf("  %d  |", j);
                for (int k = 0; k < i; ++k){
                    System.out.printf("  %6.2f  |", arr[j][k]);
                }
                System.out.println();
            }
            System.out.println("Running Brute Force algorithm...");
            int[] result = TspBrute(arr,i);
            System.out.printf("Found sequence: ");
            for (int j = 0; j < i; ++j){
                System.out.printf("%d ", result[j]);
            }
            System.out.printf("  Cost of path: %.2f\n\n", bruteSum);

        }
    }

    public static void performanceTesting(){
        int N = 4, prev_N = 0;
        boolean keepGoing = true;
        long bruteTime = 0, prevBrute = 0, greedyTime = 0, prevGreedy = 0, maxTime = (long)Math.pow(2,27);
        long timeBefore = 0, timeAfter = 0;
        float ratio = 0, eRatio = 0;

        System.out.println("         Brute Force                             Greedy");
        System.out.println("  N  |   Time   |   Inc. Ratio  |  Exp. Ratio  |   Time   |  Inc. Ratio  | Exp. Ratio |");

        while (keepGoing){
            System.out.printf("%4d |", N);
            float[][] arr = Generate.generateRandomEuclideanCostMatrix(N, 100,100);

            if (bruteTime <= maxTime){
                timeBefore = getCpuTime();
                TspBrute(arr, N);
                timeAfter = getCpuTime();
                bruteTime = timeAfter - timeBefore;
                System.out.printf("%9d |", bruteTime);
                if (prev_N == 0){
                    System.out.printf("      na       |       na     |");
                }else{
                    ratio = (float)(bruteTime/prevBrute);
                    eRatio = (float)(factorial(N-1)/factorial(prev_N-1));
                    System.out.printf("%14.2f | %12.2f |",ratio,eRatio);
                }
                prevBrute = bruteTime;
            }else{
                System.out.printf("   --     |      --       |      --      |");
            }

            if (greedyTime <= maxTime){
                timeBefore = getCpuTime();
                TspGreedy(arr, N);
                timeAfter = getCpuTime();
                greedyTime = timeAfter - timeBefore;
                System.out.printf("%9d |", greedyTime);
                if (prev_N == 0){
                    System.out.printf("      na       |       na     |");
                }else{
                    ratio = (float)(greedyTime/prevGreedy);
                    eRatio = (float)((Math.pow(N,2) * (Math.log(N)/Math.log(2)))/(Math.pow(prev_N,2) * (Math.log(prev_N)/Math.log(2))));
                    System.out.printf("%14.2f | %12.2f |",ratio,eRatio);
                }
                prevGreedy = greedyTime;
            }else{
                System.out.printf("   --     |      --       |      --      |");
            }

            if (bruteTime >= maxTime && greedyTime >= maxTime){
                keepGoing = false;
            }
            prev_N = N;
            if (bruteTime >= maxTime){
                N = N*2;
            }else{
                N = N+1;
            }
            System.out.println();
        }

    }

    public static int factorial(int n){
        if (n <= 2){
            return n;
        }
        return (n * factorial(n-1));
    }

    public static void solutionQuality(){
        int N = 4;
        long bruteTime = 0, maxTime = (long)Math.pow(2,27);
        float optimalSolution = 0, allGreedySum = 0, sqr = 0;
        long timeBefore = 0, timeAfter = 0;

        System.out.println("  N  |  SQR  | Opt. Sol | Greedy Sol |");

        while (bruteTime <= maxTime){
            System.out.printf("%4d |", N);
            float[][] bArr = Generate.generateRandomEuclideanCostMatrix(N, 100,100);
            float[][] gArr = bArr.clone();

            timeBefore = getCpuTime();
            int[] bruteResult = TspBrute(bArr,N);
            timeAfter = getCpuTime();
            bruteTime = timeAfter - timeBefore;
            optimalSolution = bruteSum;

            int[] greedyResult = new int[N];
            for (int i = 0; i < 10; ++i){
                greedyResult = TspGreedy(gArr, N);
                allGreedySum += greedySum;
            }
            allGreedySum = allGreedySum/10;

            sqr = allGreedySum / optimalSolution;
            System.out.printf("%6.2f |%9.2f | %11.2f", sqr, optimalSolution, allGreedySum);

            N++;
            allGreedySum = 0;
            System.out.println();
        }

    }

    public static long getCpuTime(){
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        return bean.isCurrentThreadCpuTimeSupported() ?
                bean.getCurrentThreadCpuTime() : 0L;
    }


}
