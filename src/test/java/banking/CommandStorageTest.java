package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class CommandStorageTest {

    CommandStorage commandStorage;
    Bank bank;

    @BeforeEach
    void setUp() {
        commandStorage = new CommandStorage(bank);
    }

    @Test
    void get_specific_string() {
        commandStorage.add_invalid_commands("creat checking 12345678 1.0");

        boolean actual = commandStorage.find("creat checking 12345678 1.0");
        assertTrue(actual);
    }

    @Test
    void add_string_to_list() {
        commandStorage.add_invalid_commands("creat checking 12345678 1.0");
        ArrayList<String> actual = commandStorage.getInvalidCommands();
        assertEquals("creat checking 12345678 1.0", actual.get(0));
    }

    @Test
    void command_is_blank_string() {
        commandStorage.add_invalid_commands("");
        ArrayList<String> actual = commandStorage.getInvalidCommands();
        assertEquals("", actual.get(0));
    }

    @Test
    void get_all_commands() {
        commandStorage.add_invalid_commands("creat checking 12345678 1.0");
        commandStorage.add_invalid_commands("depositttt 12345678 20");
        commandStorage.add_invalid_commands("savings create 12345678 1.0");

        ArrayList<String> commandStorageList = commandStorage.getInvalidCommands();
        ArrayList<String> actualList = new ArrayList<>();

        actualList.add("creat checking 12345678 1.0");
        actualList.add("depositttt 12345678 20");
        actualList.add("savings create 12345678 1.0");

        assertEquals(actualList, commandStorageList);
    }

    @Test
    void start_with_empty_list() {
        ArrayList<String> commandStorageList = commandStorage.getInvalidCommands();

        assertEquals(0, commandStorageList.size());
    }

}
