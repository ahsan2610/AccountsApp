package techsol.wajeeh.smartaccounts.models;

/**
 * Created by wajeeh on 1/28/2018.
 */

public class class_transaction {

    String _id;
    String account_id;
    String admin_id;
    String description;
    String type;
    String amount;
    String new_b;
    String old_b;
    String date;


    public class_transaction(String id, String account_id, String admin_id , String description, String type, String amount, String new_b, String old_b, String date) {

        this._id = id;
        this.account_id = account_id;
        this.admin_id = admin_id;
        this.description = description;
        this.type = type;
        this.amount = amount;
        this.new_b = new_b;
        this.old_b = old_b;
        this.date = date;

    }


    public String getId() {
        return _id;
    }

    public String getAccount_id() {
        return account_id;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getAmount() {
        return amount;
    }


    public String getNew_b() {
        return new_b;
    }

    public String getOld_b() {
        return old_b;
    }

    public String getDate() {
        return date;
    }
}
