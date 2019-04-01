package models;

import operations.MatrixOperations;
import operations.Vector3Operations;

public class Test {
    public static  void main(String[]args){

        System.out.println("a) ");
        Matrix m1 = new Matrix();
        m1.setMatrix(new double[][] {
                {1.5,2.5,3.5},
                {4.5,5.5,6.5}
        });
        Matrix m2 = new Matrix();
        m2.setMatrix(new double[][]{
                {7.5, 8.5},
                {9.5, 10.5},
                {11.5, 12.5}
        });

        Matrix result1 = MatrixOperations.getInstance().matrixMultiplication(m1, m2);
        System.out.println(result1);

        System.out.println("b) ");
        Vector3 result2 = Vector3Operations.getInstance().subtraction(new Vector3(3.5, 1.5, 2), new Vector3(1.0, 2.0, 1.5));
        System.out.println(result2);
    }
}
