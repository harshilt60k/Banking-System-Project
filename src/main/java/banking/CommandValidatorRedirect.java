package banking;

public class CommandValidatorRedirect {
    Bank bank;
    CreateValidator createValidator;
    DepositValidator depositValidator;
    WithdrawValidator withdrawValidator;
    TransferValidator transferValidator;
    PassTimeValidator passTimeValidator;

    public CommandValidatorRedirect(Bank bank) {
        this.bank = bank;
        this.createValidator = new CreateValidator(bank);
        this.depositValidator = new DepositValidator(bank);
        this.withdrawValidator = new WithdrawValidator(bank);
        this.transferValidator = new TransferValidator(bank);
        this.passTimeValidator = new PassTimeValidator();
    }

    public boolean validate(String command) {
        String[] tokens = command.split(" ");
        if (!command.equals(" ")) {
            if ((tokens[0].toLowerCase()).equals("create") && createValidator.validate(command)) {
                return true;
            } else if ((tokens[0].toLowerCase()).equals("deposit") && depositValidator.validate(command)) {
                return true;
            } else if ((tokens[0].toLowerCase()).equals("withdraw") && withdrawValidator.validate(command)) {
                return true;
            } else if ((tokens[0].toLowerCase()).equals("transfer") && transferValidator.validate(command)) {
                return true;
            } else if ((tokens[0].toLowerCase()).equals("pass") && passTimeValidator.validate(command)) {
                return true;
            }
        }

        return false;
    }

}
