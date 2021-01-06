import engine2d.core.Application;
import engine2d.core.Display;

public class Main extends Application {

    static int width = 800;
    static int height = 640;
    static String title = "Test";

    Main() {
        super(width, height, title);
        pushLayer(new ExampleLayer(this));
    }

    public static void main(String[] args) {
        Display.setTitle("false");
        new Main().run();
    }

}
