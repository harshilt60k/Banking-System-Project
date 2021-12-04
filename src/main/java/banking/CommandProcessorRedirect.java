package banking;

import banking.accounts.Account;

public class CommandProcessorRedirect {
    Bank bank;
    CreateProcessor createProcessor;
    DepositProcessor depositProcessor;
    WithdrawProcessor withdrawProcessor;
    TransferProcessor transferProcessor;
    PassTimeProcessor passTimeProcessor;

    public CommandProcessorRedirect(Bank bank) {
        this.bank = bank;
        this.createProcessor = new CreateProcessor(bank);
        this.depositProcessor = new DepositProcessor(bank);
        this.withdrawProcessor = new WithdrawProcessor(bank);
        this.transferProcessor = new TransferProcessor(bank);
        this.passTimeProcessor = new PassTimeProcessor(bank);
    }

    public boolean process(String command) {
        String[] tokens = command.split(" ");
        if ((tokens[0].toLowerCase()).equals("create")) {
            createProcessor.process(command);
        } else if ((tokens[0].toLowerCase()).equals("deposit")) {
            depositProcessor.process(command);
        } else if ((tokens[0].toLowerCase()).equals("withdraw")) {
            withdrawProcessor.process(command);
        } else if ((tokens[0].toLowerCase()).equals("transfer")) {
            transferProcessor.process(command);
        } else if ((tokens[0].toLowerCase()).equals("pass")) {
            passTimeProcessor.process(command);
        }
        return false;
    }

    public void addTransactionsForAccount(String command) {
        String[] tokens = command.split(" ");
        this.process(command);

        if ((tokens[0].toLowerCase()).equals("create")) {
            bank.getAccounts().get(Integer.parseInt(tokens[2])).addTransaction(command);
        }
        if (((tokens[0].toLowerCase()).equals("deposit")) || ((tokens[0].toLowerCase()).equals("withdraw"))) {
            bank.getAccounts().get(Integer.parseInt(tokens[1])).addTransaction(command);
        }
        if ((tokens[0].toLowerCase()).equals("transfer")) {
            bank.getAccounts().get(Integer.parseInt(tokens[1])).addTransaction(command);
            bank.getAccounts().get(Integer.parseInt(tokens[2])).addTransaction(command);
        }
        if ((tokens[0].toLowerCase()).equals("pass")) {
            for (Account account : bank.getAccounts().values()) {
                bank.getAccounts().get(account.getID()).addTransaction(command);
            }
        }

    }
}
