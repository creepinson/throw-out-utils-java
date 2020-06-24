package dev.throwouterror.util.event;

import org.easygson.JsonEntity;

/**
 * EventHandler class.
 *
 * @author Theo Paris
 */
public class EventHandler implements EventCallback {
	/**
	 * callback function, listener or event handler.
	 */
	EventCallback callback;

	/**
	 * once. (one time if true)
	 */
	boolean once;

	/**
	 * constructor.
	 *
	 * @param callback Callback
	 * @param once     boolean
	 */
	public EventHandler(EventCallback callback, boolean once) {
		this.callback = callback;
		this.once = once;
	}

	/**
	 * do callback.
	 *
	 * @param args Object...
	 * @param body 
	 */
	public void handle(Event args, JsonEntity body) {
		callback.handle(args, body);
	}
}