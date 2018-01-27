package techsol.wajeeh.smartaccounts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import techsol.wajeeh.smartaccounts.database.acount;
import techsol.wajeeh.smartaccounts.login.Login;
import techsol.wajeeh.smartaccounts.login.Registration;
import techsol.wajeeh.smartaccounts.models.DividerItemDecoration;
import techsol.wajeeh.smartaccounts.models.class_accDetail;
import techsol.wajeeh.smartaccounts.models.class_account;



public class MainActivity extends Activity {


	Context context;
	acount db;
	AccountsAdapter adapter;
	TextView  tv_rec ;
	TextView  tv_pay ;
	TextView  tv_exp ;
	TextView  tv_cb ;
	List<class_account> accList = new ArrayList<class_account>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_main);

		context = this;

       Intent i = getIntent();
       String id = i.getStringExtra("AccID");
		Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();


		tv_rec  = findViewById(R.id.tv_rec);
		tv_pay  = findViewById(R.id.tv_pay);
		tv_exp  = findViewById(R.id.tv_exp);
		tv_cb  = findViewById(R.id.tv_cb);


		db=new acount(this);
            Cursor cursor = db.DetailOF(id);
		if (cursor != null )
			if(cursor.getCount()!= 0)
			{
				Toast.makeText(context, "found ", Toast.LENGTH_SHORT).show();



				String detail_id              = cursor.getString(0);
				String acc_id                 = cursor.getString(1);
				String current_ballence       = cursor.getString(2);
				String today_expense          = cursor.getString(3);
				String today_paying           = cursor.getString(4);
				String today_recieving        = cursor.getString(5);


				tv_rec.setText("today_recieving" +  today_recieving);
				tv_exp.setText("today_expense" +  today_expense);
				tv_cb.setText("current_ballence" +  current_ballence);
				tv_pay.setText("today_paying" +  today_paying);



				//class_accDetail c  = new class_accDetail( detail_id ,acc_id,current_ballence,today_expense,today_paying,today_recieving);

			}else
				Toast.makeText(context, "0 record ", Toast.LENGTH_SHORT).show();




		RecyclerView recyclerView = findViewById(R.id.recyclr);
		LinearLayoutManager glm = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(glm);
		recyclerView.setItemViewCacheSize(35);
		recyclerView.setDrawingCacheEnabled(true);
		recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
		recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
		 adapter  = new AccountsAdapter(context);
		recyclerView.setAdapter(adapter);


		acount db = new acount(context);
		SQLiteDatabase database1 = db.getWritableDatabase();
		accList = db.getAllaccounts();
		if (accList.isEmpty()){
		}
		else {

			adapter.notifyDataSetChanged();

		}


	}










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

			holder.name.setText(i.getpName());
			holder.id.setText(i.getAccount_id());

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

						class_account c = getItem(pos);
						Toast.makeText(MainActivity.this, ""+c.getAccount_id(), Toast.LENGTH_SHORT).show();

						//  ((StickerSelectActivity)getActivity()).onStickerSelected(c.getd());;
					}
				}
			});





										itemView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						int pos = getAdapterPosition();
						if (pos >= 0) { // might be NO_POSITION
							// onStickerSelected(getItem(pos));

							class_account c = getItem(pos);

							//  ((StickerSelectActivity)getActivity()).onStickerSelected(c.getd());;
						}
					}
				});


			}
		}
	}








}
