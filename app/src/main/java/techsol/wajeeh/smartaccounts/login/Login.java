package techsol.wajeeh.smartaccounts.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import techsol.wajeeh.smartaccounts.MainActivity;
import techsol.wajeeh.smartaccounts.R;
import techsol.wajeeh.smartaccounts.database.acount;
import techsol.wajeeh.smartaccounts.models.class_accDetail;
import techsol.wajeeh.smartaccounts.models.class_account;

public class Login extends AppCompatActivity {

   TextView tvRegister;
    private Context context;
    Button registerBtn;
    private ProgressDialog progressDialog;
Button loginBtn;
    EditText Name,password;
    String n,p;
    acount db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

     context = this;
        db = new acount(this);

    tvRegister= findViewById(R.id.tvRegister);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context , Registration.class);
                startActivity(i);

            }
        }) ;

        Name     = findViewById(R.id.Name);
        password = findViewById(R.id.password);


    loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            n=   Name.getText().toString();
            p=   password.getText().toString();

            Cursor cursor =  db.accountOF( n, p);
            if (cursor != null )
                if(cursor.getCount()!= 0)
            {
                String account_id = cursor.getString(0);
                String pName = cursor.getString(1);
                String password = cursor.getString(2);

                Intent i = new Intent(context,MainActivity.class);
                i.putExtra("AccID",account_id);
                startActivity(i);

            }else
                Toast.makeText(context, "0 record ", Toast.LENGTH_SHORT).show();


        }
    });
    }








}
