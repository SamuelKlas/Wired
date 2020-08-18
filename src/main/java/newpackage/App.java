/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import newpackage.Controller.LevelController;
import newpackage.Controller.Mediator;
import newpackage.Controller.TitleScreenController;

/**
 * @author ACER
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fl = new FXMLLoader(getClass().getClassLoader().getResource("wired-titlescreen.fxml"));
        FXMLLoader f2 = new FXMLLoader(getClass().getClassLoader().getResource("levelselect.fxml"));
        Pane root = fl.load();
        Pane levelSelect = f2.load();
        TitleScreenController tC = fl.getController();
        LevelController lC = f2.getController();
        Mediator med = new Mediator(tC,lC);
        tC.setMed(med);
        lC.setMed(med);

        Scene scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());
        primaryStage.setTitle("FXML Welcome");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
