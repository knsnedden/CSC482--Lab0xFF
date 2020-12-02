package com.company;

public class Main {

    public static void main(String[] args) {
	   /* float[][] arr = Generate.generateRandomCircularGraphCostMatrix(6,10);

	    System.out.println("     |    0    |    1    |    2    |    3    |    4    |    5   |");
	    for (int i = 0; i < 6; ++i){
	        System.out.printf("  %d  |", i);
	        for (int j = 0; j < 6; ++j){
	            System.out.printf("  %5.2f  |", arr[i][j]);
            }
	        System.out.println();
        }

	    int[] result = TSP.TspBrute(arr,6);
	    for (int i = 0; i < 6; ++i){
	    	System.out.printf("%d ", result[i]);
		}*/
		TSP.BruteCorrectness();
    }




}
