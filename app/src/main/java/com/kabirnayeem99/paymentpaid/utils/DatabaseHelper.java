package com.kabirnayeem99.paymentpaid.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.kabirnayeem99.paymentpaid.models.Work;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "payment_paid_db";
    public static final String DB_WORK_TABLE = "works_db_table";
    public static final int DB_VERSION = 2;
    private static final String TAG = "DatabaseHelper";
    private static final String KEY_ID = "id";
    private static final String KEY_STUDENT_NAME = "student_name";
    private static final String KEY_WORK_NAME = "work_description";
    private static final String KEY_PAYMENT = "payment";
    private static final String KEY_DATE = "date";


    public DatabaseHelper(Context context) {
        super(context, DatabaseHelper.DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // CREATE TABLE DB_WORK_TABLE(id INT PRIMARY KEY, student TEXT, work_name
        // TEXT, INT payment, date TEXT);

        String dbCreateQuery = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT, " +
                        "%s TEXT, %s INT , %s TEXT)",
                DB_WORK_TABLE, KEY_ID, KEY_STUDENT_NAME, KEY_WORK_NAME, KEY_PAYMENT, KEY_DATE);

        db.execSQL(dbCreateQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion >= newVersion) {
            return;
        }

        String dbUpgradeQuery = String.format("DROP TABLE IF EXISTS %s", DB_WORK_TABLE);
        db.execSQL(dbUpgradeQuery);
        onCreate(db);
    }


    public long addToWork(Work work) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        if (work.getDate() != null && work.getPayment() > 100) {
            contentValues.put(KEY_WORK_NAME, work.getName());
            contentValues.put(KEY_DATE, work.getDate());
            contentValues.put(KEY_PAYMENT, work.getPayment());
            contentValues.put(KEY_STUDENT_NAME, work.getStudentName());

            return db.insert(DB_WORK_TABLE, null, contentValues);
        }

        return -1;

    }

    public List<Work> getWorkList() {
        List<Work> workList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String selectNoteQuery = String.format("SELECT * FROM %s", DB_WORK_TABLE);
        try (Cursor cursor = db.rawQuery(selectNoteQuery, null)) {
            // creates a work array list
            if (cursor.moveToFirst()) {
                do {
                    Work work = new Work();

                    work.setStudentName(cursor.getString(1));
                    work.setName(cursor.getString(2));
                    work.setPayment(cursor.getInt(3));
                    work.setDate(cursor.getString(4));

                    workList.add(work);
                } while (cursor.moveToNext());
            }
        }

        return workList;
    }

    public List<Work> getWorkListSortedByMonth(Integer month) {
        List<Work> workList = this.getWorkList();
        List<Work> workListByMonth = new ArrayList<>();


        for (int i = 0; i < workList.size(); i++) {

            if (Utils.checkDate(workList.get(i).getDate(), month, 2021)) {
                workListByMonth.add(workList.get(i));
            }
        }


        return workListByMonth;
    }

    public String getTotalPayment() {
        int totalPayment = 0;
        SQLiteDatabase db = getReadableDatabase();
        String selectNoteQuery = String.format("SELECT SUM(%s) FROM %s", KEY_PAYMENT, DB_WORK_TABLE);

        try (Cursor cursor = db.rawQuery(selectNoteQuery, null)) {
            if (cursor.moveToFirst()) {
                totalPayment = cursor.getInt(0);
            }
        }
        return String.format("%s", totalPayment);
    }

    public Map<Integer, Integer> getTotalPaymentByMonth() {

        Map<Integer, Integer> paymentListByMonth = new HashMap<Integer, Integer>();

        for (int i = 1; i <= 12; i++) {
            int monthlyPayment = 0;
            for (int j = 0; j < getWorkListSortedByMonth(i).size(); j++) {
                monthlyPayment = monthlyPayment + getWorkListSortedByMonth(i).get(j).getPayment();
                Log.d(TAG, "getTotalPaymentByMonth: " + getWorkListSortedByMonth(i).get(j).getPayment());
                Log.d(TAG, "getTotalPaymentByMonth: " + monthlyPayment);
            }
            paymentListByMonth.put(i, monthlyPayment);

            Log.d(TAG, "getTotalPaymentByMonth: " + i);
            Log.d(TAG, "getTotalPaymentByMonth: " + paymentListByMonth);
        }
        return paymentListByMonth;
    }

}