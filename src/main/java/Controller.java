import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Controller {
    @FXML
    private TabPane tabPane;
    @FXML
    private ColorPicker colorPicker;

    @FXML
    public void initialize(){
        colorPicker.setValue(Color.BLACK);
        tabPane.getTabs().addAll(new CanvasTab("Testing"));
        colorPicker.setOnAction(event -> {
            ObservableList<Tab> tabs = tabPane.getTabs();
            tabs.forEach(tab -> {
                Canvas canvas = ((CanvasTab) tab).getCanvas();
                canvas.getGraphicsContext2D().setStroke(colorPicker.getValue());
                canvas.getGraphicsContext2D().setFill(colorPicker.getValue());
            });
        });
    }

    @FXML
    public void save(){
        CanvasTab tab = (CanvasTab) tabPane.getSelectionModel().getSelectedItem();
        tab.saveCanvas();
    }

    @FXML
    public void close(){
        Stage stage = (Stage) tabPane.getScene().getWindow();
        stage.getScene().getWindow().hide();
    }

    @FXML
    public void addTab(){
        CanvasTab tab = new CanvasTab("New Tab");
        tab.getCanvas().getGraphicsContext2D().setFill(colorPicker.getValue());
        tab.getCanvas().getGraphicsContext2D().setStroke(colorPicker.getValue());
        tabPane.getTabs().addAll(tab);
        tabPane.getSelectionModel().select(tabPane.getTabs().size()-1);
    }
}
