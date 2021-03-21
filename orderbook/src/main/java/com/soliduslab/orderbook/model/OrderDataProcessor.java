package com.soliduslab.orderbook.model;

public interface OrderDataProcessor {
    void processOrderData(OrderData orderData, OrderBookSide orderBookSide);
}
