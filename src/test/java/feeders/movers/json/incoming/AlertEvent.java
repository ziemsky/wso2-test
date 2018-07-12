package feeders.movers.json.incoming;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AlertEvent {

    @JsonProperty
    private Alert event;

    public AlertEvent() {
    }

    public AlertEvent(Alert event) {
        this.event = event;
    }

    public Alert getEvent() {
        return event;
    }

    public static AlertEvent alert(final String selection, final String user) {
        return new AlertEvent(Alert.alert(selection, user));
    }
}
