package ru.yandex.metricaworkshop.receiver;

import com.android.volley.Response;

import org.json.JSONObject;

public class OSStatisticsPieFragment extends PieFragment{

    public static OSStatisticsPieFragment create() {
        return new OSStatisticsPieFragment();
    }

    @Override
    protected void sendRequest(Response.Listener<JSONObject> listener, Response.ErrorListener errorListener, Object tag) {
        getRequestHelper().sendOperatingSystemRequest(listener, errorListener, tag);
    }

    @Override
    protected Parser getParser() {
        return new OSInfoParser();
    }
}
