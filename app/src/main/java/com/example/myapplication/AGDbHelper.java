package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AGDbHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = AGDbHelper.class.getSimpleName();

    /**
     * Имя файла базы данных
     */
    private static final String DATABASE_NAME = "List_AG.db";

    /**
     * Версия базы данных. При изменении схемы увеличить на единицу
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Конструктор {@link AGDbHelper}.
     *
     * @param context Контекст приложения
     */
    public AGDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Вызывается при создании базы данных
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Строка для создания таблицы
        String SQL_CREATE_GUESTS_TABLE = "CREATE TABLE " + AGContract.GuestEntry.TABLE_NAME + " ("
                + AGContract.GuestEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + AGContract.GuestEntry.COLUMN_YAHR1 + "  TEXT NOT NULL, "
                + AGContract.GuestEntry.COLUMN_YAHR2 + " TEXT NOT NULL, "
                + AGContract.GuestEntry.COLUMN_GENDER + " INTEGER NOT NULL DEFAULT 0);";

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
