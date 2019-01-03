import java.util.Random;

public class Point implements Cloneable{
    int x, y;
    Random generator = new Random();
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }
    public void mutate(double amount){
        move((int)(generator.nextGaussian()*amount*Constants.RES_X), (int)(generator.nextGaussian()*amount*Constants.RES_Y));
    }

    void move(int x, int y){
        this.x += x;
        this.y += y;
        if (this.x>=Constants.RES_X) this.x = Constants.RES_X;
        if (this.x<0) this.x = 0;
        if (this.y>=Constants.RES_Y) this.y = Constants.RES_Y;
        if (this.y<0) this.y=0;

    }
    public Object clone(){
        return new Point(x,y);
    }
    public String toString(){
        return "x: "+(Integer)x+", y: "+(Integer)y+"\n";
    }
    static Point middle(Point a, Point b){
        return new Point((a.x+b.x)/2, (a.y+b.y)/2);
    }
}
