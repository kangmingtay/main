package seedu.addressbook.data.employee;

import seedu.addressbook.data.exception.IllegalValueException;

//@@author kianhong95
/**
 * Represents an Employee's phone number in the Rms.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class EmployeePhone {

    public static final String EXAMPLE = "91234567";
    public static final String MESSAGE_PHONE_CONSTRAINTS = "Employee phone numbers should only contain numbers"
                                                            + "and must be 8 digits";
    public static final String PHONE_VALIDATION_REGEX = "\\d{8}";

    public final String value;

    /**
    * Empty constructor
     */
    public EmployeePhone() {
        this.value = "";
    }

    /**
     * Validates given phone number.
     *
     * @throws IllegalValueException if given phone string is invalid.
     */
    public EmployeePhone(String phone) throws IllegalValueException {
        String trimmedPhone = phone.trim();
        if (!isValidPhone(trimmedPhone)) {
            throw new IllegalValueException(MESSAGE_PHONE_CONSTRAINTS);
        }
        this.value = trimmedPhone;
    }

    /**
     * Checks if a given string is a valid employee phone number.
     */
    public static boolean isValidPhone(String test) {
        return test.matches(PHONE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmployeePhone // instanceof handles nulls
                && this.value.equals(((EmployeePhone) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
