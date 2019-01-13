import javafx.scene.paint.Color;

import java.io.Serializable;

public class Colorado implements Serializable {
    private double r, g, b, a;
    public Colorado (double r, double g, double b, double a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }


    @Override
    public String toString() {
        return "r: "+r+"\ng: "+g+"\nb: "+b+"\na: "+a;
    }

    public Color translateToColor() {
        return new Color(r, g, b, a);
    }

    public double getRed() {
        return r;
    }

    public double getGreen() {
        return g;
    }

    public double getBlue() {
        return b;
    }

    public double getOpacity() {
        return a;
    }


}
