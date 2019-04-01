package test;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class PointLine1 {
    public static void main(String[] args) {
        GUI guiObj = new GUI();
    }
}

    class GUI extends JFrame {
        //Specify the horizontal and vertical size of a JFrame
        // object.
        int hSize = 200;
        int vSize = 200;

        GUI(){//constructor

            //Set JFrame size and title
            setSize(hSize,vSize);
            setTitle("R.G.Baldwin");

            //Create a new drawing canvas and add it to the
            // center of the JFrame.
            MyCanvas myCanvas = new MyCanvas();
            this.getContentPane().add(myCanvas);

            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            setVisible(true);

        }//end constructor
        //----------------------------------------------------//


        //This is an inner class of the GUI class.
        class MyCanvas extends Canvas {
            //Override the paint() method. This method will be
            // called when the JFrame and the Canvas in its
            // contentPane are displayed on the screen.
            public void paint(Graphics g){
                //Downcast the Graphics object to a Graphics2D
                // object. The Graphics2D class provides
                // capabilities that don't exist in the Graphics
                // class.
                Graphics2D g2 = (Graphics2D)g;

                //By default, the origin is at the upper-left corner
                // of the canvas. This statement translates the
                // origin to the center of the canvas.
                g2.translate(
                        this.getWidth()/2.0,this.getHeight()/2.0);

                //Define two points.
                Point2D pointA =
                        new Point2D.Double(-this.getWidth()/2.5,0.0);
                Point2D pointB =
                        new Point2D.Double(this.getWidth()/2.5,0.0);
                //Use the points to construct an object that
                // represents a line segment that connects the two
                // points. The values of the points causes this
                // line segment to be horizontal.
                Line2D.Double horizLine =
                        new Line2D.Double(pointA,pointB);

                //Use the same procedure to construct an object that
                // represents a vertical line segment.
                Point2D pointC =
                        new Point2D.Double(0.0,-this.getHeight()/2.5);
                Point2D pointD =
                        new Point2D.Double(0.0,this.getHeight()/2.5);
                Line2D.Double vertLine =
                        new Line2D.Double(pointC,pointD);

                //Draw the horizontal and vertical line segments on
                // the canvas.
                g2.draw(horizLine);
                g2.draw(vertLine);

            }//end overridden paint()


        }//end inner class MyCanvas

    }//end class GUI

