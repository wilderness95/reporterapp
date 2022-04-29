package hu.wilderness.reporterapp.utils;

public class passwordGenerator {

    public static String generateRandomPassword(int length) {
        String randomPassword = "";

        for (int j = 0; j < length; j++) {
            randomPassword += randomCharacter();
        }
        System.out.println(randomPassword);
        return randomPassword;
    }

    public static char randomCharacter() {

        int rand = (int) (Math.random() * 62);
        if (rand <= 9) {
            int number = rand + 48;
            return (char) (number);
        } else if (rand <= 35) {
            int uppercase = rand + 55;
            return (char) (uppercase);
        } else {
            int lowercase = rand + 61;
            return (char) (lowercase);
        }
    }

}

