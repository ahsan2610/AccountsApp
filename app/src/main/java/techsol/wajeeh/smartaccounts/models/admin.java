package techsol.wajeeh.smartaccounts.models;

/**
 * Created by wajeeh on 2/5/2018.
 */

public class admin {

    String    _id       ;
    String    ad_name   ;
    String    password  ;
    String    cb        ;

    public admin(String _id, String ad_name, String password, String cb) {
        this._id = _id;
        this.ad_name = ad_name;
        this.password = password;
        this.cb = cb;
    }


    public String get_id() {
        return _id;
    }

    public String getAd_name() {
        return ad_name;
    }

    public String getPassword() {
        return password;
    }

    public String getCb() {
        return cb;
    }
}
