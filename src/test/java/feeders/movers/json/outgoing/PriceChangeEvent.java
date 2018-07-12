package feeders.movers.json.outgoing;

import static feeders.movers.Topic.MARKET_PRICE_CHANGES;

import feeders.movers.Topic;

public class PriceChangeEvent implements Event {

    private final String selection;
    private final int odds;

    private PriceChangeEvent(String selection, int odds) {
        this.selection = selection;
        this.odds = odds;
    }

    public static PriceChangeEvent price(String market, int odds) {
        return new PriceChangeEvent(market, odds);
    }

    public String getSelection() {
        return selection;
    }

    public int getOdds() {
        return odds;
    }

    @Override
    public Topic getTopic() {
        return MARKET_PRICE_CHANGES;
    }
}
