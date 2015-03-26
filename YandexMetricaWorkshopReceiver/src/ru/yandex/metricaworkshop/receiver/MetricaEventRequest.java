package ru.yandex.metricaworkshop.receiver;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class MetricaEventRequest extends JsonObjectRequest {

    public MetricaEventRequest(int method, String url, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, null, listener, errorListener);
    }


}
