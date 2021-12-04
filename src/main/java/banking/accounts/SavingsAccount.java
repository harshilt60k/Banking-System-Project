package banking.accounts;

import java.text.DecimalFormat;

public class SavingsAccount extends Account {

    boolean bool;

    public SavingsAccount(int ID, double apr) {
        super(ID, apr, "Savings");
        this.bool = true;
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
        if ((amount <= 2500) && (amount >= 0)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean validateWithdraw(double withdraw_amount) {

        if (withdraw_amount < 0) {
            return false;
        } else if (withdraw_amount >= 0 && withdraw_amount <= 2500 && (bool || this.getMonthsPassed() >= 1)) {
            this.setMonthsPassed(0);
            bool = false;
            return true;
        } else {
            return false;
        }

    }

    @Override
    public void setMonthsPassed(int month) {
        this.bool = true;
        this.monthsPassed = month;
    }

    @Override
    public void accrueAPR() {
        double decimalAPR = this.getAPR() / 100;
        double decimalAPRDividedBy12 = decimalAPR / 12;
        double multBalanceWithDecimalAPRDividedBy12 = decimalAPRDividedBy12 * this.getAmount();
        this.setAmount(multBalanceWithDecimalAPRDividedBy12 + this.getAmount());
    }

}
