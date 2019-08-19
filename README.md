# BidderBot
My take at the technical task for Optimax GmbH.

## Classes to look at:
* [BidderImpl](src/main/java/com/nathan22177/BidderBot/auction/bidder/BidderImpl.java) - class to instantiate and use for competition.
* [BiddingStrategy](src/main/java/com/nathan22177/BidderBot/auction/enums/BiddingStrategy.java) - enum-class that defines the strategy for BidderImpl.java, and a brief description of other strategies used for testing.
* [NathanStrategy](src/main/java/com/nathan22177/BidderBot/auction/strategies/NathanStrategy.java) - a strategy I came up with.
* [StrategyController](src/main/java/com/nathan22177/BidderBot/auction/StrategyController.java) - class that resolves where bidder calculates its bidding amount.

## Util classes:
* [StrategyUtil](src/main/java/com/nathan22177/BidderBot/auction/util/StrategyUtil.java) - class that provides static methods to formulate and implement strategies.
* [CalcUtil](src/main/java/com/nathan22177/BidderBot/auction/util/CalcUtil.java) - util class with general calculations.
* [MatchUtil](src/main/java/com/nathan22177/BidderBot/auction/util/MatchUtil.java) - util to decrease the number of equality checks.

## The bot was tested against the following strategies:
* [CopycatStrategy](src/main/java/com/nathan22177/BidderBot/auction/strategies/CopycatStrategy.java) - bids it's opponent's last bid plus one if has an advantage, else skips round.
* [FairStrategy](src/main/java/com/nathan22177/BidderBot/auction/strategies/FairStrategy.java) - always bids mean price of 2 QU's.
* [RisingStrategy](src/main/java/com/nathan22177/BidderBot/auction/strategies/RisingStrategy.java) - gradually raises bid so that would go with empty balance at the end.
* [SafeStrategy](src/main/java/com/nathan22177/BidderBot/auction/strategies/SafeStrategy.java) - waits for advantage then bids median plus 2.
* [WinnerIncrementStrategy](src/main/java/com/nathan22177/BidderBot/auction/strategies/WinnerIncrementStrategy.java) - waits for advantage then bids last winning bid plus one.
* [LehaSVV2009Strategy](src/main/java/com/nathan22177/BidderBot/auction/strategies/LehaSVV2009Strategy.java) - strategy formulated and implemented by @lehaSVV2009.
* [PyramidPlayerStrategy](src/main/java/com/nathan22177/BidderBot/auction/strategies/PyramidPlayerStrategy.java) - strategy formulated and implemented by @PyramidPlayer.

## Tests:
* [BidderBotIntegrationTests](src/test/java/com/nathan22177/BidderBot/BidderBotIntegrationTests.java) - integration tests ensuring that we win a considerable amount of games against other algorithms (winning rates in comments).
* [StrategyUtillUnitTests](src/test/java/com/nathan22177/BidderBot/StrategyUtillUnitTests.java) - unit coverage for StrategyUtil.java to ensure that methods do not deviate from expectable behavior since all the strategies rely on them.

## Conditions of the problem:
A product x QU (quantity units) will be auctioned under 2 parties. The parties have each y MU (monetary units) for auction. They offer then simultaneously an arbitrary number of its MU on the first 2 QU of the product. After that, the bids will be visible to both. The 2 QU of the product is awarded to who has offered the most MU; if both bid the same, then both get 1 QU. Both bidders must pay their amount - including the defeated. A bid of 0 MU is allowed. Bidding on each 2 QU is repeated until the supply of x QU is fully auctioned. Each bidder aims to get a larger amount than its competitor.
In an auction wins the program that is able to get more QU than the other. With a tie, the program that retains more MU wins. Write a program that can participate in such an auction and competes with one of our programs. Please explain its strategy.
The bidder interface:
* [Bidder](src/main/java/com/nathan22177/BidderBot/auction/bidder/Bidder.java) - Interface provided with task.
