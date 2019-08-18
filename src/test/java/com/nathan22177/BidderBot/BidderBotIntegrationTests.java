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
public class BidderBotIntegrationTests {
    private List<Integer> cashPoll = Arrays.asList(1000, 5000, 10_000, 50_000, 100_000, 500_000, 1000_000, 10_000_000);
    private List<Integer> quantityPoll = Arrays.asList(2, 4, 8, 16, 20, 30, 40, 50, 80, 100, 200, 400, 800, 1000);
    private int winThreshold = 101; // out of 112 ≈ 90.2%

    @Test
    public void NathanVsCopycat() {
        twoStrategiesCompetition(BiddingStrategy.NATHAN, BiddingStrategy.FAKE_COPYCAT);
    }

    @Test
    public void NathanVsRaiseBidder() {
        twoStrategiesCompetition(BiddingStrategy.NATHAN, BiddingStrategy.FAKE_ALWAYS_RAISES);
    }


    @Test
    public void NathanVsFairBidder() {
        twoStrategiesCompetition(BiddingStrategy.NATHAN, BiddingStrategy.FAKE_FAIR);
    }

    @Test
    public void NathanVsSafeBidder() {
        twoStrategiesCompetition(BiddingStrategy.NATHAN, BiddingStrategy.FAKE_SAFE);
    }

    @Test
    public void NathanVsWinnerIncrementBidder() {
        twoStrategiesCompetition(BiddingStrategy.NATHAN, BiddingStrategy.FAKE_WINNER_INCREMENT);
    }

    public void twoStrategiesCompetition(BiddingStrategy bidderStrategy, BiddingStrategy opponentStrategy) {
        int winOrDrawCount = 0;
        for (int cash : cashPoll) {
            for (int quantity : quantityPoll) {
                BidderImpl bidder = new BidderImpl(quantity, cash, bidderStrategy);
                BidderImpl opponent = new BidderImpl(quantity, cash, opponentStrategy);
                for (int i = 0; i < quantity / 2; i++) {
                    int bidderBid = bidder.placeBid();
                    int opponentBid = opponent.placeBid();
                    bidder.bids(bidderBid, opponentBid);
                    opponent.bids(opponentBid, bidderBid);
                }
                if (bidder.getAcquiredAmount() >= opponent.getAcquiredAmount()) {
                    winOrDrawCount++;
                }
            }
        }
        Assert.assertTrue(winOrDrawCount >= winThreshold);
    }
}
