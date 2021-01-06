package engine2d;

import engine2d.events.EventFunction;

public class WindowData {

    public int width;
    public int height;
    public String title;
    public EventFunction eventFunction;

    public WindowData(int width, int height, String title, EventFunction eventFunction) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.eventFunction = eventFunction;
    }

}
