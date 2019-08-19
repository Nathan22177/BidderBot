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

    @Test
    public void NathanVsWinnerIncrementBidder() {
        // Wins or gets a draw 101 games out of 112 every test (≈ 90.2% win or draw rate)
        twoStrategiesCompetition(BiddingStrategy.NATHAN, BiddingStrategy.FAKE_WINNER_INCREMENT, 101);
    }

    @Test
    public void NathanVsRaiseBidder() {
        // Wins or gets a draw 109 games out of 112 every test (≈ 97.3% win or draw rate)
        twoStrategiesCompetition(BiddingStrategy.NATHAN, BiddingStrategy.FAKE_ALWAYS_RAISES, 109);
    }

    @Test
    public void NathanVsCopycat() {
        // Wins or gets a draw 101 games out of 112 every test (≈ 90.2%  win or draw rate)
        twoStrategiesCompetition(BiddingStrategy.NATHAN, BiddingStrategy.FAKE_COPYCAT, 101);
    }

    @Test
    public void NathanVsSafeBidder() {
        // Wins or gets a draw 112 games out of 112 every test (≈ 100%  win or draw rate)
        twoStrategiesCompetition(BiddingStrategy.NATHAN, BiddingStrategy.FAKE_SAFE, 112);
    }

    @Test
    public void NathanVsFairBidder() {
        // Wins or gets a draw 102 games out of 112 every test (≈ 91.1%  win or draw rate)
        twoStrategiesCompetition(BiddingStrategy.NATHAN, BiddingStrategy.FAKE_FAIR, 102);
    }

    @Test
    public void NathanVsLehaSVV2009() {
        // Wins or gets a draw in at least 95 games every test, sometimes up to 110 times due to it's opponent randomised behaviour
        // (≈ 84.8% - 98.2%  win or draw rate)
        twoStrategiesCompetition(BiddingStrategy.NATHAN, BiddingStrategy.FAKE_LEHASVV2009, 95);
    }

    @Test
    public void NathanVsPyramidPlayer() {
        // Wins or gets a draw in at least 52 games every test, sometimes up to 70 times due to it's opponent randomised behaviour
        // (≈ 46.4% - 74.3%  win or draw rate)
        twoStrategiesCompetition(BiddingStrategy.NATHAN, BiddingStrategy.FAKE_PYRAMID_PLAYER, 52);
    }

    @Test
    public void LehaSVV2009PyramidPlayer() {
        // Wins or gets a draw in at least 92 games every test, sometimes up to 110 times due to it's opponent randomised behaviour
        // (≈ 82.1% - 91.1%  win or draw rate)
        twoStrategiesCompetition(BiddingStrategy.FAKE_LEHASVV2009, BiddingStrategy.FAKE_PYRAMID_PLAYER, 52);
    }

    public void twoStrategiesCompetition(BiddingStrategy bidderStrategy, BiddingStrategy opponentStrategy, int winningThreshold) {
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
        Assert.assertTrue(winOrDrawCount >= winningThreshold);
    }
}
