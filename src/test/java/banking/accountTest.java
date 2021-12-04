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

}
