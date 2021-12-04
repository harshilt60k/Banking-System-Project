package banking;

import banking.accounts.Account;

public class DepositValidator extends CommandValidator {

    public DepositValidator(Bank bank) {
        super(bank);
    }

    public boolean validate_deposit_tokens(String command) {
        String tokens[] = command.split(" ");
        if (tokens.length == 3) {
            return true;
        }
        return false;
    }

    public boolean validate_deposit_id(String command) {
        String tokens[] = command.split(" ");
        if (Integer.parseInt(tokens[1]) >= 0 && Integer.parseInt(tokens[1]) <= 99999999 && tokens[1].length() == 8) {
            return true;
        }
        return false;
    }

    public boolean validate_deposit(String command) {
        String tokens[] = command.split(" ");
        String[] decimal_places = tokens[2].split("\\.");
        if (validate_deposit_tokens(command) && validate_deposit_id(command) && ((decimal_places.length == 2 && decimal_places[1].length() <= 2) || decimal_places.length == 1)) {
            Account account = bank.getAccounts().get(Integer.parseInt(tokens[1]));
            return account.validateDeposit(Double.parseDouble(tokens[2]));
        }
        return false;
    }


    public boolean id_exists(String command) {
        String[] tokens = command.split(" ");
        int ID = Integer.parseInt(tokens[2]);
        if (bank.account_id_exists(ID)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean validate(String command) {
        return validate_deposit(command);
    }

}
