package banking;

import banking.accounts.Account;

public class TransferValidator extends CommandValidator {

    public TransferValidator(Bank bank) {
        super(bank);
    }

    public boolean transfer_tokens(String command) {
        String[] tokens = command.split(" ");
        if (tokens.length == 4) {
            return true;
        }
        return false;
    }

    public boolean validate_transfer_ids(String command) {
        String[] tokens = command.split(" ");
        if (Integer.parseInt(tokens[1]) >= 0 && Integer.parseInt(tokens[1]) <= 99999999 && tokens[1].length() == 8 && Integer.parseInt(tokens[2]) >= 0 && Integer.parseInt(tokens[2]) <= 99999999 && tokens[2].length() == 8) {
            return true;
        }
        return false;
    }

    public boolean transfer_amount(String command) {
        String[] tokens = command.split(" ");
        Account fromAccount = bank.getAccounts().get(Integer.parseInt(tokens[1]));
        Account toAccount = bank.getAccounts().get(Integer.parseInt(tokens[2]));

        if (((fromAccount.getAccountType() != "cd") || toAccount.getAccountType() != "cd") && (fromAccount.validateWithdraw(Integer.parseInt(tokens[3]))) && (toAccount.validateDeposit(Integer.parseInt(tokens[3]))) && (fromAccount.getID() != toAccount.getID())) {
            return true;
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

    public boolean decimal_places(String command) {
        String[] tokens = command.split(" ");
        String[] decimal_places = tokens[3].split("\\.");

        if ((decimal_places.length == 2 && decimal_places[1].length() <= 2) || decimal_places.length == 1) {
            return true;
        }
        return false;
    }

    public boolean validate_transfer(String command) {

        if (transfer_tokens(command) && validate_transfer_ids(command) && transfer_amount(command) && !(id_exists(command)) && decimal_places(command)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean validate(String command) {
        return validate_transfer(command);
    }
}
