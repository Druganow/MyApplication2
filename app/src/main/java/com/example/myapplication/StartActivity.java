package com.example.myapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.data.SMDbHelper;
import com.example.myapplication.data.SportsmenContract;

import java.util.ArrayList;


public class StartActivity   extends Activity {
    private ArrayList<AG> AGs;
    private ArrayList<SM1> SMs;
    private AGDbHelper mDbHelper;
    private SMDbHelper DbHelper;
    int countAG;
    int countSM;
    TextView AGname;
    TextView SMname;
    Cursor cursor;
    Cursor cursor1;
    SQLiteDatabase db;
    SQLiteDatabase db1;
    EditText Time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        SMs = new ArrayList<>();
        AGs = new ArrayList<>();
        mDbHelper = new AGDbHelper(this);
        DbHelper = new SMDbHelper(this);
        Time = (EditText) findViewById(R.id.editTextTime);
        db = mDbHelper.getReadableDatabase();
        db1 = DbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + AGContract.GuestEntry.TABLE_NAME, null);
        int yahr1ColumnIndex = cursor.getColumnIndex(AGContract.GuestEntry.COLUMN_YAHR1);
        int yahr2ColumnIndex = cursor.getColumnIndex(AGContract.GuestEntry.COLUMN_YAHR2);
        int genderColumnIndex = cursor.getColumnIndex(AGContract.GuestEntry.COLUMN_GENDER);
        countAG = 0;
        countSM = 0;
        while (cursor.moveToNext()) {
            String Yahr1 = cursor.getString(yahr1ColumnIndex);
            String Yahr2 = cursor.getString(yahr2ColumnIndex);
            String Gender = cursor.getString(genderColumnIndex);
            AGs.add(new AG(Yahr1, Yahr2, Gender));
        }
        AGname = (TextView) findViewById(R.id.textViewNameAg);
        SMname = (TextView) findViewById(R.id.textViewNameSM);
        if (AGs != null) AGname.setText(AGs.get(countAG).toString());
        cursor1 = db1.rawQuery("SELECT * FROM " + SportsmenContract.GuestEntry.TABLE_NAME +
                " WHERE yahr >= " + AGs.get(countAG).getYahr1() + " and yahr <= " + AGs.get(countAG).getYahr2() + " and gender == " + AGs.get(countAG).getGender() + " ORDER by rand", null);
        while (cursor1.moveToNext()) {
            String name = cursor1.getString(cursor1.getColumnIndex(SportsmenContract.GuestEntry.COLUMN_NAME));
            String id = cursor1.getString(cursor1.getColumnIndex(SportsmenContract.GuestEntry._ID));
            SMs.add(new SM1(id, name));
        }
        if (SMs.size() != 0) {
            SMname.setText(SMs.get(countSM).toString());
            cursor1 = db1.rawQuery("SELECT * FROM " + SportsmenContract.GuestEntry.TABLE_NAME +
                    " WHERE _ID = " + SMs.get(countSM).toID(), null);
            String sec="";
            while(cursor1.moveToNext())
                sec = cursor1.getString(cursor1.getColumnIndex(SportsmenContract.GuestEntry.TIME_RESULT));
            Time.setText(Tim.Times(sec));


        } else SMname.setText("Нет спортсменов");
    }

    public void SMmi(View view) {
        if(SMs.size()!=0) {
            ContentValues values = new ContentValues();
            cursor1 = db1.rawQuery("SELECT * FROM " + SportsmenContract.GuestEntry.TABLE_NAME +
                    " WHERE _ID = " + SMs.get(countSM).toID(), null);
            String timeStart="0";
            while(cursor1.moveToNext())
                timeStart = cursor1.getString(cursor1.getColumnIndex(SportsmenContract.GuestEntry.TIME_START));
            values.put(SportsmenContract.GuestEntry.TIME_FINISH, Tim.Secunds(Time.getText().toString()));
            values.put(SportsmenContract.GuestEntry.TIME_RESULT, (Integer.parseInt(String.valueOf(Tim.Secunds(Time.getText().toString())))- Integer.parseInt(timeStart)));
            db1.update(SportsmenContract.GuestEntry.TABLE_NAME,
                    values,
                    SportsmenContract.GuestEntry._ID + "= ?", new String[]{SMs.get(countSM).toID()});
        }

        countSM--;
        if (countSM < 0) countSM = SMs.size() - 1;
        if (SMs.size() != 0) {
            SMname.setText(SMs.get(countSM).toString());
            cursor1 = db1.rawQuery("SELECT * FROM " + SportsmenContract.GuestEntry.TABLE_NAME +
                    " WHERE _ID = " + SMs.get(countSM).toID(), null);
            String sec="";
            while(cursor1.moveToNext())
                sec = cursor1.getString(cursor1.getColumnIndex(SportsmenContract.GuestEntry.TIME_FINISH));
            Time.setText(Tim.Times(sec));


        } else SMname.setText("Нет спортсменов");

    }

    public void SMpl(View view) {

        if(SMs.size()!=0) {
            ContentValues values = new ContentValues();
            cursor1 = db1.rawQuery("SELECT * FROM " + SportsmenContract.GuestEntry.TABLE_NAME +
                    " WHERE _ID = " + SMs.get(countSM).toID(), null);
            String timeStart="0";
            while(cursor1.moveToNext())
                timeStart = cursor1.getString(cursor1.getColumnIndex(SportsmenContract.GuestEntry.TIME_START));
            values.put(SportsmenContract.GuestEntry.TIME_FINISH,Tim.Secunds(Time.getText().toString()));
            values.put(SportsmenContract.GuestEntry.TIME_RESULT, (Integer.parseInt(String.valueOf(Tim.Secunds(Time.getText().toString())))- Integer.parseInt(timeStart)));
            db1.update(SportsmenContract.GuestEntry.TABLE_NAME,
                    values,
                    SportsmenContract.GuestEntry._ID + "= ?", new String[]{SMs.get(countSM).toID()});
        }

        countSM++;
        if (countSM == SMs.size()) countSM = 0;
        if (SMs.size() != 0) {
            SMname.setText(SMs.get(countSM).toString());
            cursor1 = db1.rawQuery("SELECT * FROM " + SportsmenContract.GuestEntry.TABLE_NAME +
                    " WHERE _ID = " + SMs.get(countSM).toID(), null);
            String sec="";
            while(cursor1.moveToNext())
                sec = cursor1.getString(cursor1.getColumnIndex(SportsmenContract.GuestEntry.TIME_FINISH));
            Time.setText(Tim.Times(sec));


        } else SMname.setText("Нет спортсменов");

    }

    public void AGmi(View view) {
        if(SMs.size()!=0) {
            ContentValues values = new ContentValues();
            cursor1 = db1.rawQuery("SELECT * FROM " + SportsmenContract.GuestEntry.TABLE_NAME +
                    " WHERE _ID = " + SMs.get(countSM).toID(), null);
            String timeStart="0";
            while(cursor1.moveToNext())
                timeStart = cursor1.getString(cursor1.getColumnIndex(SportsmenContract.GuestEntry.TIME_START));
            values.put(SportsmenContract.GuestEntry.TIME_FINISH, Tim.Secunds(Time.getText().toString()));
            values.put(SportsmenContract.GuestEntry.TIME_RESULT, (Integer.parseInt(String.valueOf(Tim.Secunds(Time.getText().toString())))- Integer.parseInt(timeStart)));
            db1.update(SportsmenContract.GuestEntry.TABLE_NAME,
                    values,
                    SportsmenContract.GuestEntry._ID + "= ?", new String[]{SMs.get(countSM).toID()});
        }

        countSM=0;
        SMs = new ArrayList<>();
        countAG--;
        if (countAG < 0) countAG = AGs.size() - 1;
        if (AGs.size() != 0) {
            AGname.setText(AGs.get(countAG).toString());
        } else
        {
            AGname.setText("Нет групп");
        }
        cursor1 = db1.rawQuery("SELECT * FROM " + SportsmenContract.GuestEntry.TABLE_NAME +
                " WHERE yahr >= " + AGs.get(countAG).getYahr1() + " and yahr <= " + AGs.get(countAG).getYahr2() + " and gender == " + AGs.get(countAG).getGender() + " ORDER by rand", null);
        while (cursor1.moveToNext()) {
            String name = cursor1.getString(cursor1.getColumnIndex(SportsmenContract.GuestEntry.COLUMN_NAME));
            String id = cursor1.getString(cursor1.getColumnIndex(SportsmenContract.GuestEntry._ID));
            SMs.add(new SM1(id, name));
        }
            if (SMs.size() != 0) {
                SMname.setText(SMs.get(countSM).toString());
                cursor1 = db1.rawQuery("SELECT * FROM " + SportsmenContract.GuestEntry.TABLE_NAME +
                        " WHERE _ID = " + SMs.get(countSM).toID(), null);
                String sec="";
                while(cursor1.moveToNext())
                    sec = cursor1.getString(cursor1.getColumnIndex(SportsmenContract.GuestEntry.TIME_FINISH));
                Time.setText(Tim.Times(sec));


            } else SMname.setText("Нет спортсменов");

    }

    public void AGpl(View view) {
        if(SMs.size()!=0) {
            ContentValues values = new ContentValues();
            cursor1 = db1.rawQuery("SELECT * FROM " + SportsmenContract.GuestEntry.TABLE_NAME +
                    " WHERE _ID = " + SMs.get(countSM).toID(), null);
            String timeStart="0";
            while(cursor1.moveToNext())
                timeStart = cursor1.getString(cursor1.getColumnIndex(SportsmenContract.GuestEntry.TIME_START));
            values.put(SportsmenContract.GuestEntry.TIME_FINISH, Tim.Secunds(Time.getText().toString()));
            values.put(SportsmenContract.GuestEntry.TIME_RESULT, (Integer.parseInt(String.valueOf(Tim.Secunds(Time.getText().toString())))- Integer.parseInt(timeStart)));
            db1.update(SportsmenContract.GuestEntry.TABLE_NAME,
                    values,
                    SportsmenContract.GuestEntry._ID + "= ?", new String[]{SMs.get(countSM).toID()});
        }

        countSM=0;
        SMs = new ArrayList<>();
        countAG++;
        if (countAG == AGs.size()) countAG = 0;
        if (AGs.size() != 0) {
            AGname.setText(AGs.get(countAG).toString());
        } else {
            AGname.setText("Нет групп");
        }
        cursor1 = db1.rawQuery("SELECT * FROM " + SportsmenContract.GuestEntry.TABLE_NAME +
                " WHERE yahr >= " + AGs.get(countAG).getYahr1() + " and yahr <= " + AGs.get(countAG).getYahr2() + " and gender == " + AGs.get(countAG).getGender() + " ORDER by rand", null);
        while (cursor1.moveToNext()) {
            String name = cursor1.getString(cursor1.getColumnIndex(SportsmenContract.GuestEntry.COLUMN_NAME));
            String id = cursor1.getString(cursor1.getColumnIndex(SportsmenContract.GuestEntry._ID));
            SMs.add(new SM1(id, name));
        }
        if (SMs.size() != 0) {
            SMname.setText(SMs.get(countSM).toString());
            cursor1 = db1.rawQuery("SELECT * FROM " + SportsmenContract.GuestEntry.TABLE_NAME +
                    " WHERE _ID = " + SMs.get(countSM).toID(), null);
            String sec="";
            while(cursor1.moveToNext())
                sec = cursor1.getString(cursor1.getColumnIndex(SportsmenContract.GuestEntry.TIME_FINISH));
            Time.setText(Tim.Times(sec));


        } else SMname.setText("Нет спортсменов");
    }


}

