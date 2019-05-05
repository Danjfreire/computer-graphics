package operations;

import models.Triangle;
import models.Vector3;

import java.util.List;

public class ScanLine {

    public void scanline(List<Triangle> triangles, List<Vector3> projectedVectors){
        int yMax;
        int yMin;
        int xMax;
        int xMin;
        Vector3 vector1;
        Vector3 vector2;
        Vector3 vector3;
        for(Triangle t : triangles){
            vector1 = projectedVectors.get(t.getV1() - 1);
            vector2 = projectedVectors.get(t.getV2() - 1);
            vector3 = projectedVectors.get(t.getV3() - 1);

            

        }
    }
}
