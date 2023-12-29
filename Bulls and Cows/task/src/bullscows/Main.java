package bullscows;
import java.util.*;

/**
 * The game creates a secret code using numbers from
 * 0-9 and letters from a-z based on the user's input.
 * The user then has to guess the code that was generated.
 * @author PoohL0ve (SWH)
 */
public class Main {
    public static String secretCode;
    public static char[] characters = {'a', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z'};
    public static int codeLength;
    public static int characterLength;
    public static void main(String[] args) {
        int giveLength = 0;
        int turn = 1;

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Input the length of the secret code:");
            giveLength = scanner.nextInt();
            scanner.nextLine();
            boolean isGood = getCodeLength(giveLength);
            if (isGood) {
                System.out.println("Input the number of possible symbols in the code:");
                int giveChars = scanner.nextInt();
                scanner.nextLine();
                boolean check = getNumberOfCharacters(giveChars);

                if (check) {
                    generateCode();

                    System.out.println("Okay, let's start a game!");
                    while(true) {
                        try {
                            System.out.println("Turn " + turn + " :");
                            String guessNumber = scanner.nextLine();
                            boolean correct = grader(guessNumber);

                            if (correct) {
                                break;
                            }

                        } catch (StringIndexOutOfBoundsException e) {
                            System.out.println("Grader: 0 bulls and 0 cows");
                        }
                        turn += 1;
                    }

                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: " + giveLength + " isn't a valid number.");
        }
    }

    /**
     * Obtains the length for the secret code, a value used
     * throughout the program.
     * @param len obtained from the user
     * @return true or false
     */
    public static boolean getCodeLength(int len) {
        boolean gotIt = false;
        if (len > 0 && len <= 36) {
            codeLength = len;
            gotIt = true;
        } else {
            System.out.println("Error: can't generate a secret number with a length of " + len +
                    " because there aren't enough unique digits.");
        }
        return gotIt;
    }

    /**
     * Obtains the possible number of characters that can
     * be used to create the secret code.
     * @param numChar integer obtained from the user
     * @return true or false
     */
    public static boolean getNumberOfCharacters(int numChar) {
        boolean lessThan = false;
        if (numChar > codeLength) {
            if (numChar <= 36) {
                for (int i = 0; i <= 36; i++) {
                    if (i == numChar) {
                        characterLength = numChar;
                        lessThan = true;
                        break;
                    }
                }
            } else {
                System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            }
        } else {
            System.out.println("Error: it's not possible to generate a code with a length of "
                            + codeLength + " with " + numChar + " unique symbols.");
            lessThan = false;
        }
        return lessThan;
    }

    /**
     * Compares the value entered by the user to the secret code.
     * If the user guesses the secret code it returns true,
     * otherwise false.
     * @param digit String input from the user
     * @return true or false
     */
    public static boolean grader(String digit) {
        boolean guessed = false;
        String secret = secretCode;
        int cows = 0;
        int bulls = 0;


        for (int i = 0; i < secret.length(); i++) {
            if (digit.charAt(i) == secret.charAt(i)) {
                bulls += 1;
            }
            else if (digit.charAt(i) != secret.charAt(i)) {
                if (digit.charAt(i) == secret.charAt(0) ||
                        digit.charAt(i) == secret.charAt(1) ||
                        digit.charAt(i) == secret.charAt(2)
                        || digit.charAt(i) == secret.charAt(3)) {
                    cows += 1;
                }
            }

        }
        // Creating plural or singular texts
        String strCows;
        String strBulls;
        if (bulls > 1) {
            strBulls = "bulls";
        }
        else {
            strBulls = "bull";
        }

        if (cows > 1) {
            strCows = "cows";
        }
        else {
            strCows = "cow";
        }

        // Output of the game

        if (bulls == codeLength) {
            System.out.printf("Grade: %d %s\n", bulls, strBulls);
            System.out.println("Congratulations! You guessed the secret code. ");
            guessed = true;
        }
        else if (bulls == 0) {
            if (cows > bulls) {
                System.out.printf("Grade: %d %s\n", cows, strCows);
            } else {
                System.out.println("Grade: None");
            }
        } else if (cows == 0 && bulls > cows && bulls < codeLength) {
            System.out.printf("Grade: %d %s\n", bulls, strBulls);
        }
        else {
            System.out.printf("Grade: %d %s and %d %s\n", bulls, strBulls, cows, strCows);
        }
        return guessed;
    }

    /**
     * Creates the secrete code using the code length
     * and the number of characters. If the number of characters
     * is more than 10, the secret code will contain both numbers
     * and digits, otherwise it will only contain digits (0-9).
     */
    public static void generateCode() {
        Random random = new Random();
        int randomNumber;
        int randomChar = 0;
        StringBuilder code = new StringBuilder();
        int letterPosition = characterLength;

        if (letterPosition > 10) {
            while (code.length() < codeLength) {
                randomNumber = random.nextInt(0, 10);
                randomChar = random.nextInt(0, letterPosition - 10);

                char letter = characters[randomChar];

                String value = code.toString();
                String strNumber = String.valueOf(randomNumber);
                String strChar = String.valueOf(letter);

                if ((!value.contains(strNumber)) && (!value.contains(strChar))) {
                    if (random.nextBoolean()) {
                        code.append(randomNumber);
                    } else {
                        code.append(letter);
                    }
                }
            }
            secretCode = code.toString();
            String star = "*";
            StringBuilder stars = new StringBuilder();
            for (int i = 0; i < codeLength; i++) {
                stars.append(star);
            }
            String finishedStars = stars.toString();
            char first = characters[0];
            char last = characters[letterPosition - 11];

            System.out.printf("The secret is prepared: %s (0-9, %c-%c).\n", finishedStars, first, last);
        } else if (letterPosition <= 10) {
            while (code.length() < codeLength) {
                randomNumber = random.nextInt(0, 10);
                String value = code.toString();
                String strNumber = String.valueOf(randomNumber);
                if ((!value.contains(strNumber))) {
                    code.append(randomNumber);
                }
            }
            secretCode = code.toString();
            StringBuilder stars = new StringBuilder();
            for (int i = 0; i < codeLength; i++) {
                stars.append("*");
            }
            String finalStars = stars.toString();
            System.out.printf("The secret is prepared: %s (0-9).\n", finalStars);
        }
    }
}
