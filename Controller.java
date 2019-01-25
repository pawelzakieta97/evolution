
//import com.sun.prism.Image;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    final double AMOUNT;
    final double ADDPOLYCHANCE;
    final double DELPOLYCHANCE;
    final double POLYSIZE;
    final double COLCHANGE;
    final double VERTEXADD;
    final double VERTEXDEL;
    final double VERTEXSHIFT;
    final double ROI;

    private PolygonMutationParams params = new PolygonMutationParams(0.02,0.05,0.025,0.2,0.05,0.04,0.02,0.05);
//    private double nowAmount;
//    private double nowChAddPol;
//    private double nowChDelPol;
//    private double nowPolSize;
//    private double nowColChange;
//    private double nowChAddVer;
//    private double nowChDelVer;
//    private double nowShVer;
    private double nowRoi;
    private int numParents;
    private int popSize;
    private double scale;

    private String path;
    private int genNum;
    private PolygonSet entity;

    private boolean running;
    private boolean runningRequest;
    private int imgNum;

    @FXML
    private TextField fieldOne;

    @FXML
    private TextField fieldOne1;

    @FXML
    private TextField fieldOne2;

    @FXML
    private TextField fieldOne3;

    @FXML
    private TextField fieldOne4;

    @FXML
    private TextField fieldOne5;

    @FXML
    private TextField fieldOne6;

    @FXML
    private TextField fieldOne7;

    @FXML
    private TextField fieldOne8;

    @FXML
    private TextField fieldPopSize;

    @FXML
    private TextField fieldNumParents;


    @FXML
    private Slider slideOne;

    @FXML
    private Slider slideOne1;

    @FXML
    private Slider slideOne2;

    @FXML
    private Slider slideOne3;

    @FXML
    private Slider slideOne4;

    @FXML
    private Slider slideOne5;

    @FXML
    private Slider slideOne6;

    @FXML
    private Slider slideOne7;

    @FXML
    private Slider slideOne8;

    @FXML
    private Slider slidePopSize;

    @FXML
    private Slider slideNumParents;

    @FXML
    private ImageView rImage;

    @FXML
    private ImageView lImage;

//    private LinkedList<Polygon> = new LinkedList<Polygon>();

    public Controller() {
        AMOUNT = 0.1;
        ADDPOLYCHANCE = 0.2;
        DELPOLYCHANCE = 0.2;
        POLYSIZE = 0.5;
        COLCHANGE = 0.2;
        VERTEXADD = 0.2;
        VERTEXDEL = 0.2;
        VERTEXSHIFT = 0.2;
        ROI = 0;

        nowRoi = 0;
        running = false;
        runningRequest = false;
        imgNum = 0;
        genNum = 0;
        entity = new PolygonSet();
        scale = 3;
    }

    @Override
    public void initialize(URL var1, ResourceBundle var2) {
        slideOne.setMax(AMOUNT);
        slideOne.setBlockIncrement(AMOUNT/100);
        fieldOne.setText(((Double)params.amount).toString());
        slideOne.valueProperty().addListener((observable, oldValue, newValue) ->{
            if (!running) {
                params.amount = newValue.doubleValue();
                fieldOne.setText(Double.toString(newValue.doubleValue()));
            }
        });

        slideOne1.setMax(ADDPOLYCHANCE);
        slideOne1.setBlockIncrement(ADDPOLYCHANCE/100);
        fieldOne1.setText(((Double)params.addPolyChance).toString());
        slideOne1.valueProperty().addListener((observable, oldValue, newValue) ->{
            if (!running) {
                params.addPolyChance = newValue.doubleValue();
                fieldOne1.setText(Double.toString(newValue.doubleValue()));
            }
        });

        slideOne2.setMax(DELPOLYCHANCE);
        slideOne2.setBlockIncrement(DELPOLYCHANCE/100);
        fieldOne2.setText(((Double)params.deletePolyChance).toString());
        slideOne2.valueProperty().addListener((observable, oldValue, newValue) ->{
            if (!running) {
                params.deletePolyChance = newValue.doubleValue();
                fieldOne2.setText(Double.toString(newValue.doubleValue()));
            }
        });

        slideOne3.setMax(POLYSIZE);
        slideOne3.setBlockIncrement(POLYSIZE/100);
        fieldOne3.setText(((Double)params.polygonSize).toString());
        slideOne3.valueProperty().addListener((observable, oldValue, newValue) ->{
            if (!running) {
                params.polygonSize = newValue.doubleValue();
                fieldOne3.setText(Double.toString(newValue.doubleValue()));
            }
        });

        slideOne4.setMax(COLCHANGE);
        slideOne4.setBlockIncrement(COLCHANGE/100);
        fieldOne4.setText(((Double)params.colorChange).toString());
        slideOne4.valueProperty().addListener((observable, oldValue, newValue) ->{
            if (!running) {
                params.colorChange = newValue.doubleValue();
                fieldOne4.setText(Double.toString(newValue.doubleValue()));
            }
        });

        slideOne5.setMax(VERTEXADD);
        slideOne5.setBlockIncrement(VERTEXADD/100);
        fieldOne5.setText(((Double)params.addVertexChance).toString());
        slideOne5.valueProperty().addListener((observable, oldValue, newValue) ->{
            if (!running) {
                params.addVertexChance = newValue.doubleValue();
                fieldOne5.setText(Double.toString(newValue.doubleValue()));
            }
        });

        slideOne6.setMax(VERTEXDEL);
        slideOne6.setBlockIncrement(VERTEXDEL/100);
        fieldOne6.setText(((Double)params.deleteVertexChance).toString());
        slideOne6.valueProperty().addListener((observable, oldValue, newValue) ->{
            if (!running) {
                params.deleteVertexChance = newValue.doubleValue();
                fieldOne6.setText(Double.toString(newValue.doubleValue()));
            }
        });

        slideOne7.setMax(VERTEXSHIFT);
        slideOne7.setBlockIncrement(VERTEXSHIFT/100);
        fieldOne7.setText(((Double)params.vertexShift).toString());
        slideOne7.valueProperty().addListener((observable, oldValue, newValue) ->{
            if (!running) {
                params.vertexShift = newValue.doubleValue();
                fieldOne7.setText(Double.toString(newValue.doubleValue()));
            }
        });

        slideOne8.setBlockIncrement(ROI);
        slideOne8.setMax(2);
        slideOne8.setMin(0);
        fieldOne8.setText(((Double)nowRoi).toString());
        slideOne8.valueProperty().addListener((observable, oldValue, newValue) ->{
            if (!running) {
                nowRoi = newValue.doubleValue();
                fieldOne8.setText(Double.toString(newValue.doubleValue()));
            }
        });

        fieldPopSize.setText(((Integer)popSize).toString());
        slidePopSize.valueProperty().addListener((observable, oldValue, newValue) ->{
            if (!running) {
                popSize = newValue.intValue();
                fieldPopSize.setText(Integer.toString(newValue.intValue()));
            }
        });

        fieldNumParents.setText(((Integer)numParents).toString());
        slideNumParents.valueProperty().addListener((observable, oldValue, newValue) ->{
            if (!running) {
                numParents = newValue.intValue();
                fieldNumParents.setText(Integer.toString(newValue.intValue()));
            }
        });

    }



    //    function to implement the evolutionary algorithm
    @FXML
    protected void startProcessing(ActionEvent event) {
//        sample code here for now
        if (runningRequest) return;
        if (rImage.getImage()==null) return;
        this.runningRequest = true;
        new Thread(() -> {
            Image img = rImage.getImage();
            final javafx.scene.canvas.Canvas canvas = new Canvas(img.getWidth()*scale,img.getHeight()*scale);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            this.lImage.setFitWidth(320);
            this.lImage.setPreserveRatio(true);
            System.out.println(Thread.currentThread().getName());
            params.width = (int)img.getWidth();
            params.height = (int)img.getHeight();
            entity.recentParams = params;
            entity.setTargetImage(img);
            entity.merge();
            if(this.nowRoi != 0){
                entity.base = (PolygonSet) entity.clone();
                entity.clearPolys();
            }

            //adding some random polyogns before starting evolution
            for (int i = 0; i<0; i++){
                entity.polygons.add(new Polygon(params));
            }
            double fitness = 0.5;

            Point ROI = entity.getROIsafe(this.nowRoi,null);
            params.ROIx = ROI.x;
            params.ROIy = ROI.y;
            params.width = (int)(img.getWidth() / Math.pow(2,this.nowRoi));
            params.height = (int)(img.getHeight() / Math.pow(2,this.nowRoi));


            EvolutionController controller = new EvolutionController(entity);

            System.out.println(img.getWidth());
            System.out.println(img.getHeight());

            System.out.println(params.width);
            System.out.println(params.height);

            System.out.println("roi: "+ROI);
            //System.out.println(entity);

            //creating one generation to copy the base to all the individuals
            controller.update(popSize, 1, params);

            while (this.runningRequest) {
                //params.polygonSize = Math.sqrt(1-fitness);
                controller.update(popSize, numParents, params);
                controller.clear();
                fitness = ((PolygonSet)(controller.getLastGen().getBest())).getFitness();
                System.out.println(fitness+" at gen: "+this.genNum);
                entity = (PolygonSet)((PolygonSet)controller.getLastGen().getBest()).clone();
                entity.setScale(scale);
                entity.drawBackground(gc);
                if(entity.base != null) entity.base.drawPolygons(gc);
                entity.drawPolygons(gc);
                entity.drawROI(gc);
                AppThread.runAndWait(()->this.lImage.setImage(canvas.snapshot(null, null)));
                genNum ++;
            }
            running = false;
        }).start();

    }

//
    @FXML
    protected void resetBut(ActionEvent event) {
        runningRequest = false;
        System.out.println("bla");
        entity = new PolygonSet();
        genNum = 0;
        rImage.setImage(null);
        lImage.setImage(null);
    }

    @FXML
    protected void stopProcessing(ActionEvent event) throws IOException {
        if (runningRequest) {
            System.out.println("STOP");
            saveImg();
            runningRequest = false;
        }
    }

//
//    start code for first value
//
    @FXML
    protected void minusOne(ActionEvent event) {
        if (!running) {
            if (params.amount <= 0)
                return;
            params.amount-=(AMOUNT/100);
//        valueOne.setText(Integer.toString(nowAmount));
            fieldOne.setText(Double.toString(params.amount));
            slideOne.setValue(params.amount);
        }
    }

    @FXML
    protected void plusOne(ActionEvent event) {
        if (!running) {
            if (params.amount >= AMOUNT)
                return;
            params.amount+=(AMOUNT/100);
//        valueOne.setText(Integer.toString(nowAmount));
            fieldOne.setText(Double.toString(params.amount));
            slideOne.setValue(params.amount);
        }
    }

    @FXML
    protected void enterHandle(KeyEvent event) {
        if (!running) {
            if (event.getCode() == KeyCode.ENTER) {
                double pom = Double.parseDouble(fieldOne.getText());
                if (pom < 0 || pom > AMOUNT)
                    return;
                params.amount = pom;
                slideOne.setValue(params.amount);
            }
        }
    }
//
//    end code for first value
//

//
//    start code for second value
//
    @FXML
    protected void minusOne1(ActionEvent event) {
        if (!running) {
            if (params.addPolyChance <= 0)
                return;
            params.addPolyChance-=(ADDPOLYCHANCE/100);
    //        valueOne.setText(Integer.toString(nowAmount));
            fieldOne1.setText(Double.toString(params.addPolyChance));
            slideOne1.setValue(params.addPolyChance);
        }
    }

    @FXML
    protected void plusOne1(ActionEvent event) {
        if (!running) {
            if (params.addPolyChance >= ADDPOLYCHANCE)
                return;
            params.addPolyChance+=(ADDPOLYCHANCE/100);
//        valueOne.setText(Integer.toString(nowAmount));
            fieldOne1.setText(Double.toString(params.addPolyChance));
            slideOne1.setValue(params.addPolyChance);
        }
    }

    @FXML
    protected void enterHandle1(KeyEvent event) {
        if (!running) {
            if (event.getCode() == KeyCode.ENTER) {
                double pom = Double.parseDouble(fieldOne1.getText());
                if (pom < 0 || pom > ADDPOLYCHANCE)
                    return;
                params.addPolyChance = pom;
                slideOne1.setValue(params.addPolyChance);
            }
        }
    }
    //
//    end code for second value
//

    //
//    start code for third value
//
    @FXML
    protected void minusOne2(ActionEvent event) {
        if (!running) {
            if (params.deletePolyChance <= 0)
                return;
            params.deletePolyChance-=(DELPOLYCHANCE/100);
//        valueOne.setText(Integer.toString(nowAmount));
            fieldOne2.setText(Double.toString(params.deletePolyChance));
            slideOne2.setValue(params.deletePolyChance);
        }
    }

    @FXML
    protected void plusOne2(ActionEvent event) {
        if (!running) {
            if (params.deletePolyChance >= DELPOLYCHANCE)
                return;
            params.deletePolyChance+=(DELPOLYCHANCE/100);
//        valueOne.setText(Integer.toString(nowAmount));
            fieldOne2.setText(Double.toString(params.deletePolyChance));
            slideOne2.setValue(params.deletePolyChance);
        }
    }

    @FXML
    protected void enterHandle2(KeyEvent event) {
        if (!running) {
            if (event.getCode() == KeyCode.ENTER) {
                double pom = Double.parseDouble(fieldOne2.getText());
                if (pom < 0 || pom > DELPOLYCHANCE)
                    return;
                params.deletePolyChance = pom;
                slideOne2.setValue(params.deletePolyChance);
            }
        }
    }
    //
//    end code for third value
//

    //    start code for fourth value
//
    @FXML
    protected void minusOne3(ActionEvent event) {
        if (!running) {
            if (params.polygonSize <= 0)
                return;
            params.polygonSize-=POLYSIZE/100;
//        valueOne.setText(Integer.toString(nowAmount));
            fieldOne3.setText(Double.toString(params.polygonSize));
            slideOne3.setValue(params.polygonSize);
        }
    }

    @FXML
    protected void plusOne3(ActionEvent event) {
        if (!running) {
            if (params.polygonSize >= POLYSIZE)
                return;
            params.polygonSize+=POLYSIZE/100;
//        valueOne.setText(Integer.toString(nowAmount));
            fieldOne3.setText(Double.toString(params.polygonSize));
            slideOne3.setValue(params.polygonSize);
        }
    }

    @FXML
    protected void enterHandle3(KeyEvent event) {
        if (!running) {
            if (event.getCode() == KeyCode.ENTER) {
                double pom = Double.parseDouble(fieldOne3.getText());
                if (pom < 0 || pom > POLYSIZE)
                    return;
                params.polygonSize = pom;
                slideOne3.setValue(params.polygonSize);
            }
        }
    }
//
//    end code for fourth value
//

//    start code for fith value
//
    @FXML
    protected void minusOne4(ActionEvent event) {
        if (!running) {
            if (params.colorChange <= 0)
                return;
            params.colorChange-=COLCHANGE/100;
//        valueOne.setText(Integer.toString(nowAmount));
            fieldOne4.setText(Double.toString(params.colorChange));
            slideOne4.setValue(params.colorChange);
        }
    }

    @FXML
    protected void plusOne4(ActionEvent event) {
        if (!running) {
            if (params.colorChange >= COLCHANGE)
                return;
            params.colorChange+=COLCHANGE/100;
//        valueOne.setText(Integer.toString(nowAmount));
            fieldOne4.setText(Double.toString(params.colorChange));
            slideOne4.setValue(params.colorChange);
        }
    }

    @FXML
    protected void enterHandle4(KeyEvent event) {
        if (!running) {
            if (event.getCode() == KeyCode.ENTER) {
                double pom = Double.parseDouble(fieldOne4.getText());
                if (pom < 0 || pom > COLCHANGE)
                    return;
                params.colorChange = pom;
                slideOne4.setValue(params.colorChange);
            }
        }
    }
    //
//    end code for fith value
//

//  start code for sixth value
//
    @FXML
    protected void minusOne5(ActionEvent event) {
        if (!running) {
            if (params.addVertexChance <= 0)
                return;
            params.addVertexChance-=VERTEXADD/100;
//        valueOne.setText(Integer.toString(nowAmount));
            fieldOne5.setText(Double.toString(params.addVertexChance));
            slideOne5.setValue(params.addVertexChance);
        }
    }

    @FXML
    protected void plusOne5(ActionEvent event) {
        if (!running) {
            if (params.addVertexChance >= VERTEXADD)
                return;
            params.addVertexChance+=VERTEXADD/100;
//        valueOne.setText(Integer.toString(nowAmount));
            fieldOne5.setText(Double.toString(params.addVertexChance));
            slideOne5.setValue(params.addVertexChance);
        }
    }

    @FXML
    protected void enterHandle5(KeyEvent event) {
        if (!running) {
            if (event.getCode() == KeyCode.ENTER) {
                double pom = Double.parseDouble(fieldOne5.getText());
                if (pom < 0 || pom > VERTEXADD)
                    return;
                params.addVertexChance = pom;
                slideOne5.setValue(params.addVertexChance);
            }
        }
    }
    //
//    end code for sixth value
//

//  start code for seventh value
//
    @FXML
    protected void minusOne6(ActionEvent event) {
        if (!running) {
            if (params.deleteVertexChance <= 0)
                return;
            params.deleteVertexChance-=VERTEXDEL/100;
//        valueOne.setText(Integer.toString(nowAmount));
            fieldOne6.setText(Double.toString(params.deleteVertexChance));
            slideOne6.setValue(params.deleteVertexChance);
        }
    }

    @FXML
    protected void plusOne6(ActionEvent event) {
        if (!running) {
            if (params.deleteVertexChance >= VERTEXDEL)
                return;
            params.deleteVertexChance+=VERTEXDEL/100;
//        valueOne.setText(Integer.toString(nowAmount));
            fieldOne6.setText(Double.toString(params.deleteVertexChance));
            slideOne6.setValue(params.deleteVertexChance);
        }
    }

    @FXML
    protected void enterHandle6(KeyEvent event) {
        if (!running) {
            if (event.getCode() == KeyCode.ENTER) {
                double pom = Double.parseDouble(fieldOne6.getText());
                if (pom < 0 || pom > VERTEXDEL)
                    return;
                params.deleteVertexChance = pom;
                slideOne6.setValue(params.deleteVertexChance);
            }
        }
    }
    //
//    end code for seventh value
//

//  start code for eighth value
//
    @FXML
    protected void minusOne7(ActionEvent event) {
        if (!running) {
            if (params.vertexShift <= 0)
                return;
            params.vertexShift-=VERTEXSHIFT/100;
//        valueOne.setText(Integer.toString(nowAmount));
            fieldOne7.setText(Double.toString(params.vertexShift));
            slideOne7.setValue(params.vertexShift);
        }
    }

    @FXML
    protected void plusOne7(ActionEvent event) {
        if (!running) {
            if (params.vertexShift >= VERTEXSHIFT)
                return;
            params.vertexShift+=VERTEXSHIFT/100;
//        valueOne.setText(Integer.toString(nowAmount));
            fieldOne7.setText(Double.toString(params.vertexShift));
            slideOne7.setValue(params.vertexShift);
        }
    }

    @FXML
    protected void enterHandle7(KeyEvent event) {
        if (!running) {
            if (event.getCode() == KeyCode.ENTER) {
                double pom = Double.parseDouble(fieldOne7.getText());
                if (pom < 0 || pom > VERTEXSHIFT)
                    return;
                params.vertexShift = pom;
                slideOne7.setValue(params.vertexShift);
            }
        }
    }
    //
//    end code for eighth value
//

//  start code for ninth value
//
    @FXML
    protected void minusOne8(ActionEvent event) {
        if (!running) {
            if (nowRoi == 0.03125)
                return;
            nowRoi-=ROI;
//        valueOne.setText(Integer.toString(nowAmount));
            fieldOne8.setText(Double.toString(nowRoi));
            slideOne8.setValue(nowRoi);
        }
    }

    @FXML
    protected void plusOne8(ActionEvent event) {
        if (!running) {
            if (nowRoi == 1)
                return;
            nowRoi+=ROI;
//        valueOne.setText(Integer.toString(nowAmount));
            fieldOne8.setText(Double.toString(nowRoi));
            slideOne8.setValue(nowRoi);
        }
    }

    @FXML
    protected void enterHandle8(KeyEvent event) {
        if (!running) {
            if (event.getCode() == KeyCode.ENTER) {
                double pom = Double.parseDouble(fieldOne8.getText());
                if (pom < 0 || pom > 1)
                    return;
                nowRoi = pom;
                slideOne8.setValue(nowRoi);
            }
        }
    }
//
//    end code for ninth value
//

    @FXML
    protected void minusOnePopSize(ActionEvent event) {
        if (!running) {
            if (popSize == 10)
                return;
            popSize--;
//        valueOne.setText(Integer.toString(nowAmount));
            fieldPopSize.setText(Integer.toString(popSize));
            slidePopSize.setValue(popSize);
        }
    }

    @FXML
    protected void plusOnePopSize(ActionEvent event) {
        if (!running) {
            if (popSize == 1000)
                return;
            popSize++;
//        valueOne.setText(Integer.toString(nowAmount));
            fieldPopSize.setText(Integer.toString(popSize));
            slidePopSize.setValue(popSize);
        }
    }

    @FXML
    protected void enterPopSize(KeyEvent event) {
        if (!running) {
            if (event.getCode() == KeyCode.ENTER) {
                int pom = Integer.parseInt(fieldPopSize.getText());
                if (pom < 10 || pom > 1000)
                    return;
                popSize = pom;
                slidePopSize.setValue(popSize);
            }
        }
    }
    //
//    end code for Population Size
//
//
//    start code for Number of Parents
//
    @FXML
    protected void minusOneNumParents(ActionEvent event) {
        if (!running) {
            if (numParents == 1)
                return;
            numParents--;
//          valueOne.setText(Integer.toString(nowAmount));
            fieldNumParents.setText(Integer.toString(numParents));
            slideNumParents.setValue(numParents);
        }
    }

    @FXML
    protected void plusOneNumParents(ActionEvent event) {
        if (!running) {
            if (numParents == 100)
                return;
            numParents++;
//        valueOne.setText(Integer.toString(nowAmount));
            fieldNumParents.setText(Integer.toString(numParents));
            slideNumParents.setValue(numParents);
        }
    }
    @FXML
    protected void enterNumParents(KeyEvent event) {
        if (!running) {
            if (event.getCode() == KeyCode.ENTER) {
                int pom = Integer.parseInt(fieldNumParents.getText());
                if (pom < 1 || pom > 100)
                    return;
                numParents = pom;
                slideNumParents.setValue(numParents);
            }
        }
    }



    @FXML
    protected void open(ActionEvent event) throws IOException {
        if (!running) {

            String dir;

//        opens new dir explorer
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose Reference Image");
//            fileChooser.setInitialDirectory(new File("/media/ig-88/BE1854CB18548475/Moje_Projekty/Java/JavaFXTutorials/sceNeBuilderDemo/src/sample/img/"));
            File file = fileChooser.showOpenDialog(rImage.getScene().getWindow());
            if (file != null) {
                dir = file.getCanonicalPath();
                String tab[] = dir.split("/");
                dir = tab[tab.length-1];
                System.out.println(dir);
                path = dir;
                loadrImage(dir);
            }
        }
    }

    protected void loadrImage(String dir){
        Image img = new Image("FILE:///"+dir);
        this.rImage.setImage(img);
        this.rImage.setFitWidth(320);
        this.rImage.setPreserveRatio(true);
        System.out.println(this.rImage.getX()+" "+this.rImage.getY()+" "+this.rImage.getScaleX());
        System.out.println("opened");
    }

    private void saveImg() throws IOException{
        BufferedImage bufIImg = SwingFXUtils.fromFXImage(lImage.getImage(), null);
        System.out.println(path);
        ImageIO.write(bufIImg, "png", new File(getParentDir(path)+"\\out"+this.imgNum+".png"));
    }

//    public double[] getValues(){
//        double[] pom = {nowAmount, nowChAddPol, nowChDelPol, nowPolSize, nowColChange, nowChAddVer, nowChDelVer, nowShVer, nowRoi};
//        return pom;
//    }
    static private String getParentDir(String dir){
        int i = dir.length()-1;
        while(dir.charAt(i)!='\\') i--;
        return dir.substring(0,i);
    }
}
