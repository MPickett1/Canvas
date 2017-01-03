import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


public class CanvasTab extends Tab{

    public CanvasTab(String text) {
        final Label label = new Label(text);
        setGraphic(label);
        final TextField textField = new TextField();
        label.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                textField.setText(label.getText());
                setGraphic(textField);
                textField.selectAll();
                textField.requestFocus();
            }
        });

        textField.setOnAction(event -> {
            label.setText(textField.getText());
            setGraphic(label);
        });

        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                label.setText(textField.getText());
                setGraphic(label);
            }
        });

        setContent(createCanvas());

        setOnCloseRequest(event -> {
            System.out.println(event);
        });
    }

    public Canvas getCanvas(){
        return (Canvas) ((Pane) getContent()).getChildren().get(0);
    }
    private Pane createCanvas(){
        Canvas canvas = new Canvas();
        Pane pane = new Pane();
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.BLACK);
        gc.setStroke(Color.BLACK);

        canvas.setOnMousePressed(event -> {
            gc.beginPath();
            gc.moveTo(event.getX(), event.getY());
            gc.stroke();
        });

        canvas.setOnMouseDragged(event ->{
            gc.lineTo(event.getX(), event.getY());
            gc.stroke();
        });

        canvas.widthProperty().bind(pane.widthProperty());
        canvas.heightProperty().bind(pane.heightProperty());

        pane.getChildren().addAll(canvas);

        return pane;
    }

    public void saveCanvas(){
        File file = new File(((Label)getGraphic()).getText()+".png");

        WritableImage image = getCanvas().snapshot(new SnapshotParameters(), null);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}