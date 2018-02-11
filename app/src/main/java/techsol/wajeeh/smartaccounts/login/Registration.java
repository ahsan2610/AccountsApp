package techsol.wajeeh.smartaccounts.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import techsol.wajeeh.smartaccounts.R;
import techsol.wajeeh.smartaccounts.database.acount;
import techsol.wajeeh.smartaccounts.models.*;

public class Registration extends AppCompatActivity {

    TextView tvAreadyhave;
    private Context context;
    Button registerBtn;
    private ProgressDialog progressDialog;

    EditText Name,password,openB;
    String n,p,b;
    acount db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        context = this;
        tvAreadyhave = findViewById(R.id.tvAreadyhave);
        tvAreadyhave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context , Login.class);
                startActivity(i);
            }
        });

        db = new acount(this);
        SQLiteDatabase database1 = db.getWritableDatabase();



        Name     = findViewById(R.id.Name);
        password = findViewById(R.id.password);
        openB    = findViewById(R.id.openB);

        registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              n=   Name.getText().toString();
              p=   password.getText().toString();
              b=  openB.getText().toString();

             Long id =  db.addAdmin(new admin("1",n, p,b));
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
