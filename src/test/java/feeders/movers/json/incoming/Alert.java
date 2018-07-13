package feeders.movers.json.incoming;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Alert {

    @JsonProperty
    private String selection;

    @JsonProperty
    private String user;

    @JsonProperty
    private int oddsBettedOn;

    @JsonProperty
    private int oddsNew;

    public Alert() {
    }

    private Alert(
        final String selection,
        final String user,
        final int oddsBettedOn,
        final int oddsNew
    ) {
        this.selection = selection;
        this.user = user;
        this.oddsBettedOn = oddsBettedOn;
        this.oddsNew = oddsNew;
    }

    public static Alert alert(final String selection,
                              final String user,
                              final int oddsBettedOn,
                              final int newOdds
    ) {
        return new Alert(selection, user, oddsBettedOn, newOdds);
    }

    public String getSelection() {
        return selection;
    }

    public String getUser() {
        return user;
    }

    public int getOddsBettedOn() {
        return oddsBettedOn;
    }

    public int getOddsNew() {
        return oddsNew;
    }

    @Override
    public String toString() {
        return "Alert{" +
            "selection='" + selection + '\'' +
            ", user='" + user + '\'' +
            ", oddsBettedOn=" + oddsBettedOn +
            ", oddsNew=" + oddsNew +
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

        return new EqualsBuilder()
            .append(oddsBettedOn, alert.oddsBettedOn)
            .append(oddsNew, alert.oddsNew)
            .append(selection, alert.selection)
            .append(user, alert.user)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(selection)
            .append(user)
            .append(oddsBettedOn)
            .append(oddsNew)
            .toHashCode();
    }
}
