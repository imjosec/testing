package com.soliduslab.orderbook.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderDataParserImplTest {
    private static final OrderData DATA = new OrderData(1L, "EXCH", "SYMB", OrderData.Side.BUY, 10.0, 100, OrderData.Status.New, OrderData.OrderType.LIMIT);

    @Test
    void test() {
        OrderDataParserImpl dataParser = new OrderDataParserImpl();
        OrderData result = dataParser.parse("1,EXCH,SYMB,BUY,10.0,100,New,LIMIT");
        assertEquals(DATA, result);
    }
}
