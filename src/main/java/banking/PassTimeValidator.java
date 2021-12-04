package banking;

public class PassTimeValidator {

    private boolean pass_time_tokens(String command) {
        String[] tokens = command.split(" ");
        if (tokens.length == 2) {
            return true;
        }
        return false;
    }

    private boolean valid_months(String command) {
        String[] tokens = command.split(" ");
        try {
            if ((Integer.parseInt(tokens[1]) > 0 && Integer.parseInt(tokens[1]) <= 60)) {
                return true;
            }
        } catch (NumberFormatException e) {
            return false;
        }

        return false;
    }

    private boolean validate_pass_time(String command) {
        String[] tokens = command.split(" ");
        if ((!command.equals(" ")) && (tokens[0].toLowerCase()).equals("pass") && pass_time_tokens(command) && valid_months(command)) {
            return true;
        }
        return false;
    }

    public boolean validate(String command) {
        return validate_pass_time(command);
    }
}
