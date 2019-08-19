package com.nathan22177.BidderBot.auction.enums;

public enum BiddingStrategy {
    /**
     * My own strategy
     */
    NATHAN,

    /**
     * Enables input of bids trough console.
     */
    USER,

    /**
     * Emulates bidder who always raises by two.
     */
    FAKE_ALWAYS_RAISES,

    /**
     * Emulates bidder who skips first bid,
     * and then bids the same as opponent on
     * previous round plus one.
     * */
    FAKE_COPYCAT,

    /**
     * Emulates bidder who waits for an advantage
     * and bids median plus one.
     * */
    FAKE_SAFE,

    /**
    * Emulates bidder who always bids price of 2 QUs.
    * */
    FAKE_FAIR,

    /**
     * Emulates bidder who skips first bid and then
     * bids last winning bid plus one when has advantage.
     * */
    FAKE_WINNER_INCREMENT,

    /**
    * Emulates lehaSVV2009s AwesomeBidder strategy.
    * */
    FAKE_LEHASVV2009,

    /**
    * Emulates PyramidPlayers AdvancedBidder strategy.
    * */
    FAKE_PYRAMID_PLAYER

}
