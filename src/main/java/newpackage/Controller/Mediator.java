package newpackage.Controller;

public class Mediator {
    protected final TitleScreenController tC;
    protected final LevelController lC;

    public Mediator(TitleScreenController tC, LevelController lC) {
        this.tC = tC;
        this.lC = lC;
    }
}
