package functions;

import java.util.ArrayList;

public class Shuberts implements IFunction {
    @Override
    public double calculate(ArrayList<Double> xList) {
        double result = 0;
        for (int i = 0; i < xList.size(); i++) {

            double innerResult = 0;
            for (int j = 1; j <= 5; j++) {
                innerResult += j * Math.cos((j + 1) * xList.get(i) + j);
            }
            if (result != 0) {
                result *= innerResult;
            } else {
                result = innerResult;
            }
        }

        return 0 - result;
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
