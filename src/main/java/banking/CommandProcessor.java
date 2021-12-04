package banking;

public abstract class CommandProcessor {

    Bank bank;

    public CommandProcessor(Bank bank) {
        this.bank = bank;
    }

    public abstract boolean process(String command);


}
