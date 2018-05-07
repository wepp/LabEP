package functions;


import java.util.ArrayList;

public class Griewangks implements IFunction {
    @Override
    public double calculate(ArrayList<Double> xList) {
        double result = xList.size();
        for (int i = 1; i <= xList.size(); i++) {
            double production = 1;
            for (int j = 1; j <= xList.size(); j++) {
                production *= Math.cos(xList.get(j - 1) / Math.pow(j, 1 / 2));
            }
            result -= (Math.pow(xList.get(i - 1), 2) / 4000 - production + 1);
        }
        return result;
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
