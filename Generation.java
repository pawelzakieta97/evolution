import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Generation {

    /**
     * an array of individuals in the generation
     */
    public ArrayList<Evolvable> population;

    /**
     * returns an array of the best individuals in the generation
     * @param n size of the array
     * @return array of best individuals
     */
    ArrayList<Evolvable> getBest(int n){
        if (n>population.size()) n = population.size();
        return new ArrayList<Evolvable>(population.subList(0,n));
    }

    /**
     * returns the individual with the hoghest fitness value in the population
     * @return best individual
     */
    Evolvable getBest(){
        return getBest(1).get(0);
    }

    /**
     * Populates the generation with a given number of individuals based on the array of parents
     * @param parents
     * @param size the desired number of individuals in generation
     */
    public void populate(ArrayList<Evolvable> parents, int size){
        //parents = new ArrayList<Evolvable>(population.subList(0, parentsNum));
        population = parents.get(0).breed(parents, size, 0.1);
    }

    /**
     * this method calls mutate method in each individual of the generation population
     * @param params specifies the parameters of mutation
     */
    public void mutate(MutationParameters params){
        for (Evolvable i: population){
            i.mutate(params);
        }
    }

    /**
     * method checks fitness and sorts individuals (best ones are at the beginning). Evaluation of individuals MUST
     * be done using the Application thread due to the rendering required to find cost map.
     */
    public void process(){
        for (Evolvable i: population){
            AppThread.runAndWait(()->{i.evaluate();});
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
