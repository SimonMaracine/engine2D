package engine2D.core;

import engine2D.events.EventFunction;

public class WindowData {

    public int width;
    public int height;
    public String title;
    public EventFunction eventFunction;

    WindowData(int width, int height, String title, EventFunction eventFunction) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.eventFunction = eventFunction;
    }

}
