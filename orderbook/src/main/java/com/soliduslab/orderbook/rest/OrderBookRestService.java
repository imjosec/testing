package com.soliduslab.orderbook.rest;

import com.soliduslab.orderbook.model.OrderDataParser;
import com.soliduslab.orderbook.service.OrderBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static java.util.Objects.nonNull;

@RestController
public class OrderBookRestService {
    private final OrderBookService service;
    private final OrderDataParser orderDataParser;

    @Autowired
    public OrderBookRestService(OrderBookService service, OrderDataParser orderDataParser) {
        this.service = service;
        this.orderDataParser = orderDataParser;
    }

    @PostMapping(value="/orderbook/update", consumes = MediaType.TEXT_PLAIN_VALUE)
    public void update(@RequestBody String body) {
        service.addOrder(orderDataParser.parse(body));
    }

    @GetMapping(value = "/orderbook/fetch", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> fetch(@RequestParam("exchange") String exchange, @RequestParam("symbol") String symbol) {
        final String orderBook = service.getOrderBookSnap(exchange, symbol);
        return nonNull(orderBook) ? ResponseEntity.ok(orderBook) : ResponseEntity.notFound().build();
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<String> handleAllExceptions(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error");
    }
}
