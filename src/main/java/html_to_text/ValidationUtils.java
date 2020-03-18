package html_to_text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtils {

    private static final String EMAIL_ADDRESS_REGEX = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
    private static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(EMAIL_ADDRESS_REGEX, Pattern.CASE_INSENSITIVE);
    private static final String PHONE_NUMBER_REGEX = "\\D*([2-9]\\d{2})(\\D*)([2-9]\\d{2})(\\D*)(\\d{4})\\D*";
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile(PHONE_NUMBER_REGEX);

    /**
     * A method to validate the input as an email address
     * @param emailAddress the string to be validated
     * @return true (valid) or false (invalid)
     */
    public static boolean validateEmailAddress(String emailAddress) {
        Matcher matcher = EMAIL_ADDRESS_PATTERN.matcher(emailAddress);
        return matcher.find();
    }

    /**
     * A method to validate the input as a phone number from the US or Canada
     * @param phoneNumber the string to be validated
     * @return true (valid) or false (invalid)
     */
    public static boolean validatePhoneNumber(String phoneNumber) {
        Matcher matcher = PHONE_NUMBER_PATTERN.matcher(phoneNumber);
        return matcher.find();
    }
}
