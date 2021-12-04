package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MasterControlTest {

    MasterControl masterControl;
    List<String> input;

    @BeforeEach
    void setUp() {
        input = new ArrayList<>();
        Bank bank = new Bank();
        masterControl = new MasterControl(bank, new CommandValidatorRedirect(bank), new CommandProcessorRedirect(bank), new CommandStorage(bank));
    }

    private void assertSingleCommand(String command, List<String> actual) {
        assertEquals(1, actual.size());
        assertEquals(command, actual.get(0));
    }

    @Test
    void type_in_create_command_is_invalid() {
        input.add("creat checking 12345678 1.0");

        List<String> actual = masterControl.start(input);

        assertEquals(1, actual.size());
        assertSingleCommand("creat checking 12345678 1.0", actual);
    }

    @Test
    void type_in_deposit_command_is_invalid() {
        input.add("depositt 12345678 100");

        List<String> actual = masterControl.start(input);

        assertEquals(1, actual.size());
        assertSingleCommand("depositt 12345678 100", actual);
    }

    @Test
    void typo_commands_both_invalid() {
        input.add("creat checking 12345678 1.0");
        input.add("depositt 12345678 100");

        List<String> actual = masterControl.start(input);

        assertEquals(2, actual.size());
        assertEquals("creat checking 12345678 1.0", actual.get(0));
        assertEquals("depositt 12345678 100", actual.get(1));
    }

    @Test
    void invalid_to_create_commands_with_same_ID() {
        input.add("Create checking 12345678 1.0");
        input.add("Create checking 12345678 1.0");

        List<String> actual = masterControl.start(input);

        assertEquals(2, actual.size());
        assertEquals("Checking 12345678 0.00 1.00", actual.get(0));
        assertEquals("Create checking 12345678 1.0", actual.get(1));
    }

    @Test
    void withdraw_twice_from_account() {
        input.add("Create savings 12345678 1.0");
        input.add("Deposit 12345678 200");
        input.add("Deposit 12345678 200");
        input.add("Deposit 12345678 500");
        input.add("Withdraw 12345678 700");
        input.add("Withdraw 12345678 200");

        List<String> actual = masterControl.start(input);

        assertEquals(6, actual.size());
        assertEquals("Savings 12345678 200.00 1.00", actual.get(0));
        assertEquals("Deposit 12345678 200", actual.get(1));
        assertEquals("Deposit 12345678 200", actual.get(2));
        assertEquals("Deposit 12345678 500", actual.get(3));
        assertEquals("Withdraw 12345678 700", actual.get(4));
        assertEquals("Withdraw 12345678 200", actual.get(5));
    }

    @Test
    void pdf_example() {
        input.add("Create savings 12345678 0.6");
        input.add("Deposit 12345678 700");
        input.add("Deposit 12345678 5000");
        input.add("creAte cHecKing 98765432 0.01");
        input.add("Deposit 98765432 300");
        input.add("Transfer 98765432 12345678 300");
        input.add("Pass 1");
        input.add("Create cd 23456789 1.2 2000");
        List<String> actual = masterControl.start(input);

        assertEquals(5, actual.size());
        assertEquals("Savings 12345678 1000.50 0.60", actual.get(0));
        assertEquals("Deposit 12345678 700", actual.get(1));
        assertEquals("Transfer 98765432 12345678 300", actual.get(2));
        assertEquals("Cd 23456789 2000.00 1.20", actual.get(3));
        assertEquals("Deposit 12345678 5000", actual.get(4));
    }

    @Test
    void create_test_for_final_output() {
        input.add("Create savings 12345678 0.6");
        List<String> actual = masterControl.start(input);
        assertEquals("Savings 12345678 0.00 0.60", actual.get(0));
    }
}
