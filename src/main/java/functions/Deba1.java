package functions;

import interfaces.IFunction;

import java.util.ArrayList;

public class Deba1 implements IFunction {
    @Override
    public double calculate(ArrayList<Double> xList) {
        double sum = 0;
        for (int i = 0; i < xList.size(); i++){
            sum += Math.pow(Math.sin(5 * Math.PI * xList.get(i)), 6);
        }
        return sum / xList.size();
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
        return (long) Math.pow(5, dimension);
    }

    @Override
    public long getLocalMaxSize(int dimension){
        return 0;
    }

    public double[] globalMax = { 0.1, 0.3, 0.5, 0.7, 0.9 };
    public double[] localMax = { };

}
