package com.example.myapplication;

import android.provider.BaseColumns;

public final class AGContract {

    private AGContract() {
    }

    ;

    public static final class GuestEntry implements BaseColumns {
        public final static String TABLE_NAME = "guests1";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_YAHR1 = "yahr1";
        public final static String COLUMN_YAHR2 = "yahr2";
        public final static String COLUMN_GENDER = "gender";
    }
}
