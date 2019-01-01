import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.LinkedList;
import java.util.Random;

public class Polygon implements Cloneable{
    Random generator = new Random();
    private LinkedList<Point> vertices;
    private Color color;
    public Polygon(LinkedList<Point> list, Color color){
        vertices = list;
        this.color = color;
    }
    public Polygon(int[] x, int[] y, int size, Color color){
        vertices = new LinkedList<>();
        for (int i = 0; i<size; i++){
            vertices.add(new Point(x[i], y[i]));
        }
        this.color = color;
    }
    /**
     * constructor for creating random polygon
     */
    public Polygon(){

    }
    public void draw(GraphicsContext gc, double alpha){
        gc.setGlobalAlpha(alpha);
        gc.setFill(color);
        gc.fillPolygon(getXCoordinates(), getYCoordinates(),vertices.size());
    }
    public void mutate(double amount){
        if (generator.nextDouble()<amount) addRandomVertex();
        if (generator.nextDouble()<amount) removeRandomVertex();
        for(Point p: vertices){
            //if (generator.nextDouble()<amount) p.mutate(amount);
            p.mutate(amount);
        }
        mutateColor(amount);

    }
    public void move(int x, int y){
        for (Point p: vertices){
            p.move(x,y);
        }
    }

    public void addRandomVertex(){
        int num = generator.nextInt(vertices.size());
        vertices.add(num, (Point)vertices.get(num).clone());
    }
    public void removeRandomVertex(){
        if (vertices.size() == 3) return;
        vertices.remove(generator.nextInt(vertices.size()));
    }
    public void mutateColor(double amount){
        color = new Color(Math.max(Math.min(color.getRed()+generator.nextGaussian()*amount,1),0),
                Math.max(Math.min(color.getGreen()+generator.nextGaussian()*amount,1),0),
                Math.max(Math.min(color.getBlue()+generator.nextGaussian()*amount,1),0),
                Math.max(Math.min(color.getOpacity()+generator.nextGaussian()*amount,1),0)
        );
    }

    public double[] getXCoordinates(){
        double x[] = new double[vertices.size()];
        int i = 0;
        for (Point p: vertices){
            x[i] = p.x;
            i++;
        }
        return x;
    }
    public double[] getYCoordinates(){
        double y[] = new double[vertices.size()];
        int i = 0;
        for (Point p: vertices){
            y[i] = p.y;
            i++;
        }
        return y;
    }
    public Object clone(){
        Polygon o  = new Polygon();
        o.color = new Color(color.getRed(), color.getGreen(), color.getRed(), color.getOpacity());
        for(Point p: vertices){
            o.vertices.add((Point)p.clone());
        }
        return null;
    }
}
