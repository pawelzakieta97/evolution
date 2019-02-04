
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


/**
 * Class responsible for controlling the GUI and main flow of the application
 * controls interface created in sample.fxml
 */

public class Controller implements Initializable {

    /**
     * maximum value for AMOUNT field in GUI
     */
    final double AMOUNT;
    /**
     * maximum value for CHANCE OF ADDING POLYGON field in GUI
     */
    final double ADDPOLYCHANCE;
    /**
     * maximum value for CHANCE OF DELETING POLYGON field in GUI
     */
    final double DELPOLYCHANCE;
    /**
     * maximum value for CHANCE OF CHANGING SIZE OF POLYGON field in GUI
     */
    final double POLYSIZE;
    /**
     * maximum value for CHANCE OF CHANGING THE COLOR field in GUI
     */
    final double COLCHANGE;
    /**
     * maximum value for CHANCE OF ADDING NEW VERTEX TO POLYGON field in GUI
     */
    final double VERTEXADD;
    /**
     * maximum value for CHANCE OF DELETING VERTEX FROM POLYGON field in GUI
     */
    final double VERTEXDEL;
    /**
     * maximum value for SHIFTING A VERTEX IN A POLYGON field in GUI
     */
    final double VERTEXSHIFT;
    /**
     * maximum value for ROI LEVEL field in GUI
     */
    final double ROI;

    /**
     * field to save backup polygon
     */
    private String imgName;

    /**
     * @return current set of parameters defined in GUI used to manipulate the image
     */
    public PolygonMutationParams getParams() {
        return params;
    }

    /**
     * initial set of parameters manipulating created image to start the application with
     */
    private PolygonMutationParams params = new PolygonMutationParams(0.02,0.05,0.025,0.2,0.05,0.04,0.02,0.05);

    /**
     * current value of ROI level
     */
    private double nowRoi;
    /**
     * current number of parents of every entity in nex generation
     */
    private int numParents;
    /**
     * current population size in generation
     */
    private int popSize;
    /**
     * value of image scale in previous generation
     */
    private double prevScale;
    /**
     * value of image scale in current generation
     */
    private double scale;
    /**
     * path required to save the best fitting image from each generation
     */
    private String path;
    /**
     * number of generation used to print as dev information
     */
    private int genNum;
    /**
     * set of evolving polygons, is later rendered as an image to compare with original
     */
    private PolygonSet entity;
    private Image tempImg;

    /**
     *
     * @return information if the application is performing the evolutionary algorithm
     */
    public boolean isRunning() {
        return running;
    }

    private boolean running;
    /**
     * bool to check if Scale value can be changed
     */
    private boolean runningScale;
    /**
     * request to activate a new thread
     */
    private boolean runningRequest;
    /**
     * number of generation used to save most accurate image from every generation
     */
    private int imgNum;
    /**
     * counting value for x-axis of two existing charts
     */
    private static int xChartCount;

    /**
     * text field for AMOUNT value
     */
    @FXML
    private TextField fieldOne;
    /**
     * text field for ROI level value
     */
    @FXML
    private TextField fieldOne8;
    /**
     * textfield for size of population value
     */
    @FXML
    private TextField fieldPopSize;
    /**
     * textfield for size of number of parents value
     */
    @FXML
    private TextField fieldNumParents;
    /**
     * textfield for value of scaling the image
     */
    @FXML
    private TextField fieldScaling;
    /**
     * slider for AMOUNT value
     */
    @FXML
    private Slider slideOne;
    /**
     * slider for changing ROI level value
     */
    @FXML
    private Slider slideOne8;
    /**
     * slider for population size value
     */
    @FXML
    private Slider slidePopSize;
    /**
     * slider for number of parents value
     */
    @FXML
    private Slider slideNumParents;
    /**
     * slider for scaling level value
     */
    @FXML
    private Slider slideScaling;

    /**
     * original image
     */
    @FXML
    private ImageView rImage;
    /**
     * image that will be created by the algorithm
     */
    @FXML
    private ImageView lImage;
    /**
     * x-axis of the Ch chart
     */
    @FXML
    private NumberAxis xAxis;
    /**
     * y-axis of the Ch chart
     */
    @FXML
    private NumberAxis yAxis;
    /**
     * x-axis of generalCh chart
     */
    @FXML
    private NumberAxis genXAxis;
    /**
     * y-axis of generalCh chart
     */
    @FXML
    private NumberAxis genYAxis;

    /**
     * chart used to display accuracy of the image in the last 50 generations
     */
    @FXML
    private LineChart<Number, Number> Ch;
    /**
     * series used to accumulate data for Ch chart
     */
    private LineChart.Series<Number, Number> series = new LineChart.Series<Number, Number>();

    /**
     * chart used to display whole history of image accuracy
     */
    @FXML
    private LineChart<Number, Number> generalCh;
    /**
     * series used to accumulate data for generalCh chart
     */
    private LineChart.Series<Number, Number> generalSeries = new LineChart.Series<>();

    /**
     * constructor used to initialize the values in the beginning of the program
     */
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

    /**
     * function used to initialize charts, set initial values to textFields, attach listeners to all of the sliders
     * @param var1 not used in this implementation
     * @param var2 not used in this implementation
     */
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

//  initialize all of the sliders and textFields

//  AMOUNT
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
//  ROI
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
//  POPULATION SIZE
        fieldPopSize.setText(((Integer)popSize).toString());
        slidePopSize.setValue(popSize);
        slidePopSize.valueProperty().addListener((observable, oldValue, newValue) ->{
            if (!running) {
                popSize = newValue.intValue();
                fieldPopSize.setText(Integer.toString(newValue.intValue()));
            }
        });
//  NUMBER OF PARENTS
        fieldNumParents.setText(((Integer)numParents).toString());
        slideNumParents.setValue(numParents);
        slideNumParents.valueProperty().addListener((observable, oldValue, newValue) ->{
            if (!running) {
                numParents = newValue.intValue();
                fieldNumParents.setText(Integer.toString(newValue.intValue()));
            }
        });
//  SCALING THE IMAGE
        fieldScaling.setText(Double.toString(scale));
        slideScaling.setValue(scale);
        slideScaling.valueProperty().addListener((observable, oldValue, newValue) ->{
            if (!runningScale) {
                scale = newValue.doubleValue();
                fieldScaling.setText(Double.toString(newValue.doubleValue()));
            }
            else
                slideScaling.setValue(scale);
        });


    }

    /**
     *function to control main flow of the application, creates new thread, draws generated image,
     * handles evolution and chooses the most accurate from each generation
     * @param event
     */
    @FXML
    protected void startProcessing(ActionEvent event) {
//        check if application is running and the reference image is loadeed
        if (runningRequest) return;
        if (rImage.getImage()==null) return;
        this.runningRequest = true;
        runningScale = true;
        new Thread(() -> {
//            create base for the later created image
            System.out.println(scale);
            Image img = resampleImage(rImage.getImage(), scale);
            entity.setScale(scale/prevScale);
            prevScale = scale;
            final Canvas canvas = new Canvas(rImage.getImage().getWidth(),rImage.getImage().getHeight());
            GraphicsContext gc = canvas.getGraphicsContext2D();
            this.lImage.setFitWidth(400);
            this.lImage.setPreserveRatio(true);
            System.out.println(Thread.currentThread().getName());
            params.width = (int)img.getWidth();
            params.height = (int)img.getHeight();
            entity.setRecentParams(params);
            entity.setTargetImage(img);
            entity.merge();
//            check for changing region of interest
            if(this.nowRoi != 0){
                entity.setBase((PolygonSet) entity.clone());
                entity.clearPolys();
            }
            double fitness = 0.5;
//change region of interest
            Point ROI = entity.getROIsafe(this.nowRoi,null);
            params.ROIx = ROI.x;
            params.ROIy = ROI.y;
            params.width = (int)(img.getWidth() / Math.pow(2,this.nowRoi));
            params.height = (int)(img.getHeight() / Math.pow(2,this.nowRoi));


            EvolutionController controller = new EvolutionController(entity);

//            System.out.println(img.getWidth());
//            System.out.println(img.getHeight());

//            System.out.println(params.width);
//            System.out.println(params.height);

//            System.out.println("roi: "+ROI);
            //System.out.println(entity);

            //creating one generation to copy the base to all the individuals
            controller.update(popSize, 1, params);

            while (this.runningRequest) {
                //params.polygonSize = Math.sqrt(1-fitness);
//                update all the values from parameters set in GUI
                controller.update(popSize, numParents, params);
                controller.clear();
//                get the best image accuracy from last generation and update the charts
                fitness = ((PolygonSet)(controller.getLastGen().getBest())).getFitness();
                chartUpd(fitness);
                System.out.println(fitness+" at gen: "+this.genNum);
                imgNum++;
//                get the best image from last generation
                entity = (PolygonSet)controller.getLastGen().getBest();
                updateViewport();
                AppThread.runAndWait(()->{saveSet(); saveImg();});
                genNum ++;
            }
            running = false;
        }).start();

    }

//    function to refresh display of generated image
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
    private Image getRenderedImage(){
        if (rImage.getImage() == null)return null;
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
        AppThread.runAndWait(()->tempImg = canvas.snapshot(null, null));
        return tempImg;
    }

    /**
     * button used to restore default/initial view of the GUI
     * @param event not used
     */
    @FXML
    protected void resetBut(ActionEvent event) {
        runningRequest = false;
//        System.out.println("bla");
        entity = new PolygonSet();
        genNum = 0;
        //rImage.setImage(null);
        lImage.setImage(null);
    }

    /**
     * function used to pause algorithm and save last generated image
     * @param event not used
     * @throws IOException
     */
    @FXML
    protected void stopProcessing(ActionEvent event) throws IOException {
        if (runningRequest) {
//            System.out.println("STOP");
            runningScale = false;
            updateViewport();
            saveImg();
            runningRequest = false;
        }
    }

//
//    start code for first value
//

    /**
     * function to decrement value AMOUNT
     * @param event not used
     */
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
    /**
     * function to increment value AMOUNT
     * @param event not used
     */
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

    /**
     * function to change value of Amount from textField
     * @param event not used
     */
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
//    end code for first value
//

//  start code for ninth value
//
    /**
     * function to decrement value of ROI level
     * @param event not used
     */
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
    /**
     *function to increment value of ROI level
     * @param event not used
     */
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

    /**
     * function to update ROI level value from textField
     * @param event not used
     */
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
    /**
     * function to decrement population value
     * @param event not used
     */
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
    /**
     * function to increment population value
     * @param event not used
     */
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

    /**
     * function used to change population value from textField
     * @param event not used
     */
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
    /**
     * function to decrement number of parents value
     * @param event not used
     */
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
    /**
     * function to increment number of parents value
     * @param event not used
     */
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

    /**
     * function to change number of parents value from textField
     * @param event not used
     */
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
    /**
     * function to decrement scaling value
     * @param event not used
     */
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
    /**
     * function to increment scaling value
     * @param event not used
     */
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

    /**
     * function to change scaling value from textField
     * @param event not used
     */
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


    /**
     * button to invoke advanced popup, where further changes to algorithm variables are possible
     * @param event not used
     */
    @FXML
    protected void advancedButton(ActionEvent event) {
        //Parent root;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("advancedSliders.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Advanced Variables");
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

    /**
     *  function to add points to both charts and update the view
     * @param value best accuracy got from the latest generation, will be displayed as the latest value on both charts
     */
    @FXML
    protected void chartUpd(double value) {
//        check for changing boundaries of last 50 generations
        if (series.getData().size() > xAxis.getUpperBound()) {
            xAxis.setUpperBound(xAxis.getUpperBound()+1);
        }
        if(xChartCount>=50){
            xAxis.setLowerBound(xChartCount-50);
//            change scale in y-axis
            double pom = (Double)series.getData().get(xChartCount - 50).getYValue().doubleValue();
            yAxis.setLowerBound(pom);
            yAxis.setUpperBound(1);
        }
//        update series for the Ch chart
        series.getData().add(new LineChart.Data<Number, Number>(xChartCount, value));
//        check for changing boundaries in general chart
        if (generalSeries.getData().size() > genXAxis.getUpperBound()) {
            genXAxis.setUpperBound(genXAxis.getUpperBound()+1);
        }
//        update series of generalCh
        generalSeries.getData().add(new LineChart.Data<Number, Number>(xChartCount, value));
//        update both charts
        AppThread.runAndWait(()->{Ch.getData().set(0, series); generalCh.getData().set(0, generalSeries);});
        xChartCount++;
    }


    /**
     * function to load reference image before starting the algorithm,
     * this will be used to create reference images and will be used to
     * compare for best results
     * @param event not used
     * @throws IOException in case of not finding correct file
     */
    @FXML
    protected void open(ActionEvent event) throws IOException {
        if (!running) {

            String dir;

//        opens new dir explorer
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose Reference Image");
            File file = fileChooser.showOpenDialog(rImage.getScene().getWindow());
            if (file != null) {
                dir = file.getCanonicalPath();
                String tab[] = dir.split("/");
                dir = tab[tab.length-1];
                System.out.println(dir);
                path = dir;
                imgName = dir.substring(0, dir.length()-4);
                loadrImage(dir);
            }
        }
    }

    /**
     * function used to create backup in case of any application failure
     * so that user can run the application again and start from the last save
     * @param event not used
     * @throws IOException
     */
    @FXML
    protected void saveProgress(ActionEvent event) {
        try {
            if (!running) {
                DirectoryChooser directoryChooser = new DirectoryChooser();
                File dir = directoryChooser.showDialog(rImage.getScene().getWindow());

                if (dir == null) {
                    //No Directory selected
                } else {
                    System.out.println(dir.getAbsolutePath());
                }
                if (System.getProperty("os.name").contains("Windows"))
                    SerialTest.serialize(entity, dir.getAbsolutePath() + "\\polySet.txt");
                else
                    SerialTest.serialize(entity, "polySet.txt");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveSet() {
        try {
            if (!running) {
//                DirectoryChooser directoryChooser = new DirectoryChooser();
//                File dir = directoryChooser.showDialog(rImage.getScene().getWindow());

//                if (dir == null) {
//                    //No Directory selected
//                } else {
//                    System.out.println(dir.getAbsolutePath());
//                }
                if (System.getProperty("os.name").contains("Windows")) {
                    new File("ImageDataSets").mkdirs();
                    SerialTest.serialize(entity, /*dir.getAbsolutePath() +*/ "polySet.txt");
                }
                else
                    SerialTest.serialize(entity, "polySet.txt");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * function used to restore previously saved latest result
     * @param event not used
     * @throws IOException
     */
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

    /**
     * function used to display reference image in GUI
     * @param dir path to chosen image
     */
    private void loadrImage(String dir){
        Image img;
        System.out.println(System.getProperty("os.name"));
//        System.out.println(dir);
        if (System.getProperty("os.name").contains("Windows")) img = new Image("FILE:///"+dir);
        else img = new Image(dir);
        this.rImage.setImage(img);
        this.rImage.setFitWidth(390);
        this.rImage.setPreserveRatio(true);
//        System.out.println(this.rImage.getX()+" "+this.rImage.getY()+" "+this.rImage.getScaleX());
//        System.out.println("opened");
    }

    /**
     * function to deserialize chosen backup file
     * @param dir path to serialized latest entity
     */
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

    /**
     * function to save created most accurate image in separated folder
     * @throws IOException
     */
    private void saveImg() {
        try {
            BufferedImage bufIImg = SwingFXUtils.fromFXImage(getRenderedImage(), null);
            System.out.println(path);
            File directory = new File("Images");
            if (!directory.exists()) {
                directory.mkdir();
            }
            if (System.getProperty("os.name").contains("Windows")) {
                new File(getParentDir(path) + "\\Images").mkdirs();
                ImageIO.write(bufIImg, "png", new File(getParentDir(path) + "\\Images\\out" + this.imgNum + ".png"));
            } else {
                ImageIO.write(bufIImg, "png", new File(/*path+ "/"+*/directory + "/out" + this.imgNum + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static private String getParentDir(String dir){
        int i = dir.length()-1;
        while(dir.charAt(i)!='\\') i--;
        return dir.substring(0,i);
    }

    static private Image resampleImage(Image image, double scale){
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
