import java.util.ArrayList;

/**
 * an example class that is compatible with evolution controller. This class has 3 features- x,y and z.
 * The aim of evolution is to minimize the sum of those values.
 */
public class EvolvableClass implements Evolvable{
    int x0 = 0;
    int y0 = 0;
    int z0 = 0;
    double x, y, z;

    double cost;

    public void evaluate(){
        cost = (x0-x)*(x0-x)+(y0-y)*(y0-y)+(z0-z)*(z0-z);
    }
    public double getCost(){
        return cost;
    }
    public void mutate(float amount){
        x+=((Math.random()-0.5)*amount);
        y+=((Math.random()-0.5)*amount);
        z+=((Math.random()-0.5)*amount);
    }

    public ArrayList<Evolvable> breed(ArrayList<Evolvable> parents, int n){
        ArrayList<Evolvable> children = new ArrayList<>();
        for (int i=0; i<n; i++){
            Evolvable p1 = parents.get((int)(Math.random() * parents.size()));
            Evolvable p2 = parents.get((int)(Math.random() * parents.size()));
            Evolvable p3 = parents.get((int)(Math.random() * parents.size()));
            EvolvableClass child = new EvolvableClass();
            child.x = ((EvolvableClass)p1).x;
            child.y = ((EvolvableClass)p2).y;
            child.z = ((EvolvableClass)p3).z;
            children.add(child);
        }
        return children;
    }
}
