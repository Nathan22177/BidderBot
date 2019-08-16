package com.nathan22177.BidderBot.auction.strategies;

import com.nathan22177.BidderBot.auction.bidder.BidderImpl;
import com.nathan22177.BidderBot.auction.util.StrategyUtil;

/***
 * Always bids mean price of 2 QU's.
 * */
public class FairStrategy {
    public static int getBiddingAmount(BidderImpl bidder) {
        return StrategyUtil.getMeanPriceForOneUnit(bidder) * 2;
    }
}
