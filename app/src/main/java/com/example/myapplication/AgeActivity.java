package com.example.myapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AgeActivity  extends Activity {
    private AGDbHelper mDbHelper;

    private EditText Yahr1EditText;
    private EditText Yahr2EditText;
    RecyclerView AGRecyclerView;
    AGAdapter AG_Adapter;
    ArrayList<AG> AGs;
    long newRowId;

    public void onClick_add(View view) {
        insertGuest();
        AG_Adapter.notifyDataSetChanged();
    }

    private void insertGuest() {
        // Считываем данные из текстовых полей
        String yahr1 = Yahr1EditText.getText().toString().trim();
        String yahr2 = Yahr2EditText.getText().toString().trim();
        AGDbHelper mDbHelper = new AGDbHelper(this);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AGContract.GuestEntry.COLUMN_YAHR1, yahr1);
        values.put(AGContract.GuestEntry.COLUMN_YAHR2, yahr2);
        values.put(AGContract.GuestEntry.COLUMN_GENDER, 0);

        // Вставляем новый ряд в базу данных и запоминаем его идентификатор
        newRowId = db.insert(AGContract.GuestEntry.TABLE_NAME, null, values);
        values.put(AGContract.GuestEntry.COLUMN_GENDER, 1);
        newRowId = db.insert(AGContract.GuestEntry.TABLE_NAME, null, values);

        // Выводим сообщение в успешном случае или при ошибке
        if (newRowId == -1) {
            // Если ID  -1, значит произошла ошибка
            Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show();
        }
        AGs.add(new AG(yahr1, yahr2, "0"));
        AGs.add(new AG(yahr1, yahr2, "1"));
        Yahr1EditText.setText("");
        Yahr2EditText.setText("");

    }

    public void Remove(View view)
    {
        if(AGs.size()!=0)
        {
            newRowId=AGs.size()-1;
            AGs.remove(AGs.size()-1);
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            db.delete(AGContract.GuestEntry.TABLE_NAME,"_ID = "+ newRowId, null);
            newRowId--;

            AGs.remove(AGs.size()-1);
            db.delete(AGContract.GuestEntry.TABLE_NAME,"_ID = "+ newRowId, null);
            newRowId--;
        }
        AG_Adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AGs = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.age_group);
        AGRecyclerView = (RecyclerView) findViewById(R.id.AG_recycler_view);

        Yahr1EditText = (EditText) findViewById(R.id.Yahr1editText);
        Yahr2EditText = (EditText) findViewById(R.id.Yahr2editText);
        mDbHelper = new AGDbHelper(this);
        viewDatabaseInfo();

        AG_Adapter = new AGAdapter(AGs);
        AGRecyclerView.setLayoutManager(new GridLayoutManager(this,1));
        AGRecyclerView.setAdapter(AG_Adapter);

    }

    private void viewDatabaseInfo() {
        // Создадим и откроем для чтения базу данны
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Зададим условие для выборки - список столбцов
        String[] projection = {
                AGContract.GuestEntry.COLUMN_YAHR1,
                AGContract.GuestEntry.COLUMN_YAHR2,
                AGContract.GuestEntry.COLUMN_GENDER };

        // Делаем запрос
        Cursor cursor = db.query(
                AGContract.GuestEntry.TABLE_NAME,   // таблица
                projection,            // столбцы
                null,                  // столбцы для условия WHERE
                null,                  // значения для условия WHERE
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // порядок сортировки

        try {

            // Узнаем индекс каждого столбца
            int yahr1ColumnIndex = cursor.getColumnIndex(AGContract.GuestEntry.COLUMN_YAHR1);
            int yahr2ColumnIndex = cursor.getColumnIndex(AGContract.GuestEntry.COLUMN_YAHR2);
            int genderColumnIndex = cursor.getColumnIndex(AGContract.GuestEntry.COLUMN_GENDER);


            // Проходим через все ряды
            while (cursor.moveToNext()) {
                // Используем индекс для получения строки или числа
                String currentYahr1 = cursor.getString(yahr1ColumnIndex);
                String currentYahr2 = cursor.getString(yahr2ColumnIndex);
                String currentGender = cursor.getString(genderColumnIndex);
                // Выводим значения каждого столбца
                AGs.add(new AG(currentYahr1, currentYahr2, currentGender));
            }
        } finally {
            // Всегда закрываем курсор после чтения
            cursor.close();
        }
    }
}
