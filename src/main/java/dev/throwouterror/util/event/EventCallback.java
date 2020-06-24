package dev.throwouterror.util.event;

import org.easygson.JsonEntity;

public interface EventCallback {
	/**
	 * Event callback.
	 * @param body 
	 *
	 * @param args
	 *            Object...
	 */
	void handle(Event event, JsonEntity body); 
}
