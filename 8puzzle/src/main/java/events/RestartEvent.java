package events;

public class RestartEvent {
    private final int newLabel;

    public RestartEvent(int newLabel) {
        this.newLabel = newLabel;
    }

    public int getNewLabel() {
        return newLabel;
    }
}

