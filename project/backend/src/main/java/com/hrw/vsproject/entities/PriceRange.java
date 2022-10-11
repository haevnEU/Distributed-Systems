package com.hrw.vsproject.entities;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Container class containing a {@link Map}<{@link Long}, {@link Price}>. <br>
 * The {@link Long} key holds the machine time value for the {@link Price}.
 */
@Data
public class PriceRange {
    private Map<Long, Price> data = new HashMap<>();

    /**
     * Adds a Price to the data map.
     * @param date to be added as machine time value
     * @param price price for that machine time
     */
    public void addPrice(Long date, Price price) {
        data.put(date, price);
    }
}
