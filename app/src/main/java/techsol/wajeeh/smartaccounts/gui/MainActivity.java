package techsol.wajeeh.smartaccounts.gui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import techsol.wajeeh.smartaccounts.R;
import techsol.wajeeh.smartaccounts.database.acount;
import techsol.wajeeh.smartaccounts.othes.DividerItemDecoration;
import techsol.wajeeh.smartaccounts.models.class_account;
import techsol.wajeeh.smartaccounts.models.class_transaction;


public class MainActivity extends AppCompatActivity {


	Context context;
	acount db;
	AccountsAdapter adapter;
	TextView  tv_rec ;
	TextView  tv_pay ;
	TextView  tv_exp ;
	TextView  tv_cb ;





	String admin_id;
	String pName;
	String current_ballence;


	long todayExpense;
	long todayPay;
	long todayRecieve;

	List<class_transaction> transactionList = new ArrayList<class_transaction>();

	List<class_account> accList = new ArrayList<class_account>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_main);

		context = this;

		Intent i = getIntent();
		admin_id = i.getStringExtra("admin_id");
		db=new acount(this);


		Toast.makeText(this, ""+admin_id, Toast.LENGTH_SHORT).show();


		tv_rec  = findViewById(R.id.tv_rec);
		tv_pay  = findViewById(R.id.tv_pay);
		tv_exp  = findViewById(R.id.tv_exp);
		tv_cb  = findViewById(R.id.tv_cb);




		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		db = new acount(context);
		SQLiteDatabase database1 = db.getWritableDatabase();






		//class_accDetail c  = new class_accDetail( detail_id ,acc_id,current_ballence,today_expense,today_paying,today_recieving);


		Button btnNewAcc = findViewById(R.id.btnNewAcc);
		btnNewAcc.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {


				if (TextUtils.isEmpty(admin_id))
				{
					Toast.makeText(context, "account id empty", Toast.LENGTH_SHORT).show();
					return;
				}


				Intent i = new Intent(context,add_new_account.class);
				i.putExtra("admin_id",admin_id);

				startActivity(i);
			}
		});



		RecyclerView recyclerView = findViewById(R.id.recyclr);
		LinearLayoutManager glm = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(glm);
		recyclerView.setItemViewCacheSize(35);
		recyclerView.setDrawingCacheEnabled(true);
		recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
		recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
		adapter  = new AccountsAdapter(context);
		recyclerView.setAdapter(adapter);






		get_and_set_detail_of_id(admin_id);

	} // on create










	class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.BrandViewHolder> {

		private final LayoutInflater layoutInflater;

		AccountsAdapter(@NonNull Context context) {

			this.layoutInflater = LayoutInflater.from(context);
		}

		@Override
		public BrandViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			return new BrandViewHolder(layoutInflater.inflate(R.layout.list_item_acc, parent, false));
		}

		@Override
		public void onBindViewHolder(BrandViewHolder holder, int position) {


			class_account i = accList.get(position);

			holder.name.setText(i.getName());
			holder.id.setText(i.get_id());

		}

		@Override
		public int getItemCount() {
			return accList.size();
		}

		private class_account getItem(int position) {
			return accList.get(position);
		}


		class BrandViewHolder extends RecyclerView.ViewHolder {

			TextView name;
			TextView id;
            Button b ;

			BrandViewHolder(View itemView) {
				super(itemView);

				name = itemView.findViewById(R.id.textView_name);
				id = itemView.findViewById(R.id.textView_id);
                b= itemView.findViewById(R.id.btnDetail);
			b.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					int pos = getAdapterPosition();
					if (pos >= 0) { // might be NO_POSITION
						// onStickerSelected(getItem(pos));

						class_account a = getItem(pos);

						Intent i = new Intent(context,acc_detail.class);


						i.putExtra("account_id",    a.get_id());
						i.putExtra("admin_id",      a.getAdmin_id());
						i.putExtra("name",          a.getName());
						i.putExtra("phone",         a.getPhone());
						i.putExtra("pay_able",      a.getPay_able());
						i.putExtra("rec_able",      a.getRec_able());
						i.putExtra("current_ballence",current_ballence);


						startActivity(i);

						//  ((StickerSelectActivity)getActivity()).onStickerSelected(c.getd());;
					}
				}
			});





				itemView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {

					}
				});


			}
		}
	}


	@Override
	protected void onResume() {
		super.onResume();
		get_and_set_detail_of_id(admin_id);


	}


	//-----------------------------------------------------------------------------------------------


	void get_and_set_detail_of_id(String admin_id)

	{
		Cursor cursor = db.Admin_Detail_OF(admin_id);
		if (cursor != null )
			if(cursor.getCount()!= 0) {
				Toast.makeText(context, "found ", Toast.LENGTH_SHORT).show();

				pName = cursor.getString(1);
				current_ballence = cursor.getString(3);
				this.setTitle("Admin: "+pName);
				tv_cb.setText(current_ballence);

			}





		transactionList = db.Today_Transaction_Detail_OF_Admin(admin_id);
		for(class_transaction t :transactionList ){

			if (t.getType().equals("pay")) {
				todayPay      =     todayPay + Long.parseLong(t.getAmount());
			} else if (t.getType().equals("Expense")) {
				todayExpense  = todayExpense + Long.parseLong(t.getAmount());
			} else if (t.getType().equals("Recieve")){
				todayRecieve  = todayRecieve + Long.parseLong(t.getAmount());
			}

		}


		tv_rec.setText(""+todayRecieve);
		tv_exp.setText(""+todayExpense);
		tv_pay.setText(""+todayPay);




		accList = db.getAllaccounts_of_admin(admin_id);
		if (accList.isEmpty()){
		}
		else {

			adapter.notifyDataSetChanged();

		}








	}
	//------------------------------------------------------------------------------------------------

}
