/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage.Model;

import java.util.List;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.util.Pair;

/**
 * @author ACER
 */
public class Wire {

    protected Line line;
    protected Vertex start;
    protected Vertex end;
    protected List<Wire> wires;
    protected boolean crossed;
    protected Line visibleLine;


    public Wire(Line line, Vertex start, Vertex end, List<Wire> wires) {
        this.wires = wires;
        this.line = line;
        this.start = start;
        this.end = end;
        visibleLine = new Line();
        update();
        this.crossed = false;
        visibleLine.setStrokeWidth(1);
        this.line.setStrokeWidth(0.1);
        this.line.setVisible(false);


    }

    public void update() {
        double v1 = end.circle.getCenterX() - start.circle.getCenterX();
        double v2 = end.circle.getCenterY() - start.circle.getCenterY();
        Pair<Double,Double> rets = normalize(v1,v2);
        double transX = rets.getKey();
        double transY = rets.getValue();
        line.setStartX(start.circle.getCenterX()+transX);
        line.setStartY(start.circle.getCenterY()+transY);
        line.setEndX(end.circle.getCenterX()-transX);
        line.setEndY(end.circle.getCenterY()-transY);
        visibleLine.setStartX(start.circle.getCenterX()+transX);
        visibleLine.setStartY(start.circle.getCenterY()+transY);
        visibleLine.setEndX(end.circle.getCenterX()-transX);
        visibleLine.setEndY(end.circle.getCenterY()-transY);

    }

    public void checkCrossing() {
        boolean collisionDetected = false;
        for (Wire static_bloc : wires) {

            if (static_bloc != this) {

                Shape intersect = Shape.intersect(this.line, static_bloc.line);
                if (intersect.getBoundsInParent().getWidth() != -1) {
                        collisionDetected = true;

                }
            }
        }

        if (collisionDetected) {
            this.visibleLine.setStroke(Color.RED);
            this.crossed = true;

        } else {
            this.visibleLine.setStroke(Color.GREEN);
            this.crossed = false;
        }
    }

    public void selectedWidth() {
        if (this.start.selected || this.end.selected) {
            this.visibleLine.setStrokeWidth(3);
        } else {
            this.visibleLine.setStrokeWidth(1);
        }

    }
    public Pair<Double,Double> normalize(double x, double y){
        double norm = Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
        return new Pair<>(x / norm*10,y /norm*10);


    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public Vertex getStart() {
        return start;
    }

    public void setStart(Vertex start) {
        this.start = start;
    }

    public Vertex getEnd() {
        return end;
    }

    public void setEnd(Vertex end) {
        this.end = end;
    }

    public List<Wire> getWires() {
        return wires;
    }

    public void setWires(List<Wire> wires) {
        this.wires = wires;
    }

    public boolean isCrossed() {
        return crossed;
    }

    public void setCrossed(boolean crossed) {
        this.crossed = crossed;
    }

    public Line getVisibleLine() {
        return visibleLine;
    }

    public void setVisibleLine(Line visibleLine) {
        this.visibleLine = visibleLine;
    }
}
