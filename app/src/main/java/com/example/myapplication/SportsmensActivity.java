package com.example.myapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.provider.Telephony;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.data.SMDbHelper;
import com.example.myapplication.data.SportsmenContract;

import java.util.ArrayList;
import java.util.Random;


public class SportsmensActivity  extends Activity {
    private SMDbHelper mDbHelper;

    private EditText NameEditText;
    private EditText YahrEditText;
    private Spinner SexSpinner;
    private int Sex = 2;
    RecyclerView SMRecyclerView;
    SMAdapter sm_Adapter;
    ArrayList<SM> SMs;
    long newRowId;

    public void onClick_add(View view) {
        insertGuest();
        sm_Adapter.notifyDataSetChanged();
    }

    private void insertGuest() {
        // Считываем данные из текстовых полей
        String name = NameEditText.getText().toString().trim();
        String yahr = YahrEditText.getText().toString().trim();
        mDbHelper = new SMDbHelper(this);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Random rand = new Random();
        ContentValues values = new ContentValues();
        values.put(SportsmenContract.GuestEntry.COLUMN_NAME, name);
        values.put(SportsmenContract.GuestEntry.COLUMN_YAHR, yahr);
        values.put(SportsmenContract.GuestEntry.COLUMN_GENDER, Sex);
        values.put(SportsmenContract.GuestEntry.TIME_START, 0);
        values.put(SportsmenContract.GuestEntry.TIME_FINISH, 0);
        values.put(SportsmenContract.GuestEntry.TIME_RESULT, 0);
        values.put(SportsmenContract.GuestEntry.RAND, rand.nextInt(1000));
        // Вставляем новый ряд в базу данных и запоминаем его идентификатор
        newRowId = db.insert(SportsmenContract.GuestEntry.TABLE_NAME, null, values);

        // Выводим сообщение в успешном случае или при ошибке
        if (newRowId == -1) {
            // Если ID  -1, значит произошла ошибка
            Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show();
        }

        SMs.add(new SM(name, yahr, String.valueOf(Sex)));
        NameEditText.setText("");
        YahrEditText.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SMs = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sportsmens);
        SMRecyclerView = (RecyclerView) findViewById(R.id.resycler);

        NameEditText = (EditText) findViewById(R.id.editTextName);
        YahrEditText = (EditText) findViewById(R.id.editTextYahr);
        SexSpinner = (Spinner) findViewById(R.id.spinner);
        mDbHelper = new SMDbHelper(this);
        setupSpinner();
        viewDatabaseInfo();

        sm_Adapter = new SMAdapter(SMs);
        SMRecyclerView.setLayoutManager(new GridLayoutManager(this,1));
        SMRecyclerView.setAdapter(sm_Adapter);

    }

    private void setupSpinner() {
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        SexSpinner.setAdapter(genderSpinnerAdapter);
        SexSpinner.setSelection(2);

        SexSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (selection.equals(getString(R.string.gender_female))) {
                    Sex = SportsmenContract.GuestEntry.GENDER_FEMALE; // Кошка
                } else if (selection.equals(getString(R.string.gender_male))) {
                    Sex = SportsmenContract.GuestEntry.GENDER_MALE; // Кот
                } else {
                    Sex = SportsmenContract.GuestEntry.GENDER_UNKNOWN; // Не определен
                }
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.gender_female))) {
                        Sex = 0; // Кошка
                    } else if (selection.equals(getString(R.string.gender_male))) {
                        Sex = 1; // Кот
                    } else {
                        Sex = 2; // Не определен
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Sex = 2; // Unknown
            }

        });
    }

    public void Delete(View view)
    {
        newRowId=SMs.size()-1;
        if(SMs.size()!=0)
        {
            SMs.remove(SMs.size()-1);
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            db.delete(SportsmenContract.GuestEntry.TABLE_NAME,"_ID = " + newRowId, null);
            newRowId--;
        }
        sm_Adapter.notifyDataSetChanged();
    }

    protected void onStart() {
        super.onStart();
    }

    private void viewDatabaseInfo() {
        // Создадим и откроем для чтения базу данны
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Зададим условие для выборки - список столбцов
        String[] projection = {
                SportsmenContract.GuestEntry._ID,
                SportsmenContract.GuestEntry.COLUMN_NAME,
                SportsmenContract.GuestEntry.COLUMN_GENDER,
                SportsmenContract.GuestEntry.COLUMN_YAHR };

        // Делаем запрос
        Cursor cursor = db.query(
                SportsmenContract.GuestEntry.TABLE_NAME,   // таблица
                projection,            // столбцы
                null,                  // столбцы для условия WHERE
                null,                  // значения для условия WHERE
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // порядок сортировки

        try {

            // Узнаем индекс каждого столбца
            int idColumnIndex = cursor.getColumnIndex(SportsmenContract.GuestEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(SportsmenContract.GuestEntry.COLUMN_NAME);
            int yahrColumnIndex = cursor.getColumnIndex(SportsmenContract.GuestEntry.COLUMN_YAHR);
            int genderColumnIndex = cursor.getColumnIndex(SportsmenContract.GuestEntry.COLUMN_GENDER);


            // Проходим через все ряды
            while (cursor.moveToNext()) {
                // Используем индекс для получения строки или числа
                String currentName = cursor.getString(nameColumnIndex);
                String currentYahr = cursor.getString(yahrColumnIndex);
                String currentGender = cursor.getString(genderColumnIndex);
                // Выводим значения каждого столбца
                SMs.add(new SM(currentName, currentYahr, currentGender));
            }
        } finally {
            // Всегда закрываем курсор после чтения
            cursor.close();
        }
    }
}
