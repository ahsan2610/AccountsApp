package techsol.wajeeh.smartaccounts.gui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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



String current_ballence;
//  --------------------------------------------------------------
    //  for acount detail

    String  account_id   ;
    String  admin_id     ;
    String  name         ;
    String  phone        ;
    String  pay_able     ;
    String  rec_able     ;
//----------------------------------------------------------------
    TextView tv_name, tv_id,tv_payable,tv_recieveable,tv_phone,tv_cashFlow;




    RecyclerView recyclerView;

    long tempPayable;
    long tempRecievable;
    long tempCurrentb;

    List<class_transaction> transactionList = new ArrayList<class_transaction>();
    TransactionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc_detail);
        context =this;
        db = new acount(context);

        Intent i = getIntent();
        account_id      = i.getStringExtra("account_id");
        admin_id        = i.getStringExtra("admin_id");
        name            = i.getStringExtra("name");
        phone           = i.getStringExtra("phone");
        pay_able        = i.getStringExtra("pay_able");
        rec_able        = i.getStringExtra("rec_able");
        current_ballence = i.getStringExtra("current_ballence");


         tempPayable    = Long.valueOf(pay_able);
         tempRecievable = Long.valueOf(rec_able);
         tempCurrentb = Long.valueOf(current_ballence);





        tv_name        = findViewById(R.id.tv_name);
        tv_phone       = findViewById(R.id.tv_phone);
        tv_id          = findViewById(R.id.tv_id);
        tv_payable     = findViewById(R.id.tv_payable);
        tv_recieveable = findViewById(R.id.tv_recieveable);
        tv_cashFlow  = findViewById(R.id.tv_cashFlow);


        tv_name       .setText(""+ name);
        tv_phone      .setText(""+ phone);
        tv_id         .setText(""+account_id);




        tv_payable    .setText(""+pay_able);
        tv_recieveable.setText(""+rec_able);



        setvalues(tempPayable,tempRecievable);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        Toast.makeText(this, ""+account_id, Toast.LENGTH_SHORT).show();


         recyclerView = findViewById(R.id.detailTransRecyc);
        LinearLayoutManager glm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(glm);
        recyclerView.setItemViewCacheSize(35);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter  = new TransactionAdapter (context);
        recyclerView.setAdapter(adapter);


        Button btnTr = findViewById(R.id.btnTr);
        btnTr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              callLoginDialog();


            }
        });




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

            private   TextView description      ;
            private   TextView type             ;
            private  TextView amount           ;
            private  TextView new_b            ;
            private  TextView old_b            ;
            private TextView date             ;


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


    @Override
    protected void onResume() {
        super.onResume();

        transactionList = db.Transaction_Detail_OF_Account(account_id);
        if (transactionList.isEmpty()){
        }
        else {
            adapter.notifyDataSetChanged();
        }

    }








    private void callLoginDialog()
    {


        final String[] description = new String[1];
        final String[] type = new String[1];
        final String[] amount = new String[1];
        String new_b;
        String old_b;
        String date;







        final Dialog myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.transaction_dailouge);
        myDialog.setCancelable(false);
        Button login = (Button) myDialog.findViewById(R.id.btnClose);


         RadioGroup radioGroup;
         RadioButton radioSexButton;
         radioGroup=(RadioGroup)myDialog.findViewById(R.id.radioGroup);
         radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(RadioGroup group, int checkedId) {

                 if(checkedId == R.id.idPay) {
                     type[0] = "pay";
                }else if (checkedId == R.id.idRecieve){
                     type[0] = "Recieve";

                 }
             }
         });


         final EditText Etamount = (EditText) myDialog.findViewById(R.id.ETamount_ForTransaction);
         final EditText Etdescrip = (EditText) myDialog.findViewById(R.id.ETdescription_ForTransaction);

        myDialog.show();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                description[0] = Etdescrip.getText().toString();
                amount[0] = Etamount.getText().toString();

                long tempAmount;
                if (amount[0].equals(""))
                    tempAmount = 0;
                      else
                          tempAmount = Long.valueOf(amount[0]);

                long tempPAY_REC_amount_for_upt = 0;









                if (type[0].equals("pay")) {

                    if (tempAmount > tempPayable)
                    {
                        Toast.makeText(context, " amount exceed the limit of payable ", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    if (tempPayable> tempCurrentb)
                    {
                        Toast.makeText(context, "Not enough ballence ", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (tempPayable<=0)
                    {
                        Toast.makeText(context, "payable ballncee is 0 ", Toast.LENGTH_SHORT).show();
                        return;
                    }





                     tempPayable = tempPayable - tempAmount ;

                     tempPAY_REC_amount_for_upt = tempPayable;

                     tempCurrentb = tempCurrentb - tempAmount;

                } else if (type[0].equals("Recieve")) {

                    if (tempAmount > tempCurrentb)
                    {
                        Toast.makeText(context, " amount exceed the limit of recieveable ", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (tempRecievable<=0)
                    {
                        Toast.makeText(context, "recable ballncee is 0 ", Toast.LENGTH_SHORT).show();
                        return;
                    }


                     tempRecievable = tempRecievable - tempAmount;

                    tempPAY_REC_amount_for_upt = tempRecievable;

                    tempCurrentb = tempCurrentb + tempAmount;

                }



                Calendar c = Calendar.getInstance();
                Long d = ((Long) c.getTimeInMillis());

                if (TextUtils.isEmpty(account_id))
                {
                    Toast.makeText(context, "account id empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(description[0]))
                {
                    Toast.makeText(context, "description  empty", Toast.LENGTH_SHORT).show();
                    return;
                } if (TextUtils.isEmpty(type[0]))
            {
                Toast.makeText(context, "type  empty", Toast.LENGTH_SHORT).show();
                return;
            } if (TextUtils.isEmpty(amount[0]))
            {
                Toast.makeText(context, "amount  empty", Toast.LENGTH_SHORT).show();
                return;
            } if (TextUtils.isEmpty(String.valueOf(tempCurrentb)))
            {
                Toast.makeText(context, "new  balllence empty", Toast.LENGTH_SHORT).show();
                return;
            } if (TextUtils.isEmpty(String.valueOf(current_ballence)))
            {
                Toast.makeText(context, " old ballence empty", Toast.LENGTH_SHORT).show();
                return;
            } if (TextUtils.isEmpty(String.valueOf(d)))
            {
                Toast.makeText(context, "date empty", Toast.LENGTH_SHORT).show();
                return;
            }







                class_transaction t = new class_transaction("1",account_id, admin_id, description[0], type[0], amount[0], String.valueOf(tempCurrentb),String.valueOf(current_ballence),String.valueOf(d));
                Toast.makeText(context, "going to add", Toast.LENGTH_SHORT).show();


                Log.e("values",   "Curent blncr"+ current_ballence +"    new baallnce "+ tempCurrentb+"   recable"+tempRecievable+ "    payable  "+tempPayable);


                   Long id =  db.add_new_transacttion(t);
              if (id < 0)
                   Toast.makeText(context, "error transaction add", Toast.LENGTH_SHORT).show();
               else {
                  Toast.makeText(context, "ok add", Toast.LENGTH_SHORT).show();

                  myDialog.dismiss();


                  //------------------------------------------------------------------------------------------------------------------------------
                  // update in account table the new pay able or recieveble amount
                  long ok = db.updateAcount_payaable_recable(account_id, tempPAY_REC_amount_for_upt + "", type[0]);
                  if (ok > 0) {
                      if (type[0].equals("pay")) {

                          tv_payable.setText("" + tempPayable);
                      } else if (type[0].equals("Recieve")) {
                          tv_recieveable.setText("" + tempRecievable);
                      }
                      //--------------------------------------------------------------------------------------------------------------------------

                      // update in admin account the current ballence
                      db.updateAdmin_current_ballence(admin_id, String.valueOf(tempCurrentb));
                      current_ballence = String.valueOf(tempCurrentb);
                      Toast.makeText(context, "new ballence" + current_ballence, Toast.LENGTH_SHORT).show();


                  }

//--------------------------------------------------------------------------------------------------------------
                  //  load list again
                  transactionList = db.Transaction_Detail_OF_Account(account_id);
                  if (transactionList.isEmpty()) {
                  } else {
                      adapter.notifyDataSetChanged();
                      recyclerView.scrollToPosition(adapter.getItemCount() - 2);
                  }
//-------------------------------------------------------------------------------------------------------------------

              }   // transaction add ok


            } //  on click on button
        });
        myDialog.setCancelable(true);

    }





   void setvalues(long tempPayable ,  long tempRecievable){


        if (tempPayable > tempRecievable  )
        {

            if (tempPayable - tempRecievable >0){


                long a  = tempPayable -  tempRecievable;

                tv_cashFlow .setText("Have to pay " +a);



            }

        }else if (tempRecievable > tempPayable) {
            if (tempRecievable - tempPayable > 0) {


                long a = tempRecievable - tempPayable;
                tv_cashFlow.setText("Have to Rec " + a);

            } else if (tempRecievable == 0 && tempPayable == 0) {



                    long a = tempRecievable - tempPayable;
                    tv_cashFlow.setText("" + a);




            }


        }}







}
