package com.nathan22177.BidderBot.auction.bidder;

import com.nathan22177.BidderBot.auction.StrategyController;
import com.nathan22177.BidderBot.auction.enums.BiddingStrategy;
import com.nathan22177.BidderBot.auction.util.MatchUtil;
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


    public BidderImpl (int quantity, int cash, BiddingStrategy strategy) {
        Assert.isTrue(quantity % 2 == 0 && quantity > 0, "Quantity must be a positive and even number.");
        Assert.isTrue(MatchUtil.isOneOf(strategy, BiddingStrategy.values()), "Must use one of the defined strategies.");
        Assert.isTrue(cash > 0, "Cash must be positive number.");
        this.init(quantity, cash);
        this.biddingStrategy = strategy;
    }

    /*
    * Closing default constructor to ensure no one will call it.
    * */
    private BidderImpl() {}

    private void addToBiddingHistory(int own, int other) {
        if (this.biddingHistory == null) {
            initializeBiddingHistory(own, other);
        } else {
            this.biddingHistory.add(newBiddingEntry(own, other));
        }

        if (other < own) {
            this.acquiredAmount += 2;
        }

        if (own == other) {
            this.acquiredAmount++;
        }
    }

    private Pair<Integer, Integer> newBiddingEntry(int own, int other) {
        return new Pair<>(own, other);
    }

    private void initializeBiddingHistory(int own, int other) {
        this.biddingHistory = new LinkedList<>(Collections.singletonList(new Pair<>(own, other)));
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
        int bid = StrategyController.getBiddingAmountForStrategy(this);
        this.balance -= bid;
        return bid;
    }

    @Override
    public void bids(int own, int other) {
        addToBiddingHistory(own, other);
    }
}
