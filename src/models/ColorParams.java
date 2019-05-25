package models;

public class ColorParams {

    private Vector3 Iamb = new Vector3(255,200,0);
    private double Ka = 0.3;
    private Vector3 Il = new Vector3(50,213,254);
    private Vector3 Pl = new Vector3(120,30,0);
    private Vector3 Kd = new Vector3(0.5,0.4,0.2);
    private Vector3 Od = new Vector3(0.7,0.5,0.9);
    private double Ks = 0.5;
    private double eta = 1;


    public Vector3 getIamb() {
        return Iamb;
    }

    public double getKa() {
        return Ka;
    }

    public Vector3 getIl() {
        return Il;
    }

    public Vector3 getPl() {
        return Pl;
    }

    public Vector3 getKd() {
        return Kd;
    }

    public Vector3 getOd() {
        return Od;
    }

    public double getKs() {
        return Ks;
    }

    public double getEta() {
        return eta;
    }

}
