package feeders.movers;

import com.fasterxml.jackson.annotation.JsonProperty;

class Alert {

    @JsonProperty
    private String selection;

    @JsonProperty
    private String user;

    public Alert() {
    }

    private Alert(
        String selection,
        String user
    ) {
        this.selection = selection;
        this.user = user;
    }

    static Alert alert(final String selection, final String user) {
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
}
