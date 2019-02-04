import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class advancedSliderController implements Initializable{

    @FXML
    private Slider sliderAddPoly;

    @FXML
    private Slider sliderDelPoly;

    @FXML
    private Slider sliderPolySize;

    @FXML
    private Slider sliderChangeCol;

    @FXML
    private Slider sliderAddVertex;

    @FXML
    private Slider sliderDelVertex;

    @FXML
    private Slider sliderShiftVertex;

    @FXML
    private TextField fieldAddPoly;

    @FXML
    private TextField fieldDelPoly;

    @FXML
    private TextField fieldPolySize;

    @FXML
    private TextField fieldChangeCol;

    @FXML
    private TextField fieldAddVertex;

    @FXML
    private TextField fieldDelVertex;

    @FXML
    private TextField fieldShiftVertex;

    public void setParentController(Controller controller) {
        this.parentController = controller;
    }

    private Controller parentController;


    public advancedSliderController() {
//        fieldAddPoly.setText(Double.toString(Controller.getNowChAddPol()));
//        fieldDelPoly.setText(Double.toString(Controller.getNowChDelPol()));
//        fieldPolySize.setText(Double.toString(Controller.getNowPolSize()));
//        fieldChangeCol.setText(Double.toString(Controller.getNowColChange()));
//        fieldAddVertex.setText(Double.toString(Controller.getNowChAddVer()));
//        fieldDelVertex.setText(Double.toString(Controller.getNowChDelVer()));
//        fieldShiftVertex.setText(Double.toString(Controller.getNowShVer()));
    }



    @Override
    public void initialize(URL var1, ResourceBundle var2) {}

    public void userInit(){
        fieldAddPoly.setText(Double.toString(parentController.getParams().addPolyChance));
        sliderAddPoly.setValue(parentController.getParams().addPolyChance);
        sliderAddPoly.valueProperty().addListener((observable, oldValue, newValue) ->{
            if (!parentController.isRunning()) {
                parentController.getParams().addPolyChance = newValue.doubleValue();
                fieldAddPoly.setText(Double.toString(newValue.doubleValue()));
//                Controller.setNowChAddPol(nowAddPoly);
            }
        });
        fieldDelPoly.setText(Double.toString(parentController.getParams().deletePolyChance));
        sliderDelPoly.setValue(parentController.getParams().deletePolyChance);
        sliderDelPoly.valueProperty().addListener((observable, oldValue, newValue) ->{
            if (!parentController.isRunning()) {
                parentController.getParams().deletePolyChance = (newValue.doubleValue());
                fieldDelPoly.setText(Double.toString(newValue.doubleValue()));
//                Controller.setNowChAddPol(nowAddPoly);
            }
        });
        fieldPolySize.setText(Double.toString(parentController.getParams().polygonSize));
        sliderPolySize.setValue(parentController.getParams().polygonSize);
        sliderPolySize.valueProperty().addListener((observable, oldValue, newValue) ->{
            if (!parentController.isRunning()) {
                parentController.getParams().polygonSize = (newValue.doubleValue());
                fieldPolySize.setText(Double.toString(newValue.doubleValue()));
//                Controller.setNowChAddPol(nowAddPoly);
            }
        });
        fieldChangeCol.setText(Double.toString(parentController.getParams().colorChange));
        sliderChangeCol.setValue(parentController.getParams().colorChange);
        sliderChangeCol.valueProperty().addListener((observable, oldValue, newValue) ->{
            if (!parentController.isRunning()) {
                parentController.getParams().colorChange = (newValue.doubleValue());
                fieldChangeCol.setText(Double.toString(newValue.doubleValue()));
//                Controller.setNowChAddPol(nowAddPoly);
            }
        });
        fieldAddVertex.setText(Double.toString(parentController.getParams().addVertexChance));
        sliderAddVertex.setValue(parentController.getParams().addVertexChance);
        sliderAddVertex.valueProperty().addListener((observable, oldValue, newValue) ->{
            if (!parentController.isRunning()) {
                parentController.getParams().addVertexChance = (newValue.doubleValue());
                fieldAddVertex.setText(Double.toString(newValue.doubleValue()));
//                Controller.setNowChAddPol(nowAddPoly);
            }
        });
        fieldDelVertex.setText(Double.toString(parentController.getParams().deleteVertexChance));
        sliderDelVertex.setValue(parentController.getParams().deleteVertexChance);
        sliderDelVertex.valueProperty().addListener((observable, oldValue, newValue) ->{
            if (!parentController.isRunning()) {
                parentController.getParams().deleteVertexChance = (newValue.doubleValue());
                fieldDelVertex.setText(Double.toString(newValue.doubleValue()));
//                Controller.setNowChAddPol(nowAddPoly);
            }
        });
        fieldShiftVertex.setText(Double.toString(parentController.getParams().vertexShift));
        sliderShiftVertex.setValue(parentController.getParams().vertexShift);
        sliderShiftVertex.valueProperty().addListener((observable, oldValue, newValue) ->{
            if (!parentController.isRunning()) {
                parentController.getParams().vertexShift = (newValue.doubleValue());
                fieldShiftVertex.setText(Double.toString(newValue.doubleValue()));
//                Controller.setNowChAddPol(nowAddPoly);
            }
        });

    }

//
//    start code for chnging NowChAddPoly
//

    @FXML
    protected void minusOneAddPoly(ActionEvent event) {
        if (!parentController.isRunning()) {
            if (parentController.getParams().addPolyChance <= 0)
                return;
            parentController.getParams().addPolyChance-=0.2/100;
//        valueOne.setText(Integer.toString(nowAmount));
            fieldAddPoly.setText(Double.toString(parentController.getParams().addPolyChance));
            sliderAddPoly.setValue(parentController.getParams().addPolyChance);
        }
    }

    @FXML
    protected void plusOneAddPoly(ActionEvent event) {
        if (!parentController.isRunning()) {
            if (parentController.getParams().addPolyChance >= 0.2) {
                return;
            }
            //System.out.println(Controller.getNowChAddPol());
            parentController.getParams().addPolyChance+=0.2/100;
            fieldAddPoly.setText(Double.toString(parentController.getParams().addPolyChance));
            sliderAddPoly.setValue(parentController.getParams().addPolyChance);
            //System.out.println(Controller.getNowChAddPol());
        }
    }

    @FXML
    protected void enterAddPoly(KeyEvent event) {
        if (!parentController.isRunning()) {
            if (event.getCode() == KeyCode.ENTER) {
                double pom = Double.parseDouble(fieldAddPoly.getText());
                if (pom < 0 || pom > 0.2)
                    return;
                parentController.getParams().addPolyChance=(pom);
                sliderAddPoly.setValue(parentController.getParams().addPolyChance);
            }
        }
    }
//
//    end code for changing NowChAddPoly
//

//
//    start code for changing NowChDelPoly
//

    @FXML
    protected void minusOneDelPoly(ActionEvent event) {
        if (!parentController.isRunning()) {
            if (parentController.getParams().deletePolyChance <= 0)
                return;
            parentController.getParams().deletePolyChance -= 0.2/100;
//        valueOne.setText(Integer.toString(nowAmount));
            fieldDelPoly.setText(Double.toString(parentController.getParams().deletePolyChance));
            sliderDelPoly.setValue(parentController.getParams().deletePolyChance);
        }
    }

    @FXML
    protected void plusOneDelPoly(ActionEvent event) {
        if (!parentController.isRunning()) {
            if (parentController.getParams().deletePolyChance >= 0.2) {
                return;
            }
//            System.out.println(Controller.getNowChAddPol());
            parentController.getParams().deletePolyChance+=0.2/100;
            fieldDelPoly.setText(Double.toString(parentController.getParams().deletePolyChance));
            sliderDelPoly.setValue(parentController.getParams().deletePolyChance);
//            System.out.println(Controller.getNowChAddPol());
        }
    }

    @FXML
    protected void enterDelPoly(KeyEvent event) {
        if (!parentController.isRunning()) {
            if (event.getCode() == KeyCode.ENTER) {
                double pom = Double.parseDouble(fieldDelPoly.getText());
                if (pom < 0 || pom > 0.2)
                    return;
                parentController.getParams().deletePolyChance=(pom);
                sliderDelPoly.setValue(parentController.getParams().deletePolyChance);
            }
        }
    }
//
//    end code for changing NowChDelPoly
//

//
//    start code for changing size
//

    @FXML
    protected void minusOnePolySize(ActionEvent event) {
        if (!parentController.isRunning()) {
            if (parentController.getParams().polygonSize <= 0) {
                parentController.getParams().polygonSize=(0.0);
                return;
            }
            parentController.getParams().polygonSize-=0.4/100;
//        valueOne.setText(Integer.toString(nowAmount));
            fieldPolySize.setText(Double.toString(parentController.getParams().polygonSize));
            sliderPolySize.setValue(parentController.getParams().polygonSize);
        }
    }

    @FXML
    protected void plusOnePolySize(ActionEvent event) {
        if (!parentController.isRunning()) {
            if (parentController.getParams().polygonSize >= 0.4) {
                return;
            }
//            System.out.println(Controller.getNowChAddPol());
            parentController.getParams().polygonSize+=0.4/100;
            fieldPolySize.setText(Double.toString(parentController.getParams().polygonSize));
            sliderPolySize.setValue(parentController.getParams().polygonSize);
//            System.out.println(Controller.getNowChAddPol());
        }
    }

    @FXML
    protected void enterPolySize(KeyEvent event) {
        if (!parentController.isRunning()) {
            if (event.getCode() == KeyCode.ENTER) {
                double pom = Double.parseDouble(fieldPolySize.getText());
                if (pom < 0 || pom > 0.4)
                    return;
                parentController.getParams().polygonSize=(pom);
                sliderPolySize.setValue(parentController.getParams().polygonSize);
            }
        }
    }
//
//    end code for changing size
//

//
//    start code for changing colour
//

    @FXML
    protected void minusOneChangeCol(ActionEvent event) {
        if (!parentController.isRunning()) {
            if (parentController.getParams().colorChange <= 0)
                return;
            parentController.getParams().colorChange-=0.2/100;
//        valueOne.setText(Integer.toString(nowAmount));
            fieldChangeCol.setText(Double.toString(parentController.getParams().colorChange));
            sliderChangeCol.setValue(parentController.getParams().colorChange);
        }
    }

    @FXML
    protected void plusOneChangeCol(ActionEvent event) {
        if (!parentController.isRunning()) {
            if (parentController.getParams().colorChange == 0.2) {
                return;
            }
//            System.out.println(Controller.getNowChAddPol());
            parentController.getParams().colorChange+=0.05/100;
            fieldChangeCol.setText(Double.toString(parentController.getParams().colorChange));
            sliderChangeCol.setValue(parentController.getParams().colorChange);
//            System.out.println(Controller.getNowChAddPol());
        }
    }

    @FXML
    protected void enterChangeCol(KeyEvent event) {
        if (!parentController.isRunning()) {
            if (event.getCode() == KeyCode.ENTER) {
                double pom = Double.parseDouble(fieldChangeCol.getText());
                if (pom < 0 || pom > 0.05)
                    return;
                parentController.getParams().colorChange=(pom);
                sliderChangeCol.setValue(parentController.getParams().colorChange);
            }
        }
    }
//
//    end code for changing colour
//

//
//    start code for adding vertex
//

    @FXML
    protected void minusOneAddVertex(ActionEvent event) {
        if (!parentController.isRunning()) {
            if (parentController.getParams().addVertexChance == 0)
                return;
            parentController.getParams().addVertexChance-=0.02/100;
//        valueOne.setText(Integer.toString(nowAmount));
            fieldAddVertex.setText(Double.toString(parentController.getParams().addVertexChance));
            sliderAddVertex.setValue(parentController.getParams().addVertexChance);
        }
    }

    @FXML
    protected void plusOneAddVertex(ActionEvent event) {
        if (!parentController.isRunning()) {
            if (parentController.getParams().addVertexChance >= 0.2) {
                return;
            }
//            System.out.println(Controller.getNowChAddPol());
            parentController.getParams().addVertexChance+=0.2/100;
            fieldAddVertex.setText(Double.toString(parentController.getParams().addVertexChance));
            sliderAddVertex.setValue(parentController.getParams().addVertexChance);
//            System.out.println(Controller.getNowChAddPol());
        }
    }

    @FXML
    protected void enterAddVertex(KeyEvent event) {
        if (!parentController.isRunning()) {
            if (event.getCode() == KeyCode.ENTER) {
                double pom = Double.parseDouble(fieldAddVertex.getText());
                if (pom < 0 || pom > 0.2)
                    return;
                parentController.getParams().addVertexChance=(pom);
                sliderAddVertex.setValue(parentController.getParams().addVertexChance);
            }
        }
    }
//
//    end code for adding vertex
//

//
//    start code for deleting vertex
//

    @FXML
    protected void minusOneDelVertex(ActionEvent event) {
        if (!parentController.isRunning()) {
            if (parentController.getParams().deleteVertexChance <= 0)
                return;
            parentController.getParams().deleteVertexChance-=0.2/100;
//        valueOne.setText(Integer.toString(nowAmount));
            fieldDelVertex.setText(Double.toString(parentController.getParams().deleteVertexChance));
            sliderDelVertex.setValue(parentController.getParams().deleteVertexChance);
        }
    }

    @FXML
    protected void plusOneDelVertex(ActionEvent event) {
        if (!parentController.isRunning()) {
            if (parentController.getParams().deleteVertexChance >= 0.2) {
                return;
            }
//            System.out.println(Controller.getNowChAddPol());
            parentController.getParams().deleteVertexChance+=0.2/100;
            fieldDelVertex.setText(Double.toString(parentController.getParams().deleteVertexChance));
            sliderDelVertex.setValue(parentController.getParams().deleteVertexChance);
//            System.out.println(Controller.getNowChAddPol());
        }
    }

    @FXML
    protected void enterDelVertex(KeyEvent event) {
        if (!parentController.isRunning()) {
            if (event.getCode() == KeyCode.ENTER) {
                double pom = Double.parseDouble(fieldDelVertex.getText());
                if (pom < 0 || pom > 0.2)
                    return;
                parentController.getParams().deleteVertexChance=(pom);
                sliderDelVertex.setValue(parentController.getParams().deleteVertexChance);
            }
        }
    }
//
//    end code for deleting vertex
//

//
//    start code for shifting vertex
//

    @FXML
    protected void minusOneShiftVertex(ActionEvent event) {
        if (!parentController.isRunning()) {
            if (parentController.getParams().vertexShift == 0)
                return;
            parentController.getParams().vertexShift-=0.2/100;
//        valueOne.setText(Integer.toString(nowAmount));
            fieldShiftVertex.setText(Double.toString(parentController.getParams().vertexShift));
            sliderShiftVertex.setValue(parentController.getParams().vertexShift);
        }
    }

    @FXML
    protected void plusOneShiftVertex(ActionEvent event) {
        if (!parentController.isRunning()) {
            if (parentController.getParams().vertexShift >= 0.2) {
                return;
            }
//            System.out.println(Controller.getNowChAddPol());
            parentController.getParams().vertexShift += 0.2/100;
            fieldShiftVertex.setText(Double.toString(parentController.getParams().vertexShift));
            sliderShiftVertex.setValue(parentController.getParams().vertexShift);
//            System.out.println(Controller.getNowChAddPol());
        }
    }

    @FXML
    protected void enterShiftVertex(KeyEvent event) {
        if (!parentController.isRunning()) {
            if (event.getCode() == KeyCode.ENTER) {
                double pom = Double.parseDouble(fieldShiftVertex.getText());
                if (pom < 0 || pom > 0.05)
                    return;
                parentController.getParams().vertexShift=(pom);
                sliderShiftVertex.setValue(parentController.getParams().vertexShift);
            }
        }
    }
//
//    end code for shifting vertex
//

}


