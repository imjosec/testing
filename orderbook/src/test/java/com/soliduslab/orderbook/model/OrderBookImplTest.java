package com.soliduslab.orderbook.model;

import com.soliduslab.orderbook.model.OrderData.OrderType;
import com.soliduslab.orderbook.model.OrderData.Side;
import com.soliduslab.orderbook.model.OrderData.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.Function;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderBookImplTest {
    private static final OrderData BUY = new OrderData(1L, "EXCH", "SYMB", Side.BUY, 10.0, 100, Status.New, OrderType.LIMIT);
    private static final OrderData SELL = new OrderData(1L, "EXCH", "SYMB", Side.SELL, 10.0, 100, Status.New, OrderType.LIMIT);
    @Mock OrderBookSide buySide;
    @Mock OrderBookSide sellSide;
    @Mock Function<Side, OrderBookSide> orderBookSideFunction;
    @Mock OrderDataProcessor orderDataProcessor;

    @Test
    void test() {
        when(orderBookSideFunction.apply(Side.BUY)).thenReturn(buySide);
        when(orderBookSideFunction.apply(Side.SELL)).thenReturn(sellSide);
        OrderBookImpl orderBook = new OrderBookImpl("EXCH", "SYMB", orderDataProcessor, orderBookSideFunction);
        verify(orderBookSideFunction).apply(Side.BUY);
        verify(orderBookSideFunction).apply(Side.SELL);
        verifyNoInteractions(orderDataProcessor, buySide, sellSide);
        orderBook.addOrderData(BUY);
        verify(orderDataProcessor).processOrderData(BUY, buySide);
        verifyNoInteractions(buySide, sellSide);
        orderBook.addOrderData(SELL);
        verify(orderDataProcessor).processOrderData(SELL, sellSide);
        verifyNoMoreInteractions(orderDataProcessor, buySide, sellSide);
    }
}