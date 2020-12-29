package com.example.myapplication.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SMDbHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = SMDbHelper.class.getSimpleName();

    /**
     * Имя файла базы данных
     */
    private static final String DATABASE_NAME = "List_SM.db";

    /**
     * Версия базы данных. При изменении схемы увеличить на единицу
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Конструктор {@link SMDbHelper}.
     *
     * @param context Контекст приложения
     */
    public SMDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Вызывается при создании базы данных
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Строка для создания таблицы
        String SQL_CREATE_GUESTS_TABLE = "CREATE TABLE " + SportsmenContract.GuestEntry.TABLE_NAME + " ("
                + SportsmenContract.GuestEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SportsmenContract.GuestEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + SportsmenContract.GuestEntry.COLUMN_GENDER + " INTEGER NOT NULL DEFAULT 2, "
                + SportsmenContract.GuestEntry.COLUMN_YAHR + " INTEGER NOT NULL DEFAULT 0,"
                + SportsmenContract.GuestEntry.TIME_START + " INTEGER NOT NULL DEFAULT 0, "
                + SportsmenContract.GuestEntry.TIME_FINISH + " INTEGER NOT NULL DEFAULT 0, "
                + SportsmenContract.GuestEntry.TIME_RESULT + " INTEGER NOT NULL DEFAULT 0, "
                + SportsmenContract.GuestEntry.RAND + " INTEGER NOT NULL DEFAULT 0);";

        // Запускаем создание таблицы
        db.execSQL(SQL_CREATE_GUESTS_TABLE);
    }

    /**
     * Вызывается при обновлении схемы базы данных
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
