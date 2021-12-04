package banking;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommandStorage {
    CommandValidatorRedirect commandValidatorRedirect;
    ArrayList<String> list_of_invalid_commands = new ArrayList<>();
    ArrayList<String> finalOutput = new ArrayList<>();

    Bank bank;
//    ArrayList<String> list_of_commands = new ArrayList<>();

    public CommandStorage(Bank bank) {
        this.bank = bank;
        commandValidatorRedirect = new CommandValidatorRedirect(bank);
    }

    public void add_invalid_commands(String command) {
        if (commandValidatorRedirect.validate(command) == false) {
            list_of_invalid_commands.add(command);
        }
    }

//    public void add_all_commands(String command) {
//        if (commandValidatorRedirect.validate(command) == true || commandValidatorRedirect.validate(command) == false) {
//            list_of_commands.add(command);
//        }
//    }

//    public ArrayList<String> getAllCommands() {
//        return list_of_commands;
//    }

    public ArrayList<String> getInvalidCommands() {
        return list_of_invalid_commands;
    }

    public boolean find(String s) {

        if (list_of_invalid_commands.contains(s)) {
            return true;
        }
        return false;
    }

    public List<String> finalOutput() {
//        LinkedHashMap<Integer, Account> accounts = bank.getAccounts();

        for (Map.Entry pairs : bank.getAccounts().entrySet()) {

            int id = (int) pairs.getKey();
            String state = bank.getAccountFromId(id).getState();
            finalOutput.add(state);
            finalOutput.addAll(bank.getAccountFromId(id).getTransactions());
        }
//        for (Integer id : bank.idHistory) {
//            if (bank.getAccounts().values().contains(id)) {
//                finalOutput.add(String.valueOf(bank.getAccounts().get(id).getState()));
//                finalOutput.addAll(bank.getAccounts().get(id).getTransactions());
//            }
//        }

        finalOutput.addAll(this.getInvalidCommands());
        return getFinalOutput();
    }

    public List<String> getFinalOutput() {
        return finalOutput;
    }

}
