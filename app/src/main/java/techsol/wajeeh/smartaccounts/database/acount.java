package techsol.wajeeh.smartaccounts.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import techsol.wajeeh.smartaccounts.models.class_accDetail;
import techsol.wajeeh.smartaccounts.models.class_account;

import static android.content.ContentValues.TAG;

/**
 * Created by wajeeh on 1/26/2018.
 */

public class acount extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "accountsManager";

    // Contacts table name
    private static final String TABLE_ACCOUNTS = "accounts";
    private static final String TABLE_Detail = "accountDetail";
    Context context;


    public acount(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        //   _id
        // name
        //  password

        String q = "CREATE TABLE " + TABLE_ACCOUNTS + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT , name TEXT NOT NULL , password TEXT NOT NULL);";


        //    _id
        //    account_id
        //      cb
        //      exp
        //      paying
        //       rec


        String q2 = "CREATE TABLE " + TABLE_Detail + "( _id INTEGER PRIMARY KEY AUTOINCREMENT, account_id INTEGER, cb VARCHAR(10), exp VARCHAR(10),paying VARCHAR(10) , rec VARCHAR(10) , FOREIGN KEY(account_id) REFERENCES accounts(_id) );";

        //****************************************************************************************************

        try {
            db.execSQL(q);
            Log.e(TAG, " account table create ");
            Toast.makeText(context, "contact table create ", Toast.LENGTH_LONG).show();


        } catch (SQLException e) {
            Log.e(TAG, "account table create error    " + e);
            Toast.makeText(context, "error ", Toast.LENGTH_LONG).show();
        }

//*******************************************************************************************************
        try {
            db.execSQL(q2);
            Log.e(TAG, " detail table create ");
            Toast.makeText(context, "detail table create ", Toast.LENGTH_LONG).show();


        } catch (SQLException e) {
            Log.e(TAG, "detail table create error    " + e);
            Toast.makeText(context, "error ", Toast.LENGTH_LONG).show();
        }
//********************************************************************************************************

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    // --------------------------------------------------------------------------------------------

    public Long addAccount(class_account a) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", a.getpName()); // Contact Name
        values.put("password", a.getPassword()); // Contact Phone
        // Inserting Row
        long id = db.insert(TABLE_ACCOUNTS, null, values);


        db.close(); // Closing database connection

        return id;
    }

    //-------------------------------------------------------------------------------------------------
    public void addAccountDetail(class_accDetail a) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("account_id", a.getAcc_id());
        values.put("cb", a.getCurrent_ballence());
        values.put("exp", a.getToday_expense());
        values.put("rec", a.getToday_recieving());
        values.put("paying", a.getToday_paying());

        try {
            long id = db.insert(TABLE_Detail, null, values);
            if (id < 0) {
                Toast.makeText(context, "error detail add", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(context, "ok detail add", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Log.e(TAG, "addAccountDetail: error " + e.getMessage());

        }


        db.close(); // Closing database connection
    }


//--------------------------------------------------------------------------------------------------------------

    public List<class_account> getAllaccounts() {
        List<class_account> contactList = new ArrayList<class_account>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ACCOUNTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String account_id = cursor.getString(0);
                String pName = cursor.getString(1);
                String password = cursor.getString(2);
                class_account acc = new class_account(account_id, pName, password);
                // Adding contact to list
                contactList.add(acc);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }


//-------------------------------------------------------------------------------------------------------------

    public Cursor accountOF(String n, String p) {
        SQLiteDatabase db = this.getWritableDatabase();
        //sqlite> SELECT * FROM COMPANY WHERE AGE >= 25 AND SALARY >= 65000;
        // prmry key aysy run hojati ha laikin   by name search ni hota uupar show detail dekho
        String q = "SELECT * FROM " + this.TABLE_ACCOUNTS + " WHERE name = '" + n + "' AND password = '" + p + "' ;";
        Cursor cursor = null;
        try {

            Log.e(TAG, q);
            cursor = db.rawQuery(q, null);

        } catch (Exception e) {
            Log.e(TAG, "get account error : error " + e.getMessage());
            Toast.makeText(context, "get account error" + e, Toast.LENGTH_SHORT).show();
        }

        if (cursor != null)
            cursor.moveToFirst();

        return cursor;
    }

//---------------------------------------------------------------------------------------------------------------




    public Cursor DetailOF(String accountID) {
        SQLiteDatabase db = this.getWritableDatabase();
        //sqlite> SELECT * FROM COMPANY WHERE AGE >= 25 AND SALARY >= 65000;
        // prmry key aysy run hojati ha laikin   by name search ni hota uupar show detail dekho
        String q = "SELECT * FROM " + this.TABLE_Detail + " WHERE _id = '"+accountID+"' ;";
        Cursor cursor = null;
        try {

            Log.e(TAG, q);
            cursor = db.rawQuery(q, null);

        } catch (Exception e) {
            Log.e(TAG, "get account Detail: error " + e.getMessage());
            Toast.makeText(context, "get account Detail error" + e, Toast.LENGTH_SHORT).show();
        }

        if (cursor != null)
            cursor.moveToFirst();

        return cursor;
    }









}