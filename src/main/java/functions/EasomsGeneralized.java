package functions;

import java.util.ArrayList;

public class EasomsGeneralized implements IFunction {
    @Override
    public double calculate(ArrayList<Double> xList) {
        double result = Math.pow(-1, xList.size());
        for (int i = 0; i < xList.size(); i++) {
            result *= Math.pow(Math.cos(xList.get(i)), 2);
        }
        double sum = 0;
        for (int i = 0; i < xList.size(); i++) {
            sum += Math.pow(xList.get(i) - Math.PI, 2);
        }
        return result * Math.exp(-sum);
    }

    @Override
    public double[] getGlobalMax() {
        return new double[0];
    }

    @Override
    public double[] getLocalMax() {
        return new double[0];
    }

    @Override
    public long getGlobalMaxSize(int dimension) {
        return 0;
    }

    @Override
    public long getLocalMaxSize(int dimension) {
        return 0;
    }
}
