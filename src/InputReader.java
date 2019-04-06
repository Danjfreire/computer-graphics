import models.Vector3;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InputReader {

    public static void main(String args[]){
        int vertNum;
        int triangleNum;
        List<Vector3> vectors = new ArrayList<>();
        BufferedReader reader;
        try{
            reader = new BufferedReader(new FileReader("./src/input/vaso.BYU"));
            String line = reader.readLine();
//            System.out.println(line);
            String[] line1 = line.split(" ");
            vertNum = Integer.parseInt(line1[0]);
            triangleNum = Integer.parseInt(line1[1]);
            int aux = 0;
            String[] splitLine;
            line = reader.readLine();
            while(line != null){
//                System.out.println(line);
                splitLine = line.split(" ");
                if(aux < vertNum){
                    vectors.add(new Vector3(Double.parseDouble(splitLine[0]),Double.parseDouble(splitLine[1]),Double.parseDouble(splitLine[2])));
                }
                aux++;
                line = reader.readLine();
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        DrawPanel panel = new DrawPanel(vectors, findMinMax(vectors));
        JFrame frame = new JFrame("Drawing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static int[] findMinMax(List<Vector3> vectors){
        Double xMin = Double.MAX_VALUE;
        Double xMax = Double.MIN_VALUE;
        Double yMin = Double.MAX_VALUE;
        Double yMax = Double.MIN_VALUE;

        for(Vector3 vector : vectors){
            if(vector.getX() < xMin)
                xMin = vector.getX();
            if(vector.getX() > xMax)
                xMax = vector.getX();
            if(vector.getY() < yMin)
                yMin = vector.getY();
            if(vector.getY() > yMax)
                yMax = vector.getY();
        }

        int[] minMax = {xMin.intValue(),xMax.intValue(),yMin.intValue(),yMax.intValue()};
        return minMax;
    }
}
