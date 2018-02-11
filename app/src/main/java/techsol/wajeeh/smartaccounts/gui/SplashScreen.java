package techsol.wajeeh.smartaccounts.gui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import techsol.wajeeh.smartaccounts.R;
import techsol.wajeeh.smartaccounts.database.acount;
import techsol.wajeeh.smartaccounts.login.Login;
import techsol.wajeeh.smartaccounts.login.Registration;
import techsol.wajeeh.smartaccounts.models.admin;
import techsol.wajeeh.smartaccounts.models.class_account;

public class SplashScreen extends AppCompatActivity {

	// Splash screen timer
	private static int SPLASH_TIME_OUT = 3000;
	private String userid;
	List<admin> accList = new ArrayList<admin>();
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		context = this;

		new Handler().postDelayed(new Runnable() {

			/*
			 * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

			@Override
			public void run() {
				// This method will be executed once the timer is over
				// Start your app main activity
				acount db = new acount(context);
				SQLiteDatabase database1 = db.getWritableDatabase();
				accList = db.getAlladmis();
				if (accList.isEmpty()){

					Intent i = new Intent(context , Registration.class);
					startActivity(i);
				}
				else {

					Intent i = new Intent(context , Login.class);
					startActivity(i);

				}

				// close this activity
				finish();
			}
		}, SPLASH_TIME_OUT);
	}

}
