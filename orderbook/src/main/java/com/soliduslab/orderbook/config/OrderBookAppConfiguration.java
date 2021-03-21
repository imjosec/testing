package com.soliduslab.orderbook.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soliduslab.orderbook.model.*;
import com.soliduslab.orderbook.model.OrderData.Side;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.BiFunction;
import java.util.function.Function;

@Configuration
public class OrderBookAppConfiguration {
    @Bean
    public OrderDataParser orderDataParser() {
        return new OrderDataParserImpl();
    }

    @Bean
    public OrderDataProcessor orderDataProcessor() {
        return new OrderDataProcessorImpl();
    }

    @Bean
    public Function<Side, OrderBookSide> orderBookSideFactory() {
        return OrderBookSideImpl::new;
    }

    @Bean
    public BiFunction<String, String, OrderBook> orderBookFactory(OrderDataProcessor orderDataProcessor,
                                                                  Function<Side, OrderBookSide> orderBookSideFactory) {
        return (s1, s2) -> new OrderBookImpl(s1, s2, orderDataProcessor, orderBookSideFactory);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}