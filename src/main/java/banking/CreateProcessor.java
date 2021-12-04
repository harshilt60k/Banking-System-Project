package banking;

import banking.accounts.Account;
import banking.accounts.CdAccount;
import banking.accounts.CheckingsAccount;
import banking.accounts.SavingsAccount;

public class CreateProcessor extends CommandProcessor {


    public CreateProcessor(Bank bank) {
        super(bank);
    }

    @Override
    public boolean process(String command) {
        String[] tokens = command.split(" ");
        if ((tokens[1].toLowerCase()).equals("cd")) {
            Account account = new CdAccount(Integer.parseInt(tokens[2]), Double.parseDouble(tokens[3]), Double.parseDouble(tokens[4]));
            return bank.add_account(Integer.parseInt(tokens[2]), account);
        } else if ((tokens[1].toLowerCase()).equals("savings")) {
            Account account = new SavingsAccount(Integer.parseInt(tokens[2]), Double.parseDouble(tokens[3]));
            return bank.add_account(Integer.parseInt(tokens[2]), account);
        } else if ((tokens[1].toLowerCase()).equals("checking")) {
            Account account = new CheckingsAccount(Integer.parseInt(tokens[2]), Double.parseDouble(tokens[3]));
            return bank.add_account(Integer.parseInt(tokens[2]), account);
        }
        return false;
    }

}
