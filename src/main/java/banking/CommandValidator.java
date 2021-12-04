package banking;

public abstract class CommandValidator {

    Bank bank;

    public CommandValidator(Bank bank) {
        this.bank = bank;
    }


    public abstract boolean validate(String command);

}
