package com.soliduslab.orderbook.model;

public interface OrderBookSide {
    void addOrder(double price, double size);

    void removeOrder(double price, double size);

    void execute(double price, double size);
}
