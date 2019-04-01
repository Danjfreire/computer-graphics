package operations;

import models.Matrix;

public class MatrixOperations {

    private static MatrixOperations instance;

    private MatrixOperations(){}

    public static MatrixOperations getInstance(){
        if(instance == null){
            instance = new MatrixOperations();
        }
        return instance;
    }

    public Matrix matrixMultiplication(Matrix entry1, Matrix entry2){
        if(entry1.getColumns() != entry2.getRows()){
            System.out.println("Operação inválida");
            return null;
        }

        double[][] matrix1 = entry1.getMatrix();
        double[][] matrix2 = entry2.getMatrix();
        Matrix resultMatrix = new Matrix(entry1.getRows(), entry2.getColumns());
        double [][] values = resultMatrix.getMatrix();

        for(int i = 0; i < entry1.getRows(); i++) {
            for (int j = 0; j < entry2.getColumns(); j++) {
                for (int k = 0; k < entry1.getColumns(); k++) {
                    values[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }

        resultMatrix.setMatrix(values);

        return resultMatrix;
    }
}
