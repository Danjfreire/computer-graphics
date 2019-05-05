import models.Matrix;
import models.Vector3;
import operations.MatrixOperations;
import operations.ScanLine;
import operations.Vector3Operations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Test {

    public static void main(String[] args){


//        for(double i = 2.1; i < 10.5; i++){
//            System.out.println(i);
//        }

        List<Vector3> list = new ArrayList<>();
        list.add(new Vector3(1,2,3));
        list.add(new Vector3(3,1,2));
        list.add(new Vector3(1,3,2));

        sortTriangle(list);




//        Vector3 V = new Vector3(0,0,1);
//        Vector3 N = new Vector3(-1,-1,-1);
//
//        Vector3 Vline = Vector3Operations.getInstance().orthogonalize(V, N);
//        Vector3 U = Vector3Operations.getInstance().getU(N,Vline);
////        System.out.println(Vline);
////        System.out.println(U);
//
//        Vector3 normVline = Vector3Operations.getInstance().normalizeVector(Vline);
//        Vector3 normN = Vector3Operations.getInstance().normalizeVector(N);
//        Vector3 normU = Vector3Operations.getInstance().normalizeVector(U);
////        System.out.println(normVline);
////        System.out.println(normN);
////        System.out.println(normU);
//
//        Matrix m1 = new Matrix(3,3);
//        m1.setRow(normU, 0);
//        m1.setRow(normVline, 1);
//        m1.setRow(normN, 2);
//
//        System.out.println(m1);
//
//        Matrix m2 = new Matrix(3,1);
//        Vector3 P = new Vector3(1,-3,-5);
//        Vector3 C = new Vector3(1,1,2);
//        Vector3 subtraction = Vector3Operations.getInstance().subtraction(P, C);
//
//        m2.setMatrix(new double[][]{{subtraction.getX()},{subtraction.getY()},{subtraction.getZ()}});
////        System.out.println(m2);
//
//        Matrix matrixR = MatrixOperations.getInstance().matrixMultiplication(m1, m2);
////        System.out.println(matrixR);
    }


    private static List<Vector3> sortTriangle(List<Vector3> vectors) {
        Collections.sort(vectors);
        for(Vector3 v : vectors){
            System.out.println(v);
        }
        return null;
    }
}
