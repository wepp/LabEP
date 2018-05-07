package functions;

import java.util.ArrayList;

public class Deba3 implements IFunction {
    @Override
    public double calculate(ArrayList<Double> xList) {
        double sum = 0;

        for(int i=0; i<xList.size(); i++){
            sum += Math.pow(Math.sin(5 * Math.PI * (Math.pow(xList.get(i), 0.75) - 0.05)), 6);
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

    public double[] globalMax = { 0.08, 0.247, 0.451, 0.681, 0.934 };
    public double[] localMax = { };
}
