package ru.yandex.metricaworkshop.receiver;

import com.android.volley.Response;

import org.json.JSONObject;

public class UserStatisticsBarFragment extends BarFragment {

    public static UserStatisticsBarFragment create() {
        return new UserStatisticsBarFragment();
    }

    @Override
    protected void sendRequest(Response.Listener<JSONObject> listener, Response.ErrorListener errorListener, Object tag) {
        getRequestHelper().sendUsersStatisticRequest(listener, errorListener, tag);
    }

    @Override
    protected Parser getParser() {
        return new UserStatisticParser(getActivity());
    }
}
