package ru.yandex.metricaworkshop.receiver;

import java.util.HashMap;

public class MetricaEventData {
    private final HashMap<String, Integer> mAgeData = new HashMap<String, Integer>();
    private final HashMap<String, Integer> mGenderData = new HashMap<String, Integer>();
    private final HashMap<String, Integer> mWorkStatusData = new HashMap<String, Integer>();
    private final HashMap<String, Integer> mEducationData = new HashMap<String, Integer>();
    private final HashMap<String, Integer> mAbsenceReasonData = new HashMap<String, Integer>();
    private final HashMap<String, Integer> mRatingData = new HashMap<String, Integer>();

    public HashMap<String, Integer> getAgeData() {
        return mAgeData;
    }

    public HashMap<String, Integer> getGenderData() {
        return mGenderData;
    }

    public HashMap<String, Integer> getWorkStatusData() {
        return mWorkStatusData;
    }

    public HashMap<String, Integer> getEducationData() {
        return mEducationData;
    }

    public HashMap<String, Integer> getAbsenceReasonData() {
        return mAbsenceReasonData;
    }

    public HashMap<String, Integer> getRatingData() {
        return mRatingData;
    }
}
