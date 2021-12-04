package banking;

import banking.accounts.Account;
import banking.accounts.CheckingsAccount;
import banking.accounts.SavingsAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BankTest {

    private static final int ID = 12345678;
    private static final double APR = 5;
    private static final double AMOUNT = 2000;
    Account account;

    banking.Bank bank;

    @BeforeEach
    void setUp() {
        bank = new banking.Bank();
        account = new SavingsAccount(ID, APR);
        bank.add_account(ID, account);
    }

    @Test
    void add_account_to_bank() {
        assertEquals(ID, bank.getAccounts().get(account.getID()).getID());
    }

    @Test
    void add_2_accounts_to_bank() {
        account = new SavingsAccount(ID + 1, APR);
        bank.add_account(ID + 1, account);
        assertEquals(ID + 1, bank.getAccounts().get(account.getID()).getID());
    }

    @Test
    void remove_account_from_bank() {
        bank.removeAccount(ID);
        assertTrue(bank.getAccounts().isEmpty());
    }

    @Test
    void deposit_money() {
        bank.add_account(ID, new SavingsAccount(ID, APR));
        bank.add_money_to_account(ID, 200);
        assertEquals(200, bank.getAccounts().get(ID).getAmount());
    }

    @Test
    void deposit_money_twice_into_savings_account() {
        bank.add_account(ID, new SavingsAccount(ID, APR));
        bank.add_money_to_account(ID, 200);
        bank.add_money_to_account(ID, 200);
        assertEquals(400, bank.getAccounts().get(ID).getAmount());
    }

    @Test
    void withdraw_money() {
        bank.add_account(ID, new SavingsAccount(ID, APR));
        bank.add_money_to_account(ID, 200);
        bank.take_out_money_from_account(ID, 200);
        assertEquals(0, bank.getAccounts().get(ID).getAmount());
    }

    @Test
    void savings_account_cannot_go_under_zero() {
        bank.add_account(ID, new SavingsAccount(ID, APR));
        bank.add_money_to_account(ID, 50);
        bank.take_out_money_from_account(ID, 100);
        assertEquals(0, bank.getAccounts().get(ID).getAmount());
    }

    @Test
    void pass_time_to_checking_account() {
        bank.add_account(ID, new CheckingsAccount(ID, APR));
        bank.add_money_to_account(ID, 50);
        bank.passTime(4);
        assertEquals(true, bank.getAccounts().isEmpty());
    }

    @Test
    void pass_time_to_savings_account() {
        bank.add_account(ID, new SavingsAccount(ID, APR));
        bank.add_money_to_account(ID, 50);
        bank.passTime(4);
        assertEquals(true, bank.getAccounts().isEmpty());
    }

    @Test
    void minimum_balance() {
        bank.add_account(ID, new CheckingsAccount(ID, APR));
        bank.add_money_to_account(ID, 50);
        bank.passTime(1);
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String outputNumber = decimalFormat.format(bank.getAccounts().get(ID).getAmount());
        String expected = decimalFormat.format(25.1);

        assertEquals(expected, outputNumber);
    }

}
