import functions.IFunction;

import java.util.ArrayList;
import java.util.Random;

public class Individual implements Comparable<Individual>{

    public static final int LENGTH = 10;
    public static final int FOR_ACCURACY = 1000;

    private ArrayList<String> chromosome;
    private ArrayList<Double> phenotype;
    private double fitness;
    private IFunction function;
    private int dimension;

    public static long fitnessCounter = 0;

    // Constructor for random generation of individual
    public Individual(int dimension, IFunction function) {
        double a = Algorithm.A;
        double b = Algorithm.B;
        int q = Algorithm.Q;
        int m = Algorithm.M;
        int bound = Algorithm.BOUND;

        this.dimension = dimension;

        this.chromosome = new ArrayList<String>();
        this.phenotype = new ArrayList<Double>();

        for (int i=0; i<dimension; i++){
            String tempChrom = generateRandomChromosome(m, bound);
            this.chromosome.add(tempChrom);
            this.phenotype.add(generatePhenotype(tempChrom, a, b, q, m));
        }

        this.function = function;
        this.fitness = calculateFitness();

    }

//    public Individual(int dimension, IFunction function) {
//        this.dimension = dimension;
//
//        this.chromosome = new ArrayList<>();
//        this.phenotype = new ArrayList<>();
//
//        for (int i=0; i<dimension; i++){
//            double n = generateRandomPhenotype(0, 1000);
//            this.phenotype.add(n);
//            this.chromosome.add(getChromosomeFromPhenotype(n));
//        }
//
//        this.function = function;
//        this.fitness = calculateFitness();
//    }

//    private double generateRandomPhenotype(int min, int max){
//        Random random = new Random();
//        double n = (double) random.nextInt(max) / max;
//
//        return n;
//    }

//    private String getChromosomeFromPhenotype(double phen){
//        String ret = Integer.toBinaryString((int) (phen * 1000));
//
//        int temp = ret.length();
//        for (int i = 0; i < 10 - temp; i++)
//            ret = "0".concat(ret);
//
//        return ret;
//    }

    public Individual(int dimension, ArrayList<String> chromosome, IFunction function) {
        double a = Algorithm.A;
        double b = Algorithm.B;
        int q = Algorithm.Q;
        int m = Algorithm.M;
        int bound = Algorithm.BOUND;

        this.chromosome = chromosome;
        this.phenotype = new ArrayList<Double>();

        for (int i=0; i<dimension; i++){
            this.phenotype.add(generatePhenotype(chromosome.get(i), a, b, q, m));
        }

        this.function = function;
        this.fitness = calculateFitness();
    }

//    public Individual(int dimension, ArrayList<String> chromosome, IFunction function){
//        this.chromosome = chromosome;
//        this.phenotype = new ArrayList<>();
//
//        for (int i=0; i<dimension; i++){
//            double temp = (double) Integer.parseInt(chromosome.get(i), 2) / 1000;
//            this.phenotype.add(temp);
//        }
//
//        this.function = function;
//        this.fitness = calculateFitness();
//    }

    public ArrayList<String> getChromosome() {
        return chromosome;
    }

    public ArrayList<Double> getPhenotype() {
        return phenotype;
    }

    public double getFitness() {
        return fitness;
    }

    private String generateRandomChromosome(int m, int bound){
        Random rg = new Random();
        int n = rg.nextInt(bound);
        String ret = Integer.toBinaryString(n);
        int temp = ret.length();

        for (int i = 0; i< m - temp; i++)
            ret = "0".concat(ret);

        return ret;
    }

    private double generatePhenotype(String num, double a, double b, int q, int m){
        double temp = a + Integer.parseInt(num, 2) *  (b - a) / (Math.pow(2, m) - 1);
        return (double)Math.round(temp * 1000d) / 1000d;
    }

    private double calculateFitness(){

        fitnessCounter++;
        //System.out.println(fitnessCounter);

        return function.calculate(phenotype);
    }

    @Override
    public int compareTo(Individual o) {
        return (int) (this.fitness - o.fitness);
    }
}
