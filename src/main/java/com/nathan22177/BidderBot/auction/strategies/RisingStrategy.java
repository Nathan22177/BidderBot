package com.nathan22177.BidderBot.auction.strategies;

import com.nathan22177.BidderBot.auction.bidder.BidderImpl;
import com.nathan22177.BidderBot.auction.util.StrategyUtil;

/***
* Gradually raises bid so that would go with empty balance at the end.
* */
public class RisingStrategy {
    public static int getBiddingAmount(BidderImpl bidder) {
        int bid = bidder.getBalance() / (StrategyUtil.getRoundsLeft(bidder) * 2) ^ 2;
    return bid < bidder.getBalance() ? bid : bidder.getBalance();
    }

}
