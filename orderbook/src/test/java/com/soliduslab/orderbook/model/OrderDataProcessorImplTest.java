package com.soliduslab.orderbook.model;

import com.soliduslab.orderbook.model.OrderData.OrderType;
import com.soliduslab.orderbook.model.OrderData.Side;
import com.soliduslab.orderbook.model.OrderData.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderDataProcessorImplTest {
    OrderDataProcessorImpl orderDataProcessor = new OrderDataProcessorImpl();

    @Mock OrderBookSide orderBookSide;

    @Test
    void newLimitOrder() {
        final OrderData data = new OrderData(1L, "EXCH", "SYMB", Side.BUY, 10.0, 100, Status.New, OrderType.LIMIT);
        orderDataProcessor.processOrderData(data, orderBookSide);
        verify(orderBookSide).addOrder(10.0, 100);
    }

    @Test
    void cancelLimitOrder() {
        final OrderData data = new OrderData(1L, "EXCH", "SYMB", Side.BUY, 10.0, 100, Status.Cancel, OrderType.LIMIT);
        orderDataProcessor.processOrderData(data, orderBookSide);
        verify(orderBookSide).removeOrder(10.0, 100);
    }

    @Test
    void newMarketOrder() {
        final OrderData data = new OrderData(1L, "EXCH", "SYMB", Side.BUY, 10.0, 100, Status.New, OrderType.MARKET);
        orderDataProcessor.processOrderData(data, orderBookSide);
        verify(orderBookSide).addOrder(Double.NaN, 100);
    }

    @Test
    void cancelMarketOrder() {
        final OrderData data = new OrderData(1L, "EXCH", "SYMB", Side.BUY, 10.0, 100, Status.Cancel, OrderType.MARKET);
        orderDataProcessor.processOrderData(data, orderBookSide);
        verify(orderBookSide).removeOrder(Double.NaN, 100);
    }

    @Test
    void execExecution() {
        final OrderData data = new OrderData(1L, "EXCH", "SYMB", Side.BUY, 10.0, 100, Status.Execute, OrderType.EXEC);
        orderDataProcessor.processOrderData(data, orderBookSide);
        verify(orderBookSide).execute(10.0, 100);
    }

    @Test
    void limitExecution() {
        final OrderData data = new OrderData(1L, "EXCH", "SYMB", Side.BUY, 10.0, 100, Status.Execute, OrderType.LIMIT);
        orderDataProcessor.processOrderData(data, orderBookSide);
        verify(orderBookSide).execute(10.0, 100);
    }
}
