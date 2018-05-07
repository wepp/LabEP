package functions;

import java.util.ArrayList;

public class Schwefels implements IFunction {
    @Override
    public double calculate(ArrayList<Double> xList) {
        double result = 0;
        for (double aChild : xList) {
            result += aChild * Math.sin(Math.pow(Math.abs(aChild), 1 / 2d));
        }
        return result / xList.size();
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
