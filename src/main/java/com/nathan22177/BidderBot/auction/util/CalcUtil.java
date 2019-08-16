package com.nathan22177.BidderBot.auction.util;

import java.util.List;

public class CalcUtil {

    /***
     * Determines if {@param value} lower
     * than all of the {@param comparables}.
     * */
    public static boolean firstSmallerThanAllComparables(int value, List<Integer> comparables) {
        for (int comparable : comparables) {
            if (value >= comparable) {
                return false;
            }
        }
        return true;
    }
}
