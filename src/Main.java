import javafx.scene.Camera;
import models.CameraParams;
import models.Matrix;
import models.Triangle;
import models.Vector3;
import operations.MatrixOperations;
import operations.ScanLine;
import operations.Vector3Operations;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static int xMin;
    private static int xMax;
    private static int yMin;
    private static int yMax;

    private static int vertNum;
    private static List<Vector3> vectors = new ArrayList<>();
    private static List<Triangle> triangles = new ArrayList<>();
    private static int width = 600;
    private static int height = 600;

    public static void main(String args[]) throws IOException {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("./src/input/calice2.BYU"));
            String line = reader.readLine();
            String[] line1 = line.split(" ");
            vertNum = Integer.parseInt(line1[0]);
            int aux = 0;
            String[] splitLine;
            line = reader.readLine();
            while (line != null) {
                splitLine = line.split(" ");
                if (aux < vertNum) {
                    vectors.add(new Vector3(Double.parseDouble(splitLine[0]), Double.parseDouble(splitLine[1]), Double.parseDouble(splitLine[2])));
                } else {
                    triangles.add(new Triangle(Integer.parseInt(splitLine[0]), Integer.parseInt(splitLine[1]), Integer.parseInt(splitLine[2])));
                }
                aux++;
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // load camera params
        Scanner scan = new Scanner(System.in);
        JFrame frame = new JFrame("Drawing");
        while (true) {
            ScanLine scanline = new ScanLine();
            CameraParams cameraParams = loadCameraParams();
            Matrix basisChangeMatrix = getBasisChangeMatrix(cameraParams);
            List<Vector3> viewVectors = worldToView(basisChangeMatrix, cameraParams.getC());
            List<Vector3> projectedVectors = projectVectors(cameraParams, viewVectors);
            List<Vector3> rasterizedVectors = scanline.rasterize(triangles, projectedVectors, width, height);

            DrawPanel panel = new DrawPanel(rasterizedVectors);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(panel);
            frame.setSize(width, height);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            System.out.println("Press any button + enter to reload camera params");
            String input = scan.next();
        }
    }

    private static List<Vector3> projectVectors(CameraParams cameraParams, List<Vector3> viewVectors) {
        double xs;
        double ys;
        List<Vector3> projectedVectors = new ArrayList<>();
        for (Vector3 v : viewVectors) {
            xs = cameraParams.getD() * (v.getX() / v.getZ());
            ys = cameraParams.getD() * (v.getY() / v.getZ());

            xs = xs / cameraParams.getHx();
            ys = ys / cameraParams.getHy();

            xs = ((xs + 1) / 2) * width + 0.5;
            ys = height - ((ys + 1) / 2) * height + 0.5;

            projectedVectors.add(new Vector3(xs, ys, 0));
        }

        return projectedVectors;
    }

    private static List<Vector3> worldToView(Matrix basisChangeMatrix, Vector3 C) {
        List<Vector3> viewVectors = new ArrayList<>();
        Matrix m = new Matrix(3, 1);
        Vector3 subtraction;
        Matrix changeResult;

        for (Vector3 P : vectors) {
            subtraction = Vector3Operations.getInstance().subtraction(P, C);
            m.setMatrix(new double[][]{{subtraction.getX()}, {subtraction.getY()}, {subtraction.getZ()}});
            changeResult = MatrixOperations.getInstance().matrixMultiplication(basisChangeMatrix, m);
            viewVectors.add(new Vector3(changeResult.getMatrix()[0][0], changeResult.getMatrix()[1][0], changeResult.getMatrix()[2][0]));
        }
        return viewVectors;
    }

    private static Matrix getBasisChangeMatrix(CameraParams params) {
        // ORTHOGONALIZE V
        Vector3 Vline = Vector3Operations.getInstance().orthogonalize(params.getV(), params.getN());

        //CALCULATE U
        Vector3 U = Vector3Operations.getInstance().getU(params.getN(), Vline);

        //NORMALIZE PARAMS
        Vector3 normVline = Vector3Operations.getInstance().normalizeVector(Vline);
        Vector3 normN = Vector3Operations.getInstance().normalizeVector(params.getN());
        Vector3 normU = Vector3Operations.getInstance().normalizeVector(U);

        //CREATE BASIS CHANGE MATRIX
        Matrix m1 = new Matrix(3, 3);
        m1.setRow(normU, 0);
        m1.setRow(normVline, 1);
        m1.setRow(normN, 2);

        return m1;
    }

    private static CameraParams loadCameraParams() throws IOException {
        CameraParams cParams = new CameraParams();
        BufferedReader reader = new BufferedReader(new FileReader("./src/camera-input/input.txt"));
        String line = reader.readLine();
        while (line != null) {
            cParams.addParam(line);
            line = reader.readLine();
        }
        return cParams;
    }

    private static void findMinMax(List<Vector3> vectors) {
        Double xMinAux = Double.MAX_VALUE;
        Double xMaxAux = Double.MIN_VALUE;
        Double yMinAux = Double.MAX_VALUE;
        Double yMaxAux = Double.MIN_VALUE;

        for (Vector3 vector : vectors) {
            if (vector.getX() < xMinAux)
                xMinAux = vector.getX();
            if (vector.getX() > xMaxAux)
                xMaxAux = vector.getX();
            if (vector.getY() < yMinAux)
                yMinAux = vector.getY();
            if (vector.getY() > yMaxAux)
                yMaxAux = vector.getY();
        }

        xMin = xMinAux.intValue();
        xMax = xMaxAux.intValue();
        yMax = yMaxAux.intValue();
        yMin = yMinAux.intValue();
//        int[] minMax = {xMin.intValue(), xMax.intValue(), yMin.intValue(), yMax.intValue()};
//        return minMax;
    }

    private static Double normalizeX(double x, int w, int h) {
        double xn = ((x - xMin) / (xMax - xMin)) * (w - 1);
        return xn;
    }

    private static Double normalizeY(double y, int w, int h) {
        double yn = ((y - yMin) / (yMax - yMin)) * (h - 1);
        return yn;
    }

}
