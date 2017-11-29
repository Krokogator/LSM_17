package com.falcon.helloandroid.Weights;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.falcon.helloandroid.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class WeightActivity extends AppCompatActivity {

    private TextView listTextView;
    private EditText inputWeight, inputDate;
    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener inputDateListener;

    WeightDBhelper mDbHelper;

    public static final String dateFormat = "dd/MM/yy";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.US);
    private float mass_unit = 1.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);

        updateMassUnit();

        listTextView = (TextView) findViewById(R.id.weightList);
        inputDate = (EditText) findViewById(R.id.inputDate);
        inputWeight = (EditText) findViewById(R.id.inputWeight);
        mDbHelper = new WeightDBhelper(this);

        inputWeight.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(3,1)});
        inputDate.setText(simpleDateFormat.format(new Date().getTime()));

        myCalendar = Calendar.getInstance();
        inputDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabel();
            }
        };

        DatePickerClick(null);

        fillDataFromSQLite();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(updateMassUnit()){
            fillDataFromSQLite();
        }

    }

    private boolean updateMassUnit(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        // then you use
        float new_mass_unit = Float.valueOf(prefs.getString("unit_pref", "1"));
        if(new_mass_unit != mass_unit){
            Toast toast = Toast.makeText(this, "Wybrano: " + new_mass_unit, Toast.LENGTH_LONG);
            mass_unit = new_mass_unit;
            toast.show();
            return true;
        }
        Toast toast = Toast.makeText(this, "Wybrano: " + mass_unit, Toast.LENGTH_LONG);
        toast.show();
        return false;


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.weight_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.weightActivityMenuOption: {
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void DatePickerClick(View view){
        inputDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(WeightActivity.this, inputDateListener, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel(){
        inputDate.setText(simpleDateFormat.format(myCalendar.getTime()));
    }

    public void addWeight(View view){
        int weightInGrams = 0;
        weightInGrams = (int) (Float.valueOf(inputWeight.getText().toString())*1000);

        long dateInInteger = 0;
        try {
            Date date = simpleDateFormat.parse(inputDate.getText().toString());
            dateInInteger = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(WeightDB.COLUMN_NAME_WEIGHT, weightInGrams);
        values.put(WeightDB.COLUMN_NAME_DATE, dateInInteger);

        if(db.update(WeightDB.TABLE_NAME, values, "DATE = "+dateInInteger, null) == 0){
            db.insert(WeightDB.TABLE_NAME, null, values);
        }

        refresh(null);
    }

    public void deleteWeight(View view){

        long dateInInteger = 0;
        try {
            Date date = simpleDateFormat.parse(inputDate.getText().toString());
            dateInInteger = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        db.delete(WeightDB.TABLE_NAME, "DATE = "+dateInInteger, null);

        refresh(null);
    }

    public void refresh(View view){
        fillDataFromSQLite();
    }

    private void fillDataFromSQLite(){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {
                WeightDB._ID,
                WeightDB.COLUMN_NAME_WEIGHT,
                WeightDB.COLUMN_NAME_DATE
        };

        String sortOrder = WeightDB.COLUMN_NAME_DATE + " ASC";

        Cursor cursor = db.query(
                WeightDB.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        String text = "";
        while(cursor.moveToNext()){
            //long itemID = cursor.getLong(cursor.getColumnIndexOrThrow(WeightDB._ID));
            long date = cursor.getLong(cursor.getColumnIndexOrThrow(WeightDB.COLUMN_NAME_DATE));
            long weight = cursor.getLong(cursor.getColumnIndexOrThrow(WeightDB.COLUMN_NAME_WEIGHT));
            text += "Date : " + simpleDateFormat.format(date) + " " +
                    "Weight: " + String.format("%.1f", ((float) weight*mass_unit)/1000f) + "kg\n";
        }

        listTextView.setText(text);

    }
}
