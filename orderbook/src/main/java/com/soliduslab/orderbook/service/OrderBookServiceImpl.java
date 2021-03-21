package com.soliduslab.orderbook.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soliduslab.orderbook.model.OrderBook;
import com.soliduslab.orderbook.model.OrderData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UncheckedIOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.BiFunction;

import static java.util.Objects.isNull;

@Component
public class OrderBookServiceImpl implements OrderBookService  {
    private final ObjectMapper mapper;
    private final BiFunction<String, String, OrderBook> orderBookFactory;
    private final Map<String, OrderBook> exchangeSymbolMap = new ConcurrentHashMap<>();
    private final Map<String, ReentrantReadWriteLock> lockingMap = new ConcurrentHashMap<>();

    @Autowired
    public OrderBookServiceImpl(final ObjectMapper mapper, final BiFunction<String, String, OrderBook> orderBookFactory) {
        this.mapper = mapper;
        this.orderBookFactory = orderBookFactory;
    }

    @Override
    public String getOrderBookSnap(final String exchange, final String symbol) {
        final String keyMap = getKeyMap(exchange, symbol);
        final OrderBook orderBook = exchangeSymbolMap.get(keyMap);
        if(isNull(orderBook)) return null;

        final String orderBookJson;
        final Lock readLock = lockingMap.computeIfAbsent(keyMap, (k) -> new ReentrantReadWriteLock()).readLock();
        try {
            readLock.lock();
            orderBookJson = mapper.writeValueAsString(orderBook);
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException(e);
        } finally {
            readLock.unlock();
        }
        return orderBookJson;
    }

    @Override
    public void addOrder(final OrderData data) {
        final String keyMap = getKeyMap(data);
        final OrderBook orderBook = exchangeSymbolMap.computeIfAbsent(keyMap,
                k -> orderBookFactory.apply(data.getExchange(), data.getSymbol()));
        final Lock writeLock = lockingMap.computeIfAbsent(keyMap, (k) -> new ReentrantReadWriteLock()).writeLock();
        try {
            writeLock.lock();
            orderBook.addOrderData(data);
        } finally {
            writeLock.unlock();
        }
    }

    private static String getKeyMap(OrderData data) {
        return getKeyMap(data.getExchange(), data.getSymbol());
    }

    private static String getKeyMap(String exchange, String symbol) {
        return exchange + "-" + symbol;
    }
}