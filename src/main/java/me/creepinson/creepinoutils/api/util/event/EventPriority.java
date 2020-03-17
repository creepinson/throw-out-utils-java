package me.creepinson.creepinoutils.api.util.event;

public enum EventPriority implements Event.Listener {
    HIGHEST,
    HIGH,
    NORMAL,
    LOW,
    LOWEST;

    private EventPriority() {
    }

    public void invoke(Event event) {
        event.setPhase(this);
    }
}
