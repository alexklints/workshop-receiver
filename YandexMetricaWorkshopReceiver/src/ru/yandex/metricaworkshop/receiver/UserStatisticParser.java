package ru.yandex.metricaworkshop.receiver;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserStatisticParser implements Parser {

    private final Map<String, Float> mUserStatisticData = new HashMap<String, Float>();
    private Context mContext;
    private String mNewUsersKey;
    private String mActiveUsersKey;

    public UserStatisticParser(Context context) {
        mContext = context;
        mNewUsersKey = context.getString(R.string.new_users);
        mActiveUsersKey = context.getString(R.string.active_users);
    }

    @Override
    public Map<String, Float> parse(JSONObject data) {
        try {
            JSONArray metricsJsonArray = data.getJSONArray(DATA).getJSONObject(0).getJSONArray(METRICS);
            mUserStatisticData.put(mActiveUsersKey, (float) metricsJsonArray.getDouble(0));
            mUserStatisticData.put(mNewUsersKey, (float) metricsJsonArray.getDouble(1));
        } catch (JSONException e) {
            Log.e("Parser", e.getMessage(), e);
        }
        return mUserStatisticData;
    }
}
