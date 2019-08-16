package com.nathan22177.BidderBot.auction.util;

import java.util.Objects;

public class MatchUtil {
    /***
     * Determines if {@param value} equals
     * to anything in {@param comparables}.
     * */
    public static <T> boolean isOneOf(T value, T... comparables) {
        for (T comparable : comparables) {
            if (Objects.equals(value, comparable)) {
                return true;
            }
        }
        return false;
    }
}
