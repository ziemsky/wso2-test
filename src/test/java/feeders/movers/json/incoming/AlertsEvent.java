package feeders.movers.json.incoming;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AlertsEvent {

    @JsonProperty
    private AlertEvent[] alerts;

    public AlertsEvent() {
    }


    public AlertsEvent(AlertEvent[] alerts) {
        this.alerts = alerts;
    }

    public AlertEvent[] getAlerts() {
        return alerts;
    }

    public static AlertsEvent alerts(final AlertEvent... alerts) {
        return new AlertsEvent(alerts);
    }
}
