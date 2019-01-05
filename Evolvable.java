import java.util.ArrayList;

/**
 * Evolving entities will implement this interface to be able to
 */
public interface Evolvable{
    public double getCost();


    public void mutate(MutationParameters params);
//
//    default public double compareTo(Evolvable o) {
//        return (cost - o.cost);
//    }
    /**
     * generates children whose features values are copied from a random parent
     * @param parents an array containing parent objects
     * @param n specifies how many children you want to generate
     * @return an array of children (all of them are the same)
     */
    public ArrayList<Evolvable> breed(ArrayList<Evolvable> parents, int n, double crossover);

    /**
     * sepcifies how good the object is (cost function). Updates cost field
     */
    public void evaluate();

}
