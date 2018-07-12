package feeders.movers.json.incoming;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Alert {

    @JsonProperty
    private String selection;

    @JsonProperty
    private String user;

    @JsonProperty
    @JsonIgnore
    private int odds;

    public Alert() {
    }

    private Alert(
        String selection,
        String user
    ) {
        this.selection = selection;
        this.user = user;
    }

    public static Alert alert(final String selection, final String user) {
        return new Alert(selection, user);
    }

    public String getSelection() {
        return selection;
    }

    public String getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "Alert{" +
            "selection='" + selection + '\'' +
            ", user='" + user + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Alert alert = (Alert) o;
        return Objects.equals(selection, alert.selection) &&
            Objects.equals(user, alert.user);
    }

    @Override
    public int hashCode() {

        return Objects.hash(selection, user);
    }
}
