package com.example.myapplication.data;

import android.provider.BaseColumns;

public final class SportsmenContract {

    private SportsmenContract() {
    };

    public static final class GuestEntry implements BaseColumns {
        public final static String TABLE_NAME = "guests";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_NAME = "name";
        public final static String COLUMN_GENDER = "gender";
        public final static String COLUMN_YAHR = "yahr";
        public final static String TIME_START = "start";
        public final static String TIME_FINISH = "finish";
        public final static String TIME_RESULT = "result";
        public final static String RAND = "rand";
        public static final int GENDER_FEMALE = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_UNKNOWN = 2;
    }
}