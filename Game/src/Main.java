import engine2D.core.Application;

public class Main extends Application {

    static int width = 800;
    static int height = 640;
    static String title = "Test";

    Main() {
        super(width, height, title);
        pushLayer(new SecondLayer("SecondLayer", this));
        pushLayer(new ExampleLayer("ExampleLayer", this));
    }

    public static void main(String[] args) {
        new Main().run();
    }

}
