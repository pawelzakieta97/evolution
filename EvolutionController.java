import java.util.ArrayList;

public class EvolutionController{
    public ArrayList<Generation> generations = new ArrayList<Generation>();

    public void setOwner(ControllerInterface owner) {
        this.owner = owner;
    }
    public Generation getLastGen(){
        return generations.get(generations.size()-1);
    }

    private ControllerInterface owner;
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
     * @param amount specifies the rate of change of individuals due to mutation
     * @return cost function value of the best individual in new generation
     */
    public double update(int genSize, int parentsNum, double amount){
        Generation nextGen = new Generation();
        nextGen.populate(getLastGen().getBest(parentsNum), genSize);
        nextGen.mutate(amount);
        //adding previous best individuals without mutating
        nextGen.population.addAll(getLastGen().getBest(parentsNum));
        nextGen.process();
        generations.add(nextGen);
        return nextGen.getBest().getCost();
    }

    public void start(int genSize, int parentsNum, float amount) throws Exception{
        if(owner == null) throw new Exception("you need to specify owner first");
        new Thread(()->{
            while (owner.isRunning()){
                update(genSize, parentsNum, amount);
                owner.setCurrentBest(generations.get(generations.size()-1).getBest());
            }
            System.out.println("thread ending");
        }).start();

    }

}
