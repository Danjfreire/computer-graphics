package models;

public class ColorParams {

    private Vector3 Iamb;
    private double Ka;
    private Vector3 Il;
    private Vector3 Pl;
    private Vector3 Kd;
    private Vector3 Od;
    private double Ks;
    private double eta;
    private int currentParam = 1;

    public void addParam(String line){

        switch (this.currentParam){
            case 1: this.Iamb = getVector(line); break;
            case 2: this.Ka = Double.parseDouble(line); break;
            case 3: this.Il = getVector(line); break;
            case 4: this.Pl = getVector(line); break;
            case 5: this.Kd = getVector(line); break;
            case 6: this.Od = getVector(line); break;
            case 7: this.Ks = Double.parseDouble(line); break;
            case 8: this.eta = Double.parseDouble(line); break;
        }
        this.currentParam++;
    }

    private Vector3 getVector(String line){
        String[] split = line.split(" ");
        return new Vector3(Double.parseDouble(split[0]),Double.parseDouble(split[1]), Double.parseDouble(split[2]));
    }

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
