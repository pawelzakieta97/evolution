import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Generation {
    public ArrayList<Evolvable> population;

    ArrayList<Evolvable> getBest(int n){
        if (n>population.size()) n = population.size();
        return new ArrayList<Evolvable>(population.subList(0,n));
    }
    Evolvable getBest(){
        return getBest(1).get(0);
    }

    public void populate(ArrayList<Evolvable> parents, int size){
        //parents = new ArrayList<Evolvable>(population.subList(0, parentsNum));
        population = parents.get(0).breed(parents, size, 0);
    }
    public void mutate(double amount){
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
