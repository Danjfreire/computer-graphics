package operations;

import models.Matrix;
import models.Triangle;
import models.Vector3;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ScanLine {

    private List<Vector3> originalVertices;
    private int width;
    private int height;
    private double[][] zbuffer;
    private double cameraD;
    private ColorCalculator color;
    private Triangle currentTri;

    public ScanLine(List<Vector3> originalVertices,double cameraD, ColorCalculator color, int width, int height) {
        this.originalVertices = originalVertices;
        this.width = width;
        this.height = height;
        this.cameraD = cameraD;
        this.color = color;
        this.startZbuffer(width, height);
    }

    public List<Vector3> rasterize(List<Triangle> triangles, List<Vector3> projectedVectors) {
        List<Vector3> result = new ArrayList<>();

        for (Triangle t : triangles) {
            this.currentTri = t;
            List<Vector3> vectors = new ArrayList<>();
            vectors.add(projectedVectors.get(t.getV1() - 1));
            vectors.add(projectedVectors.get(t.getV2() - 1));
            vectors.add(projectedVectors.get(t.getV3() - 1));
            Collections.sort(vectors);
            Vector3 v1 = vectors.get(0);
            v1.setZ(cameraD);
            Vector3 v2 = vectors.get(1);
            v2.setZ(cameraD);
            Vector3 v3 = vectors.get(2);
            v3.setZ(cameraD);

//            System.out.println(v1.getY() + ", " + v2.getY() + ", " + v3.getY());

            if (v2.getY() == v3.getY()) {
                bottomFlatTriangle(v1, v2, v3, result);
            } else if (v1.getY() == v2.getY()) {
                topFlatTriangle(v1, v2, v3, result);
            } else {
                double aux = v1.getX() + ((v2.getY() - v1.getY()) / (v3.getY() - v1.getY()) * (v3.getX() - v1.getX()));
                Vector3 v4 = new Vector3(aux, vectors.get(1).getY(), 0);
                topFlatTriangle(v1, v2, v4, result);
                bottomFlatTriangle(v4, v2, v3, result);
            }
        }
        return result;
    }

    private void topFlatTriangle(Vector3 v1, Vector3 v2, Vector3 v3, List<Vector3> result) {
        double slopeMin;
        double slopeMax;
        if (v2.getX() >= v1.getX()) {
//            slopeMin = (v3.getY() - v1.getY()) / (v3.getX() - v1.getX());
//            slopeMax = (v2.getY() - v1.getY()) / (v2.getX() - v1.getX());
            slopeMin = (v1.getX() - v3.getX())/ (v1.getY()-v3.getY());
            slopeMax = (v1.getX() - v2.getX())/ (v1.getY()-v2.getY());
        } else {
            slopeMin = (v1.getX() - v2.getX())/ (v1.getY()-v2.getY());
            slopeMax = (v1.getX() - v3.getX())/ (v1.getY()-v3.getY());
//            slopeMin = (v2.getY() - v1.getY()) / (v2.getX() - v1.getX());
//            slopeMax = (v3.getY() - v1.getY()) / (v3.getX() - v1.getX());
        }


        double xMin = v1.getX();
        double xMax = v1.getX();

        for (double scan = v1.getY() ; scan >= v3.getY(); scan--) {
            double aux = xMin;
            while (aux <= xMax) {
                if(aux >= 0 && aux < this.width && scan >= 0 && scan < this.height) {
                    Vector3 P = new Vector3(aux, scan, this.cameraD);
                    Vector3 barycentricCord = Vector3Operations.getInstance().barycentricCoordinates(P, v1, v2, v3);
                    Vector3 originalP = getOriginalP(barycentricCord);
                    if (originalP.getZ() < zbuffer[(int) aux][(int) scan]) {
                        zbuffer[(int) aux][(int) scan] = originalP.getZ();
                        Color c = color.calculate(barycentricCord, currentTri, P);
                        color.setColor(c, (int) aux, (int) scan);
                        result.add(new Vector3(aux, scan, 0));
                    }
                }
                aux++;
            }
            xMin -= slopeMin;
            xMax -= slopeMax;
        }
    }

    private void bottomFlatTriangle(Vector3 v1, Vector3 v2, Vector3 v3, List<Vector3> result) {
        double slopeMin;
        double slopeMax;
        if (v2.getX() >= v1.getX()) {
//            slopeMin = (v1.getY() - v3.getY()) / (v1.getX() - v3.getX());
//            slopeMax = (v2.getY() - v3.getY()) / (v2.getX() - v3.getX());
            slopeMin = (v1.getX() - v3.getX())/(v1.getY() - v3.getY());
            slopeMax = (v2.getX() - v3.getX())/(v2.getY() - v3.getY());
        } else {
            slopeMin = (v2.getX() - v3.getX())/(v2.getY() - v3.getY());
            slopeMax = (v1.getX() - v3.getX())/(v1.getY() - v3.getY());
//            slopeMin = (v2.getY() - v3.getY()) / (v2.getX() - v3.getX());
//            slopeMax = (v1.getY() - v3.getY()) / (v1.getX() - v3.getX());
        }


        double xMin = v3.getX();
        double xMax = v3.getX();

        for (double scan = v3.getY(); scan <= (int)v2.getY(); scan++) {
            double aux = xMin;
            while (aux <= xMax) {
                if(aux >= 0 && aux < this.width && scan >= 0 && scan < this.height) {
                    Vector3 P = new Vector3(aux, scan, this.cameraD);
                    Vector3 barycentricCord = Vector3Operations.getInstance().barycentricCoordinates(P, v1, v2, v3);
                    Vector3 originalP = getOriginalP(barycentricCord);
                    if (originalP.getZ() < zbuffer[(int) aux][(int) scan]) {
                        zbuffer[(int) aux][(int) scan] = originalP.getZ();
                        Color c = color.calculate(barycentricCord, currentTri, P);
                        color.setColor(c, (int) aux, (int) scan);
                        result.add(new Vector3(aux, scan, 0));
                    }
                }
                aux++;
            }
            xMin += slopeMin;
            xMax += slopeMax;
        }
    }

    private void startZbuffer(int width, int height) {
        this.zbuffer = new double[width][height];
        for (int i = 0; i < this.zbuffer.length; i++) {
            for (int j = 0; j < this.zbuffer[i].length; j++) {
                this.zbuffer[i][j] = Double.MAX_VALUE;
            }
        }
    }

    private Vector3 getOriginalP(Vector3 barycentricCord) {

        double v1x = barycentricCord.getX() * this.originalVertices.get(this.currentTri.getV1()-1).getX();
        double v1y = barycentricCord.getX() * this.originalVertices.get(this.currentTri.getV1()-1).getY();
        double v1z = barycentricCord.getX() * this.originalVertices.get(this.currentTri.getV1()-1).getZ();

//        Vector3 v1 = new Vector3(v1x,v1y,v1z);


        double v2x = barycentricCord.getY() * this.originalVertices.get(this.currentTri.getV2()-1).getX();
        double v2y = barycentricCord.getY() * this.originalVertices.get(this.currentTri.getV2()-1).getY();
        double v2z = barycentricCord.getY() * this.originalVertices.get(this.currentTri.getV2()-1).getZ();

//        Vector3 v2 = new Vector3(v2x,v2y,v2z);

        double v3x = barycentricCord.getZ() * this.originalVertices.get(this.currentTri.getV3()-1).getX();
        double v3y = barycentricCord.getZ() * this.originalVertices.get(this.currentTri.getV3()-1).getY();
        double v3z = barycentricCord.getZ() * this.originalVertices.get(this.currentTri.getV3()-1).getZ();

//        Vector3 v3 = new Vector3(v3x,v3y,v3z);

        double x = v1x + v2x + v3x;
        double y = v1y + v2y + v3y;
        double z = v1z + v2z + v3z;

//        Vector3 alfav1 = Vector3Operations.getInstance().scalarMultiplication(barycentricCord.getX(), v1);
//        Vector3 betav2 = Vector3Operations.getInstance().scalarMultiplication(barycentricCord.getY(), v2);
//        Vector3 gamav3 = Vector3Operations.getInstance().scalarMultiplication(barycentricCord.getZ(), v3);


        return new Vector3(x, y, z);
    }

    private Vector3 getOriginalP2(Vector3 barycentricCord, Vector3 v1, Vector3 v2, Vector3 v3) {

        Vector3 alfaP1 = Vector3Operations.getInstance().scalarMultiplication(barycentricCord.getX(), v1);
        Vector3 betaP2 = Vector3Operations.getInstance().scalarMultiplication(barycentricCord.getY(), v2);
        Vector3 gamaP3 = Vector3Operations.getInstance().scalarMultiplication(barycentricCord.getZ(), v3);

        double px = alfaP1.getX() + betaP2.getX() + gamaP3.getX();
        double py = alfaP1.getY() + betaP2.getY() + gamaP3.getY();
        double pz = alfaP1.getZ() + betaP2.getZ() + gamaP3.getZ();

        return new Vector3(px, py, pz);
    }

}
