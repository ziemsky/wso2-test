package basic;

class Bet {

    private int amount;
    private double factor;

    public Bet(int amount, double factor) {
        this.amount = amount;
        this.factor = factor;
    }

    public int getAmount() {
        return amount;
    }

    public double getFactor() {
        return factor;
    }
}
