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
    public void start(Stage stage) {stage.setTitle("BufferedImageTest");
        PolygonSet entity = new PolygonSet();
        int[] x1 = {100, 150, 150, 100};
        int[] y1 = {100, 100, 150, 150};
        int[] x2 = {200, 250, 250, 200};
        int[] y2 = {100, 100, 150, 150};
        int[] x3 = {100, 150, 150, 100};
        int[] y3 = {200, 200, 250, 250};
        Polygon p1 = new Polygon(x1, y1, 4, Color.color(0,0,0,0.5));
        Polygon p2 = new Polygon(x2, y2, 4, Color.color(0,0,0,0.5));
        Polygon p3 = new Polygon(x3, y3, 4, Color.color(0,0,1,0.5));
//        entity.addRandomPolygon();
        entity.polygons.add(p1);
        //entity.polygons.add(p2);
        //entity.polygons.add(p3);
        //System.out.println(entity);
        Image img = new Image("mona.jpg");
        final Canvas canvas = new Canvas(img.getWidth(),img.getHeight());
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        stage.setScene(new Scene(new Group(canvas)));
        stage.show();

        entity.setTargetImage(img);

        System.out.println(entity);
        PolygonMutationParams params = new PolygonMutationParams(0.02, 0.0,0.0,0.1, 0.05, 0.02, 0.02, 0.05);
        double error = 2000;
        for (int i = 0; i<50; i++){
            entity.polygons.add(new Polygon(params));
        }
        EvolutionController controller = new EvolutionController(entity);
        for (int i = 0; i<1000; i++) {
            params.polygonSize = 0.1*error/800;
            error = controller.update(100, 2, params);
            Generation gen = controller.getLastGen();
            controller.generations = new ArrayList<Generation>();
            controller.generations.add(gen);
            System.out.println(error+" at gen: "+i);

            entity = (PolygonSet)controller.getLastGen().getBest();
            entity.drawPolygons(gc);

            //stage.setScene(new Scene(new Group(canvas)));
            //stage.show();
        }
        System.out.println((PolygonSet)controller.generations.get(controller.generations.size()-1).getBest());
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
