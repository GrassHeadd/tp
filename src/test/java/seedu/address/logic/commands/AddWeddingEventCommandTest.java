package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingDate;
import seedu.address.model.wedding.WeddingLocation;
import seedu.address.model.wedding.WeddingName;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code AddWeddingEventCommand}.
 */
public class AddWeddingEventCommandTest {

    @Test
    public void constructor_nullWedding_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddWeddingEventCommand(null));
    }

    @Test
    public void execute_weddingAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingWeddingAdded modelStub = new ModelStubAcceptingWeddingAdded();
        // Suppose your new Wedding constructor looks like:
        // public Wedding(WeddingName name, WeddingDate date, WeddingLocation location) { ... }

        Wedding validWedding = new Wedding(
                new WeddingName("John & Jane's Wedding"),
                new WeddingDate("20-Feb-2025"),
                new WeddingLocation("Grand Ballroom")
        );

        CommandResult commandResult = new AddWeddingEventCommand(validWedding).execute(modelStub);

        assertEquals(String.format(AddWeddingEventCommand.MESSAGE_SUCCESS, validWedding),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validWedding), modelStub.weddingsAdded);
    }

    @Test
    public void execute_duplicateWedding_throwsCommandException() {
        // Same wedding details
        Wedding validWedding = new Wedding(
                new WeddingName("John & Jane's Wedding"),
                new WeddingDate("20-Feb-2025"),
                new WeddingLocation("Grand Ballroom")
        );
        AddWeddingEventCommand addWeddingCommand = new AddWeddingEventCommand(validWedding);
        ModelStub modelStub = new ModelStubWithWedding(validWedding);

        // Expecting a CommandException because modelStub already has `validWedding`
        assertThrows(CommandException.class,
                AddWeddingEventCommand.MESSAGE_DUPLICATE_WEDDING, () -> addWeddingCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Wedding aliceWedding = new Wedding(
                new WeddingName("Alice & Bob's Wedding"),
                new WeddingDate("21-Feb-2025"),
                new WeddingLocation("Central Park")
        );
        Wedding bobWedding = new Wedding(
                new WeddingName("Bob & Charlie's Wedding"),
                new WeddingDate("22-Feb-2025"),
                new WeddingLocation("Garden")
        );

        AddWeddingEventCommand addAliceWeddingCommand = new AddWeddingEventCommand(aliceWedding);
        AddWeddingEventCommand addBobWeddingCommand = new AddWeddingEventCommand(bobWedding);

        // same object -> returns true
        assertTrue(addAliceWeddingCommand.equals(addAliceWeddingCommand));

        // same values -> returns true
        AddWeddingEventCommand addAliceWeddingCommandCopy = new AddWeddingEventCommand(aliceWedding);
        assertTrue(addAliceWeddingCommand.equals(addAliceWeddingCommandCopy));

        // different types -> returns false
        assertFalse(addAliceWeddingCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceWeddingCommand.equals(null));

        // different wedding -> returns false
        assertFalse(addAliceWeddingCommand.equals(addBobWeddingCommand));
    }

    // ============================ MODEL STUBS ============================

    /**
     * A default Model stub that has all methods throwing {@code AssertionError}.
     * This prevents unimplemented methods from accidentally being called in tests.
     */
    private class ModelStub implements Model {

        @Override
        public boolean hasWedding(Wedding wedding) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addWedding(Wedding wedding) {
            throw new AssertionError("This method should not be called.");
        }

        // --- Person-related methods (not used by this command test) ---
        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        // --- Wedding-related filter methods (if your Model has them) ---
        @Override
        public ObservableList<Wedding> getFilteredWeddingList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredWeddingList(Predicate<Wedding> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        // --- AddressBook / UserPrefs methods (not used by this command test) ---
        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(java.nio.file.Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public java.nio.file.Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(seedu.address.commons.core.GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public seedu.address.commons.core.GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that already contains one wedding.
     */
    private class ModelStubWithWedding extends ModelStub {
        private final Wedding wedding;

        ModelStubWithWedding(Wedding wedding) {
            requireNonNull(wedding);
            this.wedding = wedding;
        }

        @Override
        public boolean hasWedding(Wedding wedding) {
            requireNonNull(wedding);
            // Use isSameWedding if your Wedding class has such a method
            return this.wedding.isSameWedding(wedding);
        }
    }

    /**
     * A Model stub that always accepts the wedding being added.
     */
    private class ModelStubAcceptingWeddingAdded extends ModelStub {
        final ArrayList<Wedding> weddingsAdded = new ArrayList<>();

        @Override
        public boolean hasWedding(Wedding wedding) {
            requireNonNull(wedding);
            return weddingsAdded.stream().anyMatch(wedding::isSameWedding);
        }

        @Override
        public void addWedding(Wedding wedding) {
            requireNonNull(wedding);
            weddingsAdded.add(wedding);
        }
    }
}
