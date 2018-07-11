package feeders.flopmaster;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Bet {

    @JsonProperty("time_stamp")
    private final long timestamp;

    @JsonProperty("stake_factor")
    private double stakeFactor;

    @JsonProperty("bet_stake")
    private double betStake;

    @JsonProperty("max_bet_percent")
    private double maxBetPercent;

    @JsonProperty("username")
    private String userName;

    public Bet(long timestamp,
               double stakeFactor,
               double betStake,
               double maxBetPercent,
               String userName) {

        this.timestamp = timestamp;
        this.stakeFactor = stakeFactor;
        this.betStake = betStake;
        this.maxBetPercent = maxBetPercent;
        this.userName = userName;
    }

    public long getTimestamp() {
        return timestamp;
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
