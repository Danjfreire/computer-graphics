import models.Vector3;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;


public class DrawPanel extends JPanel {

  private List<Vector3> vectors;
  private double xMin;
  private double xMax;
  private double yMin;
  private double yMax;

    public DrawPanel(List<Vector3> vectors, int[]minMax){
        this.vectors = vectors;
        this.xMin = minMax[0];
        this.xMax = minMax[1];
        this.yMin = minMax[2];
        this.yMax = minMax[3];
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.black);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.white);

        Dimension size = getSize();
        int w = size.width ;
        int h = size.height;
        System.out.println("width:" + w);
        System.out.println("height:" + h);

        for(Vector3 vector : vectors){
            int x = normalizeX(vector.getX(), w,h).intValue();
            int y = normalizeY(vector.getY(),w,h).intValue();
            g2d.drawLine(x,y,x,y);
        }
    }

    private Double normalizeX(double x,int w, int h) {
        double xn = ((x - xMin)/(xMax-xMin)) * (w-1);
        return xn;
    }

    private Double normalizeY(double y, int w ,int h){
        double yn = ((y - yMin)/(yMax-yMin)) * (h-1);
        return yn;
    }
}
