import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Generation {
    private ArrayList<Evolvable> population;
    private int size = 10;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    ArrayList<Evolvable> getBest(int n){
        return new ArrayList<Evolvable>(population.subList(0,n));
    }
    //private ArrayList<Evolvable> parents;

    public void populate(ArrayList<Evolvable> parents){
        //parents = new ArrayList<Evolvable>(population.subList(0, parentsNum));
        population = parents.get(0).breed(parents, size);
    }
    public void randomize(float amount){
        for (Evolvable i: population){
            i.mutate(amount);
        }
    }

    /**
     * method checks fitness and sorts individuals (best ones are at the beginning)
     */
    public void process(){
        for (Evolvable i: population){
            i.evaluate();
        }
        Collections.sort(population, new Comparator<Evolvable>() {
            @Override
            public int compare(final Evolvable object1, final Evolvable object2) {
                if (object1.getCost() == object2.getCost()) return 0;
                else if (object1.getCost()>object2.getCost()) return 1;
                else return -1;
            }
        });

    }

}
