package com.soliduslab.orderbook.model;

import lombok.Value;

@Value
public class OrderData {
    public enum Status {New, Cancel, Execute};
    public enum OrderType {LIMIT, EXEC, MARKET};
    public enum Side {BUY, SELL}

    long timestamp;
    String exchange;
    String symbol;
    Side side;
    double price;
    double size;
    Status status;
    OrderType orderType;
}