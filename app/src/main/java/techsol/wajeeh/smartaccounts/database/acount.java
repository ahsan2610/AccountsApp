package techsol.wajeeh.smartaccounts.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import techsol.wajeeh.smartaccounts.models.*;

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
    private static final String TABLE_TRANSACTION = "transac";

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

        String q = "CREATE TABLE " + TABLE_ACCOUNTS + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT , name TEXT NOT NULL , password TEXT NOT NULL , cb VARCHAR(10));";

        // current ballence , before ballence

        String q1 = "CREATE TABLE " + TABLE_TRANSACTION +"( _id INTEGER PRIMARY KEY AUTOINCREMENT , account_id INTEGER , description TEXT NOT NULL , type VARCHAR(10) , amount VARCHAR(10), nb VARCHAR(10), ob VARCHAR(10) , date VARCHAR(10), FOREIGN KEY(account_id) REFERENCES accounts(_id));";
   //     String q2 = "CREATE TABLE "+  TABLE_Detail+"( _id INTEGER PRIMARY KEY AUTOINCREMENT , account_id INTEGER , cb VARCHAR(10) , exp VARCHAR(10) , paying VARCHAR(10) , rec VARCHAR(10) , FOREIGN KEY(account_id) REFERENCES accounts(_id));";



        //  _id
        //  account_id
        //  description
        //  type
        //  amount
        //  nb
        //  ob
        //  date




        //****************************************************************************************************

        try {
            db.execSQL(q);
            Log.e(TAG, " account table create ");
            Toast.makeText(context, "contact table create ", Toast.LENGTH_LONG).show();


        } catch (SQLException e) {
            Log.e(TAG, "account table create error    " + e);
            Toast.makeText(context, "error ", Toast.LENGTH_LONG).show();
        }



        try {
            db.execSQL(q1);
            Log.e(TAG, " transaction table create ");
            Toast.makeText(context, "transaction table create ", Toast.LENGTH_LONG).show();


        } catch (SQLException e) {
            Log.e(TAG, "transaction table create error    " + e.getMessage());
            Toast.makeText(context, "transaction tabl error "+e, Toast.LENGTH_LONG).show();
        }

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
        values.put("cb", a.getCurrent_ballence());




        // Inserting Row
        long id = db.insert(TABLE_ACCOUNTS, null, values);


        db.close(); // Closing database connection

        return id;
    }



    //---------------------------------------------------------------------------------------------------------------


    public Long add_new_transacttion(class_transaction t) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("account_id", t.getAccount_id()); // Contact Phone
        values.put("description", t.getDescription());
        values.put("type", t.getType()); // Contact Name
        values.put("amount", t.getAmount()); // Contact Name
        values.put("nb", t.getNew_b()); // Contact Name
        values.put("ob", t.getOld_b()); // Contact Name
        values.put("date", t.getDate()); // Contact Name

        long id = db.insert(TABLE_TRANSACTION, null, values);

        db.close(); // Closing database connection

        return id;
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
                String account_id         = cursor.getString(0);
                String pName              = cursor.getString(1);
                String password           = cursor.getString(2);
                String current_ballence   = cursor.getString(2);
               class_account acc = new class_account(account_id, pName, password,current_ballence );
                // Adding contact to list
                contactList.add(acc);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }


//-------------------------------------------------------------------------------------------------------------

    public Cursor Check_account_of_user(String n, String p) {
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



    public Cursor Account_Detail_OF(String accountID) {
        SQLiteDatabase db = this.getWritableDatabase();
        //sqlite> SELECT * FROM COMPANY WHERE AGE >= 25 AND SALARY >= 65000;
        // prmry key aysy run hojati ha laikin   by name search ni hota uupar show detail dekho
        String q = "SELECT * FROM " + TABLE_ACCOUNTS + " WHERE _id = '"+accountID+"' ;";
        Cursor cursor = null;
        try {

            Log.e(TAG, q);
            cursor = db.rawQuery(q, null);

        } catch (Exception e) {
            Log.e(TAG, "get account Detail: error " + e);
            Toast.makeText(context, "get account Detail error" + e, Toast.LENGTH_SHORT).show();
        }

        if (cursor != null)
            cursor.moveToFirst();

        return cursor;
    }
    //----------------------------------------------------------------------------------------------------------------------------------



    public List<class_transaction>  Transaction_Detail_OF_Account(String accountID) {


        List<class_transaction> transactionList = new ArrayList<class_transaction>();

        SQLiteDatabase db = this.getWritableDatabase();
        //sqlite> SELECT * FROM COMPANY WHERE AGE >= 25 AND SALARY >= 65000;
        // prmry key aysy run hojati ha laikin   by name search ni hota uupar show detail dekho
        String q = "SELECT * FROM " + TABLE_TRANSACTION + " WHERE account_id = '"+accountID+"' ;";
        Cursor cursor = db.rawQuery(q, null);

        if (cursor.moveToFirst()) {
            do {

                String id                      = cursor.getString(0);
                String account_id              = cursor.getString(1);
                String description             = cursor.getString(2);
                String type                 = cursor.getString(3);
                String amount                 = cursor.getString(4);
                String new_b                 = cursor.getString(5);
                String old_b                 = cursor.getString(6);
                String date                 = cursor.getString(7);

                class_transaction tr = new class_transaction(id, account_id, description, type, amount, new_b, old_b, date);
                    transactionList.add(tr);

            } while (cursor.moveToNext());
        }

        // return contact list
        return transactionList;








    }
//--------------------------------------------------------------------------------------------------------------------



    public List<class_transaction>  Today_Transaction_Detail_OF_Account(String accountID) {


        List<class_transaction> transactionList = new ArrayList<class_transaction>();


        SQLiteDatabase db = this.getWritableDatabase();
        //sqlite> SELECT * FROM COMPANY WHERE AGE >= 25 AND SALARY >= 65000;
        // prmry key aysy run hojati ha laikin   by name search ni hota uupar show detail dekho
        String q = "SELECT * FROM " + TABLE_TRANSACTION + " WHERE account_id = '"+accountID+"' ;";
        Cursor cursor = db.rawQuery(q, null);

        if (cursor.moveToFirst()) {
            do {

                String id                      = cursor.getString(0);
                String account_id              = cursor.getString(1);
                String description             = cursor.getString(2);
                String type                 = cursor.getString(3);
                String amount                 = cursor.getString(4);
                String new_b                 = cursor.getString(5);
                String old_b                 = cursor.getString(6);
                String date                 = cursor.getString(7);


                Log.e("date compare" , getDateFromMili(date) +"   ?  "   +getDateFromComputer() );

                if (getDateFromMili(date).equals(getDateFromComputer())) {
                    Log.e("date compare" , "   add ok  " );
                    class_transaction tr = new class_transaction(id, account_id, description, type, amount, new_b, old_b, date);
                    transactionList.add(tr);
                }

            } while (cursor.moveToNext());
        }

        // return contact list
        return transactionList;
    }

 //-----------------------------------------------------------------------------------------------------------------------



   public long updateAcount_ballence(String Ac_id, String cb ){


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("cb", cb);

        long id =   db.update(TABLE_ACCOUNTS, contentValues,"_id = "+ Ac_id , null);

        db.close(); // Closing database connection

        return id;

    }






//********************************************************************************************************************

//********************************************************************************************************************
    String getDateFromMili(String d){

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(Long.parseLong(d));
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String strDate = sdf.format(c.getTime()).toString();
        return strDate;

    }
    public String getDateFromComputer() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String strDate = sdf.format(c.getTime()).toString();
        return strDate.toString();
    }

//********************************************************************************************************************

//********************************************************************************************************************


}