package functions;

import java.util.ArrayList;

public class Rastrigin implements IFunction {
    @Override
    public double calculate(ArrayList<Double> xList) {
        double value = 0;

        for (int i = 0; i < xList.size(); i++) {
            value += (10 * Math.cos(2 * Math.PI * xList.get(i)) - Math.pow(xList.get(i), 2));
        }
        return value - 10 * xList.size();
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
