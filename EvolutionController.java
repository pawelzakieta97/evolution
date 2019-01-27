import javafx.application.Platform;

import java.io.Serializable;
import java.util.ArrayList;

public class EvolutionController implements Serializable {

    /**
     * the array containing all the generations
     */
    public ArrayList<Generation> generations = new ArrayList<Generation>();

    /**
     * @return the latest generation
     */
    public Generation getLastGen(){
        return generations.get(generations.size()-1);
    }

    /**
     * Constructor. Populates the initial generation with 1 individual
     * @param origin the individual in the initial generation
     */
    public EvolutionController(Evolvable origin){
        Generation gen0 = new Generation();
        //gen0.setSize(10);
        ArrayList<Evolvable> o = new ArrayList<>();
        o.add(origin);
        gen0.populate(o, 1);
        gen0.process();
        generations.add(gen0);
    }

    /**
     * proceedes with next generation and processes it- checks fitness and sorts
     * @param genSize desribes the size of the generation
     * @param parentsNum specifies how many best individuals from previous generation are parents for the next
     * @param params specifies the rate of change of individuals due to mutation
     * @return cost function value of the best individual in new generation
     */
    public double update(int genSize, int parentsNum, MutationParameters params){
        Generation nextGen = new Generation();
        nextGen.populate(getLastGen().getBest(parentsNum), genSize);
        nextGen.mutate(params);
        //adding previous best individuals without mutating
        nextGen.population.addAll(getLastGen().getBest(parentsNum));
                nextGen.process();

        generations.add(nextGen);
        return nextGen.getBest().getCost();
    }


    /**
     * method used to clean the array of previous generations. It leaves only the most recent one.
     */
    public void clear(){
        Generation gen = getLastGen();
        generations = new ArrayList<Generation>();
        generations.add(gen);
    }

}
