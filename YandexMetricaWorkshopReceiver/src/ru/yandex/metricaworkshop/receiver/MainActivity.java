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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import java.util.Locale;

public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                default:
                    return null;
                case 0:
                    return UserInfoPieFragment.create(Consts.ATTRIBUTES.GENDER);
                case 1:
                    return UserInfoBarFragment.create(Consts.ATTRIBUTES.AGE);
                case 2:
                    return UserInfoBarFragment.create(Consts.ATTRIBUTES.EDUCATION);
                case 3:
                    return UserInfoBarFragment.create(Consts.ATTRIBUTES.WORK_STATUS);
                case 4:
                    return UserInfoBarFragment.create(Consts.ATTRIBUTES.ABSENCE_STATUS);
                case 5:
                    return OSStatisticsPieFragment.create();
                case 6:
                    return UserStatisticsBarFragment.create();
                case 7:
                    return UserInfoBarFragment.create(Consts.ATTRIBUTES.RATING);
            }
        }

        @Override
        public int getCount() {
            return 8;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.gender).toUpperCase(l);
                case 1:
                    return getString(R.string.age).toUpperCase(l);
                case 2:
                    return getString(R.string.education_stage).toUpperCase(l);
                case 3:
                    return getString(R.string.work_status).toUpperCase(l);
                case 4:
                    return getString(R.string.absence_reason).toUpperCase(l);
                case 5:
                    return getString(R.string.operations_systems).toUpperCase(l);
                case 6:
                    return getString(R.string.user_statistic).toUpperCase(l);
                case 7:
                    return getString(R.string.rating).toUpperCase(l);
                case 10:
                    return getString(R.string.rating).toUpperCase(l);
            }
            return null;
        }
    }

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;

    private RequestHelper mRequestHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });


        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this)
            );
        }
        mRequestHelper = RequestHelper.getInstance(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mRequestHelper.cancelRequests(this);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

}
