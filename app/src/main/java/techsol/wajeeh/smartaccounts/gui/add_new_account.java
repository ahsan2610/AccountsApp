package techsol.wajeeh.smartaccounts.gui;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import techsol.wajeeh.smartaccounts.R;
import techsol.wajeeh.smartaccounts.database.acount;
import techsol.wajeeh.smartaccounts.models.admin;
import techsol.wajeeh.smartaccounts.models.class_account;

public class add_new_account extends AppCompatActivity {


    Context context;
    acount db;
    String n,ph,r,p;
    EditText ETName,ETphone,ETrecievable,ETpayable;
    Button adAccBtn;
    private String admin_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_account);


        context = this;
        db = new acount(this);
        SQLiteDatabase database1 = db.getWritableDatabase();

        Intent i = getIntent();
        admin_id = i.getStringExtra("admin_id");
        db=new acount(this);

        ETName = findViewById(R.id.ETName);
        ETphone = findViewById(R.id.ETphone);
        ETrecievable = findViewById(R.id.ETrecievable);
        ETpayable = findViewById(R.id.ETpayable);

        ETrecievable.setText("0");
        ETpayable.setText("0");

        adAccBtn = findViewById(R.id.adAccBtn);
        adAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                n  =   ETName.getText().toString();
                ph =   ETphone.getText().toString();
                r  =   ETrecievable.getText().toString();
                p  =   ETpayable.getText().toString();



                if (TextUtils.isEmpty(admin_id))
                {
                    Toast.makeText(context, "account id empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(ph))
                {
                    Toast.makeText(context, "phone  empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(r))
                {
                    Toast.makeText(context, "recievable  empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(p))
                {
                    Toast.makeText(context, "payable  empty", Toast.LENGTH_SHORT).show();
                    return;
                }







                Long id =  db.addAccount(new class_account("1",admin_id, n, ph,r,p));
                if (id < 0)
                    Toast.makeText(context, "error acount add", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(context, "ok acount add", Toast.LENGTH_SHORT).show();

                    finish();

                }



            }
        });




    }
}
