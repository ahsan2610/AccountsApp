package techsol.wajeeh.smartaccounts.gui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import techsol.wajeeh.smartaccounts.database.acount;
import techsol.wajeeh.smartaccounts.models.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import techsol.wajeeh.smartaccounts.R;

public class newTransation extends AppCompatActivity {


    String description;
    String type ="";
    String amount;
    String new_b;
    String old_b;
     String date;


    String account_id;
    String  admin_id;

    String pName;
    String current_ballence;

    EditText ETamount;
    EditText ETdescription;

    Button btndone;


    acount db;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_transation);

        context = this;
        db = new acount(this);

        Intent i = getIntent();

        account_id = i.getStringExtra("account_id");
        pName = i.getStringExtra("pName");
        current_ballence = i.getStringExtra("current_ballence");


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.setTitle("Make a Transaction");


        ETdescription = findViewById(R.id.description);
       ETamount = findViewById(R.id.amount);









//-----------------------------------------------------------------------------------------------------------------

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("pay");
        categories.add("Recieve");
        categories.add("Expense");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                type = "";
            }
        });


//------------------------------------------------------------------------------------------------------------------

        btndone = findViewById(R.id.doo);
        btndone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                description = ETdescription.getText().toString();
                amount = ETamount.getText().toString();

                Long tempAmount = Long.valueOf(amount);
                Long tempOldBallnece = Long.valueOf(current_ballence);
                Long tempNewBallence;






                if (type.equals("pay")) {
                    tempNewBallence = tempOldBallnece - tempAmount;
                } else if (type.equals("Expense")) {
                    tempNewBallence = tempOldBallnece - tempAmount;
                } else if (type.equals("Recieve")){
                    tempNewBallence = tempOldBallnece + tempAmount;
                }



                else
                    tempNewBallence = tempOldBallnece;






                Calendar c = Calendar.getInstance();
                Long d = ((Long) c.getTimeInMillis());


              ///  String description;
            //    String type;
              //  String amount;
              //  String new_b;
              //  String old_b;
              //  String date;

                if (TextUtils.isEmpty(account_id))
                {
                    Toast.makeText(context, "account id empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(description))
                {
                    Toast.makeText(context, "description  empty", Toast.LENGTH_SHORT).show();
                    return;
                } if (TextUtils.isEmpty(type))
                {
                    Toast.makeText(context, "type  empty", Toast.LENGTH_SHORT).show();
                    return;
                } if (TextUtils.isEmpty(amount))
                {
                    Toast.makeText(context, "amount  empty", Toast.LENGTH_SHORT).show();
                    return;
                } if (TextUtils.isEmpty(String.valueOf(tempNewBallence)))
                {
                    Toast.makeText(context, "new  balllence empty", Toast.LENGTH_SHORT).show();
                    return;
                } if (TextUtils.isEmpty(String.valueOf(tempOldBallnece)))
                {
                    Toast.makeText(context, " old ballence empty", Toast.LENGTH_SHORT).show();
                    return;
                } if (TextUtils.isEmpty(String.valueOf(d)))
                {
                    Toast.makeText(context, "date empty", Toast.LENGTH_SHORT).show();
                    return;
                }




                class_transaction t = new class_transaction("1",account_id, admin_id, description, type, amount, String.valueOf(tempNewBallence),String.valueOf(tempOldBallnece),String.valueOf(d));
                Toast.makeText(newTransation.this, "going to add", Toast.LENGTH_SHORT).show();
               Long id =  db.add_new_transacttion(t);
                if (id < 0)
                    Toast.makeText(context, "error transaction add", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(context, "ok add", Toast.LENGTH_SHORT).show();



                }


                }
        });
//-----------------------------------------------------------------------------------------------











    }






}

