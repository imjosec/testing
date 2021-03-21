package com.soliduslab.orderbook.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.soliduslab.orderbook.model.OrderData.Side;
import it.unimi.dsi.fastutil.doubles.*;

import java.util.Comparator;

import static com.soliduslab.orderbook.model.OrderData.Side.BUY;
import static com.soliduslab.orderbook.model.OrderData.Side.SELL;

@JsonSerialize(using = OrderBookSideImplSerializer.class)
public class OrderBookSideImpl implements OrderBookSide {
    private static final double DELTA = 0.00000001;
    private final Side side;
    
    private final DoubleSortedSet levels;
    private final Double2DoubleMap levelsMap = new Double2DoubleOpenHashMap();
    private double marketLevelSize;

    public OrderBookSideImpl(Side side) {
        this.side = side;
        levels = side == BUY ?  new DoubleAVLTreeSet(Comparator.reverseOrder()) :
                new DoubleAVLTreeSet(Comparator.naturalOrder());
    }

    @Override
    public void addOrder(double price, double size) {
        if (Double.isNaN(price)) {
            marketLevelSize += size;
        } else {
            if(!levelsMap.containsKey(price)) levels.add(price);
            levelsMap.mergeDouble(price, size, (ov, nv) -> ov + size);
        }
    }

    @Override
    public void removeOrder(double price, double size) {
        if (Double.isNaN(price)) {
            marketLevelSize -= size;
            if(isZero(marketLevelSize) || marketLevelSize < 0.0) marketLevelSize = 0.0;
        } else {
            final double levelSize = levelsMap.get(price);
            if(!isZero(levelSize)) {
                double result = levelsMap.mergeDouble(price, size, (ov, nv) -> ov - size);
                if (isZero(result) || result < 0.0){
                    levelsMap.remove(price);
                    levels.remove(price);
                }
            }
        }
    }

    @Override
    public void execute(double price, double size) {
        if(!isZero(marketLevelSize)) {
            if((size + DELTA) >= marketLevelSize) {
                size -= marketLevelSize;
                marketLevelSize = 0.0;
            } else {
                marketLevelSize -= size;
                size = 0.0;
            }
        }
        if(!isZero(size)) {
            for (double level : levels) {
                if (((side == BUY && price <= level) || (side == SELL && price >= level))) {
                    double levelSize = levelsMap.get(level);
                    if ((size + DELTA) >= levelSize) {
                        removeOrder(level, levelSize);
                        size -= levelSize;
                    } else {
                        removeOrder(level, size);
                        break;
                    }
                }
            }
        }
    }

    double getMarketLevelSize() {
        return marketLevelSize;
    }

    DoubleSortedSet getLevels() {
        return levels;
    }

    Double2DoubleMap getLevelsMap() {
        return levelsMap;
    }

    public static boolean isZero(double value) {
        return value >= -DELTA && value <= DELTA;
    }

}