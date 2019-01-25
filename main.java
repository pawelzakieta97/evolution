import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class main extends Application {
    static public GraphicsContext gc;
    static public Image img;
    public void start(Stage stage) {
        stage.setTitle("BufferedImageTest");
        img = new Image("FILE:///C:/img/girl.png");
        //img = (Image)f;
        final Canvas canvas = new Canvas(img.getWidth(),img.getHeight());
        gc = canvas.getGraphicsContext2D();

        BorderPane pane = new BorderPane();
        Button button = new Button("Press Me!");
        pane.setCenter(button);



        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                new Thread(() -> {
                    System.out.println(Thread.currentThread().getName());
                    PolygonSet entity = new PolygonSet();
                    PolygonMutationParams params = new PolygonMutationParams(0.02, 0.05,0.025,0.4, 0.05, 0.02, 0.01, 0.05);
                    params.width = (int)img.getWidth();
                    params.height = (int)img.getHeight();
                    entity.setTargetImage(img);
                    entity.recentParams = params;
                    for (int i = 0; i<0; i++){
                        entity.polygons.add(new Polygon(params));
                    }
                    double fitness = 0.5;
                    EvolutionController controller = new EvolutionController(entity);
                    for (int i = 0; i<100; i++) {
                        params.polygonSize = Math.sqrt(1-fitness);
                        controller.update(400, 2, params);
                        controller.clear();
                        fitness = ((PolygonSet)(controller.getLastGen().getBest())).getFitness();
                        System.out.println(fitness+" at gen: "+i);
                        entity = (PolygonSet)controller.getLastGen().getBest();
                        entity.drawBackground(gc);
                        entity.drawPolygons(gc);

                    }
                    Point ROI = entity.getROIsafe(1,null);
                    params.ROIx = ROI.x;
                    params.ROIy = ROI.y;
                    params.width /= Math.pow(2,1);
                    params.height /= Math.pow(2,1);
                    entity.base = (PolygonSet) entity.clone();
                    entity.clearPolys();
                    System.out.println("roi: "+ROI);
                    System.out.println(entity);

                    //creating one generation to copy the base to all the individuals
                    controller.update(100, 1, params);
                    for (int i = 0; i<400; i++) {
                        //params.polygonSize = Math.sqrt(1-fitness);
                        controller.update(400, 2, params);
                        controller.clear();
                        fitness = ((PolygonSet)(controller.getLastGen().getBest())).getFitness();
                        System.out.println(fitness+" at gen: "+i);
                        entity = (PolygonSet)controller.getLastGen().getBest();
                        entity.drawBackground(gc);
                        entity.base.drawPolygons(gc);
                        entity.drawPolygons(gc);

                        //stage.setScene(new Scene(new Group(canvas)));
                        //stage.show();
                    }
                }).start();
            }
        });


        stage.setScene(new Scene(new Group(canvas, button)));
        stage.show();


//        entity.drawBackground(gc);
//        entity.base.drawPolygons(gc);
//        entity.drawPolygons(gc);
//        System.out.println(entity);
    }
    public static void main(String[] args) {

        launch(args);
    }
}
