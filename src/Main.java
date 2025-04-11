import java.util.*;

/**
 * This class handles the input, processing, and operations of polynomials.
 */
public class Main {
    // Scanner to take input from the user
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Prompts the user to input terms of a polynomial in the format "coefficient, exponent".
     * The input is stored in the provided arrays.
     *
     * @param n       The number of terms in the polynomial.
     * @param coeffs  Array to store the coefficients of the polynomial.
     * @param exps    Array to store the exponents of the polynomial.
     */
    private static void getPolynomialInput(int n, double[] coeffs, int[] exps) {
        System.out.println("Enter each term of the polynomial (in the form 'coefficient, exponent'):");
        System.out.println("Press Enter after each term.");
        for (int i = 0; i < n; i++) {
            boolean validInput = false;
            while (!validInput) {
                String term = scanner.nextLine();
                String[] parts = term.split(",");

                // Ensure there are exactly two parts (coefficient and exponent)
                if (parts.length == 2) {
                    try {
                        // Parse and store the coefficient and exponent
                        coeffs[i] = Double.parseDouble(parts[0].trim());
                        exps[i] = Integer.parseInt(parts[1].trim());
                        validInput = true; // If parsing succeeds, input is valid
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input! Make sure to enter a number for both coefficient and exponent.");
                    }
                } else {
                    System.out.println("Invalid input! Please enter the term in the format 'coefficient, exponent'.");
                }
            }
        }
    }

    /**
     * Prompts the user for the number of terms in a polynomial and ensures the input is a valid positive integer.
     * If the input is invalid (non-integer or non-positive integer), the user is re-prompted until valid input is provided.
     *
     * @param prompt The message to display to the user asking for the number of terms.
     * @return The valid number of terms entered by the user.
     */
    private static int getNumberOfTerms(String prompt) {
        int n = 0;
        boolean validInput = false;

        while (!validInput) {
            System.out.println(prompt);
            try {
                // Parse and store the number of terms
                n = Integer.parseInt(scanner.nextLine());
                validInput = true;  // Input is valid, exit loop
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
        return n;
    }

    /**
     * Handles the input and operations of two polynomials, including their sum, difference, and differentiation.
     */
    private static void processPolynomials() {
        // Get the first polynomial
        int n1 = getNumberOfTerms("Enter the number of terms for the first polynomial:");
        double[] coeffs1 = new double[n1];
        int[] exps1 = new int[n1];
        getPolynomialInput(n1, coeffs1, exps1);

        // Get the second polynomial
        int n2 = getNumberOfTerms("Enter the number of terms for the second polynomial:");
        double[] coeffs2 = new double[n2];
        int[] exps2 = new int[n2];
        getPolynomialInput(n2, coeffs2, exps2);

        // Create Polynom objects
        Polynom p1 = new Polynom(coeffs1, exps1);
        Polynom p2 = new Polynom(coeffs2, exps2);

        // Display the polynomials
        System.out.println("First Polynomial: " + p1);
        System.out.println("Second Polynomial: " + p2);

        // Perform operations and display results
        System.out.println("Sum: (" + p1 + ") + (" + p2 + ") = " + p1.plus(p2));
        System.out.println("Difference: (" + p1 + ") - (" + p2 + ") = " + p1.minus(p2));
        System.out.println("Differentiation of the first polynomial: (" + p1 + ")' = " + p1.diff());
        System.out.println("Differentiation of the second polynomial: (" + p2 + ")' = " + p2.diff());

        // Check equality
        if (p1.equals(p2)) {
            System.out.println("The two polynomials are equal.");
        } else {
            System.out.println("The two polynomials are not equal.");
        }

        // Close the scanner to prevent resource leak
        scanner.close();
    }

    /**
     * Main method to execute polynomial operations.
     *
     * @param args Command-line arguments (not used in this program).
     */
    public static void main(String[] args) {
        // Call the method to interact with the user and perform polynomial operations
        processPolynomials();
    }
}
