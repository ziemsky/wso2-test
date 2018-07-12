package feeders.movers.json.outgoing;

import static feeders.movers.Topic.BETS;

import feeders.movers.Topic;

public class BetEvent implements Event {

    private final String selection;
    private final String user;

    private BetEvent(String selection, String user) {
        this.selection = selection;
        this.user = user;
    }

    public static BetEvent bet(String market, String userName) {
        return new BetEvent(market, userName);
    }

    public String getSelection() {
        return selection;
    }

    public String getUser() {
        return user;
    }

    @Override
    public Topic getTopic() {
        return BETS;
    }
}
