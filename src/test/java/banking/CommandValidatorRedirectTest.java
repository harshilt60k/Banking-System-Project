package banking;

import banking.accounts.Account;
import banking.accounts.CdAccount;
import banking.accounts.CheckingsAccount;
import banking.accounts.SavingsAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandValidatorRedirectTest {

    CommandValidatorRedirect commandValidatorRedirect;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();

        commandValidatorRedirect = new CommandValidatorRedirect(bank);
    }

    @Test
    void valid_savings_command() {

        boolean actual = commandValidatorRedirect.validate("Create savings 12345678 0.5");
        assertTrue(actual);
    }

    @Test
    void invalid_typo_accocunt_type_savings() {
        boolean actual = commandValidatorRedirect.validate("Create savingsss 12345678 0.5");
        assertFalse(actual);
    }

    @Test
    void validate_checkings_command() {
        boolean actual = commandValidatorRedirect.validate("Create checking 12345678 0.5");
        assertTrue(actual);
    }

    @Test
    void invalid_typo_account_type_checkings() {
        boolean actual = commandValidatorRedirect.validate("Create checkingsss 12345678 0.5");
        assertFalse(actual);
    }

    @Test
    void validate_cd_command() {
        boolean actual = commandValidatorRedirect.validate("Create cd 12345678 0.5 2000");
        assertTrue(actual);
    }

    @Test
    void invalid_typo_account_type_cd() {
        boolean actual = commandValidatorRedirect.validate("Create co 12345678 0.5 2000");
        assertFalse(actual);
    }

    @Test
    void amount_decimal_has_3_places() {
        boolean actual = commandValidatorRedirect.validate("Create cd 12345678 0.5 2000.000");
        assertFalse(actual);
    }

    @Test
    void amount_decimal_has_2_spaces() {
        boolean actual = commandValidatorRedirect.validate("Create cd 12345678 0.5 2000.00");
        assertTrue(actual);
    }

    @Test
    void amount_decimal_has_1_decimal() {
        boolean actual = commandValidatorRedirect.validate("Create cd 12345678 0.5 2000.0");
        assertTrue(actual);
    }

    @Test
    void amount_decimal_has_0_decimal() {
        boolean actual = commandValidatorRedirect.validate("Create cd 12345678 0.5 2000");
        assertTrue(actual);
    }

    @Test
    void create_command_missing() {
        boolean actual = commandValidatorRedirect.validate("savings 12345678 0.5");
        assertFalse(actual);
    }

    @Test
    void account_type_missing() {
        boolean actual = commandValidatorRedirect.validate("Create 12345678 0.5 2000");
        assertFalse(actual);
    }

    @Test
    void apr_is_missing() {
        boolean actual = commandValidatorRedirect.validate("Create savings 12345678");
        assertFalse(actual);
    }

    @Test
    void account_id_missing() {
        boolean actual = commandValidatorRedirect.validate("Create savings 0.5");
        assertFalse(actual);
    }

    @Test
    void balance_is_missing() {
        boolean actual = commandValidatorRedirect.validate("Create cd 12345678 0.5");
        assertFalse(actual);
    }

    @Test
    void balance_is_not_a_number() {
        boolean actual = commandValidatorRedirect.validate("Create cd 12345678 0.5 ab");
        assertFalse(actual);
    }

    @Test
    void id_is_not_a_number() {
        boolean actual = commandValidatorRedirect.validate("Create cd 12345b78 0.5 2000");
        assertFalse(actual);
    }

    @Test
    void apr_is_not_a_number() {
        boolean actual = commandValidatorRedirect.validate("Create cd 12345678 0.b 2000");
        assertFalse(actual);
    }

    @Test
    void balance_should_not_be_there() {
        boolean actual = commandValidatorRedirect.validate("Create savings 12345678 0.5 2000");
        assertFalse(actual);
    }

    @Test
    void no_command_at_all() {
        boolean actual = commandValidatorRedirect.validate("");
        assertFalse(actual);
    }

    @Test
    void too_many_arguments() {
        boolean actual = commandValidatorRedirect.validate("Create savings checking 12345678 0.5 2000");
        assertFalse(actual);
    }

    @Test
    void account_id_not_8_digits() {
        boolean actual = commandValidatorRedirect.validate("Create savings 1234567 0.5");
        assertFalse(actual);
    }

    @Test
    void account_id_is_not_number() {
        boolean actual = commandValidatorRedirect.validate("Create savings abcdefgh 0.5");
        assertFalse(actual);
    }

    @Test
    void account_id_is_decimal() {
        boolean actual = commandValidatorRedirect.validate("Create 123456.5 0.5 2000");
        assertFalse(actual);
    }

    @Test
    void apr_has_letters() {
        boolean actual = commandValidatorRedirect.validate("Create savings 12345678 ab");
        assertFalse(actual);
    }

    @Test
    void apr_greater_than_10() {
        boolean actual = commandValidatorRedirect.validate("Create savings 12345678 10.1");
        assertFalse(actual);
    }

    @Test
    void apr_less_than_0_savings() {
        boolean actual = commandValidatorRedirect.validate("Create savings 12345678 -0.5");
        assertFalse(actual);
    }

    @Test
    void apr_less_than_0_checking() {
        boolean actual = commandValidatorRedirect.validate("Create checking 12345678 -0.5");
        assertFalse(actual);
    }

    @Test
    void apr_less_than_0_cd() {
        boolean actual = commandValidatorRedirect.validate("Create cd 12345678 -0.5 2000");
        assertFalse(actual);
    }

    @Test
    void create_account_with_apr_0() {
        boolean actual = commandValidatorRedirect.validate("Create savings 12345678 0.0");
        assertTrue(actual);
    }

    @Test
    void create_command_camel_case() {
        boolean actual = commandValidatorRedirect.validate("CreAte saVinGS 12345678 0.0");
        assertTrue(actual);
    }

    @Test
    void account_id_already_exists() {
        Account account = new SavingsAccount(12345678, 0.5);
        bank.add_account(12345678, account);

        boolean actual = commandValidatorRedirect.validate("create savings 12345678 0.0");
        assertFalse(actual);
    }

    @Test
    void create_account_with_negative_balance() {
        boolean actual = commandValidatorRedirect.validate("create cd 12345678 0.0 -900");
        assertFalse(actual);
    }

    // Deposit Validator Tests:

    @Test
    void valid_deposit_command() {
        Account account = new CheckingsAccount(12345678, 0.01);
        bank.add_account(12345678, account);
        boolean actual = commandValidatorRedirect.validate("Deposit 12345678 500");
        assertTrue(actual);
    }

    @Test
    void valid_deposit_command_camel_case() {
        Account account = new CheckingsAccount(12345678, 0.01);
        bank.add_account(12345678, account);
        boolean actual = commandValidatorRedirect.validate("DePoSiT 12345678 500");
        assertTrue(actual);
    }

    @Test
    void deposit_with_create_command_inside() {
        Account account = new CheckingsAccount(12345678, 0.01);
        bank.add_account(12345678, account);

        boolean actual = commandValidatorRedirect.validate("Deposit create 12345678 500");
        assertFalse(actual);
    }

    @Test
    void typo_deposit_spelling() {
        Account account = new CheckingsAccount(12345678, 0.01);
        bank.add_account(12345678, account);

        boolean actual = commandValidatorRedirect.validate("Deposittt 12345678 500");
        assertFalse(actual);
    }

    @Test
    void deposit_is_missing_in_command() {
        Account account = new CheckingsAccount(12345678, 0.01);
        bank.add_account(12345678, account);

        boolean actual = commandValidatorRedirect.validate("12345678 500");
        assertFalse(actual);
    }

    @Test
    void no_spacing_in_command() {
        Account account = new CheckingsAccount(12345678, 0.01);
        bank.add_account(12345678, account);
        Account account1 = new SavingsAccount(98765432, 0.6);
        bank.add_account(98765432, account1);
        Account account2 = new CdAccount(56781234, 1.2, 2000);
        bank.add_account(56781234, account2);
        boolean actual = commandValidatorRedirect.validate("Deposit12345678 500");
        assertFalse(actual);
    }

    @Test
    void amount_is_before_account_id() {
        Account account = new CheckingsAccount(12345678, 0.01);
        bank.add_account(12345678, account);
        Account account1 = new SavingsAccount(98765432, 0.6);
        bank.add_account(98765432, account1);
        Account account2 = new CdAccount(56781234, 1.2, 2000);
        bank.add_account(56781234, account2);
        boolean actual = commandValidatorRedirect.validate("Deposit 500 12345678");
        assertFalse(actual);
    }

    @Test
    void deposit_in_a_cd_account() {
        Account account = new CdAccount(56781234, 1.2, 2000);
        bank.add_account(56781234, account);
        boolean actual = commandValidatorRedirect.validate("Deposit 56781234 500");
        assertFalse(actual);
    }

    @Test
    void deposit_in_a_savings_account() {
        Account account = new SavingsAccount(56781234, 1.2);
        bank.add_account(56781234, account);
        boolean actual = commandValidatorRedirect.validate("Deposit 56781234 500");
        assertTrue(actual);
    }

    @Test
    void deposit_too_much_in_savings_account() {
        Account account = new SavingsAccount(56781234, 1.2);
        bank.add_account(56781234, account);
        bank.add_money_to_account(56781234, 2000);
        boolean actual = commandValidatorRedirect.validate("Deposit 56781234 2600");
        assertFalse(actual);
    }

    @Test
    void deposit_in_a_checkings_account() {
        Account account = new CheckingsAccount(56781234, 1.2);
        bank.add_account(56781234, account);
        bank.add_money_to_account(56781234, 100);
        boolean actual = commandValidatorRedirect.validate("Deposit 56781234 200");
        assertTrue(actual);
    }

    @Test
    void deposit_too_much_in_checkings_account() {
        Account account = new CheckingsAccount(56781234, 1.2);
        bank.add_account(56781234, account);
        bank.add_money_to_account(56781234, 2000);
        boolean actual = commandValidatorRedirect.validate("Deposit 56781234 1001");
        assertFalse(actual);
    }

    @Test
    void deposit_negative_amount() {
        Account account = new CheckingsAccount(56781234, 1.2);
        bank.add_account(56781234, account);
        bank.add_money_to_account(56781234, 2000);
        boolean actual = commandValidatorRedirect.validate("Deposit 56781234 -500");
        assertFalse(actual);
    }

    @Test
    void deposit_command_camel_case() {
        Account account = new CheckingsAccount(56781234, 1.2);
        bank.add_account(56781234, account);
        bank.add_money_to_account(56781234, 2000);
        boolean actual = commandValidatorRedirect.validate("DepoSiT 56781234 500");
        assertTrue(actual);
    }

    @Test
    void deposit_amount_has_3_decimal_places() {
        Account account = new CheckingsAccount(56781234, 1.2);
        bank.add_account(56781234, account);
        bank.add_money_to_account(56781234, 2000);
        boolean actual = commandValidatorRedirect.validate("Deposit 56781234 500.012");
        assertFalse(actual);
    }

    @Test
    void deposit_amount_has_2_decimal_places() {
        Account account = new CheckingsAccount(56781234, 1.2);
        bank.add_account(56781234, account);
        bank.add_money_to_account(56781234, 2000);
        boolean actual = commandValidatorRedirect.validate("Deposit 56781234 500.01");
        assertTrue(actual);
    }

    @Test
    void deposit_amount_has_1_decimal_place() {
        Account account = new CheckingsAccount(56781234, 1.2);
        bank.add_account(56781234, account);
        bank.add_money_to_account(56781234, 2000);
        boolean actual = commandValidatorRedirect.validate("Deposit 56781234 500.5");
        assertTrue(actual);
    }

    @Test
    void deposit_amount_does_not_have_any_decimal_place() {
        Account account = new CheckingsAccount(56781234, 1.2);
        bank.add_account(56781234, account);
        bank.add_money_to_account(56781234, 2000);
        boolean actual = commandValidatorRedirect.validate("Deposit 56781234 500");
        assertTrue(actual);
    }

    //Withdraw Validator Test

    @Test
    void valid_withdraw() {
        Account account = new SavingsAccount(12345678, 0.5);
        bank.add_account(12345678, account);
        boolean actual = commandValidatorRedirect.validate("Withdraw 12345678 70");
        assertTrue(actual);
    }

    @Test
    void valid_withdraw_camel_case() {
        Account account = new SavingsAccount(12345678, 0.5);
        bank.add_account(12345678, account);
        boolean actual = commandValidatorRedirect.validate("WiThDrAw 12345678 70");
        assertTrue(actual);
    }

    @Test
    void typo_in_withdraw_word() {
        Account account = new SavingsAccount(12345678, 0.5);
        bank.add_account(12345678, account);
        boolean actual = commandValidatorRedirect.validate("Withdrawser 12345678 70");
        assertFalse(actual);
    }

    @Test
    void withdraw_command_id_more_than_8_digits() {
        Account account = new SavingsAccount(12345678, 0.5);
        bank.add_account(12345678, account);
        boolean actual = commandValidatorRedirect.validate("Withdraw 123456778 70");
        assertFalse(actual);
    }

    @Test
    void withdraw_command_more_than_maximum_amount_allowed_savings() {
        Account account = new SavingsAccount(12345678, 0.5);
        bank.add_account(12345678, account);
        boolean actual = commandValidatorRedirect.validate("Withdraw 123456778 2501");
        assertFalse(actual);
    }

    @Test
    void withdraw_command_exactly_max_amount_allowed_savings() {
        Account account = new SavingsAccount(12345678, 0.5);
        bank.add_account(12345678, account);
        boolean actual = commandValidatorRedirect.validate("Withdraw 123456778 2500");
        assertFalse(actual);
    }

    @Test
    void withdraw_command_more_than_maximum_amount_allowed_checking() {
        Account account = new CheckingsAccount(12345678, 0.5);
        bank.add_account(12345678, account);
        boolean actual = commandValidatorRedirect.validate("Withdraw 123456778 401");
        assertFalse(actual);
    }

    @Test
    void missing_id_in_withdraw_command() {
        Account account = new CheckingsAccount(12345678, 0.5);
        bank.add_account(12345678, account);
        boolean actual = commandValidatorRedirect.validate("Withdraw 01");
        assertFalse(actual);
    }

    @Test
    void missing_amount_in_command() {
        Account account = new CheckingsAccount(12345678, 0.5);
        bank.add_account(12345678, account);
        boolean actual = commandValidatorRedirect.validate("Withdraw 12345678");
        assertFalse(actual);
    }

    @Test
    void valid_amount_cd_withdraw_command() {
        Account account = new CdAccount(12345678, 0.5, 2000);
        bank.add_account(12345678, account);
        bank.passTime(12);
        boolean actual = commandValidatorRedirect.validate("Withdraw 12345678 3000");
        assertTrue(actual);
    }

    @Test
    void incorrect_withdraw_amount_for_cd() {
        Account account = new CdAccount(12345678, 0.5, 2000);
        bank.add_account(12345678, account);
        boolean actual = commandValidatorRedirect.validate("Withdraw 12345678 1999");
        assertFalse(actual);
    }

    @Test
    void savings_account_having_more_than_1_withdrawal() {
        Account account = new SavingsAccount(12345678, 0.5);
        bank.add_account(12345678, account);
        commandValidatorRedirect.validate("Withdraw 12345678 500");
        boolean bool = commandValidatorRedirect.validate("Withdraw`12345678 200");

        assertFalse(bool);
    }

    @Test
    void withdraw_command_too_many_arguments() {
        Account account = new SavingsAccount(12345678, 0.5);
        bank.add_account(12345678, account);
        boolean actual = commandValidatorRedirect.validate("create Withdraw 123456778 1001");
        assertFalse(actual);
    }

    @Test
    void withdraw_too_less_arguments() {
        Account account = new SavingsAccount(12345678, 0.5);
        bank.add_account(12345678, account);
        boolean actual = commandValidatorRedirect.validate("123456778 1001");
        assertFalse(actual);
    }

    @Test
    void balance_not_sufficient_in_account() {
        Account account = new SavingsAccount(12345678, 0.5);
        bank.add_account(12345678, account);
        bank.add_money_to_account(12345678, 200);
        boolean actual = commandValidatorRedirect.validate("Withdraw 12345678 300");
        assertTrue(actual);
    }

    //Tranfer validator tests

    @Test
    void transfer_0_from_savings_to_checking() {
        Account fromAccount = new SavingsAccount(12345678, 0.5);
        bank.add_account(12345678, fromAccount);
        bank.add_money_to_account(12345678, 700);
        Account toAccount = new CheckingsAccount(56781234, 0.5);
        bank.add_account(56781234, toAccount);
        bank.add_money_to_account(56781234, 200);
        bank.passTime(1);
        boolean actual = commandValidatorRedirect.validate("Transfer 12345678 56781234 0");
        assertTrue(actual);
    }

    @Test
    void transfer_max_from_savings_to_checking() {
        Account fromAccount = new SavingsAccount(12345678, 0.5);
        bank.add_account(12345678, fromAccount);
        bank.add_money_to_account(12345678, 2000);
        Account toAccount = new CheckingsAccount(56781234, 0.5);
        bank.add_account(56781234, toAccount);
        bank.add_money_to_account(56781234, 200);
        bank.passTime(1);
        boolean actual = commandValidatorRedirect.validate("Transfer 12345678 56781234 1000");
        assertTrue(actual);
    }

    @Test
    void transfer_min_from_savings_to_checking() {
        Account fromAccount = new SavingsAccount(12345678, 0.5);
        bank.add_account(12345678, fromAccount);
        bank.add_money_to_account(12345678, 700);
        Account toAccount = new CheckingsAccount(56781234, 0.5);
        bank.add_account(56781234, toAccount);
        bank.add_money_to_account(56781234, 200);
        bank.passTime(1);
        boolean actual = commandValidatorRedirect.validate("Transfer 12345678 56781234 0");
        assertTrue(actual);
    }

    @Test
    void transfer_negative_from_savings_to_checkings() {
        Account fromAccount = new SavingsAccount(12345678, 0.5);
        bank.add_account(12345678, fromAccount);
        bank.add_money_to_account(12345678, 700);
        Account toAccount = new CheckingsAccount(56781234, 0.5);
        bank.add_account(56781234, toAccount);
        bank.add_money_to_account(56781234, 200);
        bank.passTime(1);
        boolean actual = commandValidatorRedirect.validate("Transfer 12345678 56781234 -30");
        assertFalse(actual);
    }

    @Test
    void transfer_0_checking_to_checking() {
        Account fromAccount = new CheckingsAccount(12345678, 0.5);
        bank.add_account(12345678, fromAccount);
        bank.add_money_to_account(12345678, 700);
        Account toAccount = new CheckingsAccount(56781234, 0.5);
        bank.add_account(56781234, toAccount);
        bank.add_money_to_account(56781234, 200);
        bank.passTime(1);
        boolean actual = commandValidatorRedirect.validate("Transfer 12345678 56781234 0");
        assertTrue(actual);
    }

    @Test
    void transfer_max_from_checking_to_checking() {
        Account fromAccount = new CheckingsAccount(12345678, 0.5);
        bank.add_account(12345678, fromAccount);
        bank.add_money_to_account(12345678, 700);
        Account toAccount = new CheckingsAccount(56781234, 0.5);
        bank.add_account(56781234, toAccount);
        bank.add_money_to_account(56781234, 200);
        bank.passTime(1);
        boolean actual = commandValidatorRedirect.validate("Transfer 12345678 56781234 400");
        assertTrue(actual);
    }

    @Test
    void transfer_min_from_checking_to_checking() {
        Account fromAccount = new CheckingsAccount(12345678, 0.5);
        bank.add_account(12345678, fromAccount);
        bank.add_money_to_account(12345678, 700);
        Account toAccount = new CheckingsAccount(56781234, 0.5);
        bank.add_account(56781234, toAccount);
        bank.add_money_to_account(56781234, 200);
        bank.passTime(1);
        boolean actual = commandValidatorRedirect.validate("Transfer 12345678 56781234 0");
        assertTrue(actual);
    }

    @Test
    void transfer_negative_from_checking_to_checking() {
        Account fromAccount = new CheckingsAccount(12345678, 0.5);
        bank.add_account(12345678, fromAccount);
        bank.add_money_to_account(12345678, 700);
        Account toAccount = new CheckingsAccount(56781234, 0.5);
        bank.add_account(56781234, toAccount);
        bank.add_money_to_account(56781234, 200);
        bank.passTime(1);
        boolean actual = commandValidatorRedirect.validate("Transfer 12345678 56781234 -30");
        assertFalse(actual);
    }

    @Test
    void transfer_from_cd_to_checking_account() {
        Account fromAccount = new CdAccount(12345678, 0.5, 2000);
        bank.add_account(12345678, fromAccount);
        bank.add_money_to_account(12345678, 700);
        Account toAccount = new CheckingsAccount(56781234, 0.5);
        bank.add_account(56781234, toAccount);
        bank.add_money_to_account(56781234, 200);
        bank.passTime(12);
        boolean actual = commandValidatorRedirect.validate("Transfer 12345678 56781234 2000");
        assertFalse(actual);
    }

    @Test
    void transfer_0_from_savings_to_savings() {
        Account fromAccount = new SavingsAccount(12345678, 0.5);
        bank.add_account(12345678, fromAccount);
        bank.add_money_to_account(12345678, 700);
        Account toAccount = new SavingsAccount(56781234, 0.5);
        bank.add_account(56781234, toAccount);
        bank.add_money_to_account(56781234, 200);
        bank.passTime(1);
        boolean actual = commandValidatorRedirect.validate("Transfer 12345678 56781234 0");
        assertTrue(actual);
    }

    @Test
    void transfer_max_from_savings_to_savings() {
        Account fromAccount = new SavingsAccount(12345678, 0.5);
        bank.add_account(12345678, fromAccount);
        bank.add_money_to_account(12345678, 700);
        Account toAccount = new SavingsAccount(56781234, 0.5);
        bank.add_account(56781234, toAccount);
        bank.add_money_to_account(56781234, 200);
        bank.passTime(1);
        boolean actual = commandValidatorRedirect.validate("Transfer 12345678 56781234 1000");
        assertTrue(actual);
    }

    @Test
    void transfer_min_from_savings_to_savings() {
        Account fromAccount = new SavingsAccount(12345678, 0.5);
        bank.add_account(12345678, fromAccount);
        bank.add_money_to_account(12345678, 700);
        Account toAccount = new SavingsAccount(56781234, 0.5);
        bank.add_account(56781234, toAccount);
        bank.add_money_to_account(56781234, 200);
        bank.passTime(1);
        boolean actual = commandValidatorRedirect.validate("Transfer 12345678 56781234 0");
        assertTrue(actual);
    }

    @Test
    void transfer_negative_from_savings_to_savings() {
        Account fromAccount = new SavingsAccount(12345678, 0.5);
        bank.add_account(12345678, fromAccount);
        bank.add_money_to_account(12345678, 700);
        Account toAccount = new SavingsAccount(56781234, 0.5);
        bank.add_account(56781234, toAccount);
        bank.add_money_to_account(56781234, 200);
        bank.passTime(1);
        boolean actual = commandValidatorRedirect.validate("Transfer 12345678 56781234 -30");
        assertFalse(actual);
    }

    @Test
    void valid_transfer_command() {
        Account fromAccount = new SavingsAccount(12345678, 0.5);
        bank.add_account(12345678, fromAccount);
        bank.add_money_to_account(12345678, 700);
        Account toAccount = new CheckingsAccount(56781234, 0.5);
        bank.add_account(56781234, toAccount);
        bank.add_money_to_account(56781234, 200);
        bank.passTime(1);
        boolean actual = commandValidatorRedirect.validate("Transfer 12345678 56781234 500");
        assertTrue(actual);
    }

    @Test
    void valid_transfer_command_camel_case() {
        Account fromAccount = new SavingsAccount(12345678, 0.5);
        bank.add_account(12345678, fromAccount);
        bank.add_money_to_account(12345678, 700);
        Account toAccount = new CheckingsAccount(56781234, 0.5);
        bank.add_account(56781234, toAccount);
        bank.add_money_to_account(56781234, 200);
        bank.passTime(1);
        boolean actual = commandValidatorRedirect.validate("TrAnSfEr 12345678 56781234 500");
        assertTrue(actual);
    }

    @Test
    void same_to_from_id() {
        Account fromAccount = new SavingsAccount(12345678, 0.5);
        bank.add_account(12345678, fromAccount);
        Account toAccount = new CheckingsAccount(12345678, 0.5);
        bank.add_account(12345678, toAccount);
        boolean actual = commandValidatorRedirect.validate("Transfer 12345678 12345678 500");
        assertFalse(actual);
    }

    @Test
    void invalid_amount_to_withdraw_from_savings() {
        Account fromAccount = new SavingsAccount(12345678, 0.5);
        bank.add_account(12345678, fromAccount);
        Account toAccount = new CheckingsAccount(56781234, 0.5);
        bank.add_account(56781234, toAccount);
        boolean actual = commandValidatorRedirect.validate("Transfer 12345678 56781234 1001");
        assertFalse(actual);
    }

    @Test
    void invalid_amount_to_withdraw_from_checking() {
        Account fromAccount = new CheckingsAccount(12345678, 0.5);
        bank.add_account(12345678, fromAccount);
        bank.add_money_to_account(12345678, 401);
        Account toAccount = new SavingsAccount(56781234, 0.5);
        bank.add_account(56781234, toAccount);
        bank.add_money_to_account(56781234, 100);
        boolean actual = commandValidatorRedirect.validate("Transfer 12345678 56781234 401");
        assertFalse(actual);
    }

    @Test
    void transfer_between_cd_and_savings() {
        Account fromAccount = new CdAccount(12345678, 1.0, 2000);
        bank.add_account(12345678, fromAccount);
        Account toAccount = new SavingsAccount(56781234, 1.0);
        bank.add_account(56781234, toAccount);
        bank.add_money_to_account(56781234, 800);
        boolean actual = commandValidatorRedirect.validate("Transfer 12345678 56781234 2000");
        assertFalse(actual);
    }

    @Test
    void too_many_arguments_in_transfer_command() {
        Account fromAccount = new CdAccount(12345678, 1.0, 2000);
        bank.add_account(12345678, fromAccount);
        Account toAccount = new SavingsAccount(56781234, 1.0);
        bank.add_account(56781234, toAccount);
        bank.add_money_to_account(56781234, 800);
        boolean actual = commandValidatorRedirect.validate("Transfer 12345678 56781234 2.0 2000");
        assertFalse(actual);
    }

    @Test
    void too_few_commands_in_transfer_command() {
        Account fromAccount = new CdAccount(12345678, 1.0, 2000);
        bank.add_account(12345678, fromAccount);
        Account toAccount = new SavingsAccount(56781234, 1.0);
        bank.add_account(56781234, toAccount);
        bank.add_money_to_account(56781234, 800);
        boolean actual = commandValidatorRedirect.validate("Transfer 12345678 2000");
        assertFalse(actual);
    }

    @Test
    void withdraw_negative_from_account() {
        Account account = new SavingsAccount(12345678, 1.0);
        bank.add_account(12345678, account);
        bank.add_money_to_account(12345678, 800);
        boolean actual = commandValidatorRedirect.validate("Withdraw 12345678 -700");
        assertFalse(actual);
    }

    // PassTime commands

    @Test
    void valid_pass_time() {
        boolean bool = commandValidatorRedirect.validate("Pass 1");

        assertTrue(bool);
    }

    @Test
    void valid_pass_time_camel_case() {
        boolean bool = commandValidatorRedirect.validate("PaSs 1");

        assertTrue(bool);
    }

    @Test
    void pass_time_exacrtly_60() {
        boolean bool = commandValidatorRedirect.validate("PaSs 60");

        assertTrue(bool);
    }

    @Test
    void pass_time_lower_than_1() {
        boolean bool = commandValidatorRedirect.validate("PaSs -1");

        assertFalse(bool);
    }

    @Test
    void pass_time_lower_than_60() {
        boolean bool = commandValidatorRedirect.validate("PaSs 59");

        assertTrue(bool);
    }

    @Test
    void pass_time_in_between_1_and_60() {
        boolean bool = commandValidatorRedirect.validate("PaSs 20");

        assertTrue(bool);
    }

    @Test
    void pass_time_not_integer() {
        boolean bool = commandValidatorRedirect.validate("PaSs 1.1");

        assertFalse(bool);
    }

    @Test
    void pass_time_missing_pass_command() {
        boolean bool = commandValidatorRedirect.validate("1");

        assertFalse(bool);
    }

    @Test
    void pass_time_missing_time() {
        boolean bool = commandValidatorRedirect.validate("PaSs ");

        assertFalse(bool);
    }

    @Test
    void pass_time_having_too_many_arguments() {
        boolean bool = commandValidatorRedirect.validate("PaSs 1 create");

        assertFalse(bool);
    }

    @Test
    void pass_time_is_empty_command() {
        boolean bool = commandValidatorRedirect.validate("");

        assertFalse(bool);
    }

    @Test
    void pass_time_is_1_space() {
        boolean bool = commandValidatorRedirect.validate(" ");

        assertFalse(bool);
    }

}
