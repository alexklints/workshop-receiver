package ru.yandex.metricaworkshop.receiver;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RequestHelper {
    private RequestQueue mRequestQueue;

    private static volatile RequestHelper sInstance;

    public static synchronized RequestHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new RequestHelper(context);
        }
        return sInstance;
    }

    private RequestHelper(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public void sendUserInfoRequest(String metricaAttribute, Response.Listener<JSONObject> listener,
                                    Response.ErrorListener errorListener, Object tag) {

        String requestString = new RequestBuilder(Consts.REQUEST.EVENT.URL)
                .appendApiKey(Consts.REQUEST.API_KEY)
                .appendDateFrom(Consts.REQUEST.DATE_FROM)
                .appendDateTo(Consts.REQUEST.DATE_TO)
                .appendMetrics(Consts.REQUEST.EVENT.METRICS)
                .appendDimensions(Consts.REQUEST.EVENT.DIMENSIONS)
                .appendLanguage(Consts.REQUEST.EVENT.LANGUAGE)
                .appendAccuracy(Consts.REQUEST.EVENT.ACCURACY)
                .appendLimit(Consts.REQUEST.EVENT.LIMIT)
                .appendParentId(String.format(Consts.REQUEST.EVENT.PARENT_ID_PATTERN, metricaAttribute))
                .appendOauthToken(Consts.REQUEST.OAUTH_TOKEN).build().toString();

        Log.i("UserInfoRequest", requestString);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, requestString, null, listener, errorListener);
        request.setTag(tag);
        mRequestQueue.add(request);
    }

    public void sendUsersStatisticRequest(Response.Listener<JSONObject> listener, Response.ErrorListener errorListener, Object tag) {

        String requestString = new RequestBuilder(Consts.REQUEST.USERS.URL)
                .appendApiKey(Consts.REQUEST.API_KEY)
                .appendDateFrom(Consts.REQUEST.DATE_FROM)
                .appendDateTo(Consts.REQUEST.DATE_TO)
                .appendMetrics(Consts.REQUEST.USERS.METRICS)
                .appendOauthToken(Consts.REQUEST.OAUTH_TOKEN).build().toString();

        Log.i("UserStatisticRequest", requestString);
        sendJsonObjectRequest(requestString, listener, errorListener, tag);
    }

    public void sendOperatingSystemRequest(Response.Listener<JSONObject> listener, Response.ErrorListener errorListener, Object tag) {

        String requestString = new RequestBuilder(Consts.REQUEST.OS.URL)
                .appendApiKey(Consts.REQUEST.API_KEY)
                .appendDateFrom(Consts.REQUEST.DATE_FROM)
                .appendDateTo(Consts.REQUEST.DATE_TO)
                .appendMetrics(Consts.REQUEST.OS.METRICS)
                .appendDimensions(Consts.REQUEST.OS.DIMENSIONS)
                .appendOauthToken(Consts.REQUEST.OAUTH_TOKEN).build().toString();

        Log.i("OperatingSystemRequest", requestString);
        sendJsonObjectRequest(requestString, listener, errorListener, tag);

    }

    private void sendJsonObjectRequest(String requestUrl, Response.Listener<JSONObject> listener,
                                       Response.ErrorListener errorListener, Object tag) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, requestUrl, null, listener, errorListener);
        request.setTag(tag);
        mRequestQueue.add(request);
    }

    public void cancelRequests(Object tag) {
        mRequestQueue.cancelAll(tag);
    }

    private String getCurrentDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = simpleDateFormat.format(new Date());
        return currentDate;
    }
}
