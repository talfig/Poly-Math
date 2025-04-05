import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    private static void getPolynomialInput(int n, double[] coeffs, int[] exps) {
        System.out.println("Enter each term of the polynomial (in the form 'coefficient, exponent'):");
        System.out.println("Press Enter after each term.");
        for (int i = 0; i < n; i++) {
            boolean validInput = false;
            while (!validInput) {
                String term = scanner.nextLine();
                String[] parts = term.split(",");

                if (parts.length == 2) {
                    try {
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

    private static void processPolynomials() {
        // Get the first polynomial using the new function
        System.out.println("Enter the number of terms for the first polynomial:");
        int n1 = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        double[] coeffs1 = new double[n1];
        int[] exps1 = new int[n1];
        getPolynomialInput(n1, coeffs1, exps1);

        // Get the second polynomial using the new function
        System.out.println("Enter the number of terms for the second polynomial:");
        int n2 = scanner.nextInt();
        scanner.nextLine();  // Consume newline
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

        scanner.close();
    }

    public static void main(String[] args) {
        // Call the method to interact with user and perform polynomial operations
        processPolynomials();
    }
}
