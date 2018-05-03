import functions.*;
import interfaces.IFunction;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import utils.ExcelWriter;
import utils.Statistics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

public class Main {



    public static void main(String args[]) throws IOException, InvalidFormatException, InterruptedException {

        switch (VARIANT){
            case 1:
                strategy = 1;
                cf = 4;
                cs = 0.15;
                s = 0.01;
                cross = 0.8;
                break;
            case 2:
                strategy = 1;
                cf = 2;
                cs = 0.15;
                s = 0.15;
                cross = 1;
                break;
            case 3:
                strategy = 2;
                cf = 4;
                cs = 0.15;
                s = 0.01;
                cross = 0.8;
                break;
            case 4:
                strategy = 2;
                cf = 2;
                cs = 0.15;
                s = 0.15;
                cross = 1;
                break;
        }


        for(int i = 0; i<=NUMBER_OF_ITERATIONS; i++){
            iteration(i);
        }


    }

    static void iteration(int numberOfIteration) throws IOException, InvalidFormatException, InterruptedException {
        Algorithm alg;
        alg = new Algorithm();
        alg.setDimension(DIMENSION);
        alg.setN(N);

        alg.setCf(cf);
        alg.setCs(cs);
        alg.setS(s);

        alg.setPm(0.003);

        alg.setFunction(function);

        alg.generateRandomPopulation();

//        for(int i=0; i<alg.getPopulation().size(); i++){
//            System.out.println(alg.getPopulation().get(i).getPhenotype());
//        }
        //System.out.println(alg.averageFitness(alg.getPopulation()));

        double tempFitness = 0;
        double currentFitness;
        int j = 0;

        while(counter < 10 && Individual.fitnessCounter < NUMBER_OF_COUNT){
            HashMap<Integer, Individual> population = (HashMap<Integer, Individual>) alg.getPopulation().clone();
            HashMap<Integer, Individual> newPopulation = new HashMap<Integer, Individual>(); // population after cross and mut

            // Cross and mutation
            for (int k = 0; k < alg.getN() / 2; k++) {

                Entry<Integer, Individual> parent1 = alg.getRandomFirstParent(population);
                population.remove(parent1.getKey());

                ArrayList<Entry<Integer, Individual>> sGroup = alg.getSelectionGroup(population);

                Entry<Integer, Individual> parent2 = alg.getMostSimilarIndividual(parent1.getValue(), sGroup);
                population.remove(parent2.getKey());

                ArrayList<Entry<Integer, Individual>> parentPair = new ArrayList<Entry<Integer, Individual>>();
                parentPair.add(parent1);
                parentPair.add(parent2);

                Random random = new Random();
                double crossRandom = (double)random.nextInt(10) / 10;

                if(crossRandom <= cross){
                    ArrayList<Entry<Integer, Individual>> afterCross = alg.cross(parentPair);

                    newPopulation.put(afterCross.get(0).getKey(), afterCross.get(0).getValue());
                    newPopulation.put(afterCross.get(1).getKey(), afterCross.get(1).getValue());
                }
                else {
                    newPopulation.put(parent1.getKey(), parent1.getValue());
                    newPopulation.put(parent2.getKey(), parent2.getValue());
                }

            }

            population = (HashMap<Integer, Individual>) alg.getPopulation().clone();


            for (int i=0; i<newPopulation.size(); i++){

                Individual changer = newPopulation.get(i);

                ArrayList<HashMap<Integer, Individual>> factorGroups = alg.getFactorGroups(population);

                switch (strategy) {
                    case 1:
                        // The worst from the most similar
                        HashMap<Integer, Individual> selectionPool = alg.getSelectionPool(changer, factorGroups);
                        Entry<Integer, Individual> worstFromMostSimilar = alg.getIndWithWorstFitness(selectionPool);
                        population.put(worstFromMostSimilar.getKey(), changer);
                        break;
                    case 2:
                        // The most similar from the worst
                        HashMap<Integer, Individual> selectionPool2 = alg.getSelectionPool2(factorGroups);
                        Entry<Integer, Individual> mostSimilarFromWorst = alg.getMostSimilarIndividual(changer,
                                new ArrayList<Entry<Integer, Individual>>(selectionPool2.entrySet()));
                        population.put(mostSimilarFromWorst.getKey(), changer);
                        break;
                }

            }

            alg.setPopulation(population);

            System.out.println(alg.averageFitness(alg.getPopulation()));

            currentFitness = alg.averageFitness(alg.getPopulation());
            updateCounter(tempFitness, currentFitness);
            tempFitness = currentFitness;

            j++;
        }

        Individual.fitnessCounter = 0;
        counter = 0;

        ArrayList<Individual> seeds = alg.getSeeds(alg.getPopulation());
        printStatistics(alg, seeds, numberOfIteration);

        if(DIMENSION <= 2){
            Graphic graphic = new Graphic(function, seeds, numberOfIteration);
            graphic.drawGraph();
        }
    }

    static void updateCounter(double fitness1, double fitness2){
        if(Math.abs(fitness1 - fitness2) <= 0.001)
            counter++;
        else
            counter = 0;
    }

    static void printStatistics(Algorithm alg, ArrayList<Individual> seeds, int numberOfInteration) throws IOException, InvalidFormatException {
        ArrayList<Individual> globalMax = alg.getGlobalMax(seeds);
        ArrayList<Individual> localMax = alg.getLocalMax(seeds);

        Statistics statistics = new Statistics();

        int NSeeds = seeds.size();
        int GP = globalMax.size();
        int LP = localMax.size();
        int NP = GP + LP;
        double PR = (double) NP / (function.getGlobalMaxSize(DIMENSION) + function.getLocalMaxSize(DIMENSION));
        double GPR = (double) GP / function.getGlobalMaxSize(DIMENSION);
        double LPR = (double) LP / function.getLocalMaxSize(DIMENSION);
        double FPR = (double) (NSeeds - NP) / NSeeds;

        statistics.setNseeds(NSeeds);
        statistics.setGp(GP);
        statistics.setLp(LP);
        statistics.setNp(NP);
        statistics.setPr(PR);
        statistics.setGpr(GPR);
        statistics.setLpr(LPR);
        statistics.setFpr(FPR);

        //ExcelWriter writer = new ExcelWriter(statistics, numberOfInteration, VARIANT);

        System.out.println(String.format("NSeeds: %d", NSeeds));
        System.out.println(String.format("NP: %d", NP));
        System.out.println(String.format("GP: %d", GP));
        System.out.println(String.format("LP: %d", LP));
        System.out.println(String.format("PR: %f", PR));
        System.out.println(String.format("GPR: %f", GPR));
        System.out.println(String.format("LPR: %f", LPR));
        System.out.println(String.format("FPR: %f", FPR));
    }

    static int counter = 0;

    static IFunction function = new Deba1();

    static final int N = 500;
    static final int DIMENSION = 1;
    static final int VARIANT = 4;

    static final int NUMBER_OF_COUNT = 2000000;

    static int cf;
    static double cs;
    static double s;
    static int strategy;
    static double cross;

    static final int NUMBER_OF_ITERATIONS = 1;
}
