package ru.yandex.metricaworkshop.receiver;

import android.text.TextUtils;

import java.util.HashMap;

public class RequestBuilder {
    private static final String API_KEY = "ids";
    private static final String DATE_FROM = "date1";
    private static final String DATE_TO = "date2";
    private static final String METRICS = "metrics";
    private static final String DIMENSIONS = "dimensions";
    private static final String LANGUAGE = "lang";
    private static final String ACCURACY = "accuracy";
    private static final String FILTERS = "filters";
    private static final String SORT = "sort";
    private static final String OAUTH_TOKEN = "oauth_token";
    private static final String LIMIT = "limit";
    private static final String PARENT_ID = "parent_id";

    private HashMap<String, String> mParams = new HashMap<String, String>();
    private String mUrl;

    public RequestBuilder(String url) {
        mUrl = url;
    }

    public RequestBuilder appendApiKey(String apiKey) {
        appendIfNotEmpty(API_KEY, apiKey);
        return this;
    }

    public RequestBuilder appendDateFrom(String dateFrom) {
        appendIfNotEmpty(DATE_FROM, dateFrom);
        return this;
    }

    public RequestBuilder appendDateTo(String dateTo) {
        appendIfNotEmpty(DATE_TO, dateTo);
        return this;
    }

    public RequestBuilder appendMetrics(String metrics) {
        appendIfNotEmpty(METRICS, metrics);
        return this;
    }

    public RequestBuilder appendDimensions(String dimensions) {
        appendIfNotEmpty(DIMENSIONS, dimensions);
        return this;
    }

    public RequestBuilder appendLanguage(String language) {
        appendIfNotEmpty(LANGUAGE, language);
        return this;
    }

    public RequestBuilder appendAccuracy(String accuracy) {
        appendIfNotEmpty(ACCURACY, accuracy);
        return this;
    }

    public RequestBuilder appendFilters(String filters) {
        appendIfNotEmpty(FILTERS, filters);
        return this;
    }

    public RequestBuilder appendSort(String sort) {
        appendIfNotEmpty(SORT, sort);
        return this;
    }

    public RequestBuilder appendOauthToken(String oauthToken) {
        appendIfNotEmpty(OAUTH_TOKEN, oauthToken);
        return this;
    }

    public RequestBuilder appendLimit(String limit) {
        appendIfNotEmpty(LIMIT, limit);
        return this;
    }

    public RequestBuilder appendParentId(String parentId) {
        appendIfNotEmpty(PARENT_ID, parentId);
        return this;
    }

    public String build() {
        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append(mUrl);
        String queryParams = getQueryParams();
        if (!TextUtils.isEmpty(queryParams)) {
            requestBuilder.append("?").append(queryParams);
        }
        return requestBuilder.toString();
    }

    private String getQueryParams() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : mParams.keySet()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append("&");
            }
            stringBuilder.append(key).append("=").append(mParams.get(key));
        }
        return stringBuilder.toString();
    }

    private void appendIfNotEmpty(String name, String value) {
        if (!TextUtils.isEmpty(value)) {
            mParams.put(name, value);
        }
    }
}
