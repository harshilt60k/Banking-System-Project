package banking.accounts;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CdAccount extends Account {

    ArrayList<String> transactions = new ArrayList<>();

    public CdAccount(int ID, double apr, double amount) {
        super(ID, apr, "Cd");
        this.setAmount(amount);
    }

    @Override
    public String getState() {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String accountAmount = decimalFormat.format(this.getAmount());
        String accountApr = decimalFormat.format(this.getAPR());
        String str = "";
        if (this.getID() == 00000000) {
            str += String.format("%s %08d %s %s", this.getAccountType(), this.getID(), accountAmount, accountApr);
        } else {
            str += String.format("%s %d %s %s", this.getAccountType(), this.getID(), accountAmount, accountApr);
        }

        return str;
    }

    @Override
    public boolean validateDeposit(double amount) {
        return false;
    }

    @Override
    public boolean validateWithdraw(double withdraw_amount) {
        if (withdraw_amount > this.getAmount()) {
            return true;
        } else if ((withdraw_amount >= this.getAmount()) && this.getMonthsPassed() >= 12) {
            return true;
        }
        return false;
    }

    @Override
    public void accrueAPR() {
        for (int i = 0; i < 4; i++) {
            double decimalAPR = this.getAPR() / 100;
            double decimalAPRDividedBy12 = decimalAPR / 12;
            double multBalanceWithDecimalAPRDividedBy12 = decimalAPRDividedBy12 * this.getAmount();

            this.setAmount(multBalanceWithDecimalAPRDividedBy12 + this.getAmount());
        }
    }

    public ArrayList<String> getAccountTransactions() {


        return transactions;
    }

}
