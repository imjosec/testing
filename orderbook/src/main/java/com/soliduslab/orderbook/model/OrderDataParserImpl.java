package com.soliduslab.orderbook.model;

public class OrderDataParserImpl implements OrderDataParser {
    @Override
    public OrderData parse(String line) {
        String[] parts = line.trim().split(",");
        long timestamp = Long.parseLong(parts[0]);
        OrderData.Side side = OrderData.Side.valueOf(parts[3]);
        OrderData.Status status = OrderData.Status.valueOf(parts[6]);
        OrderData.OrderType orderType = OrderData.OrderType.valueOf(parts[7]);
        double prize = orderType == OrderData.OrderType.MARKET? 0.0 : Double.parseDouble(parts[4]);
        double size = Double.parseDouble(parts[5]);
        return new OrderData(timestamp, parts[1], parts[2], side, prize, size, status, orderType);
    }
}
