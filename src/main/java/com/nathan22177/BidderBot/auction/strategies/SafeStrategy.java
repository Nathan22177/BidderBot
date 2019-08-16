package com.nathan22177.BidderBot.auction.strategies;

import com.nathan22177.BidderBot.auction.bidder.BidderImpl;
import com.nathan22177.BidderBot.auction.util.StrategyUtil;

/***
 * Waits for an advantage then bids median plus 2.
 * */
public class SafeStrategy {
    public static int getBiddingAmount(BidderImpl bidder) {
        if (StrategyUtil.bidderHasUpperHandOverItsOpponent(bidder)) {
            return StrategyUtil.allBidsMedian(bidder) + 1;
        }

        return 0;
    }
}
