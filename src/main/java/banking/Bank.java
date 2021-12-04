package banking;

import banking.accounts.Account;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Bank {
    private static final double PENALTY = 25;
    public ArrayList<Integer> idHistory = new ArrayList<>();
    private LinkedHashMap<Integer, Account> accounts;

    Bank() {
        accounts = new LinkedHashMap<>();
    }

    public Map<Integer, Account> getAccounts() {
        return accounts;
    }

    public boolean add_account(int ID, Account account) {
        if (account_id_exists(ID)) {
            return false;
        } else {
            accounts.put(ID, account);
            idHistory.add(ID);
            return true;
        }
    }

    public void add_money_to_account(int ID, double amount_to_deposit) {
        accounts.get(ID).deposit(amount_to_deposit);
    }

    public void take_out_money_from_account(int ID, int amount_to_withdraw) {
        accounts.get(ID).withdraw(amount_to_withdraw);
    }

    public void removeAccount(int ID) {
        accounts.remove(ID);
    }

    public Account getAccountFromId(int ID) {
        return accounts.get(ID);
    }

    public boolean account_id_exists(int ID) {
        return accounts.containsKey(ID);
    }

    public void passTime(int months) {

        int startMonths = 1;
        ArrayList<Integer> removeList = new ArrayList<>();
        while (startMonths <= months) {
            for (Account account : accounts.values()) {
                if (account.getAmount() < 100) {
                    account.withdraw(PENALTY);
                }
                if (account.getAmount() == 0) {
                    removeList.add(account.getID());
                }

                account.accrueAPR();
            }
            startMonths++;
        }


        for (int i = 0; i < removeList.size(); i++) {
            removeAccount(removeList.get(i));
        }
    }

}
