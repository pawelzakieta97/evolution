import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.LinkedList;

public class Polygon {
    private LinkedList<Point> vertices;
    private Color color;
    public Polygon(LinkedList<Point> list, Color color){
        vertices = list;
        this.color = color;
    }
    public void draw(GraphicsContext gc, double alpha){
        gc.setGlobalAlpha(alpha);
        gc.setFill(color);
        gc.fillPolygon(getXCoordinates(), getYCoordinates(),vertices.size());
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
}
