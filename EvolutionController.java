import java.util.ArrayList;

public class EvolutionController{
    private ArrayList<Generation> generations = new ArrayList<Generation>();
    public EvolutionController(Evolvable origin){
        Generation gen0 = new Generation();
        gen0.setSize(10);
        ArrayList<Evolvable> o = new ArrayList<>();
        o.add(origin);
        gen0.populate(o);
        gen0.process();
        generations.add(gen0);
    }

    /**
     * proceedes with next generation and processes it- checks fitness and sorts
     * @param genSize desribes the size of the generation
     * @param parentsNum specifies how many best individuals from previus generation are parents for the next
     * @param amount specifies the rate of change of individuals due to mutation
     * @return cost function value of the best individual in new generation
     */
    public double update(int genSize, int parentsNum, float amount){
        Generation nextGen = new Generation();
        nextGen.populate(generations.get(generations.size()-1).getBest(parentsNum));
        nextGen.randomize(amount);
        nextGen.process();
        generations.add(nextGen);
        return nextGen.getBest(1).get(0).getCost();
    }
}
