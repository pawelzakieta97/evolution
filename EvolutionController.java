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
     * @param params specifies the rate of change of individuals due to mutation
     * @return cost function value of the best individual in new generation
     */
//    public double update(int genSize, int parentsNum, double amount){
//        Generation nextGen = new Generation();
//        nextGen.populate(getLastGen().getBest(parentsNum), genSize);
//        nextGen.mutate(amount);
//        //adding previous best individuals without mutating
//        nextGen.population.addAll(getLastGen().getBest(parentsNum));
//        nextGen.process();
//        generations.add(nextGen);
//        return nextGen.getBest().getCost();
//    }
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

    public void start(int genSize, int parentsNum, MutationParameters params, int steps){
        if (owner != null) {
            new Thread(() -> {
                while (owner.isRunning()) {
                    update(genSize, parentsNum, params);
                    owner.setCurrentBest(getLastGen().getBest());
                }
                System.out.println("thread ending");
            }).start();
        }
        else{
            new Thread(() -> {
                for(int i=0; i<steps; i++){
                    update(genSize, parentsNum, params);
                    owner.setCurrentBest(getLastGen().getBest());
                }
                System.out.println("thread ending");
            }).start();
        }
    }


    public void clear(){
        Generation gen = getLastGen();
        generations = new ArrayList<Generation>();
        generations.add(gen);
    }

}
