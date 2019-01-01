import java.util.ArrayList;
import java.util.Random;

public class PolygonSet{//} implements Evolvable {
    private ArrayList<Polygon> polygons = new ArrayList<>();
    Random generator = new Random();
    public void mutate(double amount){
        if (generator.nextDouble()<amount){
            removeRandomPolygon();
        }
        if (generator.nextDouble()<amount){
            addRandomPolygon();
        }
        for (Polygon pol: polygons) pol.mutate(amount);
    }
    public ArrayList<Evolvable> breed(ArrayList<Evolvable> parents, int n){
        ArrayList<Evolvable> children = new ArrayList<>();
        for(int i = 0; i < n; i++){

        }
        return null;
    }

    public void removeRandomPolygon(){
        polygons.remove(generator.nextInt(polygons.size()));
    }
    public void addRandomPolygon(){
        polygons.add(new Polygon());
    }
}
