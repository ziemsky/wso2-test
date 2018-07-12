package feeders.movers.json.outgoing;

import com.fasterxml.jackson.annotation.JsonIgnore;
import feeders.movers.Topic;

public class Message {
    private final Event event;

    public Message(Event event) {
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }

    @JsonIgnore
    public Topic getTopic() {
        return event.getTopic();
    }
}
