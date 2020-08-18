package newpackage.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import newpackage.Controller.Mediator;


import java.io.IOException;

public class TitleScreenController {

    @FXML
    protected Button optionsButton;
    @FXML
    protected Button levelSelectButton;
    @FXML
    protected Button exitButton;
    private Mediator med;

    public void setMed(Mediator med) {
        this.med = med;
    }

    @FXML
    protected void handleLevelSelectButton(ActionEvent event) throws IOException {
        Stage stage = null;
        Pane root = null;
        if (event.getSource() == levelSelectButton) {
            stage = (Stage) levelSelectButton.getScene().getWindow();
            root = (Pane)med.lC.backToTitle.getParent();


        }

        Scene scene = null;
        if(root.getScene()!=null) {
            scene = root.getScene();
        }
        else {
            scene = new Scene(root);
        }
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    public void handleExitButton(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }


}