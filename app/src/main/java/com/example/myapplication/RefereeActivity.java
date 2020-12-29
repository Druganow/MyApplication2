package com.example.myapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.core.view.MenuItemCompat;

import com.example.myapplication.data.SMDbHelper;
import com.example.myapplication.data.SportsmenContract;



public class RefereeActivity   extends Activity {

    private AGDbHelper mDbHelper;
    private SMDbHelper DbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.referee);
        update();
    }

    private void update() {
        mDbHelper = new AGDbHelper(this);
        DbHelper = new SMDbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        SQLiteDatabase db1 = DbHelper.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM " + AGContract.GuestEntry.TABLE_NAME, null);
        int yahr1ColumnIndex = cursor.getColumnIndex(AGContract.GuestEntry.COLUMN_YAHR1);
        int yahr2ColumnIndex = cursor.getColumnIndex(AGContract.GuestEntry.COLUMN_YAHR2);
        int genderColumnIndex = cursor.getColumnIndex(AGContract.GuestEntry.COLUMN_GENDER);
        TextView displayTextView = (TextView) findViewById(R.id.textViewSP);
        while (cursor.moveToNext())
        {
            String Yahr1 = cursor.getString(yahr1ColumnIndex);
            String Yahr2 = cursor.getString(yahr2ColumnIndex);
            String Gender = cursor.getString(genderColumnIndex);
            displayTextView.append(("\nВозрастная группа "+
                    Yahr1 + " - " +
                            Yahr2 + " " +
                            (Gender=="0"?"ж":"м") +"\n"));
            Cursor cursor1 =  db1.rawQuery("SELECT * FROM " + SportsmenContract.GuestEntry.TABLE_NAME +
                    " WHERE yahr >= " + Yahr1 + " and yahr <= "+ Yahr2 + " and gender == " +Gender+ " ORDER by start", null);


            int nameColumnIndex = cursor1.getColumnIndex(SportsmenContract.GuestEntry.COLUMN_NAME);
            int yahrColumnIndex = cursor1.getColumnIndex(SportsmenContract.GuestEntry.COLUMN_YAHR);
            int gender1ColumnIndex = cursor1.getColumnIndex(SportsmenContract.GuestEntry.COLUMN_GENDER);
            int timeColumnIndex = cursor1.getColumnIndex(SportsmenContract.GuestEntry.TIME_START);
int i =0;
            // Проходим через все ряды
            while (cursor1.moveToNext()) {
                i++;
                // Используем индекс для получения строки или числа
                String currentName = cursor1.getString(nameColumnIndex);
                String currentCity = cursor1.getString(yahrColumnIndex);
                int currentGender = cursor1.getInt(gender1ColumnIndex);
                int currentTime = cursor1.getInt(timeColumnIndex);
                // Выводим значения каждого столбца
                displayTextView.append((i+"-" +currentName + " - " +
                        currentCity + " - " +
                        (currentGender==0?"ж":"м") + " - "+
                        Tim.Times(currentTime)+"\n"));
            }
        }
    }

}
