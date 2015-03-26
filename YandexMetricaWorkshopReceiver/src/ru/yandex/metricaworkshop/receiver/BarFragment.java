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

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.echo.holographlibrary.Bar;
import com.echo.holographlibrary.BarGraph;
import com.echo.holographlibrary.HoloGraphAnimate;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class BarFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{

    private BarGraph mBarGraph;
    private Context mContext;
    private RequestHelper mRequestHelper;
    private Resources mResources;
    private Map<String, Bar> mBarMap = new HashMap<String, Bar>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mContext = getActivity();
        mResources = getResources();
        mRequestHelper = RequestHelper.getInstance(getActivity());
        ArrayList<Bar> aBars = new ArrayList<Bar>();
        final View v = inflater.inflate(R.layout.fragment_bargraph, container, false);
        final BarGraph barGraph = (BarGraph) v.findViewById(R.id.bargraph);
        mBarGraph = barGraph;
        barGraph.setBars(aBars);

        barGraph.setDuration(1500);//default if unspecified is 300 ms
        barGraph.setInterpolator(new AccelerateDecelerateInterpolator());//Only use over/undershoot  when not inserting/deleting
        barGraph.setAnimationListener(getAnimationListener());

        return v;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public Animator.AnimatorListener getAnimationListener(){
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1)
            return new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    ArrayList<Bar> newBars = new ArrayList<Bar>();
                    //Keep bars that were not deleted
                    for (Bar b : mBarGraph.getBars()){
                        if (b.mAnimateSpecial != HoloGraphAnimate.ANIMATE_DELETE){
                            b.mAnimateSpecial = HoloGraphAnimate.ANIMATE_NORMAL;
                            newBars.add(b);
                        }
                    }
                    mBarGraph.setBars(newBars);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            };
        else return null;

    }

    @Override
    public void onStart() {
        super.onStart();
        if (mBarGraph.getBars().isEmpty() && mBarMap != null && !mBarMap.isEmpty()) {
            for (String key : mBarMap.keySet()) {
                mBarGraph.getBars().add(mBarMap.get(key));
            }
        }
        requestData();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        mRequestHelper.cancelRequests(this);
    }

    private void requestData() {
        sendRequest(this, this, this);
    }

    private void updateBarGraph(Map<String, Float> data) {
        if (!data.isEmpty()) {
            resetBarGraph();
            int i = 0;
            for (String key : data.keySet()) {
                Bar bar = mBarMap.get(key);
                float value = data.get(key);

                if (bar == null) {
                    bar = createBar(key, value, Consts.COLORS[(mBarMap.keySet().size() + 1)%Consts.COLORS.length]);
                    mBarMap.put(key, bar);
                    mBarGraph.getBars().add(bar);
                } else {
                    bar.setGoalValue(value);
                }
                i++;
            }
            //List<Bar> barsToRemove = new ArrayList<Bar>();
            for(Bar bar : mBarGraph.getBars()) {
                if (bar.getGoalValue() == 0) {
                    bar.mAnimateSpecial = HoloGraphAnimate.ANIMATE_DELETE;
                }
            }
            mBarGraph.setInterpolator(new AccelerateDecelerateInterpolator());
            mBarGraph.setDuration(1000);
            mBarGraph.animateToGoalValues();
           // mBarGraph.getBars().removeAll(barsToRemove);
        }
    }

    private void resetBarGraph() {
        for(String key : mBarMap.keySet()) {
            mBarMap.get(key).setGoalValue(0);
        }
    }

    private Bar createBar(String name, float value, int colorResId) {
        Bar bar = new Bar();
        bar.setColor(mResources.getColor(colorResId));
        bar.setName(name);
        bar.setValue(value);
        bar.setGoalValue(value);
        bar.setValueString(String.valueOf(value));
        bar.mAnimateSpecial = HoloGraphAnimate.ANIMATE_INSERT;

        return bar;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Parser metricaEventParser = getParser();
        Map<String, Float> mDataMap = metricaEventParser.parse(response);
        updateBarGraph(mDataMap);
    }

    protected abstract void sendRequest(Response.Listener<JSONObject> listener, Response.ErrorListener errorListener,
                               Object tag);

    protected abstract Parser getParser();

    protected RequestHelper getRequestHelper() {
        return mRequestHelper;
    }
}
