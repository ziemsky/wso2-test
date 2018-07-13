package feeders.movers.json.outgoing;

import static feeders.movers.Topic.BETS;

import feeders.movers.Topic;

public class BetEvent implements Event {

    private final String selection;
    private final String user;
    private final int odds;

    private BetEvent(String selection, String user, int odds) {
        this.selection = selection;
        this.user = user;
        this.odds = odds;
    }

    public static BetEvent bet(String market, String userName, int odds) {
        return new BetEvent(market, userName, odds);
    }

    public String getSelection() {
        return selection;
    }

    public String getUser() {
        return user;
    }

    public int getOdds() {
        return odds;
    }

    @Override
    public Topic getTopic() {
        return BETS;
    }
}
