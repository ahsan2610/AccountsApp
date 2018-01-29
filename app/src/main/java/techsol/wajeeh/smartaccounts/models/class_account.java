package techsol.wajeeh.smartaccounts.models;

/**
 * Created by wajeeh on 1/27/2018.
 */

public class class_account {

    String account_id;
    String pName;
    String password;

    private String current_ballence;







    public class_account(String account_id, String pName, String password,String current_ballence) {
        this.account_id = account_id;
        this.pName = pName;
        this.password = password;



        this.current_ballence = current_ballence;



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




    public String getCurrent_ballence() {
        return current_ballence;
    }



}
