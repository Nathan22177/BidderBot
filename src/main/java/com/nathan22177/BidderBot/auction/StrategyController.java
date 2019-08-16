package com.nathan22177.BidderBot.auction;

import com.nathan22177.BidderBot.auction.bidder.BidderImpl;
import com.nathan22177.BidderBot.auction.strategies.CopycatStrategy;
import com.nathan22177.BidderBot.auction.strategies.FairStrategy;
import com.nathan22177.BidderBot.auction.strategies.NathanStrategy;
import com.nathan22177.BidderBot.auction.strategies.RisingStrategy;
import com.nathan22177.BidderBot.auction.strategies.SafeStrategy;
import com.nathan22177.BidderBot.auction.strategies.UserStrategy;
import com.nathan22177.BidderBot.auction.strategies.WinnerIncrementStrategy;

public class StrategyController {

    public static int getBiddingAmountForStrategy(BidderImpl bidder) {
        switch (bidder.getBiddingStrategy()) {
            case NATHAN:
                return NathanStrategy.getBiddingAmount(bidder);
            case USER:
                return UserStrategy.getBiddingAmount(bidder);
            case FAKE_ALWAYS_RAISES:
                return RisingStrategy.getBiddingAmount(bidder);
            case FAKE_COPYCAT:
                return CopycatStrategy.getBiddingAmount(bidder);
            case FAKE_SAFE:
                return SafeStrategy.getBiddingAmount(bidder);
            case FAKE_FAIR:
                return FairStrategy.getBiddingAmount(bidder);
            case FAKE_WINNER_INCREMENT:
                return WinnerIncrementStrategy.getBiddingAmount(bidder);
            default:
                throw new IllegalArgumentException("No such strategy exists.");
        }
    }
}
