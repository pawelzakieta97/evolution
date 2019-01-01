import java.util.Random;

public class Point implements Cloneable{
    int x, y;
    Random generator = new Random();
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }
    public void mutate(double amount){
        move((int)(generator.nextGaussian()*amount*Constants.resx), (int)(generator.nextGaussian()*amount*Constants.resy));
    }

    void move(int x, int y){
        this.x += x;
        this.y += y;
        if (this.x>=Constants.resx) this.x = Constants.resx;
        if (this.x<0) this.x = 0;
        if (this.y>=Constants.resy) this.y = Constants.resy;
        if (this.y<0) this.y=0;

    }
    public Object clone(){
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
