package feeders.movers.json.outgoing;

import com.fasterxml.jackson.annotation.JsonIgnore;
import feeders.movers.Topic;

public interface Event {

    @JsonIgnore
    Topic getTopic();
}
