package functions;

import java.util.ArrayList;

public class XinSheYang implements IFunction {

    @Override
    public double calculate(ArrayList<Double> xList) {
        double first = 0;
        for (int i = 0; i < xList.size(); i++) {
            first += Math.abs(xList.get(i));
        }
        double second = 0;
        for (int i = 0; i < xList.size(); i++) {
            second += Math.pow(xList.get(i), 2);
        }
        return first * Math.exp(0 - second);
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
