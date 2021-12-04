package banking.accounts;

import java.text.DecimalFormat;

public class CheckingsAccount extends Account {

    public CheckingsAccount(int ID, double apr) {
        super(ID, apr, "Checking");
    }

    @Override
    public String getState() {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String accountAmount = decimalFormat.format(this.getAmount());
        String accountApr = decimalFormat.format(this.getAPR());
        String str = String.format("%s %d %s %s", this.getAccountType(), this.getID(), accountAmount, accountApr);
        return str;
    }

    @Override
    public boolean validateDeposit(double amount) {
        if ((amount <= 1000) && (amount >= 0)) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public boolean validateWithdraw(double withdraw_amount) {
        if (withdraw_amount > this.getAmount()) {
            this.setAmount(0);
            return true;
        } else if ((withdraw_amount <= this.getAmount() || withdraw_amount == 0) && withdraw_amount <= 400) {
            return true;
        }
        return false;
    }

    @Override
    public void accrueAPR() {
        double decimalAPR = this.getAPR() / 100;
        double decimalAPRDividedBy12 = decimalAPR / 12;
        double multBalanceWithDecimalAPRDividedBy12 = decimalAPRDividedBy12 * this.getAmount();
        this.setAmount(multBalanceWithDecimalAPRDividedBy12 + this.getAmount());
    }


//    banking.accounts.Account banking.accounts.CheckingsAccount() {
//        return new banking.accounts.CheckingsAccount(getID(), getAPR());
//    }
}
