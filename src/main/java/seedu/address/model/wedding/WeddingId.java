package seedu.address.model.wedding;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Wedding's ID in the system.
 * Format: "W" followed by a positive integer (e.g., "W1", "W42").
 * Guarantees: immutable; is valid as declared in {@link #isValidWeddingId(String)}.
 */
public class WeddingId {
    public static final String MESSAGE_CONSTRAINTS =
            "Wedding ID must start with 'W' followed by one or more digits, e.g. 'W1', 'W15'.";

    public static final String MESSAGE_NEGATIVE_CONSTRAINTS =
            "Wedding ID cannot be negative or zero. Please use a positive number.";

    public static final String VALIDATION_REGEX = "^W\\d+$";

    public final String value;
    public final int valueInt;

    /**
     * Constructs a {@code WeddingId}.
     *
     * @param id A valid ID string (e.g., "W10").
     */
    public WeddingId(String id) {
        requireNonNull(id);
        checkArgument(isValidWeddingId(id), MESSAGE_CONSTRAINTS);
        this.value = id;
        this.valueInt = Integer.parseInt(id.substring(1)); // Parse the numeric part only
    }

    /**
     * Returns true if a given string is a valid wedding ID.
     */
    public static boolean isValidWeddingId(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof WeddingId
                && value.equals(((WeddingId) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
