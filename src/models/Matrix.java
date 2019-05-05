package models;

public class Matrix {

    private int rows;
    private int columns;
    private double[][] matrix;

    public Matrix() {
        this.rows = 0;
        this.columns = 0;
    }

    public Matrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.matrix = new double[rows][columns];
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public void setRow(Vector3 vector, int rowIndex) {
        this.matrix[rowIndex][0] = vector.getX();
        this.matrix[rowIndex][1] = vector.getY();
        this.matrix[rowIndex][2] = vector.getZ();
    }

    public void setMatrix(double[][] values) {
        this.matrix = values;
        if (this.rows == 0) {
            this.rows = values.length;
            this.columns = values[0].length;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                sb.append(" ").append(this.matrix[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
