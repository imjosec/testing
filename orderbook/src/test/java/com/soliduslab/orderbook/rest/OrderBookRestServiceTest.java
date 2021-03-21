package com.soliduslab.orderbook.rest;

import com.soliduslab.orderbook.model.OrderData;
import com.soliduslab.orderbook.model.OrderDataParser;
import com.soliduslab.orderbook.service.OrderBookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class OrderBookRestServiceTest {
    private static final OrderData DATA = new OrderData(1L, "EXCH", "SYMB", OrderData.Side.BUY, 10.0, 100, OrderData.Status.New, OrderData.OrderType.LIMIT);
    @MockBean OrderBookService service;
    @MockBean OrderDataParser orderDataParser;

    @Autowired
    private OrderBookRestService orderBookRestService;

    @Test
    public void testUpdate() {
        when(orderDataParser.parse("ORDER_DATA_BODY")).thenReturn(DATA);
        orderBookRestService.update("ORDER_DATA_BODY");
        verify(service).addOrder(DATA);
    }

    @Test
    public void testFetchExistingBook() {
        when(service.getOrderBookSnap(anyString(), anyString())).thenReturn("JSON");
        ResponseEntity<String> result = orderBookRestService.fetch("exchange", "symbol");
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("JSON", result.getBody());
    }

    @Test
    public void testFetchNonExistingBook() {
        when(service.getOrderBookSnap(anyString(), anyString())).thenReturn(null);
        ResponseEntity<String> result = orderBookRestService.fetch("exchange", "symbol");
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }
}