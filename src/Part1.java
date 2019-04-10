import models.Matrix;
import models.Vector3;
import operations.MatrixOperations;
import operations.Vector3Operations;

import java.util.Vector;

public class Part1 {
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

        System.out.println("c)");
        double result3 = Vector3Operations.getInstance().dotProduct(new Vector3(3.5,1.5,2.0), new Vector3(1,2,1.5));
        System.out.println(result3);

        System.out.println("d)");
        Vector3 result4 = Vector3Operations.getInstance().crossProduct(new Vector3(3.5,1.5,2), new Vector3(1,2,1.5));
        System.out.println(result4);

        System.out.println("e)");
        double result5 = Vector3Operations.getInstance().vectorNorm(new Vector3(3.5,1.5,2));
        System.out.println(result5);

        System.out.println("f)");
        Vector3 result6 = Vector3Operations.getInstance().normalizeVector(new Vector3(3.5,1.5,2));
        System.out.println(result6);

        System.out.println("g)");
        Vector3 result7 = Vector3Operations.getInstance().barycentricCoordinates(new Vector3(-0.25,0.75,0), new Vector3(-1,1,0), new Vector3(0,-1,0), new Vector3(1,1,0));
        System.out.println(result7);

        System.out.println("h)");
        Vector3 result8 = Vector3Operations.getInstance().barycentricReverse(new Vector3(0.5,0.25,0.25),new Vector3(-1,1,0), new Vector3(0,-1,0), new Vector3(1,1,0));
        System.out.println(result8);
    }


}
