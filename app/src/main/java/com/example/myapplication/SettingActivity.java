package com.example.myapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.example.myapplication.data.SMDbHelper;
import com.example.myapplication.data.SportsmenContract;


public class SettingActivity extends Activity {
    private AGDbHelper mDbHelper;
    private SMDbHelper DbHelper;
    SharedPreferences myPreferences;
    EditText interval;
    int it;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myPreferences
                = PreferenceManager.getDefaultSharedPreferences(SettingActivity.this);
        super.onCreate(savedInstanceState);
        Button but_ag = (Button)findViewById(R.id.button_age_grooup);
        Button but_sp = (Button)findViewById(R.id.button_sportsmens);
        setContentView(R.layout.setting);
        interval = (EditText) findViewById(R.id.editText4);

        it = myPreferences.getInt("interval", 0);
        interval.setText(String.valueOf(it));
    }
    public void onClick_ag(View view) {
        Intent intent = new Intent(SettingActivity.this, AgeActivity.class);
        startActivity(intent);
    }

    public void onClick_sp(View view) {
        Intent intent = new Intent(SettingActivity.this, SportsmensActivity.class);
        startActivity(intent);
    }

    public void UpDate(View view) {
        SharedPreferences.Editor myEditor = myPreferences.edit();
        it = Integer.parseInt(interval.getText().toString());
        myEditor.putInt("interval", it);
        myEditor.commit();
        mDbHelper = new AGDbHelper(this);
        DbHelper = new SMDbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        SQLiteDatabase db1 = DbHelper.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM " + AGContract.GuestEntry.TABLE_NAME, null);
        int yahr1ColumnIndex = cursor.getColumnIndex(AGContract.GuestEntry.COLUMN_YAHR1);
        int yahr2ColumnIndex = cursor.getColumnIndex(AGContract.GuestEntry.COLUMN_YAHR2);
        int genderColumnIndex = cursor.getColumnIndex(AGContract.GuestEntry.COLUMN_GENDER);

        while (cursor.moveToNext())
        {
            String Yahr1 = cursor.getString(yahr1ColumnIndex);
            String Yahr2 = cursor.getString(yahr2ColumnIndex);
            String Gender = cursor.getString(genderColumnIndex);

            Cursor cursor1 =  db1.rawQuery("SELECT * FROM " + SportsmenContract.GuestEntry.TABLE_NAME +
                    " WHERE yahr >= " + Yahr1 + " and yahr <= "+ Yahr2 + " and gender == " +Gender+ " ORDER by rand", null);
            int time =0;
            int idColumnIndex = cursor.getColumnIndex(SportsmenContract.GuestEntry._ID);
            while (cursor1.moveToNext())
            {
                String id = cursor1.getString(idColumnIndex);
                ContentValues values = new ContentValues();
                values.put(SportsmenContract.GuestEntry.TIME_START, time);
                values.put(SportsmenContract.GuestEntry.TIME_FINISH, 0);
                values.put(SportsmenContract.GuestEntry.TIME_RESULT, 0);
                db1.update(SportsmenContract.GuestEntry.TABLE_NAME,
                        values,
                        SportsmenContract.GuestEntry._ID + "= ?", new String[]{id});
                time+=it;
            }
        }
    }

    public void reset(View view)
    {
        SQLiteDatabase db1 = mDbHelper.getReadableDatabase();
        SQLiteDatabase db = DbHelper.getReadableDatabase();
        if(db!=null && db1!=null)
        {
            db.delete(SportsmenContract.GuestEntry.TABLE_NAME, null, null);
            db1.delete(AGContract.GuestEntry.TABLE_NAME, null, null);
        }

    }
}
