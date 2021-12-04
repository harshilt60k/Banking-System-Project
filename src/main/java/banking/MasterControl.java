package banking;

import java.util.List;

public class MasterControl {
    Bank bank;
    CommandValidatorRedirect commandValidatorRedirect;
    CommandProcessorRedirect commandProcessorRedirect;
    CommandStorage commandStorage;

    public MasterControl(Bank bank, CommandValidatorRedirect commandValidator, CommandProcessorRedirect commandProcessorRedirect, CommandStorage commandStorage) {
        this.bank = bank;
        this.commandValidatorRedirect = commandValidator;
        this.commandProcessorRedirect = commandProcessorRedirect;
        this.commandStorage = commandStorage;
    }

    public List<String> start(List<String> input) {
        for (String command : input) {
            if (commandValidatorRedirect.validate(command)) {
//                commandProcessorRedirect.process(command);
                commandProcessorRedirect.addTransactionsForAccount(command);
//                commandStorage.finalOutput();
            } else {
                commandStorage.add_invalid_commands(command);
            }
        }

//        return commandStorage.getInvalidCommands();
        return commandStorage.finalOutput();
    }
}
