/*
 * 	   Created by Daniel Nadeau
 * 	   daniel.nadeau01@gmail.com
 * 	   danielnadeau.blogspot.com
 *
 * 	   Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */

package ru.yandex.metricaworkshop.receiver;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.echo.holographlibrary.PieGraph;
import com.echo.holographlibrary.PieSlice;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public abstract class PieFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    private PieGraph mPieGraph;
    private Context mContext;
    private HashMap<String, PieSlice> mPieSliceMap;

    private RequestHelper mRequestHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mContext = getActivity();
        mRequestHelper = RequestHelper.getInstance(getActivity());
        final View v = inflater.inflate(R.layout.fragment_piegraph, container, false);
        mPieGraph = (PieGraph) v.findViewById(R.id.piegraph);
        mPieGraph.setDuration(Consts.PIE_GRAPH_ANIMATION_DURATION);
        mPieGraph.setInterpolator(new AccelerateDecelerateInterpolator());

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mPieGraph.getSlices().isEmpty()) {
            if (mPieSliceMap == null || mPieSliceMap.isEmpty()) {
                mPieSliceMap = new HashMap<String, PieSlice>();
                PieSlice pieSlice = createEmptyPieSlice();
                mPieSliceMap.put(Consts.PIE_EMPTY_SLICE_NAME, pieSlice);
                mPieGraph.addSlice(pieSlice);
            } else {
                for (String key : mPieSliceMap.keySet()) {
                    mPieGraph.addSlice(mPieSliceMap.get(key));
                }
            }
        }
        requestData();
        mPieGraph.animateToGoalValues();
    }

    @Override
    public void onStop() {
        super.onStop();
        mRequestHelper.cancelRequests(this);
    }

    private void requestData() {
        sendRequest(this, this, this);
    }

    private void updatePieGraph(Map<String, Float> data) {
        if (!data.isEmpty()) {
            resetPieGraph();

            int i = 0;
            for (String key : data.keySet()) {
                PieSlice currentSlice = mPieSliceMap.get(key);
                float value = data.get(key);
                if (currentSlice == null) {
                    currentSlice = createPieSlice(key, value, Consts.COLORS[(mPieSliceMap.size() + 1)%Consts.COLORS.length]);
                    mPieSliceMap.put(key, currentSlice);
                    mPieGraph.addSlice(currentSlice);
                } else {
                    currentSlice.setGoalValue(value);
                }
                i++;
            }
            mPieGraph.animateToGoalValues();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Parser parser = getParser();
        Map<String, Float> mDataMap = parser.parse(response);
        updatePieGraph(mDataMap);
    }

    protected abstract void sendRequest(Response.Listener<JSONObject> listener, Response.ErrorListener errorListener,
                               Object tag);

    protected abstract Parser getParser();

    protected RequestHelper getRequestHelper() {
        return mRequestHelper;
    }

    protected void resetPieGraph() {
        for(String key : mPieSliceMap.keySet()) {
            mPieSliceMap.get(key).setGoalValue(0);
        }
    }

    private PieSlice createEmptyPieSlice() {
        PieSlice slice = new PieSlice();
        int color = mContext.getResources().getColor(android.R.color.white);
        slice.setColor(color);
        slice.setSelectedColor(color);
        slice.setValue(0);
        slice.setGoalValue(100);
        slice.setTitle(Consts.PIE_EMPTY_SLICE_NAME);

        return slice;
    }

    private PieSlice createPieSlice(String name, float value, int colorResId) {
        PieSlice slice = new PieSlice();
        int color = mContext.getResources().getColor(colorResId);
        slice.setColor(color);
        slice.setSelectedColor(color);
        slice.setValue(0);
        slice.setGoalValue(value);
        slice.setTitle(String.format("%s(%s)", name, String.valueOf(value)));
        return slice;
    }
}
