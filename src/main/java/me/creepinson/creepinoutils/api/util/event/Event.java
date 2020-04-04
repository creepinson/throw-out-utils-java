package me.creepinson.creepinoutils.api.util.event;

import me.creepinson.creepinoutils.api.util.ConditionUtil;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

public class Event {
    private boolean isCanceled = false;
    private Event.Result result;
    private static List<Listener> listeners = new ArrayList<>();
    private EventPriority phase;

    public Event() {
        this.result = Event.Result.DEFAULT;
        this.phase = null;
        this.setup();
    }

    public boolean isCancelable() {
        return false;
    }

    public boolean isCanceled() {
        return this.isCanceled;
    }

    public void setCanceled(boolean cancel) {
        if (!this.isCancelable()) {
            throw new UnsupportedOperationException("Attempted to call Event#setCanceled() on a non-cancelable event of type: " + this.getClass().getCanonicalName());
        } else {
            this.isCanceled = cancel;
        }
    }

    public boolean hasResult() {
        return false;
    }

    public Event.Result getResult() {
        return this.result;
    }

    public void setResult(Event.Result value) {
        this.result = value;
    }

    protected void setup() {
    }

    public List<Listener> getListenerList() {
        return listeners;
    }

    public EventPriority getPhase() {
        return this.phase;
    }

    public void setPhase(EventPriority value) {
        ConditionUtil.checkNotNull(value, "setPhase argument must not be null");
        int prev = this.phase == null ? -1 : this.phase.ordinal();
        ConditionUtil.checkArgument(prev < value.ordinal(), "Attempted to set event phase to %s when already %s", value, this.phase);
        this.phase = value;
    }

    public static enum Result {
        DENY,
        DEFAULT,
        ALLOW;

        private Result() {
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE})
    public @interface HasResult {
    }

    public static interface Listener {
        void invoke(Event var1);
    }
}
