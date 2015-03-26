package ru.yandex.metricaworkshop.receiver;

public class MetricaItem {
    private final String mTitle;
    private final int mValue;

    public MetricaItem(String title, int value) {
        mTitle = title;
        mValue = value;
    }

    public String getTitle() {
        return mTitle;
    }

    public float getValue() {
        return mValue;
    }

    public String toString() {
        return String.format("%s(%s)", mTitle, String.valueOf(mValue));
    }
}
