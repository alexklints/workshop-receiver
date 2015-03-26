package ru.yandex.metricaworkshop.receiver;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;

import org.json.JSONObject;

public class UserInfoPieFragment extends PieFragment {

    private String mAttribute;

    private static final String ARG_ATTRIBUTE = "UserInfoPieFragment.attribute";

    public static UserInfoPieFragment create(String attribute) {
        Bundle args = new Bundle();
        args.putString(ARG_ATTRIBUTE, attribute);
        UserInfoPieFragment fragment = new UserInfoPieFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mAttribute = getArguments().getString(ARG_ATTRIBUTE);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void sendRequest(Response.Listener<JSONObject> listener, Response.ErrorListener errorListener,
                               Object tag) {
        getRequestHelper().sendUserInfoRequest(mAttribute, listener, errorListener, tag);
    }

    @Override
    protected Parser getParser() {
        return new MetricaEventParser();
    }
}
