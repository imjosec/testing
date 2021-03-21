package com.soliduslab.orderbook.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class OrderBookSideImplSerializer extends StdSerializer<OrderBookSideImpl> {

    public OrderBookSideImplSerializer() {
        this(null);
    }

    public OrderBookSideImplSerializer(Class<OrderBookSideImpl> t) {
        super(t);
    }

    @Override
    public void serialize(OrderBookSideImpl obj, JsonGenerator jgen, SerializerProvider serProv) throws IOException {
        jgen.writeStartArray();
        if (!OrderBookSideImpl.isZero(obj.getMarketLevelSize())) {
            jgen.writeStartObject();
            jgen.writeStringField("level", "MARKET");
            jgen.writeNumberField("size", obj.getMarketLevelSize());
            jgen.writeEndObject();
        }
        for (double level : obj.getLevels()) {
            jgen.writeStartObject();
            jgen.writeNumberField("level", level);
            jgen.writeNumberField("size", obj.getLevelsMap().get(level));
            jgen.writeEndObject();
        }
        jgen.writeEndArray();
    }
}
