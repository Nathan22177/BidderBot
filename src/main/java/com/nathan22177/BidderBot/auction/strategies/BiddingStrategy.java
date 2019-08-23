package com.nathan22177.BidderBot.auction.strategies;

import com.nathan22177.BidderBot.auction.bidder.BidderImpl;

public interface BiddingStrategy {

    int getBiddingAmount(BidderImpl bidder);

}
