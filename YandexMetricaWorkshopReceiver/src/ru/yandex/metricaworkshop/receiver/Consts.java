package ru.yandex.metricaworkshop.receiver;

public class Consts {
    public static interface REQUEST {
        public static final String API_KEY = "37970";
        public static final String OAUTH_TOKEN = "0e491c59ad834583b4e397ddf7ce9d9e";
        public static final String DATE_FROM = "today";
        public static final String DATE_TO = "today";

        public static interface EVENT {
            public static final String URL = "https://beta.api-appmetrika.yandex.ru/stat/v1/data/drilldown";
            public static final String METRICS = "ym:m:devices,ym:m:clientEvents,ym:m:avgParams";
            public static final String DIMENSIONS = "ym:m:eventLabel,ym:m:paramsLevel1,ym:m:paramsLevel2";
            public static final String LANGUAGE = "ru";
            public static final String ACCURACY = "low";
            public static final String FILTERS = "ym:m:eventType==%27EVENT_CLIENT%27";
            public static final String SORT_ORDER = "ym:m:paramsLevel";
            public static final String LIMIT = "20";
            public static final String PARENT_ID_PATTERN = "%%5B\"user_info\",\"%s\"%%5D";
        }

        public static interface USERS {
            public static final String URL = "https://beta.api-appmetrika.yandex.ru/stat/v1/data";
            public static final String METRICS = "ym:m:users,ym:m:newUsers";
        }

        public static interface OS {
            public static final String URL = "https://beta.api-appmetrika.yandex.ru/stat/v1/data";
            public static final String DIMENSIONS = "ym:m:operatingSystem";
            public static final String METRICS = "ym:m:users";
        }
    }

    public static interface ATTRIBUTES {
        public static final String GENDER = "gender";
        public static final String WORK_STATUS = "work";
        public static final String AGE = "age";
        public static final String ABSENCE_STATUS = "absence";
        public static final String EDUCATION = "education";
        public static final String RATING = "rating";

    }

    public static int[] COLORS = {
            R.color.blue, R.color.transparent_blue, R.color.green, R.color.green_light,
            R.color.orange, R.color.transparent_orange, R.color.purple
    };

    public static final String PIE_EMPTY_SLICE_NAME = "";


    public static final int PIE_GRAPH_ANIMATION_DURATION = 1000;
}
