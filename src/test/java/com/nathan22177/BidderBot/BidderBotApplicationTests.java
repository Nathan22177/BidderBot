package com.nathan22177.BidderBot;

import com.nathan22177.BidderBot.auction.bidder.BidderImpl;
import com.nathan22177.BidderBot.auction.enums.BiddingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class BidderBotApplicationTests {
    private List<Integer> cashPoll = Arrays.asList(1000, 10000, 100000);
    private List<Integer> quantityPoll = Arrays.asList(20, 40, 80, 100);
    private int winThreshold = 10; // out of 12

    /*
     * Test for debugging and tweaking strategies.
     * */
    @Test
    public void oneGameVsNathan() {
        int quantity = 100;
        int cash = 10000;
        BiddingStrategy opponentStrategy = BiddingStrategy.FAKE_FAIR;
        BidderImpl nathan = new BidderImpl(quantity, cash, BiddingStrategy.NATHAN);
        BidderImpl opponent = new BidderImpl(quantity, cash, opponentStrategy);


        for (int i = 0; i < quantity / 2; i++) {
            int nathansBid = nathan.placeBid();
            int opponentBid = opponent.placeBid();
            nathan.bids(nathansBid, opponentBid);
            opponent.bids(opponentBid, nathansBid);
            log.info("Nathan bids: " + nathansBid + "; Opponent bids: " + opponentBid + ";\n");
            if (nathansBid >= opponentBid) {
                log.info("Draw or Win.\n");
            } else {
                log.info("Defeat!\n");
            }
        }
        log.info("Init QU: " + nathan.getInitialQuantity() + "; Init balance: " + nathan.getInitialBalance());
        log.info("Nathan QU: " + nathan.getAcquiredAmount() + "; Opponent QU: " + opponent.getAcquiredAmount());
        Assert.assertTrue(nathan.getAcquiredAmount() >= opponent.getAcquiredAmount());

    }

    @Test
    public void NathanVsCopycat() {
        int winOrDrawCount = 0;
        for (int cash : cashPoll) {
            for (int quantity : quantityPoll) {
                BidderImpl nathan = new BidderImpl(quantity, cash, BiddingStrategy.NATHAN);
                BidderImpl copycat = new BidderImpl(quantity, cash, BiddingStrategy.FAKE_COPYCAT);
                for (int i = 0; i < quantity / 2; i++) {
                    int nathansBid = nathan.placeBid();
                    int opponentBid = copycat.placeBid();
                    nathan.bids(nathansBid, opponentBid);
                    copycat.bids(opponentBid, nathansBid);
                }
                if (nathan.getAcquiredAmount() > copycat.getAcquiredAmount()) {
                    winOrDrawCount++;
                }
            }
        }
        Assert.assertTrue(winOrDrawCount >= winThreshold);
    }

    @Test
    public void NathanVsRaiseBidder() {
        int winOrDrawCount = 0;
        for (int cash : cashPoll) {
            for (int quantity : quantityPoll) {
                BidderImpl nathan = new BidderImpl(quantity, cash, BiddingStrategy.NATHAN);
                BidderImpl raiser = new BidderImpl(quantity, cash, BiddingStrategy.FAKE_ALWAYS_RAISES);
                for (int i = 0; i < quantity / 2; i++) {
                    int nathansBid = nathan.placeBid();
                    int opponentBid = raiser.placeBid();
                    nathan.bids(nathansBid, opponentBid);
                    raiser.bids(opponentBid, nathansBid);
                }
                if (nathan.getAcquiredAmount() >= raiser.getAcquiredAmount()) {
                    winOrDrawCount++;
                }
            }
        }
        Assert.assertTrue(winOrDrawCount >= winThreshold);
    }


    @Test
    public void NathanVsFairBidder() {
        int winOrDrawCount = 0;
        for (int cash : cashPoll) {
            for (int quantity : quantityPoll) {
                BidderImpl nathan = new BidderImpl(quantity, cash, BiddingStrategy.NATHAN);
                BidderImpl fairBidder = new BidderImpl(quantity, cash, BiddingStrategy.FAKE_FAIR);
                for (int i = 0; i < quantity / 2; i++) {
                    int nathansBid = nathan.placeBid();
                    int opponentBid = fairBidder.placeBid();
                    nathan.bids(nathansBid, opponentBid);
                    fairBidder.bids(opponentBid, nathansBid);
                }
                if (nathan.getAcquiredAmount() >= fairBidder.getAcquiredAmount()) {
                    winOrDrawCount++;
                }
            }
        }
        Assert.assertTrue(winOrDrawCount >= winThreshold);
    }

    @Test
    public void NathanVsSafeBidder() {
        int winOrDrawCount = 0;
        for (int cash : cashPoll) {
            for (int quantity : quantityPoll) {
                BidderImpl nathan = new BidderImpl(quantity, cash, BiddingStrategy.NATHAN);
                BidderImpl safeBidder = new BidderImpl(quantity, cash, BiddingStrategy.FAKE_SAFE);
                for (int i = 0; i < quantity / 2; i++) {
                    int nathansBid = nathan.placeBid();
                    int opponentBid = safeBidder.placeBid();
                    nathan.bids(nathansBid, opponentBid);
                    safeBidder.bids(opponentBid, nathansBid);
                }
                if (nathan.getAcquiredAmount() >= safeBidder.getAcquiredAmount()) {
                    winOrDrawCount++;
                }
            }
        }
        Assert.assertTrue(winOrDrawCount >= winThreshold);
    }

    @Test
    public void NathanVsWinnerIncrementBidder() {
        int winOrDrawCount = 0;
        for (int cash : cashPoll) {
            for (int quantity : quantityPoll) {
                BidderImpl nathan = new BidderImpl(quantity, cash, BiddingStrategy.NATHAN);
                BidderImpl winnerIncrementBidder = new BidderImpl(quantity, cash, BiddingStrategy.FAKE_SAFE);
                for (int i = 0; i < quantity / 2; i++) {
                    int nathansBid = nathan.placeBid();
                    int opponentBid = winnerIncrementBidder.placeBid();
                    nathan.bids(nathansBid, opponentBid);
                    winnerIncrementBidder.bids(opponentBid, nathansBid);
                }
                if (nathan.getAcquiredAmount() > winnerIncrementBidder.getAcquiredAmount()) {
                    winOrDrawCount++;
                }
            }
        }
        Assert.assertTrue(winOrDrawCount >= winThreshold);
    }
}
