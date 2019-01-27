import javafx.scene.canvas.GraphicsContext;
import java.awt.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class Polygon implements Cloneable, Serializable {

    private Random generator = new Random();
    /**
     * an array of vertices of the polygon
     */
    private LinkedList<Point> vertices = new LinkedList<>();
    /**
     *A class representing the color of the polygon created purely for the purpose of serialization (the generic color
     * objects couldn't be serialized)
     */
    private Colorado color;
    private int maxVertex = 7;
    public Polygon(LinkedList<Point> list, Colorado color){
        vertices = list;
        this.color = color;
    }
    public Polygon(int[] x, int[] y, int size, Colorado color){
        vertices = new LinkedList<>();
        for (int i = 0; i<size; i++){
            vertices.add(new Point(x[i], y[i]));
        }
        this.color = color;
    }
    public Polygon(){

    }

    /**
     * constructor creating a random triangle
     * @param params
     */
    public Polygon(PolygonMutationParams params){
        int[] x = new int[3];
        int[] y = new int[3];
        int basex = params.ROIx + generator.nextInt(params.width);
        int basey = params.ROIy + generator.nextInt(params.height);
        for (int i = 0; i<3; i++){
            x[i] = basex+generator.nextInt((int)(params.width*params.polygonSize+1));
            y[i] = basey+generator.nextInt((int)(params.height*params.polygonSize+1));
            if(x[i]<params.ROIx) x[i]=params.ROIx;
            if(x[i]>params.ROIx+params.width) x[i] = params.ROIx+params.width;
            if(y[i]<params.ROIy) x[i]=params.ROIy;
            if(y[i]>params.ROIy+params.height) y[i] = params.ROIy+params.height;
        }

//        Color color = new Color(generator.nextDouble(),generator.nextDouble(),generator.nextDouble(),generator.nextDouble());
        Colorado color = new Colorado(generator.nextDouble(),generator.nextDouble(),generator.nextDouble(),generator.nextDouble());
        Polygon p = new Polygon(x,y,3,color);
        this.vertices = p.vertices;
        this.color = p.color;
    }

    /**
     * draws a polygon
     * @param gc polygon is drawn onto this graphics context
     */
    public void draw(GraphicsContext gc){
        gc.setFill(color.translateToColor());
        gc.fillPolygon(Arrays.stream(getXCoordinates()).asDoubleStream().toArray(), Arrays.stream(getYCoordinates()).asDoubleStream().toArray(),vertices.size());
    }

    /**
     * Introduces slight changes into the polyogn- color and shape
     * @param parameters specifies the probabilities and strength of mutation
     */
    public void mutate(PolygonMutationParams parameters){
        if (generator.nextDouble()<parameters.addVertexChance) addRandomVertex();
        if (vertices.size()>3) {
            if (generator.nextDouble() < parameters.deleteVertexChance) removeRandomVertex();
        }
        for(Point p: vertices){
            if (generator.nextDouble()<parameters.amount) {
                p.mutate(parameters);
            }
        }
        if (generator.nextDouble()<parameters.amount) {
            mutateColor(parameters.colorChange);
        }

    }

    public void addRandomVertex(){
        if (vertices.size()==maxVertex) return;
        int num = generator.nextInt(vertices.size()-2)+1;
        vertices.add(num, Point.middle(vertices.get(num), vertices.get(num-1)));
    }
    public void removeRandomVertex(){
        if (vertices.size() == 3) return;
        vertices.remove(generator.nextInt(vertices.size()));
    }
    public void mutateColor(double amount){
        color = new Colorado(Math.max(Math.min(color.getRed()+generator.nextGaussian()*amount,1),0),
                Math.max(Math.min(color.getGreen()+generator.nextGaussian()*amount,1),0),
                Math.max(Math.min(color.getBlue()+generator.nextGaussian()*amount,1),0),
                Math.max(Math.min(color.getOpacity()+generator.nextGaussian()*amount,1),0)
        );

    }

    public int[] getXCoordinates(){
        int x[] = new int[vertices.size()];
        int i = 0;
        for (Point p: vertices){
            x[i] = p.x;
            i++;
        }
        return x;
    }
    public int[] getYCoordinates(){
        int y[] = new int[vertices.size()];
        int i = 0;
        for (Point p: vertices){
            y[i] = p.y;
            i++;
        }
        return y;
    }



    public Object clone(){
        Polygon o  = new Polygon();
//        o.color = new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getOpacity());
        o.color = new Colorado(color.getRed(), color.getGreen(), color.getBlue(), color.getOpacity());
        for(Point p: vertices){
            o.vertices.add((Point)p.clone());
        }
        return o;
    }
    public String toString(){
        String out = new String("polygon:\ncolor:");
        out+="r: "+color.getRed()+", g: "+color.getGreen()+", b: "+color.getBlue()+", a: "+color.getOpacity()+"\n";
        for (Point p: vertices){
            out = out+p;
        }
        return out;
    }

    /**
     * resizes the polygon
     * @param scale
     */
    public void setScale(double scale){
        for (Point p: vertices){
            p.x = (int)(p.x*scale);
            p.y = (int)(p.y*scale);
        }
    }
}
