package banking;

import banking.accounts.Account;

public class WithdrawProcessor extends CommandProcessor {

    public WithdrawProcessor(Bank bank) {
        super(bank);
    }

    @Override
    public boolean process(String command) {
        String[] tokens = command.split(" ");

        int accountNumber = Integer.parseInt(tokens[1]);
        Account accountToWithdrawFrom = bank.getAccounts().get(accountNumber);
        double amountToWithdraw = Double.parseDouble(tokens[2]);
        accountToWithdrawFrom.withdraw(amountToWithdraw);
        return true;
    }
}
