package me.dmillerw.mcquery.queryable.data;

import com.google.common.collect.Maps;

import java.util.Map;

public class QuickyBuilder {

    public static final QuickyBuilder builder() {
        return new QuickyBuilder();
    }

    private final Map<String, Object> map = Maps.newHashMap();

    public QuickyBuilder set(String property, Object value) {
        map.put(property, value);
        return this;
    }

    public Map<String, Object> build() {
        return map;
    }
}
