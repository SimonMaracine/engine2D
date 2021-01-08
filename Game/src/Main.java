import engine2D.core.Application;
import engine2D.core.Display;

public class Main extends Application {

    static int width = 800;
    static int height = 600;

    Main() {
        super(width, height, "Game of Life");
//        pushLayer(new SecondLayer("SecondLayer", this));
//        pushLayer(new ExampleLayer("ExampleLayer", this));
        pushLayer(new CellPopullation("CellPopullation", this));
        pushLayer(new UI("UI", this));
        Display.setVSync(2);
    }

    public static void main(String[] args) {
        new Main().run();
    }

}
