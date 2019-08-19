package com.nathan22177.BidderBot.auction.strategies;

import com.nathan22177.BidderBot.auction.bidder.BidderImpl;
import com.nathan22177.BidderBot.auction.util.StrategyUtil;

/***
 * PyramidPlayers AdvancedBidder strategy refactored and appropriated.
 * Comments remain true to the source.
 * */
public class PyramidPlayerStrategy {
    public static int getBiddingAmount(BidderImpl bidder) {
        int spread = bidder.getInitialBalance() / bidder.getInitialQuantity() * 2;
        int restWinRounds = bidder.getInitialQuantity() / 2 + 1 - bidder.getAcquiredAmount();
        if (restWinRounds == 0) {
            return 0;
        }

        int restRounds = StrategyUtil.getRoundsLeft(bidder);
        int winRounds = restRounds / 2 + 1;
        int winBid = bidder.getBalance() / winRounds;

        int bid = winBid - spread + bidder.getRandom().nextInt(spread * 2 + 1);

        if (bid > bidder.getBalance() || bid < 0){
            return bidder.getBalance();
        }

        return bid;
    }
}
