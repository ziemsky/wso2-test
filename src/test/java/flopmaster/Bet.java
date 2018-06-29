package flopmaster;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Bet {

    @JsonProperty("stake_factor")
    private double stakeFactor;

    @JsonProperty("bet_stake")
    private double betStake;

    @JsonProperty("max_bet_percent")
    private double maxBetPercent;

    @JsonProperty("username")
    private String userName;

    public Bet(double stakeFactor, double betStake, double maxBetPercent, String userName) {

        this.stakeFactor = stakeFactor;
        this.betStake = betStake;
        this.maxBetPercent = maxBetPercent;
        this.userName = userName;
    }

    public double getStakeFactor() {
        return stakeFactor;
    }

    public double getBetStake() {
        return betStake;
    }

    public double getMaxBetPercent() {
        return maxBetPercent;
    }

    public String getUserName() {
        return userName;
    }
}
