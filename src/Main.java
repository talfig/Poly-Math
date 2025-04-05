import java.util.*;

public class Main {
    private static void processPolynomials() {
        Scanner scanner = new Scanner(System.in);

        // Get the first polynomial
        System.out.println("Enter the number of terms for the first polynomial:");
        int n1 = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        ArrayList<String> poly1 = new ArrayList<>();
        System.out.println("Enter each term of the polynomial (in the form 'coefficient, exponent'):");
        for (int i = 0; i < n1; i++) {
            poly1.add(scanner.nextLine());
        }

        // Get the second polynomial
        System.out.println("Enter the number of terms for the second polynomial:");
        int n2 = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        ArrayList<String> poly2 = new ArrayList<>();
        System.out.println("Enter each term of the polynomial (in the form 'coefficient, exponent'):");
        for (int i = 0; i < n2; i++) {
            poly2.add(scanner.nextLine());
        }

        // Create Polynom objects
        Polynom p1 = new Polynom(poly1);
        Polynom p2 = new Polynom(poly2);

        // Display the polynomials
        System.out.println("First Polynomial: " + p1);
        System.out.println("Second Polynomial: " + p2);

        // Perform operations
        Polynom sum = p1.plus(p2);
        Polynom difference = p1.minus(p2);
        Polynom diff1 = p1.diff();
        Polynom diff2 = p2.diff();

        // Display results
        System.out.println("Sum of the polynomials: " + sum);
        System.out.println("Difference of the polynomials: " + difference);
        System.out.println("Differentiation of the first polynomial: " + diff1);
        System.out.println("Differentiation of the second polynomial: " + diff2);

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
