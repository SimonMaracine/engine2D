import engine2D.core.Application;
import engine2D.core.Display;

import gameOfLife.CellPopullation;
import gameOfLife.Options;
import gameOfLife.UI;

import test1.ExampleLayer;
import test1.SecondLayer;

public class Main extends Application {

    Main() {
        super(Options.WIDTH, Options.HEIGHT, "Game of Life");
        pushLayer(new SecondLayer("SecondLayer", this));  // Only the first layer can clear the screen!
        pushLayer(new ExampleLayer("ExampleLayer", this));
        pushLayer(new CellPopullation("CellPopullation", this));
        pushLayer(new UI("UI", this));
        Display.setVSync(2);
    }

    public static void main(String[] args) {
        new Main().run();
    }

}
