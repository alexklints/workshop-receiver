package ru.yandex.metricaworkshop.receiver;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OSInfoParser implements Parser {

    private Map<String, Float> mMetricaEventData = new HashMap<String, Float>();

    @Override
    public Map<String, Float> parse(JSONObject data) {
        try {
            JSONArray dataJsonArray = data.getJSONArray(DATA);
            for(int i = 0; i < dataJsonArray.length(); i ++) {
                JSONObject jsonItem = dataJsonArray.getJSONObject(i);
                String key = jsonItem.getJSONArray(DIMENSIONS).getJSONObject(0).getString(NAME);
                Float value = (float) jsonItem.getJSONArray(METRICS).getDouble(0);
                mMetricaEventData.put(key, value);
            }
        } catch (JSONException e) {
            Log.e("Parser", e.getMessage(), e);
        }
        return mMetricaEventData;
    }
}
