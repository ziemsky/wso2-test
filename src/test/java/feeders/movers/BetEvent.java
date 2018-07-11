package feeders.movers;

public class BetEvent implements Event {

    private final String selection;
    private final int price;
    private final String user;

    private BetEvent(String selection, int price, String user) {
        this.selection = selection;
        this.price = price;
        this.user = user;
    }

    static BetEvent bet(String market, int price, String userName) {
        return new BetEvent(market, price, userName);
    }

    public String getSelection() {
        return selection;
    }

    public String getUser() {
        return user;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String getTopicName() {
        return "bets";
    }
}
