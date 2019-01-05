import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Scanner;

public class main extends Application {
    public void start(Stage stage) {
        stage.setTitle("BufferedImageTest");
        PolygonSet entity = new PolygonSet();
        int[] x1 = {10, 15, 15, 10};
        int[] y1 = {10, 10, 15, 15};
        int[] x2 = {200, 250, 250, 200};
        int[] y2 = {100, 100, 150, 150};
        int[] x3 = {100, 150, 150, 100};
        int[] y3 = {200, 200, 250, 250};
        Polygon p1 = new Polygon(x1, y1, 4, Color.color(0,0,0,0.5));
        Polygon p2 = new Polygon(x2, y2, 4, Color.color(0,0,0,0.5));
        Polygon p3 = new Polygon(x3, y3, 4, Color.color(0,0,1,0.5));
//        entity.addRandomPolygon();
        entity.polygons.add(p1);
        Image img = new Image("mona.jpg");
        final Canvas canvas = new Canvas(img.getWidth(),img.getHeight());
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        stage.setScene(new Scene(new Group(canvas)));
        stage.show();

        entity.setTargetImage(img);

        System.out.println(img.getWidth());
        PolygonMutationParams params = new PolygonMutationParams(0.02, 0.05,0.025,0.1, 0.05, 0.02, 0.01, 0.05);
        params.width = (int)img.getWidth();
        params.height = (int)img.getHeight();
        entity.recentParams = params;
        for (int i = 0; i<50; i++){
             entity.polygons.add(new Polygon(params));
        }
        double fitness = 0.5;
        EvolutionController controller = new EvolutionController(entity);
        for (int i = 0; i<400; i++) {
            params.polygonSize = Math.sqrt(fitness);
            controller.update(400, 2, params);
            Generation gen = controller.getLastGen();
            controller.generations = new ArrayList<Generation>();
            controller.generations.add(gen);
            fitness = ((PolygonSet)(controller.getLastGen().getBest())).getFitness();
            System.out.println(fitness+" at gen: "+i);

            entity = (PolygonSet)controller.getLastGen().getBest();
            entity.drawPolygons(gc);

            //stage.setScene(new Scene(new Group(canvas)));
            //stage.show();
        }
        Point ROI = entity.getROI(1,null);
        params.ROIx = ROI.x;
        params.ROIy = ROI.y;
        params.width /= Math.pow(2,1);
        params.height /= Math.pow(2,1);
        entity.base = (PolygonSet) entity.clone();
        entity.clearPolys();
        System.out.println("roi: "+ROI);
        System.out.println(entity);

        //creating one generation to copy the base to all the individuals
        controller.update(400, 1, params);
        for (int i = 0; i<400; i++) {
            params.polygonSize = Math.sqrt(fitness);
            controller.update(400, 2, params);
            Generation gen = controller.getLastGen();
            controller.generations = new ArrayList<Generation>();
            controller.generations.add(gen);
            fitness = ((PolygonSet)(controller.getLastGen().getBest())).getFitness();
            System.out.println(fitness+" at gen: "+i);

            entity = (PolygonSet)controller.getLastGen().getBest();
            entity.drawPolygons(gc);

            //stage.setScene(new Scene(new Group(canvas)));
            //stage.show();
        }
        entity.drawBackground(gc);
        entity.base.drawPolygons(gc);
        entity.drawPolygons(gc);
        System.out.println(entity);
//        entity.evaluate();
//        System.out.println(entity.getCost());

        //entity = (PolygonSet)controller.getLastGen().getBest();
        //entity.drawPolygons(gc);
        //entity.subtractTargetImage(gc);
        //stage.setScene(new Scene(new Group(canvas)));
        //stage.show();
    }
    public static void main(String[] args) {

        launch(args);
    }
}
