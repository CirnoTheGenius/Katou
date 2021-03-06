package ga.nurupeaches.kato.listening;

import ga.nurupeaches.kato.listening.events.Event;

import java.nio.ByteBuffer;

public interface EventHandler {

	/**
	 * Called when an event is triggered.
	 * Note: The data is read-only.
	 * @param event - Type of event.
	 * @param buffer - The data associated with the event.
	 */
	public void onCall(Event event, ByteBuffer buffer);

}
