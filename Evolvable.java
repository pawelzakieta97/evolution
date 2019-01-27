import java.util.ArrayList;

/**
 * Evolving entities will implement this interface to be able to
 */
public interface Evolvable{

    /**
     * @return the cost function value (the higher the worse the individual)
     */
    public double getCost();

    /**
     * Calling this method is supposed to modify the object implementing this interface slightly
     * @param params specifies the details of mutation
     */
    public void mutate(MutationParameters params);
//
//    default public double compareTo(Evolvable o) {
//        return (cost - o.cost);
//    }
    /**
     * generates children whose features values are copied from a random parent
     * @param parents an array containing parent objects
     * @param n specifies how many children you want to generate
     * @param crossover specifies the probability of a feature being copied from other parent
     * @return an array of children (all of them are the same)
     */
    public ArrayList<Evolvable> breed(ArrayList<Evolvable> parents, int n, double crossover);

    /**
     * Updates cost field
     */
    public void evaluate();

}
