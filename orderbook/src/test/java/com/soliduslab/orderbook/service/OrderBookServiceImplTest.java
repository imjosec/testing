package com.soliduslab.orderbook.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soliduslab.orderbook.model.OrderBook;
import com.soliduslab.orderbook.model.OrderData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderBookServiceImplTest {
    private static final OrderData DATA = new OrderData(1L, "EXCH", "SYMB", OrderData.Side.BUY, 10.0, 100, OrderData.Status.New, OrderData.OrderType.LIMIT);

    @Mock OrderBook orderBook;
    @Mock ObjectMapper mapper;
    @Mock BiFunction<String, String, OrderBook> orderBookFactory;
    @InjectMocks OrderBookServiceImpl service;

    @Test
    void testGetOrderBookSnap() throws JsonProcessingException {
        when(orderBookFactory.apply("EXCH", "SYMB")).thenReturn(orderBook);
        final String json = "JSON";
        when(mapper.writeValueAsString(isA(OrderBook.class))).thenReturn(json);
        service.addOrder(DATA);
        verifyNoInteractions(mapper);
        verify(orderBook).addOrderData(DATA);
        verifyNoMoreInteractions(orderBook);
        final String result = service.getOrderBookSnap("EXCH", "SYMB");
        assertEquals(json, result);
        verify(mapper).writeValueAsString(isA(OrderBook.class));
        verifyNoMoreInteractions(mapper, orderBook);
    }
}
