import models.Matrix;
import models.Vector3;
import operations.MatrixOperations;
import operations.Vector3Operations;

public class Test {

    public static void main(String[] args){
        Vector3 V = new Vector3(0,0,1);
        Vector3 N = new Vector3(-1,-1,-1);

        Vector3 Vline = Vector3Operations.getInstance().orthogonalize(V, N);
        Vector3 U = Vector3Operations.getInstance().getU(N,Vline);
//        System.out.println(Vline);
//        System.out.println(U);

        Vector3 normVline = Vector3Operations.getInstance().normalizeVector(Vline);
        Vector3 normN = Vector3Operations.getInstance().normalizeVector(N);
        Vector3 normU = Vector3Operations.getInstance().normalizeVector(U);
//        System.out.println(normVline);
//        System.out.println(normN);
//        System.out.println(normU);

        Matrix m1 = new Matrix(3,3);
        m1.setRow(normU, 0);
        m1.setRow(normVline, 1);
        m1.setRow(normN, 2);

        System.out.println(m1);

        Matrix m2 = new Matrix(3,1);
        Vector3 P = new Vector3(1,-3,-5);
        Vector3 C = new Vector3(1,1,2);
        Vector3 subtraction = Vector3Operations.getInstance().subtraction(P, C);

        m2.setMatrix(new double[][]{{subtraction.getX()},{subtraction.getY()},{subtraction.getZ()}});
//        System.out.println(m2);

        Matrix matrixR = MatrixOperations.getInstance().matrixMultiplication(m1, m2);
//        System.out.println(matrixR);
    }
}
