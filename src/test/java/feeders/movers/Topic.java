package feeders.movers;

public enum Topic {
    MARKET_PRICE_CHANGES("marketPriceChanges"),
    BETS("bets"),
    ALERTS("alerts");

    private final String topicName;

    Topic(final String topicName) {
        this.topicName = topicName;
    }

    public String getTopicName() {
        return topicName;
    }
}
