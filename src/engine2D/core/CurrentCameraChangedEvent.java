package engine2D.core;

import engine2D.core.renderer.OrthographicCamera;
import engine2D.events.Event;
import engine2D.events.EventType;

public class CurrentCameraChangedEvent extends Event {

    public final OrthographicCamera camera;

    CurrentCameraChangedEvent(OrthographicCamera camera) {
       this.camera = camera;
    }

    @Override
    public EventType getType() {
        return EventType.CURRENTCAMERACHANGED;
    }

    @Override
    public String toString() {
        return "CURRENTCAMERACHANGED";
    }

}
