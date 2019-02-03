
//import com.sun.prism.Image;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Alert;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public PolygonMutationParams getParams() {
        return params;
    }

    private PolygonMutationParams params = new PolygonMutationParams(0.02,0.05,0.025,0.2,0.05,0.04,0.02,0.05);

    private double nowRoi;
    private int numParents;
    private int popSize;
    private double prevScale;
    private double scale;

    private String path;
    private int genNum;
    private PolygonSet entity;

    public boolean isRunning() {
        return running;
    }

    private boolean running;
    private boolean runningScale;
    private boolean runningRequest;
    private int imgNum;
    private static int xChartCount;

    @FXML
    private TextField fieldOne;

    @FXML
    private TextField fieldOne8;

    @FXML
    private TextField fieldPopSize;

    @FXML
    private TextField fieldNumParents;

    @FXML
    private TextField fieldScaling;

    @FXML
    private Slider slideOne;

    @FXML
    private Slider slideOne8;

    @FXML
    private Slider slidePopSize;

    @FXML
    private Slider slideNumParents;

    @FXML
    private Slider slideScaling;


    @FXML
    private ImageView rImage;

    @FXML
    private ImageView lImage;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private NumberAxis genXAxis;

    @FXML
    private NumberAxis genYAxis;


    @FXML
    private LineChart<Number, Number> Ch;

    private LineChart.Series<Number, Number> series = new LineChart.Series<Number, Number>();

    @FXML
    private LineChart<Number, Number> generalCh;

    private LineChart.Series<Number, Number> generalSeries = new LineChart.Series<>();


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
        xChartCount = 1;

        numParents = 1;
        popSize = 100;

        nowRoi = 0;
        running = false;
        runningRequest = false;
        imgNum = 0;
        genNum = 0;
        entity = new PolygonSet();
        scale = 0.3;
        prevScale = 0.3;
    }

    @Override
    public void initialize(URL var1, ResourceBundle var2) {
//init chart for the last 50 generations
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(10);
        xAxis.setTickUnit(1);

        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(1.2);
        yAxis.setTickUnit(0.1);

        series.getData().add(new LineChart.Data<Number, Number>(xChartCount, 0));
        Ch.setCreateSymbols(false);
        Ch.setAnimated(false);
        Ch.getData().add(series);
// init the chart for the whole story
        genXAxis.setLowerBound(0);
        genXAxis.setUpperBound(10);
        genXAxis.setTickUnit(1);

        genYAxis.setAutoRanging(false);
        genYAxis.setLowerBound(0);
        genYAxis.setUpperBound(1.1);
        genYAxis.setTickUnit(0.1);

        generalSeries.getData().add(new LineChart.Data<Number, Number>(xChartCount, 0));
        generalCh.setCreateSymbols(false);
        generalCh.setAnimated(false);
        generalCh.getData().add(generalSeries);
        xChartCount++;



        slideOne.setMax(AMOUNT);
        slideOne.setBlockIncrement(AMOUNT/100);
        slideOne.setValue(params.amount);
        fieldOne.setText(((Double)params.amount).toString());
        slideOne.valueProperty().addListener((observable, oldValue, newValue) ->{
            if (!running) {
                params.amount = newValue.doubleValue();
                fieldOne.setText(Double.toString(newValue.doubleValue()));
            }
        });

        slideOne8.setBlockIncrement(ROI);
        slideOne8.setMax(2);
        slideOne8.setMin(0);
        slideOne8.setValue(nowRoi);
        fieldOne8.setText(((Double)nowRoi).toString());
        slideOne8.valueProperty().addListener((observable, oldValue, newValue) ->{
            if (!running) {
                nowRoi = newValue.doubleValue();
                fieldOne8.setText(Double.toString(newValue.doubleValue()));
            }
        });

        fieldPopSize.setText(((Integer)popSize).toString());
        slidePopSize.setValue(popSize);
        slidePopSize.valueProperty().addListener((observable, oldValue, newValue) ->{
            if (!running) {
                popSize = newValue.intValue();
                fieldPopSize.setText(Integer.toString(newValue.intValue()));
            }
        });

        fieldNumParents.setText(((Integer)numParents).toString());
        slideNumParents.setValue(numParents);
        slideNumParents.valueProperty().addListener((observable, oldValue, newValue) ->{
            if (!running) {
                numParents = newValue.intValue();
                fieldNumParents.setText(Integer.toString(newValue.intValue()));
            }
        });

        fieldScaling.setText(Double.toString(scale));
        slideScaling.setValue(scale);
        slideScaling.valueProperty().addListener((observable, oldValue, newValue) ->{
            if (!runningScale) {
                scale = newValue.doubleValue();
                fieldScaling.setText(Double.toString(newValue.doubleValue()));
//                if (entity != null){
//                    System.out.println(newValue.doubleValue()/oldValue.doubleValue());
//                    entity.setScale(newValue.doubleValue()/oldValue.doubleValue());
//                }
            }
            else
                slideScaling.setValue(scale);
        });


    }



    //    function to implement the evolutionary algorithm
    @FXML
    protected void startProcessing(ActionEvent event) {
//        sample code here for now
        if (runningRequest) return;
        if (rImage.getImage()==null) return;
        this.runningRequest = true;
        runningScale = true;
        new Thread(() -> {
            System.out.println(scale);
            Image img = resampleImage(rImage.getImage(), scale);
            entity.setScale(scale/prevScale);
            prevScale = scale;
            final Canvas canvas = new Canvas(rImage.getImage().getWidth(),rImage.getImage().getHeight());
            GraphicsContext gc = canvas.getGraphicsContext2D();
            this.lImage.setFitWidth(320);
            this.lImage.setPreserveRatio(true);
            System.out.println(Thread.currentThread().getName());
            params.width = (int)img.getWidth();
            params.height = (int)img.getHeight();
            entity.setRecentParams(params);
            entity.setTargetImage(img);
            entity.merge();
            if(this.nowRoi != 0){
                entity.setBase((PolygonSet) entity.clone());
                entity.clearPolys();
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
                chartUpd(fitness);
                System.out.println(fitness+" at gen: "+this.genNum);
                imgNum++;
                entity = (PolygonSet)controller.getLastGen().getBest();
//                PolygonSet entityDisplay = (PolygonSet)entity.clone();
//                entityDisplay.setScale(1/scale);
//                entityDisplay.drawBackground(gc, scale);
//                if(entityDisplay.getBase() != null) entityDisplay.getBase().drawPolygons(gc);
//                entityDisplay.drawPolygons(gc);
//                entityDisplay.drawROI(gc);
//                AppThread.runAndWait(()->this.lImage.setImage(canvas.snapshot(null, null)));
                updateViewport();
                genNum ++;

                //this.runningRequest = false;
            }
            running = false;
        }).start();

    }

    private void updateViewport(){
        if (rImage.getImage() == null)return;
        if (entity.getTargetImage() == null) {
            entity.setTargetImage(resampleImage(rImage.getImage(),scale));
        }
        final javafx.scene.canvas.Canvas canvas = new Canvas(rImage.getImage().getWidth(),rImage.getImage().getHeight());
        GraphicsContext gc = canvas.getGraphicsContext2D();
        PolygonSet entityDisplay = (PolygonSet)entity.clone();
        entityDisplay.setScale(1/scale);
        entityDisplay.drawBackground(gc, scale);
        if(entityDisplay.getBase() != null) entityDisplay.getBase().drawPolygons(gc);
        entityDisplay.drawPolygons(gc);
        if (runningScale)
            entityDisplay.drawROI(gc);
        AppThread.runAndWait(()->this.lImage.setImage(canvas.snapshot(null, null)));
    }

//
    @FXML
    protected void resetBut(ActionEvent event) {
        runningRequest = false;
        System.out.println("bla");
        entity = new PolygonSet();
        genNum = 0;
        //rImage.setImage(null);
        lImage.setImage(null);
    }

    @FXML
    protected void stopProcessing(ActionEvent event) throws IOException {
        if (runningRequest) {
            System.out.println("STOP");
            runningScale = false;
            updateViewport();
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
    //
//    end code for eighth value
//

//  start code for ninth value
//
    @FXML
    protected void minusOne8(ActionEvent event) {
        if (!running) {
            if (nowRoi == 0)
                return;
            nowRoi-=0.03125;
//        valueOne.setText(Integer.toString(nowAmount));
            fieldOne8.setText(Double.toString(nowRoi));
            slideOne8.setValue(nowRoi);
        }
    }

    @FXML
    protected void plusOne8(ActionEvent event) {
        if (!running) {
            if (nowRoi == 2)
                return;
            nowRoi+=0.03125;
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

    //
//    start code for Scaling the image
//
    @FXML
    protected void minusOneScaling(ActionEvent event) {
        if (!runningScale) {
            if (scale<=0)
                return;
            scale-=0.01;
//          valueOne.setText(Integer.toString(nowAmount));
            fieldScaling.setText(Double.toString(scale));
            slideScaling.setValue(scale);
        }
    }

    @FXML
    protected void plusOneScaling(ActionEvent event) {
        if (!runningScale) {
            if (scale > 1)
                return;
            scale+=0.01;
//        valueOne.setText(Integer.toString(nowAmount));
            fieldScaling.setText(Double.toString(scale));
            slideScaling.setValue(scale);
        }
    }

    @FXML
    protected void enterScaling(KeyEvent event) {
        if (!runningScale) {
            if (event.getCode() == KeyCode.ENTER) {
                double pom = Double.parseDouble(fieldScaling.getText());
                if (pom <= 0 || pom > 1)
                    return;
                scale = pom;
                slideScaling.setValue(scale);
            }
        }
        else
            fieldScaling.setText(""+scale);
    }



    @FXML
    protected void advancedButton(ActionEvent event) {
        //Parent root;

        try {
            //root = FXMLLoader.load(getClass().getResource("advancedSliders.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("advancedSliders.fxml"));
            Stage stage = new Stage();
            stage.setTitle("My New Stage Title");
            stage.setScene(new Scene((Pane) loader.load()));
            advancedSliderController asc = loader.<advancedSliderController>getController();
            System.out.println(asc);
            asc.setParentController(this);
            asc.userInit();
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void export(ActionEvent event) throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource("exportPopup.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Choose details to export");
        stage.setScene(new Scene(root, 600, 300));
        stage.show();
    }

    @FXML
    protected void chartUpd(double value) {
        if (series.getData().size() > xAxis.getUpperBound()) {
            xAxis.setUpperBound(xAxis.getUpperBound()+1);
        }
        if(xChartCount>=50){
            //series.getData().remove(0,1);
            xAxis.setLowerBound(xChartCount-50);
            double pom = (Double)series.getData().get(xChartCount - 50).getYValue().doubleValue();
            yAxis.setLowerBound(pom);

//            yAxis.setLowerBound((Double)(series.getData().get(xChartCount-50).getYValue()));
            yAxis.setUpperBound(1);
        }
        series.getData().add(new LineChart.Data<Number, Number>(xChartCount, value));

        if (generalSeries.getData().size() > genXAxis.getUpperBound()) {
            genXAxis.setUpperBound(genXAxis.getUpperBound()+1);
        }
        generalSeries.getData().add(new LineChart.Data<Number, Number>(xChartCount, value));
        AppThread.runAndWait(()->{Ch.getData().set(0, series); generalCh.getData().set(0, generalSeries);});
//        AppThread.runAndWait(()->{generalCh.getData().set(0, series);});

        xChartCount++;
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

    @FXML
    protected void saveProgress(ActionEvent event) throws IOException {
        if (!running) {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File dir = directoryChooser.showDialog(rImage.getScene().getWindow());

            if(dir == null){
                //No Directory selected
            }else{
                System.out.println(dir.getAbsolutePath());
            }
            if (System.getProperty("os.name").contains("Windows"))
                SerialTest.serialize(entity, dir.getAbsolutePath()+"\\polySet.txt");
            else
                SerialTest.serialize(entity, "polySet.txt");
        }
    }
    @FXML
    protected void restore(ActionEvent event) throws IOException {
        if (!running) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Image Recovery");
            alert.setHeaderText("When choosing the file o recover:");
            alert.setContentText("Make sure you choose correct image first");
            alert.showAndWait();
            String dir;
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose PolygonSet serialized file");
            File file = fileChooser.showOpenDialog(rImage.getScene().getWindow());
            if (file != null) {
                dir = file.getCanonicalPath();
                String tab[] = dir.split("/");
                dir = tab[tab.length-1];
                System.out.println(dir);
                path = dir;
                loadPolygonSet(dir);
            }
            updateViewport();
        }
    }

    private void loadrImage(String dir){
        Image img;
        System.out.println(System.getProperty("os.name"));
        System.out.println(dir);
        if (System.getProperty("os.name").contains("Windows")) img = new Image("FILE:///"+dir);
        else img = new Image(dir);
        this.rImage.setImage(img);
        this.rImage.setFitWidth(390);
        this.rImage.setPreserveRatio(true);
        System.out.println(this.rImage.getX()+" "+this.rImage.getY()+" "+this.rImage.getScaleX());
        System.out.println("opened");
    }

    private void loadPolygonSet(String dir){
        System.out.println(System.getProperty("os.name"));

        //if (System.getProperty("os.name").substring(0,7).equals("Windows")) dir = "FILE:///"+dir;
        try {
            entity = (PolygonSet)SerialTest.deserialize(dir);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Data Recovery");
            alert.setHeaderText("data not found");
            alert.setContentText("Make sure You chose correct file");
            alert.showAndWait();
        }
    }

    private void saveImg() throws IOException{
        BufferedImage bufIImg = SwingFXUtils.fromFXImage(lImage.getImage(), null);
        System.out.println(path);


        File directory = new File("Images");
        if (!directory.exists()) {
            directory.mkdir();
        }
        if (System.getProperty("os.name").contains("Windows")) {

            ImageIO.write(bufIImg, "png", new File(getParentDir(path) +"\\Images\\out" + this.imgNum + ".png"));
        } else {
            ImageIO.write(bufIImg, "png", new File(/*path+ "/"+*/directory+ "/out" + this.imgNum + ".png"));
        }
    }

    static private String getParentDir(String dir){
        int i = dir.length()-1;
        while(dir.charAt(i)!='\\') i--;
        return dir.substring(0,i);
    }

    static Image resampleImage(Image image, double scale){
        int xMax = (int)(image.getWidth()*scale);
        int yMax = (int)(image.getHeight()*scale);
        WritableImage output = new WritableImage(xMax,yMax);
        PixelReader reader = image.getPixelReader();
        PixelWriter writer = output.getPixelWriter();
        for(int x=0; x<xMax; x++){
            for(int y=0; y<yMax; y++){
                writer.setColor(x,y,reader.getColor((int)(x/scale),(int)(y/scale)));
            }
        }
        return output;
    }
}
