package feeders.movers.json.incoming;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AlertEvent {

    @JsonProperty("event")
    private Alert alert;

    public AlertEvent() {
    }

    public AlertEvent(Alert alert) {
        this.alert = alert;
    }

    public Alert getAlert() {
        return alert;
    }

    public static AlertEvent alert(final String selection, final String user,
                                   final int oddsBettedOn, final int oddsNew
    ) {
        return new AlertEvent(Alert.alert(selection, user, oddsBettedOn, oddsNew));
    }
}
