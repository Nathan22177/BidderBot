package com.nathan22177.BidderBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.nathan22177.BidderBot.auction.bidder.BidderImpl;
import com.nathan22177.BidderBot.auction.strategies.ConsolePlayerStrategy;
import com.nathan22177.BidderBot.auction.strategies.NathanStrategy;

@SpringBootApplication
public class BidderBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(BidderBotApplication.class, args);

		int quantity = 10;
		int cash = 10000;
		BidderImpl nathan = new BidderImpl(quantity, cash, new NathanStrategy());
		BidderImpl user = new BidderImpl(quantity, cash, new ConsolePlayerStrategy());
		for (int i = 0; i < 5; i++) {
			int nathansBid = nathan.placeBid();
			int usersBid = user.placeBid();
			nathan.bids(nathansBid, usersBid);
			user.bids(usersBid, nathansBid);
			System.out.println("Nathan bid: " + nathansBid);
			System.out.println(usersBid > nathansBid
					? "You've won!"
					: usersBid == nathansBid
					? "Draw!"
					: "Nathan won!");
		}
		System.out.println("Nathans QU: " + nathan.getAcquiredAmount() + " Nathan's balance: " + nathan.getBalance());
		System.out.println("Users QU: " + user.getAcquiredAmount());
	}

}
