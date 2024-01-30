package events;

import java.beans.PropertyChangeEvent;

public class TileChangeEvent extends PropertyChangeEvent {

    private final int tilePosition; // additional information

    public TileChangeEvent(Object source, String propertyName, int oldLabel, int newLabel, int tilePosition) {
        super(source, propertyName, oldLabel, newLabel);
        this.tilePosition = tilePosition;
    }

    public int getTilePosition() {
        return tilePosition;
    }
}
