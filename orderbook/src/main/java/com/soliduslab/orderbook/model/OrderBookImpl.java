package com.soliduslab.orderbook.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.function.Function;

@JsonSerialize(using = OrderBookImplSerializer.class)
public class OrderBookImpl implements OrderBook {
    private final String exchange;
    private final String symbol;
    private final OrderBookSide buySide;
    private final OrderBookSide sellSide;
    private final OrderDataProcessor orderDataProcessor;

    public OrderBookImpl(String exchange, String symbol, OrderDataProcessor orderDataProcessor,
                         Function<OrderData.Side, OrderBookSide> orderBookSideFactory) {
        this.exchange = exchange;
        this.symbol = symbol;
        this.buySide = orderBookSideFactory.apply(OrderData.Side.BUY);
        this.sellSide = orderBookSideFactory.apply(OrderData.Side.SELL);
        this.orderDataProcessor = orderDataProcessor;
    }

    public void addOrderData(OrderData orderData) {
        OrderBookSide orderBookSide = orderData.getSide() == OrderData.Side.BUY ? buySide : sellSide;
        orderDataProcessor.processOrderData(orderData, orderBookSide);
    }

    String getExchange() {
        return exchange;
    }

    String getSymbol() {
        return symbol;
    }

    OrderBookSide getBuySide() {
        return buySide;
    }

    OrderBookSide getSellSide() {
        return sellSide;
    }
}