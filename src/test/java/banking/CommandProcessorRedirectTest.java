package banking;

import banking.accounts.Account;
import banking.accounts.CdAccount;
import banking.accounts.CheckingsAccount;
import banking.accounts.SavingsAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandProcessorRedirectTest {


    CommandProcessorRedirect commandProcessorRedirect;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        commandProcessorRedirect = new CommandProcessorRedirect(bank);
    }

    // Create processor Tests

    @Test
    void create_checkings_account_with_correct_apr_and_id() {
        commandProcessorRedirect.process("create checking 12345678 1.0");
        assertEquals(12345678, bank.getAccounts().get(12345678).getID());
        assertEquals(1.0, bank.getAccounts().get(12345678).getAPR());
    }

    @Test
    void create_cd_account_with_correct_balance_and_apr_and_id() {
        commandProcessorRedirect.process("create cd 12345678 1.0 2000");

        assertEquals(12345678, bank.getAccounts().get(12345678).getID());
        assertEquals(1.0, bank.getAccounts().get(12345678).getAPR());
        assertEquals(2000, bank.getAccounts().get(12345678).getAmount());
    }

    @Test
    void create_savings_account_correct_id_and_apr() {
        commandProcessorRedirect.process("create savings 12345678 1.0");
        assertEquals(12345678, bank.getAccounts().get(12345678).getID());
        assertEquals(1.0, bank.getAccounts().get(12345678).getAPR());
    }

    @Test
    void create_multiple_savings_accounts() {
        commandProcessorRedirect.process("create savings 12345678 1.0");
        commandProcessorRedirect.process("create savings 24681234 2.0");
        assertEquals(12345678, bank.getAccounts().get(12345678).getID());
        assertEquals(24681234, bank.getAccounts().get(24681234).getID());
        assertEquals(1.0, bank.getAccounts().get(12345678).getAPR());
        assertEquals(2.0, bank.getAccounts().get(24681234).getAPR());
    }

    @Test
    void create_multiple_cd_accounts() {
        commandProcessorRedirect.process("create cd 12345678 1.0 2000");
        commandProcessorRedirect.process("create cd 24681234 2.0 4000");

        assertEquals(12345678, bank.getAccounts().get(12345678).getID());
        assertEquals(1.0, bank.getAccounts().get(12345678).getAPR());
        assertEquals(2000, bank.getAccounts().get(12345678).getAmount());
        assertEquals(24681234, bank.getAccounts().get(24681234).getID());
        assertEquals(2.0, bank.getAccounts().get(24681234).getAPR());
        assertEquals(4000, bank.getAccounts().get(24681234).getAmount());
    }

    @Test
    void create_multiple_checking_accounts() {
        commandProcessorRedirect.process("create checking 12345678 1.0");
        commandProcessorRedirect.process("create checking 24681234 2.0");

        assertEquals(12345678, bank.getAccounts().get(12345678).getID());
        assertEquals(1.0, bank.getAccounts().get(12345678).getAPR());
        assertEquals(24681234, bank.getAccounts().get(24681234).getID());
        assertEquals(2.0, bank.getAccounts().get(24681234).getAPR());
    }

    // Deposit processor Tests

    @Test
    void deposit_into_empty_checking_account() {
        Account account = new CheckingsAccount(12345678, 1.0);
        bank.add_account(12345678, account);
        commandProcessorRedirect.process("deposit 12345678 100");
        assertEquals(100, bank.getAccounts().get(12345678).getAmount());
    }

    @Test
    void deposit_multiple_times() {
        Account account = new CheckingsAccount(12345678, 1.0);
        bank.add_account(12345678, account);
        commandProcessorRedirect.process("deposit 12345678 100");
        commandProcessorRedirect.process("deposit 12345678 100");
        assertEquals(200, bank.getAccounts().get(12345678).getAmount());
    }

    // Withdraw Processor Tests

    @Test
    void withdraw_from_savings() {
        Account account = new SavingsAccount(12345678, 1.0);
        bank.add_account(12345678, account);
        bank.add_money_to_account(12345678, 500);
        commandProcessorRedirect.process("Withdraw 12345678 100");
        assertEquals(400, bank.getAccounts().get(12345678).getAmount());
    }

    @Test
    void withdraw_from_checking() {
        Account account = new CheckingsAccount(12345678, 1.0);
        bank.add_account(12345678, account);
        bank.add_money_to_account(12345678, 500);
        commandProcessorRedirect.process("Withdraw 12345678 100");
        assertEquals(400, bank.getAccounts().get(12345678).getAmount());
    }

    @Test
    void withdraw_from_cd() {
        Account account = new CdAccount(12345678, 1.0, 2000);
        bank.add_account(12345678, account);
        commandProcessorRedirect.process("Withdraw 12345678 3000");
        bank.passTime(12);
        assertEquals(true, bank.getAccounts().isEmpty());
    }

    @Test
    void withdraw_too_much_from_account() {
        Account account = new SavingsAccount(12345678, 1.0);
        bank.add_account(12345678, account);
        bank.add_money_to_account(12345678, 800);
        commandProcessorRedirect.process("Withdraw 12345678 900");
        Account someAccount = bank.getAccounts().get(12345678);
        assertEquals(0, someAccount.getAmount());
    }

    //Transfer Processor Test

    @Test
    void transfer_between_savings_and_checkings() {
        Account fromAccount = new SavingsAccount(12345678, 1.0);
        bank.add_account(12345678, fromAccount);
        bank.add_money_to_account(12345678, 800);
        Account toAccount = new CheckingsAccount(56781234, 1.0);
        bank.add_account(56781234, toAccount);
        bank.add_money_to_account(56781234, 800);
        commandProcessorRedirect.process("Transfer 12345678 56781234 500");
        assertEquals(300, bank.getAccounts().get(12345678).getAmount());
        assertEquals(1300, bank.getAccounts().get(56781234).getAmount());
    }

}
