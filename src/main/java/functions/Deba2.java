package functions;

import java.util.ArrayList;

public class Deba2 implements IFunction {
    @Override
    public double calculate(ArrayList<Double> xList) {
        double sum = 0;

        for (int i=0; i<xList.size(); i++){
            sum += Math.exp(-2 * Math.log(2) * Math.pow((xList.get(i) - 0.1) / 0.8, 2)) * Math.pow(Math.sin(5 * Math.PI * xList.get(i)), 6);
        }

        return sum;
    }

    @Override
    public double[] getGlobalMax() {
        return globalMax;
    }

    @Override
    public double[] getLocalMax() {
        return localMax;
    }

    @Override
    public long getGlobalMaxSize(int dimension){
        return 1;
    }

    @Override
    public long getLocalMaxSize(int dimension){
        return (long) Math.pow(5, dimension) - 1;
    }

    public double[] globalMax = { 0.1 };
    public double[] localMax = { 0.3, 0.5, 0.7, 0.9 };


}
