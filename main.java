import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

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

        Image img = new Image("view.png");
        entity.setTargetImage(img);
        EvolutionController controller = new EvolutionController(entity);
        System.out.println(entity);
        double error = 100000000;
        for (int i = 0; i<180; i++) {
            error = controller.update(1000, 1, 0.015);
            Generation gen = controller.getLastGen();
            controller.generations = new ArrayList<Generation>();
            controller.generations.add(gen);
            System.out.println(error+" at gen: "+i);
        }
        System.out.println((PolygonSet)controller.generations.get(controller.generations.size()-1).getBest());
//        entity.evaluate();
//        System.out.println(entity.getCost());
        final Canvas canvas = new Canvas(img.getWidth(),img.getHeight());
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        entity = (PolygonSet)controller.getLastGen().getBest();
        entity.drawPolygons(gc);
        //entity.subtractTargetImage(gc);
        stage.setScene(new Scene(new Group(canvas)));
        stage.show();
    }
    public static void main(String[] args) {

        launch(args);
    }
}
