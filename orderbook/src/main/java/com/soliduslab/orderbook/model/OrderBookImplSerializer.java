package com.soliduslab.orderbook.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class OrderBookImplSerializer extends StdSerializer<OrderBookImpl> {
    public OrderBookImplSerializer() {
        this(null);
    }

    public OrderBookImplSerializer(Class<OrderBookImpl> t) {
        super(t);
    }

    @Override
    public void serialize(OrderBookImpl obj, JsonGenerator jgen, SerializerProvider serProv) throws IOException {
        jgen.writeStartObject();
        jgen.writeStringField("exchange", obj.getExchange());
        jgen.writeStringField("symbol", obj.getSymbol());
        jgen.writeObjectField("buySide", obj.getBuySide());
        jgen.writeObjectField("sellSide", obj.getSellSide());
        jgen.writeEndObject();
    }
}
