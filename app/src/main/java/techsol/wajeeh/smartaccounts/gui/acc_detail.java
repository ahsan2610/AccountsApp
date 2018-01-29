package techsol.wajeeh.smartaccounts.gui;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import techsol.wajeeh.smartaccounts.R;
import techsol.wajeeh.smartaccounts.database.acount;
import techsol.wajeeh.smartaccounts.othes.DividerItemDecoration;
import techsol.wajeeh.smartaccounts.models.class_transaction;

public class acc_detail extends AppCompatActivity {
    Context context;
    acount db;
    TextView tv_rec ;
    TextView  tv_pay ;
    TextView  tv_exp ;
    TextView  tv_cb ;



    String account_id;
    String pName;
    String current_ballence;

    List<class_transaction> transactionList = new ArrayList<class_transaction>();
    TransactionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc_detail);

        context =this;
        Intent i = getIntent();
        account_id = i.getStringExtra("account_id");
        pName = i.getStringExtra("pName");
        current_ballence = i.getStringExtra("current_ballence");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.setTitle("Account owner:  "+pName);



        Toast.makeText(this, ""+account_id, Toast.LENGTH_SHORT).show();


        RecyclerView recyclerView = findViewById(R.id.detailTransRecyc);
        LinearLayoutManager glm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(glm);
        recyclerView.setItemViewCacheSize(35);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter  = new TransactionAdapter (context);
        recyclerView.setAdapter(adapter);


        acount db = new acount(context);
        SQLiteDatabase database1 = db.getWritableDatabase();
        transactionList = db.Transaction_Detail_OF_Account(account_id);
        if (transactionList.isEmpty()){
        }
        else {
            adapter.notifyDataSetChanged();
        }

    }







    class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.BrandViewHolder> {

        private final LayoutInflater layoutInflater;

        TransactionAdapter(@NonNull Context context) {

            this.layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public BrandViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BrandViewHolder(layoutInflater.inflate(R.layout.list_item_transaction, parent, false));
        }

        @Override
        public void onBindViewHolder(BrandViewHolder holder, int position) {


            class_transaction i = transactionList.get(position);

            holder.description.setText(i.getDescription());
            holder.type.setText(i.getType());
            holder.amount.setText(i.getAmount());
            holder.new_b.setText(i.getNew_b());
            holder.old_b.setText(i.getOld_b());
            holder.date.setText(getDateFromMili(i.getDate()));



        }

        @Override
        public int getItemCount() {
            return transactionList.size();
        }

        private class_transaction getItem(int position) {
            return transactionList.get(position);
        }


        class BrandViewHolder extends RecyclerView.ViewHolder {

            TextView description      ;
            TextView type             ;
            TextView amount           ;
            TextView new_b            ;
            TextView old_b            ;
            TextView date             ;


            BrandViewHolder(View itemView) {
                super(itemView);

                description = itemView.findViewById(R.id.description);
                type = itemView.findViewById(R.id.type);
                amount = itemView.findViewById(R.id.amount);
                new_b = itemView.findViewById(R.id.new_b);
                old_b = itemView.findViewById(R.id.old_b);
                date = itemView.findViewById(R.id.date);




                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });


            }
        }
    }








    String getDateFromMili(String d){

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(Long.parseLong(d));
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        String strDate = sdf.format(c.getTime()).toString();
        return strDate;
    }



}
