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
        final double W = Constants.resx;
        final double H = Constants.resy;


        final Canvas canvas = new Canvas(W * NUM_IMGS, H);
        final GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        LinkedList<Point> p1list = new LinkedList<>();
        p1list.add(new Point(100, 100));
        p1list.add(new Point(150, 100));
        p1list.add(new Point(150, 150));
        p1list.add(new Point(100, 150));
        Polygon p1 = new Polygon(p1list, Color.color(0,0,0.5,1.0));
        p1.mutate(0.1);

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