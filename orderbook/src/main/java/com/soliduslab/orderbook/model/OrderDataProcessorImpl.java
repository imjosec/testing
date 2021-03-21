package com.soliduslab.orderbook.model;

import com.soliduslab.orderbook.model.OrderData.OrderType;
import com.soliduslab.orderbook.model.OrderData.Status;

public class OrderDataProcessorImpl implements OrderDataProcessor {

    @Override
    public void processOrderData(final OrderData orderData, final OrderBookSide orderBookSide) {
        final OrderType type = orderData.getOrderType();
        final Status status = orderData.getStatus();
        if (type == OrderType.LIMIT) {
            if (status == Status.Cancel)       orderBookSide.removeOrder(orderData.getPrice(), orderData.getSize());
            else if (status == Status.New)     orderBookSide.addOrder(orderData.getPrice(), orderData.getSize());
            else if (status == Status.Execute) orderBookSide.execute(orderData.getPrice(), orderData.getSize());
            else throwError();
        } else if (type == OrderType.MARKET) {
            if (status == Status.Cancel)   orderBookSide.removeOrder(Double.NaN, orderData.getSize());
            else if (status == Status.New) orderBookSide.addOrder(Double.NaN, orderData.getSize());
            else throwError();
        } else {
            if (type == OrderType.EXEC && status == Status.Execute) orderBookSide.execute(orderData.getPrice(), orderData.getSize());
            else throwError();
        }
    }

    private static void throwError() {
        throw new IllegalArgumentException("Invalid operation");
    }
}
