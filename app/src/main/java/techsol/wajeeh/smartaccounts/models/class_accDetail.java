package techsol.wajeeh.smartaccounts.models;

/**
 * Created by wajeeh on 1/27/2018.
 */

public class class_accDetail {

   private String detail_id;
   private String acc_id;
    private String current_ballence;
   private String today_expense;
    private String today_paying;
   private String today_recieving;

   // cb
    //        exp
 //   paying
   //         rec
    public class_accDetail(String detail_id, String acc_id,  String current_ballence, String today_expense, String today_paying, String today_recieving) {
        this.detail_id = detail_id;
        this.acc_id = acc_id;
        this.today_expense = today_expense;
        this.current_ballence = current_ballence;
        this.today_paying = today_paying;
        this.today_recieving = today_recieving;
    }


    public String getDetail_id() {
        return detail_id;
    }

    public String getAcc_id() {
        return acc_id;
    }

    public String getToday_expense() {
        return today_expense;
    }

    public String getCurrent_ballence() {
        return current_ballence;
    }

    public String getToday_paying() {
        return today_paying;
    }

    public String getToday_recieving() {
        return today_recieving;
    }
}
