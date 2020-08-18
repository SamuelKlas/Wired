package newpackage.Controller;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import newpackage.Model.Vertex;
import newpackage.Model.Wire;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;

public class LevelController {
    @FXML
    protected Button backToTitle;
    @FXML
    protected Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15;
    private Mediator med;

    private List<Vertex> vertices;
    private List<Wire> wires;
    private List<Vertex> toswap;
    private AnimationTimer timer;
    private boolean[] finishedLevels;

    @FXML
    protected void changeLevel(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        Pane level = LoadLevel("level" + b.getText() + ".txt",b);
        stage.setScene(new Scene(level));

    }

    @FXML
    protected void handleBackToTitleButton(ActionEvent event) {
        Stage stage = null;
        Pane root = null;
        if (event.getSource() == backToTitle) {
            stage = (Stage) backToTitle.getScene().getWindow();
            root = (Pane) med.tC.levelSelectButton.getParent();


        }
        Scene scene = null;
        if (root.getScene() != null) {
            scene = root.getScene();
        } else {
            scene = new Scene(root);
        }
        stage.setScene(scene);
        stage.show();

    }


    public void initialize() {
        vertices = new ArrayList<>();
        wires = new ArrayList<>();
        toswap = new ArrayList<>();
        finishedLevels = new boolean[15];
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                for (Wire wire : wires) {
                    wire.update();
                    wire.checkCrossing();
                    wire.selectedWidth();

                }

                for (Vertex vertex : vertices) {

                    if (vertex.getSelected() && !toswap.contains(vertex)) {
                        toswap.add(vertex);

                    } else {
                        toswap.remove(vertex);
                    }
                    if (toswap.size() == 2) {

                        try {
                            swap(toswap.get(0), toswap.get(1));
                        } catch (InterruptedException ex) {
                        }
                        toswap.get(0).paint();
                        toswap.get(1).paint();
                        toswap.clear();
                    }

                }

            }

        };
        timer.start();
    }

    public Pane LoadLevel(String name,Button button) throws IOException {

        Pane level = new Pane();
        level.setPrefSize(900, 600);
        level.setStyle("-fx-background-color: #ADEFD1FF; -fx-text-fill: white; -fx-font-weight: bold");
        InputStream in = this.getClass().getClassLoader()
                .getResourceAsStream(name);
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(in));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] pair = line.split(";");
            if (pair[0].equals("vertex")) {
                Vertex vertex = new Vertex(new Circle(10), Double.parseDouble(pair[1]), Double.parseDouble(pair[2]));
                vertices.add(vertex);
                level.getChildren().add(vertex.getCircle());


            }
            if (pair[0].equals("wire")) {
                Wire wire = new Wire(new Line(), vertices.get(Integer.parseInt(pair[1]) - 1), vertices.get(Integer.parseInt(pair[2]) - 1), wires);
                wires.add(wire);
                level.getChildren().add(wire.getVisibleLine());
                level.getChildren().add(wire.getLine());

            }

            wires.forEach(w -> w.getLine().toBack());
            wires.forEach(w -> w.getVisibleLine().toBack());

        }
        Button back = new Button("BACKTOSELECT");
        back.setTranslateX(0);
        back.setTranslateY(0);
        String css = LevelController.class.getClassLoader().getResource("button.css").toExternalForm();
        back.getStylesheets().add(css);

        back.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                if(endRound())button.setStyle("-fx-background-color:#00ff00");
                vertices.clear();
                wires.clear();
                Stage stage = (Stage) back.getScene().getWindow();
                stage.setScene(backToTitle.getScene());
            }
        });
        level.getChildren().add(back);

        return level;
    }

    public void swap(Vertex one, Vertex two) throws InterruptedException {
        Point2D vector = new Point2D(one.getCircle().getCenterX() - two.getCircle().getCenterX(), one.getCircle().getCenterY() - two.getCircle().getCenterY());
        java.util.Timer timer = new Timer();
        Point2D originalOne = new Point2D(one.getCircle().getCenterX(), one.getCircle().getCenterY());
        Point2D originalTwo = new Point2D(two.getCircle().getCenterX(), two.getCircle().getCenterY());

        timer.schedule(new TimerTask() {
            int breakpoint = 0;

            @Override
            public void run() {
                Platform.runLater(() -> {
                    breakpoint++;
                    one.getCircle().setCenterX(one.getCircle().getCenterX() - vector.getX() / 100);
                    one.getCircle().setCenterY(one.getCircle().getCenterY() - vector.getY() / 100);
                    two.getCircle().setCenterX(two.getCircle().getCenterX() + vector.getX() / 100);
                    two.getCircle().setCenterY(two.getCircle().getCenterY() + vector.getY() / 100);
                    for (Wire wire : wires) {
                        wire.update();
                        wire.checkCrossing();
                    }

                    if (breakpoint >= 99) {
                        one.getCircle().setCenterX(originalTwo.getX());
                        one.getCircle().setCenterY(originalTwo.getY());
                        two.getCircle().setCenterX(originalOne.getX());
                        two.getCircle().setCenterY(originalOne.getY());
                        System.out.println(one.getCircle().getCenterX() + " " + one.getCircle().getCenterY() + " " + two.getCircle().getCenterX() + " " + two.getCircle().getCenterY());
                        endRound();
                        timer.cancel();


                    }
                });
            }
        }, 5, 10);

        one.setSelected(false);
        two.setSelected(false);
    }

    public boolean endRound() {
        int count = 0;
        for (Wire wire : wires) {
            if (wire.isCrossed()) count++;
        }
        System.out.println(count);
        return count == 0;
    }


    public void setMed(Mediator med) {
        this.med = med;
    }

}
