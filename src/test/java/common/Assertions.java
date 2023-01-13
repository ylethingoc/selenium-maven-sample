package common;

import utilities.Loggers;

public class Assertions {
    public static int result;

    public static void assertTrueFalse(boolean expected, boolean actual) {
        if (expected == actual) {
            Loggers.logPass("The result is matched, <br/>Expected Result: " + expected + "<br/>Actual Result: " + actual);
        } else {
            try {
                Loggers.logFail("The result did not match, <br/>Expected Result: " + expected + "<br/>Actual Result: " + actual);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
