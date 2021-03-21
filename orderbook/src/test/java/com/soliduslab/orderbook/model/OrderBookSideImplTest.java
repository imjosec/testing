package com.soliduslab.orderbook.model;

import it.unimi.dsi.fastutil.doubles.Double2DoubleMap;
import it.unimi.dsi.fastutil.doubles.DoubleSortedSet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderBookSideImplTest {

    @Test
    public void testBuyLimitOrders() {
        OrderBookSideImpl obs = new OrderBookSideImpl(OrderData.Side.BUY);
        obs.addOrder(10.0, 20.0);
        obs.addOrder(10.0, 10.0);
        obs.addOrder(12.0, 10.0);
        obs.addOrder(5.0, 10.0);
        obs.removeOrder(5.0, 5.0);
        Double2DoubleMap levelsMap = obs.getLevelsMap();
        DoubleSortedSet levels = obs.getLevels();
        assertEquals(12.0, levels.firstDouble(), 0.000001);
        assertEquals(3, levelsMap.size());
        obs.execute(10.0, 40.0);
        assertEquals(1, levelsMap.size());
        assertEquals(5.0, levels.firstDouble(), 0.000001);
        assertEquals(5.0, levelsMap.get(5.0), 0.000001);
    }

    @Test
    public void testBuyMarketOrders() {
        OrderBookSideImpl obs = new OrderBookSideImpl(OrderData.Side.BUY);
        obs.addOrder(10.0, 20.0);
        obs.addOrder(Double.NaN, 20);
        obs.removeOrder(Double.NaN, 10.0);
        obs.execute(10.0, 20);
        Double2DoubleMap levelsMap = obs.getLevelsMap();
        DoubleSortedSet levels = obs.getLevels();
        assertEquals(0.0, obs.getMarketLevelSize());
        assertEquals(1, levels.size());
        assertEquals(10.0, levels.firstDouble(), 0.000001);
        assertEquals(10.0, levelsMap.get(10.0), 0.000001);
    }

    @Test
    public void testSellLimitOrders() {
        OrderBookSideImpl obs = new OrderBookSideImpl(OrderData.Side.SELL);
        obs.addOrder(10.0, 20.0);
        obs.addOrder(10.0, 10.0);
        obs.addOrder(12.0, 10.0);
        obs.addOrder(5.0, 25.0);
        obs.removeOrder(5.0, 5.0);
        obs.addOrder(2.0, 20);
        Double2DoubleMap levelsMap = obs.getLevelsMap();
        DoubleSortedSet levels = obs.getLevels();
        assertEquals(2.0, levels.firstDouble(), 0.000001);
        obs.execute(10.0, 70.0);
        assertEquals(1, levels.size());
        assertEquals(12.0, levels.firstDouble(), 0.000001);
        assertEquals(10.0, levelsMap.get(12.0), 0.000001);
    }

    @Test
    public void testSellMarketOrders() {
        OrderBookSideImpl obs = new OrderBookSideImpl(OrderData.Side.SELL);
        obs.addOrder(10.0, 20.0);
        obs.addOrder(Double.NaN, 20);
        obs.removeOrder(Double.NaN, 10.0);
        obs.execute(10.0, 20);
        Double2DoubleMap levelsMap = obs.getLevelsMap();
        DoubleSortedSet levels = obs.getLevels();
        assertEquals(0.0, obs.getMarketLevelSize());
        assertEquals(1, levels.size());
        assertEquals(10.0, levels.firstDouble(), 0.000001);
        assertEquals(10.0, levelsMap.get(10.0), 0.000001);
    }
}