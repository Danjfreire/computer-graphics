package operations;

import models.Vector3;

public class Vector3Operations {
    public static Vector3Operations instance;

    private Vector3Operations(){}

    public static Vector3Operations getInstance(){
        if(instance == null){
            instance= new Vector3Operations();
        }
        return instance;
    }

    public Vector3 addition(Vector3 entry1, Vector3 entry2){
        Vector3 result = new Vector3();
        result.setX(entry1.getX() + entry2.getX());
        result.setY(entry1.getY() + entry2.getY());
        result.setZ(entry1.getZ() + entry2.getZ());
        return result;
    }

    public Vector3 subtraction(Vector3 entry1, Vector3 entry2){
        Vector3 negativeEntry2 = new Vector3();
        negativeEntry2.setX(entry2.getX() * (-1));
        negativeEntry2.setY(entry2.getY() * (-1));
        negativeEntry2.setZ(entry2.getZ() * (-1));

        return this.addition(entry1, negativeEntry2);
    }

    public double dotProduct(Vector3 entry1, Vector3 entry2){
        double xProduct = entry1.getX() * entry2.getX();
        double yProduct = entry1.getY() * entry2.getY();
        double zProduct = entry1.getZ() * entry2.getZ();

        return xProduct + yProduct + zProduct;
    }

    public Vector3 crossProduct(Vector3 entry1, Vector3 entry2){
        double xProduct = (entry1.getY()*entry2.getZ()) - (entry1.getZ()*entry2.getY());
        double yProduct = (entry1.getZ()*entry2.getX()) - (entry1.getX()*entry2.getZ());
        double zProduct = (entry1.getX()*entry2.getY()) - (entry1.getY()*entry2.getX());

        return new Vector3(xProduct,yProduct,zProduct);
    }
}
