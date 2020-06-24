package dev.throwouterror.test;

import org.easygson.JsonEntity;

import dev.throwouterror.util.event.Event;
import dev.throwouterror.util.event.EventCallback;
import dev.throwouterror.util.event.EventEmitter;
import dev.throwouterror.util.event.Event.Result;

public class EventEmitterTest {

	public static void main(String[] args) {
		EventEmitter d = new EventEmitter();
		d.on("hello", new EventCallback() {

			@Override
			public void handle(Event event, JsonEntity body) {
				System.out.println("Hello, " + body.get("username").asString());
				System.out.println("Allowed to enter: " + body.get("username").asString().contains("Jim"));
				if(body.get("username").asString().contains("Jim")) event.setResult(Result.DENY);
			}
		});

		d.emit(new Event("hello"), JsonEntity.emptyObject().create("username", "Joe Mama"));
		d.emit(new Event("hello"), JsonEntity.emptyObject().create("username", "Bob Jim"));

	}
}
