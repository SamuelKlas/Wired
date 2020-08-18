/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage.Model;

import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import static javafx.scene.input.MouseEvent.*;

/**
 *
 * @author ACER
 */
public class Vertex {

    protected Circle circle;
    protected Boolean selected;

    public Vertex(Circle circle, double centerX, double centerY) {
        circle.setFill(Color.BLUE);
        selected = false;
        this.circle = circle;
        circle.setCenterX(centerX);
        circle.setCenterY(centerY);
        EventHandler<MouseEvent> eventHandler = (MouseEvent e) -> {
            selected = selected ? false : true;
            paint();

        };
        circle.addEventHandler(MOUSE_CLICKED, eventHandler);
    }

    public void paint() {
        Paint paint = this.selected ? Color.RED : Color.BLUE;
        this.circle.setFill(paint);
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
