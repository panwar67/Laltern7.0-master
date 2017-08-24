package com.lions.sparsh23.laltern;

import java.util.Comparator;
import java.util.Map;

/**
 * Created by Sparsh23 on 01/09/16.
 */

class MapComparator implements Comparator<Map<String, String>>
{
    private final String key;

    public MapComparator(String key)
    {
        this.key = key;
    }

    public int compare(Map<String, String> first,
                       Map<String, String> second)
    {
        // TODO: Null checking, both for maps and values
        Double firstValue = Double.valueOf(first.get(key));
        Double secondValue = Double.valueOf(second.get(key));
        return firstValue.compareTo(secondValue);
    }
}
