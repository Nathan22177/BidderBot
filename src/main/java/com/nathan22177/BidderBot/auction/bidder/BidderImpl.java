package com.nathan22177.BidderBot.auction.bidder;

import com.nathan22177.BidderBot.auction.strategies.BiddingStrategy;
import com.nathan22177.BidderBot.auction.strategies.NathanStrategy;
import javafx.util.Pair;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

@Getter
@Setter(AccessLevel.PRIVATE)
public class BidderImpl implements Bidder {

    /***
     * Strategy that defines how to bid.
     * */
    private BiddingStrategy biddingStrategy;

    private final Random random = new Random();

    /***
     * Amount of cash left.
     * */
    private int balance;

    /***
     * Amount of won QUs.
     * */
    private int acquiredAmount;

    /***
     * Initial amount of QUs.
     * */
    private int initialQuantity;

    /***
     * Initial amount of MUs.
     * */
    private int initialBalance;

    /***
     * LIFO bidding history.
     * */
    private LinkedList<Pair<Integer, Integer>> biddingHistory;

    /***
    * Constructor without implicitly specified strategy.
    * */
    public BidderImpl (int quantity, int cash) {
        Assert.isTrue(quantity % 2 == 0 && quantity > 0, "Quantity must be a positive and even number.");
        Assert.isTrue(cash > 0, "Cash must be positive number.");
        this.init(quantity, cash);
        this.biddingStrategy = new NathanStrategy();
    }


    public BidderImpl (int quantity, int cash, BiddingStrategy strategy) {
        Assert.isTrue(quantity % 2 == 0 && quantity > 0, "Quantity must be a positive and even number.");
        Assert.isTrue(cash > 0, "Cash must be positive number.");
        this.init(quantity, cash);
        this.biddingStrategy = strategy;
    }

    @Override
    public void init(int quantity, int cash) {
        this.balance = cash;
        this.acquiredAmount = 0;
        this.initialQuantity = quantity;
        this.initialBalance = cash;
    }

    @Override
    public int placeBid() {
        int bid = this.biddingStrategy.getBiddingAmount(this);
        Assert.isTrue(bid >= 0, "Bid should be positive number");
        Assert.isTrue(bid <= this.balance, "Bid should not be larger than amount of MU on the balance");

        this.balance -= bid;
        return bid;
    }

    @Override
    public void bids(int own, int other) {
        addToBiddingHistory(own, other);
    }

    private void addToBiddingHistory(int own, int other) {
        if (this.biddingHistory == null) {
            this.biddingHistory = new LinkedList<>(Collections.singletonList(new Pair<>(own, other)));
        } else {
            this.biddingHistory.add(new Pair<>(own, other));
        }

        if (other < own) {
            this.acquiredAmount += 2;
        } else if (own == other) {
            this.acquiredAmount++;
        }
    }
}
