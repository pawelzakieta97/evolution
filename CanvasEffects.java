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

        final int NUM_IMGS = 5;
        final double W = image.getWidth();
        final double H = image.getWidth();


        final Canvas canvas = new Canvas(W * NUM_IMGS, H);
        final GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.GOLD);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        LinkedList<Point> p1list = new LinkedList<>();
        p1list.add(new Point(0, 0));
        p1list.add(new Point(50, 0));
        p1list.add(new Point(50, 50));
        p1list.add(new Point(0, 50));
        Polygon p1 = new Polygon(p1list, Color.color(0,0,0.5,1.0));

        p1.draw(gc, 0.5);
        stage.setScene(new Scene(new Group(canvas)));
        stage.show();
    }
    public static void main(String[] args) {

        launch(args);
    }

    // icon license: Linkware (Backlink to http://uiconstock.com required) commercial usage not allowed.
    private static final String IMAGE_LOC = "http://icons.iconarchive.com/icons/uiconstock/flat-halloween/128/Halloween-Bat-icon.png";
}