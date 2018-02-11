package techsol.wajeeh.smartaccounts.models;

/**
 * Created by wajeeh on 1/27/2018.
 */

public class class_account {

    String  _id         ;
    String  admin_id    ;
    String  name        ;
    String  phone       ;
    String  pay_able    ;
    String  rec_able    ;


    public class_account(String _id, String admin_id, String name, String phone, String pay_able, String rec_able) {
        this._id = _id;
        this.admin_id = admin_id;
        this.name = name;
        this.phone = phone;
        this.pay_able = pay_able;
        this.rec_able = rec_able;
    }

    public String get_id() {
        return _id;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getPay_able() {
        return pay_able;
    }

    public String getRec_able() {
        return rec_able;
    }
}
