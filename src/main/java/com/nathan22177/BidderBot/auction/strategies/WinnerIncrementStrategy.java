package com.nathan22177.BidderBot.auction.strategies;

import com.nathan22177.BidderBot.auction.bidder.BidderImpl;
import com.nathan22177.BidderBot.auction.util.StrategyUtil;

/***
 * Waits for an advantage then bids last winning bid plus one;
 * */
public class WinnerIncrementStrategy {
    public static int getBiddingAmount(BidderImpl bidder) {
        if (StrategyUtil.bidderHasUpperHandOverItsOpponent(bidder)) {
            return StrategyUtil.getPreviousWinnerBid(bidder) + 1;
        } else {
            return 0;
        }
    }
}
