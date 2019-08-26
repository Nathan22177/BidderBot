package com.nathan22177.BidderBot.auction.strategies;

import com.nathan22177.BidderBot.auction.bidder.BidderImpl;
import com.nathan22177.BidderBot.auction.util.StrategyUtil;

/***
 * Waits for an advantage then bids median plus 2.
 * */
public class SafeStrategy implements BiddingStrategy {

    private int calculateBiddingAmount(BidderImpl bidder) {
        if (StrategyUtil.bidderHasAdvantageOverItsOpponent(bidder)) {
            return StrategyUtil.allBidsMedian(bidder) + 1;
        }

        return 0;
    }


    @Override
    public int getBiddingAmount(BidderImpl bidder) {
        int bid = calculateBiddingAmount(bidder);
        return bid >= 0 && bid <= bidder.getBalance()
                ? bid
                : 0;
    }
}
