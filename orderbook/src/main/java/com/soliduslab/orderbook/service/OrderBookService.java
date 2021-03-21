package com.soliduslab.orderbook.service;

import com.soliduslab.orderbook.model.OrderData;

public interface OrderBookService {
    String getOrderBookSnap(String exchange, String symbol);

    void addOrder(OrderData data);
}
