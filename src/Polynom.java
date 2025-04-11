import javafx.util.Pair;
import java.util.*;

/**
 * This class represents a polynomial, supporting operations such as addition, subtraction, and differentiation.
 * Polynomials are stored as a list of terms, where each term is represented by a coefficient and an exponent.
 */
public class Polynom {
    // List to store terms of the polynomial as pairs of (coefficient, exponent)
    private ArrayList<Pair<Double, Integer>> poly;

    /**
     * Constructor that initializes the polynomial from an existing list of pairs (coefficient, exponent).
     *
     * @param poly The list of pairs representing the polynomial terms.
     */
    public Polynom(ArrayList<Pair<Double, Integer>> poly) {
        this.poly = new ArrayList<>(poly);
    }

    /**
     * Helper method to add terms to a map, combining terms with the same exponent.
     *
     * @param map  The map where terms are stored with the exponent as the key.
     * @param poly The polynomial terms to be added to the map.
     */
    private static void addTermsToMap(Map<Integer, Double> map, ArrayList<Pair<Double, Integer>> poly) {
        for (Pair<Double, Integer> term : poly) {
            double coefficient = term.getKey();
            int exponent = term.getValue();
            if (coefficient == 0) {
                return;
            }
            map.put(exponent, map.getOrDefault(exponent, 0.0) + coefficient);
        }
    }

    /**
     * Constructor that initializes the polynomial from arrays of coefficients and exponents.
     * Coefficients are sorted in descending order of exponents.
     *
     * @param coeffs Array of coefficients for the polynomial.
     * @param exps   Array of exponents corresponding to the coefficients.
     */
    public Polynom(double[] coeffs, int[] exps) {
        try {
            if (coeffs.length != exps.length) {
                throw new Exception("Coefficients and exponents arrays must have the same size.");
            }

            // Collect non-zero terms as (coefficient, exponent) pairs
            ArrayList<Pair<Double, Integer>> temp = new ArrayList<>();
            for (int i = 0; i < coeffs.length; i++) {
                if (coeffs[i] != 0) {
                    temp.add(new Pair<>(coeffs[i], exps[i]));
                }
            }

            // Merge terms with the same exponent
            Map<Integer, Double> map = new HashMap<>();
            addTermsToMap(map, temp);

            // Remove zero-coefficient terms
            map.entrySet().removeIf(entry -> entry.getValue() == 0.0);

            // Sort terms by exponent in descending order
            List<Map.Entry<Integer, Double>> sortedEntries = new ArrayList<>(map.entrySet());
            sortedEntries.sort((a, b) -> Integer.compare(b.getKey(), a.getKey()));

            // Build the internal polynomial list
            poly = new ArrayList<>();
            for (Map.Entry<Integer, Double> entry : sortedEntries) {
                poly.add(new Pair<>(entry.getValue(), entry.getKey()));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Adds this polynomial to another polynomial and returns the resulting polynomial.
     *
     * @param other The polynomial to be added.
     * @return A new polynomial representing the sum of this polynomial and the other polynomial.
     */
    public Polynom plus(Polynom other) {
        Map<Integer, Double> map = new HashMap<>();

        // Add terms from both polynomials to the map using the helper method
        addTermsToMap(map, this.poly);
        addTermsToMap(map, other.poly);

        int n = map.size();
        double[] coeffs = new double[n];
        int[] exps = new int[n];
        int i = 0;

        // Convert map entries to arrays for the new polynomial
        for (Map.Entry<Integer, Double> e : map.entrySet()) {
            exps[i] = e.getKey();
            coeffs[i] = e.getValue();
            i++;
        }

        return new Polynom(coeffs, exps);
    }

    /**
     * Negates the coefficients of the polynomial and returns the resulting polynomial.
     *
     * @return A new polynomial with negated coefficients.
     */
    private Polynom negate() {
        double[] coeffs = new double[poly.size()];
        int[] exps = new int[poly.size()];

        for (int i = 0; i < poly.size(); i++) {
            coeffs[i] = -poly.get(i).getKey();
            exps[i] = poly.get(i).getValue();
        }

        return new Polynom(coeffs, exps);
    }

    /**
     * Subtracts another polynomial from this polynomial and returns the resulting polynomial.
     *
     * @param other The polynomial to be subtracted.
     * @return A new polynomial representing the difference of this polynomial and the other polynomial.
     */
    public Polynom minus(Polynom other) {
        Polynom negateOther = other.negate();
        return this.plus(negateOther);
    }

    /**
     * Differentiates this polynomial and returns the resulting polynomial.
     * The degree of each term is reduced by 1, and the coefficient is multiplied by the original exponent.
     *
     * @return A new polynomial representing the derivative of this polynomial.
     */
    public Polynom diff() {
        ArrayList<Pair<Double, Integer>> diffPoly = new ArrayList<>();

        for (Pair<Double, Integer> term : this.poly) {
            double coeff = term.getKey();
            int exp = term.getValue();

            // Differentiate: if the exponent is 0, remove the term.
            if (exp > 0) {
                double newCoeff = coeff * exp;
                int newExp = exp - 1;
                Pair<Double, Integer> pair = new Pair<>(newCoeff, newExp);

                // Add the differentiated term
                diffPoly.add(pair);
            }
        }

        return new Polynom(diffPoly);
    }

    /**
     * Converts the polynomial to a string representation.
     *
     * @return A string representation of the polynomial.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Iterate over the terms of the polynomial
        for (int i = 0; i < poly.size(); i++) {
            double coeff = poly.get(i).getKey();
            int exp = poly.get(i).getValue();

            if (coeff == 0) {
                continue;
            }

            // Handle the formatting for each term
            if (i > 0) {
                // Add a '+' or '-' between terms (based on the sign of the coefficient)
                sb.append(coeff > 0 ? " + " : " - ");
                if (exp != 0) {
                    sb.append(Math.abs(coeff) == 1 ? "" : Math.abs(coeff));
                } else {
                    sb.append(Math.abs(coeff));
                }
            } else {
                if (exp != 0) {
                    if (coeff != 1) {
                        sb.append(coeff == -1 ? "-" : coeff);
                    }
                } else {
                    sb.append(coeff);
                }
            }

            if (exp != 0) {
                sb.append(exp == 1 ? "x" : "x^" + exp);
            }
        }

        return sb.length() > 0 ? sb.toString() : "0";
    }

    /**
     * Checks whether this polynomial is equal to another polynomial.
     *
     * @param other The other object to compare.
     * @return True if the polynomials are equal, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Polynom)) {
            return false;
        }

        Polynom that = (Polynom) other;

        // Check if polynomials have the same size
        if (this.poly.size() != that.poly.size()) {
            return false;
        }

        // Check if each term has the same coefficient and exponent
        for (int i = 0; i < this.poly.size(); i++) {
            Pair<Double, Integer> thisTerm = this.poly.get(i);
            Pair<Double, Integer> otherTerm = that.poly.get(i);

            if (!thisTerm.getKey().equals(otherTerm.getKey()) ||
                    !thisTerm.getValue().equals(otherTerm.getValue())) {
                return false;
            }
        }

        return true; // All terms match
    }
}
