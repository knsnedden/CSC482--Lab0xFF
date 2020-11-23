package com.company;

public class Main {

    public static void main(String[] args) {
	    float[][] arr = Generate.generateRandomEuclideanCostMatrix(5,10, 10);

	    System.out.println("     |   0    |   1    |   2    |   3    |   4    |");
	    for (int i = 0; i < 5; ++i){
	        System.out.printf("  %d  |", i);
	        for (int j = 0; j < 5; ++j){
	            System.out.printf("  %.2f  |", arr[i][j]);
            }
	        System.out.println();
        }
    }


}
