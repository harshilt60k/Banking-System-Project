package banking;

public class DepositProcessor extends CommandProcessor {

    public DepositProcessor(Bank bank) {
        super(bank);
    }

    @Override
    public boolean process(String command) {
        String[] tokens = command.split(" ");
        bank.getAccounts().get(Integer.parseInt(tokens[1])).deposit(Double.parseDouble(tokens[2]));
        return true;
    }

}
