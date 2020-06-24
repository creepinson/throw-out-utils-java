package dev.throwouterror.util.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.easygson.JsonEntity;

import dev.throwouterror.util.event.Event.Result;

/**
 * events.EventEmitter class. (like Node.js)
 *
 * @see http://nodejs.org/api/events.html
 *
 * @author Theo Paris
 */
public class EventEmitter {
	/**
	 * max listners count.
	 */
	int maxListeners = 10;
	/**
	 * array list of events and listeners.
	 */
	Map<String, List<EventHandler>> events = new HashMap<String, List<EventHandler>>();

	/**
	 * add listener.
	 *
	 * @param event    String
	 * @param listener Callback
	 */
	public void addListener(String event, EventCallback listener) {
		addListener(event, listener, false);
	}

	/**
	 * add listener. (internal)
	 *
	 * @param event    String
	 * @param listener Callback
	 * @param once     boolean
	 */
	EventEmitter addListener(String event, EventCallback listener, boolean once) {
		List<EventHandler> handlers = listeners(event);

		if (handlers == null) {
			handlers = new ArrayList<EventHandler>();
			events.put(event, handlers);
		}

		if (maxListeners > 0 && handlers.size() >= maxListeners)
			throw new Error("too many listeners for event '" + event + "'");

		handlers.add(new EventHandler(listener, once));
		return this;
	}

	/**
	 * on. (add listener)
	 *
	 * @param event    String
	 * @param listener Callback
	 */
	public EventEmitter on(String event, EventCallback listener) {
		return addListener(event, listener, false);
	}

	/**
	 * once.
	 *
	 * @param event    String
	 * @param listener Callback
	 */
	public EventEmitter once(String event, EventCallback listener) {
		return addListener(event, listener, true);
	}

	/**
	 * remove listener.
	 *
	 * @param event    String
	 * @param listener Callback
	 */
	public EventEmitter removeListener(String event, EventCallback listener) {
		List<EventHandler> handlers = listeners(event);

		if (handlers == null)
			return this;

		for (int i = 0, n = handlers.size(); i < n; ++i) {
			if (handlers.get(i).callback == listener) {
				handlers.remove(i);
				break;
			}
		}

		if (handlers.size() == 0)
			events.remove(event);

		return this;
	}

	/**
	 * remove all listeners with event.
	 *
	 * @param event String
	 */
	public EventEmitter removeAllListeners(String event) {
		List<EventHandler> handlers = listeners(event);

		if (handlers == null)
			return this;

		events.remove(event);

		handlers.clear();
		return this;
	}

	/**
	 * remove all listeners.
	 */
	public EventEmitter removeAllListeners() {
		String[] events = new String[this.events.size()];
		int i = 0;
		for (String event : this.events.keySet())
			events[i++] = event;
		for (String event : events)
			removeAllListeners(event);
		return this;
	}

	/**
	 * Sets max listeners count.
	 *
	 * By default EventEmitters will print a warning if more than 10 listeners are
	 * added for a particular event. This is a useful default which helps finding
	 * memory leaks. Obviously not all Emitters should be limited to 10. This
	 * function allows that to be increased. Set to zero for unlimited.
	 *
	 * @param maxListeners int
	 */
	public EventEmitter setMaxListeners(int maxListeners) {
		if (maxListeners < 0)
			throw new Error("max listeners count must be 0 or more");
		this.maxListeners = maxListeners;
		return this;
	}

	/**
	 * Returns an array list of listeners (event handlers) for specified event.
	 *
	 * @param event String
	 * @return List<EventHandler>
	 */
	public List<EventHandler> listeners(String event) {
		return events.get(event);
	}

	/**
	 * Emit (fire) event.
	 *
	 * @param eventName String
	 * @param event     Object...
	 */
	public EventEmitter emit(Event event, JsonEntity body) {
		List<EventHandler> handlers = listeners(event.getName());

		if (handlers == null || handlers.size() == 0) {
			if (!event.getName().equals("error"))
				return this;

			throw new Error(event.toString());
		}

		for (int i = 0, n = handlers.size(); i < n;) {
			EventHandler handler = handlers.get(i);

			if (handler.once) {
				handlers.remove(i);
				--n;
			} else
				++i;
			if (Event.registered.containsKey(event.getName())
					&& Event.registered.get(event.getName()).getResult() == Result.ALLOW)
				handler.handle(event, body);
		}

		if (handlers.size() == 0)
			events.remove(event.getName());

		return this;
	}

	/**
	 * Returns the number of listeners for a given event.
	 *
	 * @param event String
	 * @return listener count
	 */
	public int listenerCount(String event) {
		List<EventHandler> handlers = listeners(event);

		if (handlers == null)
			return 0;

		return handlers.size();
	}

	/**
	 * return the number of listeners for a given event.
	 *
	 * @param emitter EventEmitter
	 * @param event   String
	 * @return listener count
	 */
	public static int listenerCount(EventEmitter emitter, String event) {
		return emitter.listenerCount(event);
	}

	/**
	 * Prints out the properties of this event emitter to the console.
	 */
	public EventEmitter dump() {
		for (String event : events.keySet()) {
			System.out.print("event: " + event + ", ");
			System.out.print("listeners:");
			List<EventHandler> handlers = events.get(event);
			for (EventHandler handler : handlers)
				System.out.print(" " + handler.callback + "." + handler.once);
			System.out.println();
		}
		return this;
	}
}