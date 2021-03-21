# Coding Exercise
## OrderBook resolver and API wrapper
- In the implementation of the orderbook, I'm using the "it.unimi.dsi" fastutil framework.
- It allows me to have a map and treeset implementation using primitive types, so it minimizes boxing.
- It supports concurrency using RW Locks and ConcurrentHashMap.
- I added the script playData.sh, to be able to test using the data in: https://github.com/DougieHauser/SolidusOrderbookTestData
- I also included the Swagger, for manual testing. (http://localhost:8080/swagger-ui/).
- It might require more code for error handling and perhaps some extra automated integration testing.
- It would improve performance, if we could minimize object allocation when parsing the messages, as I assume that adding messages is the most common operation
# Verbal Questions
## Question 1:
```
{
"exchange": "GMAX", "symbol": "ETH/USD",
"buySide": [],
"sellSide": [
{"level": 7, "size": 288.8},
{"level": 317.15,"size": 10},
{"level": 317.2,"size": 42.3},
{"level": 317.5,"size": 35.5},
{"level": 319,"size": 50 }]
}
```
## Question 2:
If we're missing data, we could end up in sutuations like:
- missing levels in the order book
- not able to remove existing orders
- executing orders at wrong level
- errors when playing executions, as levels won't exist
## Question 3:
- We could stream all the data using Kafka.
- Use segmentation, so we'll have topics that will feed several consumers, all from the data that we're streaming, so we could have several consumers, all updating a fast database like: Redis, Memcached, Apache Ignite, Riak.
- The clients will query the database directly.
- I assume that the clients will be interested only in order book top levels (ie. 3 or 4 levels), that will minimize data published by consumers.
- The numbers of consumers will depend on the number of exchanges/symbols and the hardware that we have available.
## Question 4:
= It's a similar solution to the previous one.
- We could stream all the data using Kafka.
- Use segmentation creating topics, but they need to publish the data only at the end of the process.
- Consumers might have some message type to indicate end of the flow, so they can flush all the data to the database.
- The numbers of consumers will depend on the number of exchanges/symbols and the hardware that we have available.
## Bonus Question:
The date is 2017/07/22. 
- Ethereum briefly crashed from $319 to 10 cents in seconds on one exchange (GDAX) after ‘multimillion dollar’ trade
- Many ethereum traders lost large sums of money
- The move that was blamed on a “multimillion dollar market sell” order.
- The multimillion dollar market sell order resulted in a number of orders being filled from $317.81 to $224.48
- As the price continued to fall, another 800 stop loss orders and margin funding liquidations caused ethereum to trade as low as 10 cents
