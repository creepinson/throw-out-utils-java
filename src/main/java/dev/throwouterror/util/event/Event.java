package dev.throwouterror.util.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.easygson.JsonEntity;

import com.google.gson.JsonObject;

import dev.throwouterror.util.ConditionUtil;

public class Event {
	private Event.Result result;
	private EventPriority phase;
	private String name;
	static Map<String, Event> registered = new HashMap<>();

	public Event(String name) {
		this.name = name;
		this.result = Event.Result.ALLOW;
		this.phase = null;
		if (!registered.containsKey(name))
			registered.put(name, this);
	}

	public String getName() {
		return this.name;
	}

	Event setName(String n) {
		this.name = n;
		return this;
	}

	public boolean hasResult() {
		return false;
	}

	public Event.Result getResult() {
		return this.result;
	}

	public Event setResult(Event.Result value) {
		this.result = value;
		if (registered.containsKey(name))
			registered.get(name).result = this.result;
		return this;
	}
	
	public EventPriority getPhase() {
		return this.phase;
	}

	public void setPhase(EventPriority value) {
		ConditionUtil.checkNotNull(value, "setPhase argument must not be null");
		int prev = this.phase == null ? -1 : this.phase.ordinal();
		ConditionUtil.checkArgument(prev < value.ordinal(), "Attempted to set event phase to %s when already %s", value,
				this.phase);
		this.phase = value;
	}

	public enum Result {
		DENY, ALLOW;

		Result() {
		}
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.TYPE })
	public @interface HasResult {
	}
}
