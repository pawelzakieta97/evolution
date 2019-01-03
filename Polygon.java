import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class Polygon implements Cloneable{
    Random generator = new Random();
    private LinkedList<Point> vertices = new LinkedList<>();
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
     * constructor for creating random polygon (triangle)
     */
    public Polygon(){

    }
    public Polygon(String s){
        int[] x = new int[4];
        int[] y = new int[4];
        for (int i = 0; i<4; i++){
            x[i] = generator.nextInt(Constants.RES_X);
            y[i] = generator.nextInt(Constants.RES_Y);
        }
        Color color = new Color(generator.nextDouble(),generator.nextDouble(),generator.nextDouble(),generator.nextDouble());
        Polygon p = new Polygon(x,y,4,color);
        this.vertices = p.vertices;
        this.color = p.color;
    }
    public void draw(GraphicsContext gc){        //gc.setGlobalAlpha(color.getOpacity());
        gc.setFill(color);
        gc.fillPolygon(Arrays.stream(getXCoordinates()).asDoubleStream().toArray(), Arrays.stream(getYCoordinates()).asDoubleStream().toArray(),vertices.size());
    }
    public void draw(Graphics2D g2){
        java.awt.Color c = new java.awt.Color((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), (float)color.getOpacity());
        g2.setPaint(c);
        g2.fillPolygon(getXCoordinates(), getYCoordinates(),vertices.size());
    }
    public void mutate(double amount){
        if (generator.nextDouble()<amount) addRandomVertex();
        if (vertices.size()>3) {
            if (generator.nextDouble() < amount) removeRandomVertex();
        }
        for(Point p: vertices){
            //if (generator.nextDouble()<amount) p.mutate(amount);
            double multiplier = 1;
            p.mutate(amount*multiplier);
        }
        mutateColor(amount);

    }
    public void move(int x, int y){
        for (Point p: vertices){
            p.move(x,y);
        }
    }

    public void addRandomVertex(){
        int num = generator.nextInt(vertices.size()-2)+1;
        vertices.add(num, Point.middle(vertices.get(num), vertices.get(num-1)));
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
        o.color = new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getOpacity());
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
}
