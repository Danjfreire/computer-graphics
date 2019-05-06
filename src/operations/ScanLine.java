package operations;

import models.Matrix;
import models.Triangle;
import models.Vector3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ScanLine {

    private double slopeMax;

    public List<Vector3> rasterize(List<Triangle> triangles, List<Vector3> projectedVectors, int screenW, int screenH) {
        List<Vector3> result = new ArrayList<>();

        for (Triangle t : triangles) {
            List<Vector3> vectors = new ArrayList<>();
            vectors.add(projectedVectors.get(t.getV1() - 1));
            vectors.add(projectedVectors.get(t.getV2() - 1));
            vectors.add(projectedVectors.get(t.getV3() - 1));
            Collections.sort(vectors);
            Vector3 v1 = vectors.get(0);
            Vector3 v2 = vectors.get(1);
            Vector3 v3 = vectors.get(2);

            if(v2.getY() == v3.getY()){
                bottomFlatTriangle(v1,v2,v3, result);
            }else if(v1.getY() == v2.getY()){
                topFlatTriangle(v1,v2,v3, result);
            }else{
                double aux = v1.getX() + ((v2.getY() - v1.getY()) / (v3.getY() - v1.getY()) *(v3.getX() - v1.getX()));
                Vector3 v4 = new Vector3(aux, vectors.get(1).getY(),0);
                topFlatTriangle(v1,v2,v4,result);
                bottomFlatTriangle(v2,v4,v3,result);
            }
        }
        return result;
    }

    private void topFlatTriangle(Vector3 v1, Vector3 v2, Vector3 v3, List<Vector3> result) {
        double slopeMin;
        if(v2.getX() >= v1.getX()){
           slopeMin = (v3.getY() - v1.getY()) / (v3.getX() - v1.getX());
           slopeMax = (v2.getY() - v1.getY()) / (v2.getX() - v1.getX());
        }else {
            slopeMin = (v2.getY() - v1.getY()) / (v2.getX() - v1.getX());
            slopeMax = (v3.getY() - v1.getY()) / (v3.getX() - v1.getX());
        }

        double xMin = v1.getX();
        double xMax = v1.getX();

        for( double scan = v1.getY() ; scan <= v3.getY(); scan++){
            double aux = xMin;
            while(aux <= xMax ){
                result.add(new Vector3(aux, scan,0));
                aux++;
            }
            xMin += (1.0/slopeMin);
            xMax += (1.0/slopeMax);
        }
    }

    private void bottomFlatTriangle(Vector3 v1, Vector3 v2, Vector3 v3, List<Vector3> result) {
        double slopeMin;
        double slopeMax;
        if(v2.getX() >= v1.getX()){
            slopeMin = (v1.getY() - v3.getY()) / (v1.getX() - v3 .getX());
            slopeMax = (v2.getY() - v3.getY()) / (v2.getX() - v3.getX());
        }else{
            slopeMin = (v2.getY() - v3.getY()) / (v2.getX() - v3.getX());
            slopeMax = (v1.getY() - v3.getY()) / (v1.getX() - v3 .getX());
        }

        double xMin = v3.getX();
        double xMax = v3.getX();

        for( double scan = v3.getY() ; scan >= v2.getY(); scan--){
            double aux = xMin;
            while(aux <= xMax){
                result.add(new Vector3(aux, scan,0));
                aux++;
            }
            xMin -= (1.0/slopeMin);
            xMax -= (1.0/slopeMax);
        }
    }

}
