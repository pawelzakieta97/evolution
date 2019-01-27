import javafx.scene.paint.Color;

import java.awt.image.ColorConvertOp;
import java.io.*;

public class SerialTest {
    public static Object deserialize(String fileName) throws IOException,
            ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        BufferedInputStream bis = new BufferedInputStream(fis);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Object obj = ois.readObject();
        ois.close();
        return obj;
    }

    public static void serialize(Object obj, String fileName)
            throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.close();
    }

    public static void main(String[] args) {

//        this works
//        Colorado color = new Colorado(0.1, 0.1, 0.1,0.1);
//        try {
//            serialize(color, "color.txt");
//            Colorado newColor = (Colorado) deserialize("color.txt");
//            System.out.println(newColor.toString());
//
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }

//        this also works
        PolygonMutationParams params = new PolygonMutationParams(0.02, 0.05,0.025,0.1, 0.05, 0.02, 0.01, 0.05);
        params.width=100;
        params.height=100;
        Polygon polygon = new Polygon(params);
//        System.out.println(polygon.toString());
//        try {
//            serialize(polygon, "polygon.txt");
//            Polygon newPoly = (Polygon) deserialize("polygon.txt");
//            System.out.println(newPoly.toString());
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }

        PolygonSet entity = new PolygonSet();
        entity.setRecentParams(params);
        entity.addPolygon(new Polygon(params));
        try {
            serialize(entity, "polySet.txt");
            PolygonSet newEntity = (PolygonSet) deserialize("polySet.txt");
            System.out.println(newEntity.toString()+"zrobione");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



}
