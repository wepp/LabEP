package interfaces;

import java.util.ArrayList;

public interface IFunction {
    public double calculate(ArrayList<Double> xList);
    public double[] getGlobalMax();
    public double[] getLocalMax();
    public long getGlobalMaxSize(int dimension);
    public long getLocalMaxSize(int dimension);
}
