package banking;

import banking.accounts.Account;

public class WithdrawValidator extends CommandValidator {
    public WithdrawValidator(Bank bank) {
        super(bank);
    }

    public boolean validate_withdraw_tokens(String command) {
        String tokens[] = command.split(" ");
        if (tokens.length == 3) {
            return true;
        }
        return false;
    }

    public boolean validate_withdraw_id(String command) {
        String tokens[] = command.split(" ");
        if (Integer.parseInt(tokens[1]) >= 0 && Integer.parseInt(tokens[1]) <= 99999999 && tokens[1].length() == 8) {
            return true;
        }
        return false;
    }

    public boolean validate_decimals(String command) {
        String tokens[] = command.split(" ");
        String[] decimal_places = tokens[2].split("\\.");
        if ((decimal_places.length == 2 && decimal_places[1].length() <= 2) || decimal_places.length == 1) {
            return true;
        }
        return false;
    }


    public boolean validate_withdraw(String command) {
        String tokens[] = command.split(" ");

        if (validate_withdraw_tokens(command) && validate_withdraw_id(command) && validate_decimals(command)) {
            Account account = bank.getAccounts().get(Integer.parseInt(tokens[1]));
            return account.validateWithdraw(Double.parseDouble(tokens[2]));
        }
        return false;
    }

    @Override
    public boolean validate(String command) {
        return validate_withdraw(command);
    }
}
