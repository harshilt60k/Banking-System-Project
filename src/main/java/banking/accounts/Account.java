package banking.accounts;

import java.util.ArrayList;

public abstract class Account {

    protected int monthsPassed;
    ArrayList<String> history = new ArrayList<>();
    private double amount;
    private int ID;
    private double apr;
    private String account_type;

    public Account(int ID, double apr, String account_type) {
        this.ID = ID;
        this.apr = apr;
        this.amount = 0;
        this.account_type = account_type;
        this.monthsPassed = 1;
    }

    public String getAccountType() {
        return account_type;
    }


    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAPR() {
        return this.apr;
    }

    public int getID() {
        return ID;
    }

    public int getMonthsPassed() {
        return monthsPassed;
    }

    public void setMonthsPassed(int month) {
        this.monthsPassed = month;
    }

//    public String getState() {
//        DecimalFormat decimalFormat = new DecimalFormat("0.00");
//        String accountAmount = decimalFormat.format(this.getAmount());
//        String accountApr = decimalFormat.format(this.getAPR());
//        String str = String.format("%s %d %s %s", this.getAccountType(), this.getID(), accountAmount, accountApr);
//        return str;
//    }

    public abstract String getState();

    public void addTransaction(String command) {
        history.add(command);
    }

    public ArrayList<String> getTransactions() {
        int i = 0;
        String str = "";
        while (i < history.size()) {
            String[] tokens = history.get(i).split(" ");
            if (tokens[0].equalsIgnoreCase("create") || tokens[0].equalsIgnoreCase("pass")) {
                history.remove(i);
            } else {
                i++;
            }

        }

        return history;
    }

    public void deposit(double amount_to_add) {
        if (validateDeposit(amount_to_add)) {
            amount += amount_to_add;
        }
    }

    public void withdraw(double amount_to_withdraw) {
        double amountToSet = 0;
        if (amount_to_withdraw > amount) {
            amountToSet = 0;
        } else {
            amountToSet = amount - amount_to_withdraw;
        }
        this.setAmount(amountToSet);
//        if (validateWithdraw(amount_to_withdraw)) {
//            this.setAmount(amountToSet);
        //}
//        double amountToSet = this.getAmount() - amount_to_withdraw;
//
//        if (amountToSet < 0) {
//            this.setAmount(0);
//        } else {
//            this.setAmount(this.getAmount() - amount_to_withdraw);
//        }
    }

    public abstract boolean validateDeposit(double parseDouble);

    public abstract boolean validateWithdraw(double withdraw_amount);

    public abstract void accrueAPR();
}
