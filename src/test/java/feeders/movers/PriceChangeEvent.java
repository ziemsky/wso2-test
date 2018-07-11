package feeders.movers;

public class PriceChangeEvent implements Event {

    private final String selection;
    private final int odds;

    private PriceChangeEvent(String selection, int odds) {
        this.selection = selection;
        this.odds = odds;
    }

    static PriceChangeEvent price(String market, int odds) {
        return new PriceChangeEvent(market, odds);
    }

    public String getSelection() {
        return selection;
    }

    public int getOdds() {
        return odds;
    }

    @Override
    public String getTopicName() {
        return "marketPriceChanges";
    }
}
