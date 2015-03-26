package ru.yandex.metricaworkshop.receiver;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MetricaEventParser implements Parser {

    private final static int METRICA_INDEX = 1;

    private final Map<String,Float> mMetricaEventData = new HashMap<String, Float>();

    public Map<String, Float> parse(JSONObject data) {
        try {
            JSONArray dataJsonArray = data.getJSONArray(DATA);
            for (int i = 0; i < dataJsonArray.length(); i ++) {
                JSONObject dataItemJson = dataJsonArray.getJSONObject(i);
                String key = dataItemJson.getJSONObject(DIMENSION).getString(NAME);
                float value = (float) dataItemJson.getJSONArray(METRICS).getDouble(METRICA_INDEX);
                mMetricaEventData.put(key, value);
            }
        } catch (JSONException e) {
            Log.e("Parser", e.getMessage(), e);
        }
        return mMetricaEventData;
    }
}
