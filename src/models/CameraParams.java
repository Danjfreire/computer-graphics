package models;

public class CameraParams {
    private Vector3 N;
    private Vector3 V;
    private int d;
    private int hx;
    private int hy;
    private Vector3 C;
    private int currentParam = 1;

    public void addParam(String line){

        switch (this.currentParam){
            case 1: this.N = getVector(line); break;
            case 2:this.V = getVector(line); break;
            case 3:this.d = Integer.parseInt(line); break;
            case 4:this.hx = Integer.parseInt(line); break;
            case 5:this.hy = Integer.parseInt(line); break;
            case 6: this.C = getVector(line); break;
        }
        this.currentParam++;
    }

    private Vector3 getVector(String line){
        String[] split = line.split(" ");
        return new Vector3(Double.parseDouble(split[0]),Double.parseDouble(split[1]), Double.parseDouble(split[2]));
    }

    public Vector3 getN() {
        return N;
    }

    public Vector3 getV() {
        return V;
    }

    public int getD() {
        return d;
    }

    public int getHx() {
        return hx;
    }

    public int getHy() {
        return hy;
    }

    public Vector3 getC() {
        return C;
    }
}
