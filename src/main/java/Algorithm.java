import functions.IFunction;

import java.util.Map.Entry;

import java.util.*;

public class Algorithm {

    public static final double A = 0;
    public static final double B = 1;
    public static final int Q = 3;
    public static final int M = 10;
    public static final int BOUND = 1024;

    private HashMap<Integer, Individual> population;

    private double pc;
    private double pm;
    private double cf;
    private double cs;
    private double s;
    private int N;
    private IFunction function;
    private int dimension;

    public IFunction getFunction() {
        return function;
    }

    public void setFunction(IFunction function) {
        this.function = function;
    }

    public Algorithm() {
        population = new HashMap<Integer, Individual>();
    }

    public int getN() {
        return N;
    }

    public void setN(int n) {
        N = n;
    }

    public double getPc() {
        return pc;
    }

    public void setPc(double pc) {
        this.pc = pc;
    }

    public double getPm() {
        return pm;
    }

    public void setPm(double pm) {
        this.pm = pm;
    }

    public double getCf() {
        return cf;
    }

    public void setCf(double cf) {
        this.cf = cf;
    }

    public double getCs() {
        return cs;
    }

    public void setCs(double cs) {
        this.cs = cs;
    }

    public double getS() {
        return s;
    }

    public void setS(double s) {
        this.s = s;
    }

    public HashMap<Integer, Individual> getPopulation() {
        return population;
    }

    public void setPopulation(HashMap<Integer, Individual> population) {
        this.population = population;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public void generateRandomPopulation() {
        for (int i = 0; i < N; i++) {
            population.put(i, new Individual(dimension, function));
        }
    }

    public ArrayList<Entry<Integer, Individual>> getSelectionGroup(HashMap<Integer, Individual> population) {
        Random rg = new Random();
        int realCs = (int) (cs * N);
        ArrayList<Entry<Integer, Individual>> retList = new ArrayList<Entry<Integer, Individual>>();
        List<Integer> keys = new ArrayList<Integer>(population.keySet());

        for (int i = 0; i < realCs; i++) {
            final int key = keys.get(rg.nextInt(keys.size()));
            final Individual ind = population.get(key);
            Entry<Integer, Individual> entryInd = new Entry<Integer, Individual>() {
                public Integer getKey() {
                    return key;
                }

                public Individual getValue() {
                    return ind;
                }

                public Individual setValue(Individual value) {
                    return value;
                }
            };
            retList.add(entryInd);
        }

        return retList;
    }

    public ArrayList<HashMap<Integer, Individual>> getFactorGroups(HashMap<Integer, Individual> population) {
        Random rg = new Random();
        int realS = (int) (s * N);
        ArrayList<HashMap<Integer, Individual>> retList = new ArrayList<HashMap<Integer, Individual>>();
        List<Integer> keys = new ArrayList<Integer>(population.keySet());

        for (int i = 0; i < cf; i++) {
            HashMap<Integer, Individual> tempMap = new HashMap<Integer, Individual>();
            for (int j = 0; j < realS; j++) {
                int key = keys.get(rg.nextInt(keys.size()));
                Individual ind = population.get(key);
                tempMap.put(key, ind);
            }
            retList.add(tempMap);
        }

        return retList;
    }

    public HashMap<Integer, Individual> getSelectionPool(Individual individual, ArrayList<HashMap<Integer, Individual>> factorGroups) {
        HashMap<Integer, Individual> retMap = new HashMap<Integer, Individual>();
        for (HashMap<Integer, Individual> group : factorGroups) {

            Entry<Integer, Individual> mostSimilarEntry = getMostSimilarIndividual(individual,
                    new ArrayList<Entry<Integer, Individual>>(group.entrySet()));
            Individual mostSimilarInd = mostSimilarEntry.getValue();
            int key = mostSimilarEntry.getKey();

            retMap.put(key, mostSimilarInd);
        }

        return retMap;
    }

    public HashMap<Integer, Individual> getSelectionPool2(ArrayList<HashMap<Integer, Individual>> factorGroups) {
        HashMap<Integer, Individual> retMap = new HashMap<Integer, Individual>();
        for (HashMap<Integer, Individual> group : factorGroups) {

//            Entry<Integer, Individual> mostSimilarEntry = getMostSimilarIndividual(individual,
//                    new ArrayList<Entry<Integer, Individual>>(group.entrySet()));
            Entry<Integer, Individual> worstFitnessInd = getIndWithWorstFitness(group);
            //Individual mostSimilarInd = mostSimilarEntry.getValue();
            //int key = mostSimilarEntry.getKey();

            retMap.put(worstFitnessInd.getKey(), worstFitnessInd.getValue());
        }

        return retMap;
    }

    public Entry<Integer, Individual> getIndWithWorstFitness(HashMap<Integer, Individual> individualHashMap) {
        Set<Entry<Integer, Individual>> entries = individualHashMap.entrySet();
        Entry<Integer, Individual> retEntry = (Entry<Integer, Individual>) entries.toArray()[0];

        for (Entry<Integer, Individual> entry : entries) {
            if (entry.getValue().getFitness() < retEntry.getValue().getFitness()) {
                retEntry = entry;
            }
        }

        return retEntry;
    }

    public Entry<Integer, Individual> getRandomFirstParent(HashMap<Integer, Individual> population) {
        Random rg = new Random();
        ArrayList<Integer> keys = new ArrayList<Integer>(population.keySet());
        final int randomKey = keys.get(rg.nextInt(keys.size()));
        final Individual retInd = population.get(randomKey);

        return new Entry<Integer, Individual>() {
            public Integer getKey() {
                return randomKey;
            }

            public Individual getValue() {
                return retInd;
            }

            public Individual setValue(Individual value) {
                return null;
            }
        };
    }

    public Entry<Integer, Individual> getMostSimilarIndividual(Individual individual, ArrayList<Entry<Integer, Individual>> individuals) {
        Entry<Integer, Individual> retEntry = individuals.get(0);
        double euclidean = euclideanDistance(individual, individuals.get(0).getValue());
        for (int i = 1; i < individuals.size(); i++) {
            double temp = euclideanDistance(individual, individuals.get(i).getValue());
            if (temp > euclidean) {
                euclidean = temp;
                retEntry = individuals.get(i);
            }
        }

        return retEntry;
    }

    private double euclideanDistance(Individual ind1, Individual ind2) {
        double sum = 0;
        for (int i = 0; i < dimension; i++) {
            double temp = ind1.getPhenotype().get(i) - ind2.getPhenotype().get(i);
            sum += temp * temp;
        }
        return Math.pow(sum, 0.5);
    }

    private double euclideanDistance(ArrayList<Double> arr1, ArrayList<Double> arr2) {
        double sum = 0;
        for (int i = 0; i < dimension; i++) {
            double temp = arr1.get(i) - arr2.get(i);
            sum += temp * temp;
        }
        return Math.pow(sum, 0.5);
    }

    public ArrayList<Entry<Integer, Individual>> cross(final ArrayList<Entry<Integer, Individual>> pair) {

        int m = M;
        Random rg = new Random();
        int n = rg.nextInt(m * dimension);

        ArrayList<Entry<Integer, Individual>> retPair = new ArrayList<Entry<Integer, Individual>>();

        String longString1 = "";
        String longString2 = "";

        for (int i = 0; i < dimension; i++) {
            longString1 += pair.get(0).getValue().getChromosome().get(i);
            longString2 += pair.get(1).getValue().getChromosome().get(i);
        }

        String subA1 = longString1.substring(0, n);
        String subB2 = longString2.substring(n, longString2.length());

        String subB1 = longString2.substring(0, n);
        ;
        String subA2 = longString1.substring(n, longString1.length());

        String finalLongString1 = subA1.concat(subB2);
        String finalLongString2 = subB1.concat(subA2);

        ArrayList<String> chromosomes1 = new ArrayList<String>();
        ArrayList<String> chromosomes2 = new ArrayList<String>();

        for (int i = 0; i < finalLongString1.length(); i = i + m) {
            chromosomes1.add(finalLongString1.substring(i, Math.min(i + m, finalLongString1.length())));
        }

        for (int i = 0; i < finalLongString2.length(); i = i + m) {
            chromosomes2.add(finalLongString2.substring(i, Math.min(i + m, finalLongString2.length())));
        }


        /*********************************/

        final Individual ind1 = new Individual(dimension, chromosomes1, function);
        final Individual ind2 = new Individual(dimension, chromosomes2, function);

        Entry<Integer, Individual> entry1 = new Entry<Integer, Individual>() {
            public Integer getKey() {
                return pair.get(0).getKey();
            }

            public Individual getValue() {
                return ind1;
            }

            public Individual setValue(Individual value) {
                return value;
            }
        };

        Entry<Integer, Individual> entry2 = new Entry<Integer, Individual>() {
            public Integer getKey() {
                return pair.get(1).getKey();
            }

            public Individual getValue() {
                return ind2;
            }

            public Individual setValue(Individual value) {
                return value;
            }
        };

        retPair.add(entry1);
        retPair.add(entry2);

        return retPair;
    }

    public double averageFitness(HashMap<Integer, Individual> population) {
        double sum = 0;
        for (int i = 0; i < population.size(); i++) {
            sum += population.get(i).getFitness();
        }
        return sum / population.size();
    }

    public ArrayList<Individual> getSeeds(HashMap<Integer, Individual> population) {
        ArrayList<Individual> returnArray = new ArrayList<Individual>();
        ArrayList<Individual> populationArray = new ArrayList<Individual>(population.values());
        Collections.sort(populationArray);
        Individual first = populationArray.get(0);
        returnArray.add(first);
        boolean isFound;
        for (int i = 1; i < populationArray.size(); i++) {
            isFound = false;
            int size = returnArray.size();
            Individual tempInd = populationArray.get(i);

            for (int j = 0; j < size; j++) {
                if (euclideanDistance(returnArray.get(j), tempInd) <= 0.03) {
                    isFound = true;
                    break;
                }
            }
            if (!isFound) {
                returnArray.add(tempInd);
            }

        }
        return returnArray;
    }

    public ArrayList<Individual> getGlobalMax(ArrayList<Individual> seeds) {
        ArrayList<Individual> returnArray = new ArrayList<Individual>();
        for (int i = 0; i < seeds.size(); i++) {
            boolean checker = true;
            ArrayList<Double> arr2 = new ArrayList<>();
            for (int j = 0; j < dimension; j++) {
                final double tempX = seeds.get(i).getPhenotype().get(j);
                double temp = 0;
                for (int h = 0; h < function.getGlobalMax().length; h++) {
                    if (Math.abs(function.getGlobalMax()[h] - tempX) <= 0.01) {
                        temp = function.getGlobalMax()[h];
                    }
                }
                if (temp != 0) {
                    arr2.add(temp);
                } else {
                    checker = false;
                    break;
                }
            }
            if (checker)
                if (euclideanDistance(seeds.get(i).getPhenotype(), arr2) <= 0.03) {
                    returnArray.add(seeds.get(i));
                }
        }

        return returnArray;
    }

    public ArrayList<Individual> getLocalMax(ArrayList<Individual> seeds) {
        ArrayList<Individual> returnArray = new ArrayList<Individual>();
        for (int i = 0; i < seeds.size(); i++) {
            boolean checker = true;
            ArrayList<Double> arr2 = new ArrayList<>();
            for (int j = 0; j < dimension; j++) {
                final double tempX = seeds.get(i).getPhenotype().get(j);
                double temp = 0;
                for (int h = 0; h < function.getLocalMax().length; h++) {
                    if (Math.abs(function.getLocalMax()[h] - tempX) <= 0.01) {
                        temp = function.getLocalMax()[h];
                    }
                }
                if (temp != 0) {
                    arr2.add(temp);
                } else {
                    checker = false;
                    break;
                }
            }
            if (checker && euclideanDistance(seeds.get(i).getPhenotype(), arr2) <= 0.03) {
                returnArray.add(seeds.get(i));
            }

        }

        return returnArray;
    }


}
