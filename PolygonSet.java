import javafx.application.Platform;
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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class PolygonSet implements Evolvable, Serializable {

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    private double cost=0;
    public ArrayList<Polygon> polygons = new ArrayList<>();
    public PolygonSet base = null;
    public PolygonMutationParams recentParams;

    public static Image getTargetImage() {
        return targetImage;
    }

    public static void setTargetImage(Image targetImage) {
        PolygonSet.targetImage = targetImage;
    }

    private transient static Image targetImage;
    Random generator = new Random();

    public void mutate(MutationParameters params){
        PolygonMutationParams parameters = (PolygonMutationParams)params;
        recentParams = parameters;
        if(polygons.size()>1) {
            if (generator.nextDouble() < parameters.deletePolyChance) {
                removeRandomPolygon();
            }
        }
        if (generator.nextDouble()<parameters.addPolyChance){
            addRandomPolygon((PolygonMutationParams)params);
        }
        for (Polygon pol: polygons) pol.mutate(parameters);
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
    public void addRandomPolygon(PolygonMutationParams params){
        //System.out.println("adding random polygon");
        polygons.add(new Polygon(params));
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
        //gc.setFill(new Color(1,1,1,1));
        //gc.fillRect(0,0,targetImage.getWidth(), targetImage.getHeight());
        for (Polygon polygon: polygons){
            //System.out.println("drawing polygon");
            polygon.draw(gc);
        }

    }
    public void drawROI(GraphicsContext gc){
        double[] x = {recentParams.ROIx, recentParams.ROIx+recentParams.width, recentParams.ROIx+recentParams.width, recentParams.ROIx};
        double[] y = {recentParams.ROIy, recentParams.ROIy,recentParams.ROIy+recentParams.height, recentParams.ROIy+recentParams.height};
        gc.setStroke(new Color(1,0,0,1));
        gc.strokePolygon(x,y,4);
    }

    public void drawBackground(GraphicsContext gc){
        gc.setGlobalAlpha(1.0);
        gc.setFill(new Color(1,1,1,1));
        gc.fillRect(0,0,targetImage.getWidth(), targetImage.getHeight());
    }
    public void drawBackground(GraphicsContext gc, double scale){
        gc.setGlobalAlpha(1.0);
        gc.setFill(new Color(1,1,1,1));
        gc.fillRect(0,0,targetImage.getWidth()/scale, targetImage.getHeight()/scale);
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
        //if (true) return;
        final javafx.scene.canvas.Canvas canvas = new Canvas(targetImage.getWidth(), targetImage.getHeight());
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        drawBackground(gc);
        if (base != null) base.drawPolygons(gc);
        drawPolygons(gc);
        subtractTargetImage(gc);
        WritableImage snap = gc.getCanvas().snapshot(null, null);
        //ColorAdjust colorAdjust = new ColorAdjust();
        //colorAdjust.setSaturation(-1.0);
        //gc.setEffect(colorAdjust);
        gc.drawImage(snap, 0,0);
        PixelReader reader = snap.getPixelReader();
        cost = 0;

        for (int y=recentParams.ROIy; y<recentParams.ROIy+recentParams.height; y+=1){
            for (int x=recentParams.ROIx; x<recentParams.ROIx+recentParams.width; x+=1){
                Color sample = reader.getColor(x,y);
                cost+=(sample.getBlue()*sample.getBlue()+sample.getGreen()*sample.getGreen()+sample.getRed()*sample.getRed())/3;
            }
        }
    }
    public Object clone(){
        PolygonSet o  = new PolygonSet();
        for(Polygon polygon: polygons){
            o.polygons.add((Polygon)polygon.clone());
        }
        if (base!=null) o.base = (PolygonSet)base.clone();
        o.recentParams = (PolygonMutationParams)recentParams.clone();
        return o;
    }

    public double getFitness(){
        return 1.0-cost/recentParams.width/recentParams.height;
    }

    public WritableImage getCostMap(){
        final javafx.scene.canvas.Canvas canvas = new Canvas(recentParams.width, recentParams.height);
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        drawBackground(gc);
        if (base != null) base.drawPolygons(gc);
        drawPolygons(gc);
        subtractTargetImage(gc);
        WritableImage snap = gc.getCanvas().snapshot(null, null);
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setSaturation(-1.0);
        gc.setEffect(colorAdjust);
        gc.drawImage(snap, 0,0);
        return snap;
    }
    private Point ROI = null;
    public Point getROIsafe(double level, Image costMap){
        AppThread.runAndWait(()->{
            ROI = getROI(level, costMap);
        });
        return ROI;
    }

    public Point getROI(double level, Image costMap){
        if (costMap == null) costMap = getCostMap();
        PixelReader reader = costMap.getPixelReader();
        double[][] integral = new double[(int)costMap.getWidth()][(int)costMap.getHeight()];
        integral[0][0] = colorBrightness(reader.getColor(0,0));
        for (int x=1; x<costMap.getWidth(); x++) integral[x][0] = integral[x-1][0]+colorBrightness(reader.getColor(x,0));
        for (int y=1; y<costMap.getHeight(); y++) integral[0][y] = integral[0][y-1]+colorBrightness(reader.getColor(0,y));
        for (int y=1; y<costMap.getHeight(); y++) {
            for (int x = 1; x < costMap.getWidth(); x++) {
                integral[x][y] = integral[x-1][y]+integral[x][y-1]-integral[x-1][y-1]+colorBrightness(reader.getColor(x,y));
            }
        }
        int roiWidth = (int)(costMap.getWidth()/Math.pow(2,level));
        int roiHeight = (int)(costMap.getHeight()/Math.pow(2,level));
        int x_max = 0;
        int y_max = 0;
        double maxCost = 0;
        for(int x=0; x<costMap.getWidth()-roiWidth; x++){
            for(int y=0; y<costMap.getHeight()-roiHeight; y++){
                double sum = integral[x+roiWidth][y+roiHeight]-integral[x][y+roiHeight]-integral[x+roiWidth][y]+integral[x][y];
                if (sum>maxCost){
                    x_max = x;
                    y_max = y;
                    maxCost = sum;
                }
            }
        }
        return new Point(x_max, y_max);
    }
    public void clearPolys(){
        polygons.clear();
    }

    static double colorBrightness(Color sample){
        return (sample.getBlue()*sample.getBlue()+sample.getGreen()*sample.getGreen()+sample.getRed()*sample.getRed())/3;
    }

    public void setScale(double scale){
        for (Polygon p: polygons) p.setScale(scale);
        recentParams.ROIx*=scale;
        recentParams.ROIy*=scale;
        recentParams.width*=scale;
        recentParams.height*=scale;
        if (base!=null) base.setScale(scale);
    }


    public void merge(){
        if (base == null) return;
        base.polygons.addAll(polygons);
        polygons = base.polygons;
        base = null;
    }
}
