package ru.yandex.metricaworkshop.receiver;

import org.json.JSONObject;

import java.util.Map;

public interface Parser {
    static final String DATA = "data";
    static final String METRICS = "metrics";
    static final String DIMENSION = "dimension";
    static final String DIMENSIONS = "dimensions";
    static final String NAME = "name";

    public Map<String, Float> parse(JSONObject data);
}
