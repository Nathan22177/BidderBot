package com.nathan22177.BidderBot.auction.strategies;

import com.nathan22177.BidderBot.auction.bidder.BidderImpl;
import com.nathan22177.BidderBot.auction.util.StrategyUtil;

/***
 * Bids it's opponent's last bid plus one if has advantage, else skips round.
 * */
public class CopycatStrategy implements BiddingStrategy {

    @Override
    public int getBiddingAmount(BidderImpl bidder) {
        int defaultBid = StrategyUtil.getPreviousWinnerBid(bidder) + 1;
        if (StrategyUtil.bidderHasAdvantageOverItsOpponent(bidder)) {
            return defaultBid > bidder.getBalance()
                    ? bidder.getBalance()
                    : defaultBid;
        } else {
            return 0;
        }
    }
}
