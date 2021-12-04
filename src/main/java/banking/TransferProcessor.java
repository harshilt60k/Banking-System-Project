package banking;

public class TransferProcessor extends CommandProcessor {
    public TransferProcessor(Bank bank) {
        super(bank);
    }

    @Override
    public boolean process(String command) {
        String[] tokens = command.split(" ");
        bank.getAccounts().get(Integer.parseInt(tokens[1])).withdraw(Double.parseDouble(tokens[3]));
        bank.getAccounts().get(Integer.parseInt(tokens[2])).deposit(Double.parseDouble(tokens[3]));

        return true;
    }
}
