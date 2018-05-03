import interfaces.IFunction;
import org.math.plot.Plot2DPanel;
import org.math.plot.Plot3DPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Graphic {

    private int bound = 1000;
    private double step = 0.001;
    private IFunction function;
    private ArrayList<Individual> seeds;
    private double[] x;
    private double[] y;
    private double[][] z;

    private double[] xx;
    private double[] yy;
    private double[] zz;

    private int dimension;
    private int size;

    private int numberOfIteration;

    public Graphic(IFunction function, ArrayList<Individual> seeds, int numberOfIteration){
        this.function = function;
        this.seeds = seeds;
        this.dimension = seeds.get(0).getPhenotype().size();
        this.size = seeds.size();
        this.numberOfIteration = numberOfIteration;

        if(dimension == 1){
            this.x = new double[bound];
            this.y = new double[bound];

            int j=0;

            for (double i = 0; i < 1; i+=step, j++){
                x[j] = i;
                ArrayList<Double> tempList = new ArrayList<Double>();
                tempList.add(x[j]);
                y[j] = function.calculate(tempList);
            }

            this.xx = new double[size];
            this.yy = new double[size];

            for (int i = 0; i<size; i++){
                Individual tempInd = seeds.get(i);
                xx[i] = tempInd.getPhenotype().get(0);
                ArrayList<Double> tempList = new ArrayList<Double>();
                tempList.add(xx[i]);
                yy[i] = function.calculate(tempList);
            }
        }
        else if(dimension == 2){
            this.x = new double[bound / 10];
            this.y = new double[bound / 10];
            this.z = new double[bound / 10][bound / 10];

            int iter = 0;
            for (double i = 0; i <= 1 && iter < bound / 10; i += step * 10) {
                x[iter] = i;
                y[iter] = i;
                ++iter;
            }

            for (int i = 0; i < x.length; ++i) {
                for (int j = 0; j < y.length; ++j) {
                    ArrayList<Double> tempList = new ArrayList<Double>();
                    tempList.add(x[i]);
                    tempList.add(y[j]);
                    z[i][j] = (double)Math.round(function.calculate(tempList) * 1000d) / 1000d;
                }
            }

            this.xx = new double[size];
            this.yy = new double[size];
            this.zz = new double[size];

            for (int i = 0; i<size; i++){
                //for(int d=0; d<seeds.get(1).size(); d++){
                Individual tempInd = seeds.get(i);
                    xx[i] = tempInd.getPhenotype().get(0);
                    yy[i] = tempInd.getPhenotype().get(1);

                    ArrayList<Double> tempList = new ArrayList<Double>();
                    tempList.add(xx[i]);
                    tempList.add(yy[i]);
                    zz[i] = function.calculate(tempList);
                //}
            }

        }
    }

    private static double[] increment(double start, double step, double end) {
        double range = end - start;
        int steps = (int)(range / step);
        double[] rv = new double[steps];
        for (int i = 0; i<steps; i++) {
            rv[i] = start + ((step / range) * i);
        }
        return rv;
    }

    public void drawGraph() throws IOException, InterruptedException {

        if(dimension == 1){
            // create your PlotPanel (you can use it as a JPanel)
            Plot2DPanel plot2d = new Plot2DPanel();

            // define the legend position
            plot2d.addLegend("SOUTH");

            // add a line plot to the PlotPanel
            plot2d.addLinePlot("function", Color.LIGHT_GRAY, x, y);
            plot2d.addScatterPlot("seeds", xx, yy);

            // put the PlotPanel in a JFrame like a JPanel
            JFrame frame2d = new JFrame("a plot panel");
            frame2d.setSize(600, 600);
            frame2d.setContentPane(plot2d);
            frame2d.setVisible(true);

            Thread.sleep(2000);
            frame2d.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            BufferedImage image = new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB);
            Graphics graphics = image.createGraphics();
            plot2d.paint(graphics);
            ImageIO.write(image,"png", new File(String.format("graphics/image%d.png", numberOfIteration)));
        }
        else if(dimension == 2){
            // create your PlotPanel (you can use it as a JPanel)
            Plot3DPanel plot3d = new Plot3DPanel();

            // define the legend position
            plot3d.addLegend("SOUTH");

            // add a line plot to the PlotPanel
            plot3d.addGridPlot("function", Color.LIGHT_GRAY, x, y, z);
            plot3d.addScatterPlot("seeds", xx, yy, zz);

            // put the PlotPanel in a JFrame like a JPanel
            JFrame frame3d = new JFrame("a plot panel");
            frame3d.setSize(600, 600);
            frame3d.setContentPane(plot3d);
            frame3d.setVisible(true);
            frame3d.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            BufferedImage image = new BufferedImage(600, 600, BufferedImage.TYPE_INT_ARGB);
            Graphics graphics = image.createGraphics();
            plot3d.paint(graphics);
            ImageIO.write(image,"png", new File(String.format("graphics/image%d.png", numberOfIteration)));
        }

    }
}
