package banking;

import banking.accounts.Account;
import banking.accounts.CdAccount;
import banking.accounts.CheckingsAccount;
import banking.accounts.SavingsAccount;

public class CreateValidator extends CommandValidator {

    public CreateValidator(Bank bank) {
        super(bank);
    }

    private static boolean validate_account_amount(String command) {
        String[] tokens = command.split(" ");
        String money = tokens[4];
        String[] decimal_places = money.split("\\.");
        try {
            if (Double.parseDouble(tokens[4]) >= 1000 && Double.parseDouble(tokens[4]) <= 10000 && (tokens[1].toLowerCase()).equals("cd") && ((decimal_places.length == 2 && decimal_places[1].length() <= 2) || decimal_places.length == 1)) {
                return true;
            }
            return false;
        } catch (NumberFormatException e) {
            return false;
        }

    }

    protected static boolean validate_account_type(String command) {
        String[] tokens = command.split(" ");
        if ((tokens[1].toLowerCase()).equals("cd") || (tokens[1].toLowerCase()).equals("checking") || (tokens[1].toLowerCase()).equals("savings")) {
            return true;
        }
        return false;
    }

    protected static boolean validate_account_id(String command) {
        String[] tokens = command.split(" ");

        try {
            if (tokens[2].length() == 8 && Integer.parseInt(tokens[2]) >= 0 && Integer.parseInt(tokens[2]) <= 99999999) {
                return true;
            }
            return false;
        } catch (NumberFormatException e) {
            return false;
        }

    }

    protected static boolean validate_account_apr(String command) {
        String[] tokens = command.split(" ");

        try {
            if (validate_token_amount(command) && Double.parseDouble(tokens[3]) >= 0 && Double.parseDouble(tokens[3]) <= 10) {
                return true;
            }
            return false;
        } catch (NumberFormatException e) {
            return false;
        }

    }

    public static boolean validate_token_amount(String command) {
        String[] tokens = command.split(" ");
        if ((tokens[1].toLowerCase()).equals("cd") && tokens.length == 5 && validate_account_type(command)) {
            return true;
        } else if (((tokens[1].toLowerCase()).equals("savings") || (tokens[1].toLowerCase()).equals("checking")) && tokens.length == 4) {
            return true;
        }
        return false;
    }

    public boolean correct_spelling(String command) {
        String[] tokens = command.split(" ");
        if ((tokens[0].toLowerCase()).equals("create")) {
            return true;
        }
        return false;
    }

    public boolean validate_create(String command) {
        String[] tokens = command.split(" ");
        if (correct_spelling(command) && (tokens[1].toLowerCase()).equals("cd") && validate_account_type(command) && validate_token_amount(command) && validate_account_amount(command) && validate_account_id(command) && validate_account_apr(command)) {
            Account account = new CdAccount(Integer.parseInt(tokens[2]), Double.parseDouble(tokens[3]), Double.parseDouble(tokens[4]));
            return bank.add_account(Integer.parseInt(tokens[2]), account);
        } else if (correct_spelling(command) && (tokens[1].toLowerCase()).equals("checking") && validate_token_amount(command) && validate_account_id(command) && validate_account_apr(command)) {
            Account account = new CheckingsAccount(Integer.parseInt(tokens[2]), Double.parseDouble(tokens[3]));
            return bank.add_account(Integer.parseInt(tokens[2]), account);
        } else if (correct_spelling(command) && (tokens[1].toLowerCase()).equals("savings") && validate_token_amount(command) && validate_account_id(command) && validate_account_apr(command)) {
            Account account = new SavingsAccount(Integer.parseInt(tokens[2]), Double.parseDouble(tokens[3]));
            return bank.add_account(Integer.parseInt(tokens[2]), account);
        }

        return false;
    }

    @Override
    public boolean validate(String command) {
        return validate_create(command);
    }

}