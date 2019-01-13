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
//        Color color = new Color(1,1,1,1);
//        System.out.println(color.toString());
        Colorado color = new Colorado(0.1, 0.1, 0.1,0.1);
        try {
            serialize(color, "color.txt");
            Colorado newColor = (Colorado) deserialize("color.txt");
            System.out.println(newColor.toString());

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
