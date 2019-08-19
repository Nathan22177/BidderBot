package com.nathan22177.BidderBot.auction.util;

import com.nathan22177.BidderBot.auction.bidder.BidderImpl;
import javafx.util.Pair;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Class with methods to use while implementing the strategy.
 */
public class StrategyUtil {

    /***
     * Retrieves the median all the bids.
     * @param bidder instance of bidder
     * @return median
     * */
    public static int allBidsMedian(BidderImpl bidder) {
        return allBidsMedian(bidder.getBiddingHistory());
    }

    /***
     * Retrieves the median all the bids.
     * @param history of all bids
     * @return median
     * */
    public static int allBidsMedian(List<Pair<Integer, Integer>> history) {

        if (history == null) {
            return 0;
        }

        double[] bids = history.stream()
                .flatMap(pair -> Stream.of(pair.getKey(), pair.getValue()))
                .mapToDouble(val -> val)
                .sorted()
                .toArray();
        if (bids.length == 1) {
            return (int) bids[0];
        }

        if (bids.length == 2) {
            return (int) (bids[0] + bids[1]) / 2;
        }
        return (int) (bids[bids.length / 2] + bids[(bids.length / 2) + 1]) / 2;

    }

    /***
     * Retrieves {@param bidder's} opponent's balance
     * after all the bids they have made.
     * @return current balance
     * */
    public static int getOpponentBalance(BidderImpl bidder) {
        return bidder.getBiddingHistory() != null
                ? bidder.getInitialBalance() - bidder.getBiddingHistory().stream().mapToInt(Pair::getValue).sum()
                : bidder.getInitialBalance();
    }

    /***
     * Retrieves mean price of one QU.
     * @param bidder instance of bidder
     * @return price
     * */
    public static int getMeanPriceForOneUnit(BidderImpl bidder) {
        return bidder.getInitialBalance() / bidder.getInitialQuantity();
    }

    /***
     * Retrieves last {@param n} bids of a {@param bidder}.
     * @return FIFO list of bids
     * */
    public static List<Integer> getLastNBids(int n, BidderImpl bidder) {
        return bidder.getBiddingHistory()
                .stream()
                .map(Pair::getKey)
                .skip(bidder.getBiddingHistory().size() - n)
                .collect(Collectors.toList());
    }

    /***
     * Retrieves last {@param n} bids of a {@param bidder's} opponent.
     * @return FIFO list of bids
     * */
    public static List<Integer> getLastNOpponentBids(int n, BidderImpl bidder) {
        return bidder.getBiddingHistory()
                .stream()
                .map(Pair::getValue)
                .skip(bidder.getBiddingHistory().size() - n)
                .collect(Collectors.toList());
    }

    /***
     * Determines if {@param bidder} bidded over
     * mean price of two QUs for last {@param n} bids.
     * */
    public static boolean bidsOverMeanPriceForNRounds(BidderImpl bidder, int n) {
        if (bidder.getBiddingHistory() == null
                || bidder.getBiddingHistory().size() < n
                || n <= 1) {
            return false;
        }
        return CalcUtil.firstSmallerThanAllComparables(getMeanPriceForOneUnit(bidder) * 2,
                getLastNBids(n, bidder));
    }

    /***
     * Determines if {@param bidder} has more money
     * than their opponent.
     * */
    public static boolean bidderHasAdvantageOverItsOpponent(BidderImpl bidder) {
        if (bidder.getBiddingHistory() == null) {
            return false;
        }
        return bidder.getBalance() > getOpponentBalance(bidder);
    }

    /***
     * Retrieves bid that won last round.
     * @param bidder - the instance of a bidder
     * @return bid
     * */
    public static int getPreviousWinnerBid(BidderImpl bidder) {
        return bidder.getBiddingHistory() != null
                ? Stream.of(bidder.getBiddingHistory().peekLast().getKey(), bidder.getBiddingHistory().peekLast().getValue())
                .mapToInt(value -> value)
                .max()
                .orElse(0)
                : 0;
    }

    /***
     * Determines if {@param bidder} has enough QU
     * to win.
     * */
    public static boolean bidderHasReachedTargetAmount(BidderImpl bidder) {
        return bidder.getAcquiredAmount() >= (bidder.getInitialQuantity() / 2) + 1;
    }

    public static int getRoundsLeft(BidderImpl bidder) {
        return bidder.getBiddingHistory() == null
                ? bidder.getInitialQuantity() / 2
                : (bidder.getInitialQuantity() / 2) - bidder.getBiddingHistory().size();
    }

    public static int getRoundsToWin(BidderImpl bidder) {
        return (bidder.getInitialQuantity() / 4) + 1;
    }

    /***
     * Determines if {@param bidder's} opponent always
     * raises it's bid.
     * */
    public static boolean opponentAlwaysRaises(BidderImpl bidder) {
        if (bidder.getBiddingHistory() == null || bidder.getBiddingHistory().size() <= 1) {
            return false;
        }
        List<Integer> bids = bidder.getBiddingHistory()
                .stream()
                .map(Pair::getValue)
                .sorted(Collections.reverseOrder())
                .collect(Collectors.toList());

        return IntStream.range(1, bids.size())
                .allMatch(i -> bids.get(i) < bids.get(i - 1));
    }

    /***
     * Retrieves last bid of {@param bidder's} opponent.
     * @return bid
     * */
    public static int getLastOpponentBid(BidderImpl bidder) {
        return bidder.getBiddingHistory().peekLast().getValue();
    }

    /***
     * Determines if {@param bidder's} opponent bids
     * the same amount for the last {@param n} rounds.
     * */
    public static boolean opponentBidsTheSameLastNRounds(int n, BidderImpl bidder) {
        if (bidder.getBiddingHistory() == null) {
            return false;
        }

        Assert.isTrue(n > 1, "Can't check if he bids the same if we only check 1 or less round");
        Assert.isTrue(bidder.getBiddingHistory().size() >= n, "Can't check for not existing rounds");

        if (bidder.getBiddingHistory() == null) {
            return false;
        }

        int lastBid = bidder.getBiddingHistory().peekLast().getValue();
        return getLastNOpponentBids(n, bidder).stream().sorted(Collections.reverseOrder()).skip(1).allMatch(bid -> bid == lastBid);
    }
}
