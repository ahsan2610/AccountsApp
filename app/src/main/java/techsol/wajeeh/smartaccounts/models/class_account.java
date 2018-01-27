package techsol.wajeeh.smartaccounts.models;

/**
 * Created by wajeeh on 1/27/2018.
 */

public class class_account {

    String account_id;
    String pName;
    String password;

    public class_account(String account_id, String pName, String password) {
        this.account_id = account_id;
        this.pName = pName;
        this.password = password;
    }


    public String getAccount_id() {
        return account_id;
    }

    public String getpName() {
        return pName;
    }

    public String getPassword() {
        return password;
    }
}
