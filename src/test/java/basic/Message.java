package basic;

class Message {

    private String metaPropertyA;
    private Bet bet;

    public Message(String metaPropertyA, Bet bet) {
        this.metaPropertyA = metaPropertyA;
        this.bet = bet;
    }

    public String getMetaPropertyA() {
        return metaPropertyA;
    }

    public Bet getBet() {
        return bet;
    }
}
