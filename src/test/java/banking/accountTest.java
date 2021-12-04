package banking;

import banking.accounts.Account;
import banking.accounts.CdAccount;
import banking.accounts.CheckingsAccount;
import banking.accounts.SavingsAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class accountTest {

    private static final int ID = 12345678;
    private static final float APR = 5;
    private static final float BALANCE = 1000;
    Account account;
    Account account1;
    Account account2;

    @BeforeEach
    void setUp() {
        account = new SavingsAccount(ID, APR);
        account1 = new CheckingsAccount(ID, APR);
        account2 = new CdAccount(ID, APR, BALANCE);
    }

    @Test
    void savings_account_has_id() {
        assertEquals(ID, account.getID());
    }

    @Test
    void get_checking_account_type() {
        assertEquals("Checking", account1.getAccountType());
    }

    @Test
    void get_savings_account_type() {
        assertEquals("Savings", account.getAccountType());
    }

    @Test
    void get_cd_account_type() {
        assertEquals("Cd", account2.getAccountType());
    }

    @Test
    void checkings_account_has_id() {
        assertEquals(ID, account1.getID());
    }

    @Test
    void cd_account_has_id() {
        assertEquals(ID, account2.getID());
    }

    @Test
    void savings_account_is_empty_when_creating() {
        assertEquals(0, account.getAmount());
    }

    @Test
    void checkings_account_is_empty_when_creating() {
        assertEquals(0, account1.getAmount());
    }

    @Test
    void cd_account_has_balance_when_creating() {
        assertEquals(BALANCE, account2.getAmount());
    }

    @Test
    void savings_account_has_apr() {
        assertEquals(APR, account.getAPR());
    }

    @Test
    void checkings_account_has_apr() {
        assertEquals(APR, account1.getAPR());
    }

    @Test
    void cd_account_has_apr() {
        assertEquals(APR, account2.getAPR());
    }

    @Test
    void get_transaction_with_zeroTransactions_emptyList() {
        assertEquals(account.getTransactions().size(), 0);
    }

    @Test
    void get_transaction_with_pass_and_create_commands() {
        String command1 = "Create checking 12345678 0.5";
        account1.addTransaction(command1);
        String command2 = "Pass 1";
        account1.addTransaction(command2);

        assertEquals(account.getTransactions().size(), 0);

    }

    @Test
    void get_transaction_with_normal_transaction() {
        String command1 = "Create checking 12345678 0.5";
        account1.addTransaction(command1);
        String command2 = "Pass 1";
        account1.addTransaction(command2);
        String command3 = "Deposit 12345678 300";
        account1.addTransaction(command3);

        assertEquals(account1.getTransactions().size(), 1);
        assertEquals(account1.getTransactions().get(0), command3);
    }

}
