import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class PolygonSet implements Evolvable {

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    private double cost=0;
    public ArrayList<Polygon> polygons = new ArrayList<>();

    public static Image getTargetImage() {
        return targetImage;
    }

    public static void setTargetImage(Image targetImage) {
        PolygonSet.targetImage = targetImage;
        Constants.RES_X = (int)targetImage.getWidth();
        Constants.RES_Y = (int)targetImage.getHeight();
    }

    private static Image targetImage;
    Random generator = new Random();
    public void mutate(double amount){
        if(polygons.size()>1) {
            if (generator.nextDouble() < amount*10) {
                removeRandomPolygon();
            }
        }
        if (generator.nextDouble()<amount*10){
            addRandomPolygon();
        }
        for (Polygon pol: polygons) pol.mutate(amount);
    }
    public ArrayList<Evolvable> breed(ArrayList<Evolvable> parents, int n, double crossover){
        ArrayList<Evolvable> children = new ArrayList<>();
        for(int i = 0; i < n; i++){
            PolygonSet parent =(PolygonSet)parents.get(generator.nextInt(parents.size()));
            PolygonSet base = (PolygonSet)parent.clone();
            for(int poly=0; poly<base.polygons.size(); poly++){
                if (generator.nextDouble()<crossover){
                    PolygonSet origin = (PolygonSet)parents.get(generator.nextInt(parents.size()));
                    if (origin.polygons.size()<=poly) break;
                    base.polygons.set(poly, (Polygon)origin.polygons.get(poly).clone());
                }
            }
            children.add(base);
        }
        return children;
    }

    public void removeRandomPolygon(){
        //System.out.println("removing random polygon");
        polygons.remove(generator.nextInt(polygons.size()));
    }
    public void addRandomPolygon(){
        //System.out.println("adding random polygon");
        polygons.add(new Polygon("triangle"));
    }
    public BufferedImage getImage(){
        BufferedImage bi = new BufferedImage(
                (int)targetImage.getWidth(), (int)targetImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bi.createGraphics();
        for (Polygon polygon: polygons) polygon.draw(g);

        return bi;
    }
    public void drawPolygons(GraphicsContext gc){
        //gc.setGlobalBlendMode(BlendMode.SRC_OVER);
        gc.setGlobalAlpha(1.0);
        gc.setFill(new Color(1,1,1,1));
        gc.fillRect(0,0,targetImage.getWidth(), targetImage.getHeight());
        for (Polygon polygon: polygons){
            //System.out.println("drawing polygon");
            polygon.draw(gc);
        }

    }
    public void subtractTargetImage(GraphicsContext gc){
        BlendMode prev = gc.getGlobalBlendMode();
        gc.setGlobalBlendMode(BlendMode.DIFFERENCE);
        gc.drawImage(targetImage, 0,0);
        gc.setGlobalBlendMode(prev);
    }
    public Image awt2fx(BufferedImage src){
        WritableImage image = SwingFXUtils.toFXImage(src, null);
        return image;
    }
    public String toString(){
        String out = "";
        for (Polygon polygon: polygons){
            out = out+polygon;
        }
        return out;
    }
    public void evaluate(){
        final javafx.scene.canvas.Canvas canvas = new Canvas(Constants.RES_X, Constants.RES_Y);
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        drawPolygons(gc);
        subtractTargetImage(gc);
        WritableImage snap = gc.getCanvas().snapshot(null, null);
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setSaturation(-1.0);
        gc.setEffect(colorAdjust);
        gc.drawImage(snap, 0,0);
        PixelReader reader = snap.getPixelReader();
        cost = 0;

        for (int y=0; y<Constants.RES_Y; y+=10){
            for (int x=0; x<Constants.RES_X; x+=10){
                Color sample = reader.getColor(x,y);
                cost+=sample.getBlue()+sample.getGreen()+sample.getRed();
            }
        }
    }
    public Object clone(){
        PolygonSet o  = new PolygonSet();
        for(Polygon polygon: polygons){
            o.polygons.add((Polygon)polygon.clone());
        }
        return o;
    }

}
