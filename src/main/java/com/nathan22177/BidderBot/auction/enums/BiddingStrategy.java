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
    DUMMY_RAISES,

    /**
     * Emulates bidder who skips first bid,
     * and then bids the same as opponent on
     * previous round plus one.
     * */
    DUMMY_COPYCAT,

    /**
     * Emulates bidder who waits for an advantage
     * and bids median plus one.
     * */
    DUMMY_SAFE,

    /**
    * Emulates bidder who always bids price of 2 QUs.
    * */
    DUMMY_FAIR,

    /**
     * Emulates bidder who skips first bid and then
     * bids last winning bid plus one when has advantage.
     * */
    DUMMY_WINNER_INCREMENT

}
