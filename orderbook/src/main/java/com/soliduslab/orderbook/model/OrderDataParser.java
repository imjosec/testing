package com.soliduslab.orderbook.model;

public interface OrderDataParser {
    OrderData parse(String line);
}
