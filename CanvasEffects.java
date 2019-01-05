import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.paint.*;
import javafx.stage.Stage;

import java.util.LinkedList;

public class CanvasEffects extends Application {

    @Override
    public void start(Stage stage) {
        final Image image = new Image(IMAGE_LOC);

        final int NUM_IMGS = 1;
        final double W = Constants.RES_X;
        final double H = Constants.RES_Y;


        final Canvas canvas = new Canvas(W * NUM_IMGS, H);
        final GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        LinkedList<Point> p1list = new LinkedList<>();
        p1list.add(new Point(100, 100));
        p1list.add(new Point(150, 100));
        p1list.add(new Point(150, 150));
        p1list.add(new Point(100, 150));
        int[] x = {100, 150, 150, 100};
        int[] y = {100, 100, 150, 150};
        Polygon p1 = new Polygon(x, y, 4, Color.color(1,0,0,0.5));
        Polygon p2 = new Polygon(x, y, 4, Color.color(0,0,0,0.5));
        Polygon p3 = new Polygon(x, y, 4, Color.color(0,0,1,0.5));
        Polygon p4 = new Polygon();

        p1.draw(gc);
        p2.draw(gc);
        p3.draw(gc);
        p4.draw(gc);
        gc.setGlobalAlpha(1.0);
        gc.setGlobalBlendMode(BlendMode.DIFFERENCE);
        gc.drawImage(image, 0,0);
        stage.setScene(new Scene(new Group(canvas)));
        stage.show();
    }
    public static void main(String[] args) {

        launch(args);
    }

    // icon license: Linkware (Backlink to http://uiconstock.com required) commercial usage not allowed.
    private static final String IMAGE_LOC = "http://icons.iconarchive.com/icons/uiconstock/flat-halloween/128/Halloween-Bat-icon.png";
}