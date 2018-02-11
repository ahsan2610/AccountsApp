package techsol.wajeeh.smartaccounts.models;

/**
 * Created by wajeeh on 2/5/2018.
 */

public class expens {


   String   _id;
   String   admin_id;
   String   description ;
   String   amount ;
   String   nb ;
   String   ob ;
   String   date ;


    public expens(String _id, String admin_id, String description, String amount, String nb, String ob, String date) {
        this._id = _id;
        this.admin_id = admin_id;
        this.description = description;
        this.amount = amount;
        this.nb = nb;
        this.ob = ob;
        this.date = date;
    }


    public String get_id() {
        return _id;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public String getDescription() {
        return description;
    }

    public String getAmount() {
        return amount;
    }

    public String getNb() {
        return nb;
    }

    public String getOb() {
        return ob;
    }

    public String getDate() {
        return date;
    }
}
