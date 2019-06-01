package operations;

import models.ColorParams;
import models.Triangle;
import models.Vector3;

import java.awt.*;
import java.util.List;

public class ColorCalculator {

    private List<Vector3> normalizedEdges;
    private ColorParams colorParams;
    private Color[][] colors;

    public ColorCalculator(List<Vector3> normalizedEdges, ColorParams colorParams, int screenWidth, int screenHeight) {
        this.normalizedEdges = normalizedEdges;
        this.colorParams = colorParams;
        this.colors = new Color[screenWidth][screenHeight];
        for(int i = 0; i < colors.length; i++){
            for (int j = 0; j < colors[i].length; j++){
                colors[i][j] = new Color(0,0,0);
            }
        }

    }

    public Color calculate(Vector3 barycentricCord, Triangle triangle, Vector3 P) {

        Vector3 N = getN(barycentricCord, triangle);


        Vector3 V = Vector3Operations.getInstance().scalarMultiplication(-1, P);
        V = Vector3Operations.getInstance().normalizeVector(V);
//        System.out.println(P);

        Vector3 L = Vector3Operations.getInstance().subtraction(this.colorParams.getPl(), P);
        L = Vector3Operations.getInstance().normalizeVector(L);

        double prodNL = Vector3Operations.getInstance().dotProduct(N, L);
        double prodVN = Vector3Operations.getInstance().dotProduct(V, N);
        boolean hasId = true;
        boolean hasIs = true;

        if (prodNL < 0) {
            if (prodVN < 0) {
//                System.out.println("Inverteu N");
                N = Vector3Operations.getInstance().scalarMultiplication(-1, N);
                prodNL = Vector3Operations.getInstance().dotProduct(N, L);
//                prodVN = Vector3Operations.getInstance().dotProduct(V,N);
            } else {
                hasId = false;
                hasIs = false;
            }
        }
        double NL2 = 2 * prodNL;
        Vector3 aux = Vector3Operations.getInstance().scalarMultiplication(NL2, N);
        Vector3 R = Vector3Operations.getInstance().subtraction(aux, L);


        double prodRV = Vector3Operations.getInstance().dotProduct(R, V);

        if (prodRV < 0) {
            hasIs = false;
        }

        Vector3 Ia;
        Vector3 Id;
        Vector3 Is;

        Ia = getIa();

        if (hasId) {
            Id = getId(prodNL);
        } else {
            Id = new Vector3(0, 0, 0);
        }

        if (hasIs) {
            Is = getIs(prodRV);
        } else {
//            System.out.println("Entrou2");
            Is = new Vector3(0, 0, 0);
        }

        double r = Ia.getX() + Id.getX() + Is.getX();
        if (r > 255)
            r = 255;

        double g = Ia.getY() + Id.getY() + Is.getZ();
        if (g > 255)
            g = 255;

        double b = Ia.getZ() + Id.getZ() + Is.getZ();
        if (b > 255)
            b = 255;

//        System.out.println("Ia" + Ia);
//        System.out.println("Id" + Id);
//        System.out.println("Is" + Is);

        return new Color((int) r, (int) g, (int) b);
    }

    private Vector3 getIa() {
        Vector3 Ia = Vector3Operations.getInstance().scalarMultiplication(this.colorParams.getKa(), this.colorParams.getIamb());
        Ia = new Vector3((int) Ia.getX(), (int) Ia.getY(), (int) Ia.getZ());
        return Ia;
    }

    private Vector3 getId(double prodNL) {
        double x = prodNL * this.colorParams.getKd().getX() * this.colorParams.getOd().getX() * this.colorParams.getIl().getX();
        double y = prodNL * this.colorParams.getKd().getY() * this.colorParams.getOd().getY() * this.colorParams.getIl().getY();
        double z = prodNL * this.colorParams.getKd().getZ() * this.colorParams.getOd().getZ() * this.colorParams.getIl().getZ();

        Vector3 Id = new Vector3( x,  y,  z);
//        System.out.println(prodNL);
        return Id;
    }

    private Vector3 getIs(double prodRV) {
        double x = Math.pow(prodRV, this.colorParams.getEta()) * this.colorParams.getKs() * this.colorParams.getIl().getX();
        double y = Math.pow(prodRV, this.colorParams.getEta()) * this.colorParams.getKs() * this.colorParams.getIl().getY();
        double z = Math.pow(prodRV, this.colorParams.getEta()) * this.colorParams.getKs() * this.colorParams.getIl().getZ();

        return new Vector3((int) x, (int) y, (int) z);
    }

    private Vector3 getN(Vector3 barycentricCord, Triangle triangle) {
        Vector3 v1 = normalizedEdges.get(triangle.getV1() - 1);
        Vector3 v2 = normalizedEdges.get(triangle.getV2() - 1);
        Vector3 v3 = normalizedEdges.get(triangle.getV3() - 1);

        Vector3 alfaN1 = Vector3Operations.getInstance().scalarMultiplication(barycentricCord.getX(), v1);
        Vector3 betaN2 = Vector3Operations.getInstance().scalarMultiplication(barycentricCord.getY(), v2);
        Vector3 gamaN3 = Vector3Operations.getInstance().scalarMultiplication(barycentricCord.getZ(), v3);

        double nx = alfaN1.getX() + betaN2.getX() + gamaN3.getX();
        double ny = alfaN1.getY() + betaN2.getY() + gamaN3.getY();
        double nz = alfaN1.getZ() + betaN2.getZ() + gamaN3.getZ();

        Vector3 N = new Vector3(nx, ny, nz);
        N = Vector3Operations.getInstance().normalizeVector(N);
//        System.out.println(N);
        return N;
    }

    public void setColor(Color c, int w, int h){
        this.colors[w][h] = c;
    }

    public Color[][] getColors(){
        return this.colors;
    }

}
