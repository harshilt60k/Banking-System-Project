package banking;

public class PassTimeProcessor extends CommandProcessor {

    public PassTimeProcessor(Bank bank) {
        super(bank);
    }

    @Override
    public boolean process(String command) {
        String[] tokens = command.split(" ");
        bank.passTime(Integer.parseInt(tokens[1]));

        return true;
    }
}
