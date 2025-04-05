import java.util.*;

public class Polynom {
    private ArrayList<String> poly;

    public Polynom(ArrayList<String> poly) {
        this.poly = new ArrayList<>(poly);
    }

    public Polynom(double[] coeffs, int[] exps) {
        try {
            // Check if arrays are of the same length
            if (coeffs.length != exps.length) {
                throw new Exception("Coefficients and exponents arrays must have the same size.");
            }

            int n = coeffs.length;
            poly = new ArrayList<>();

            // Create array of indices [0, 1, 2, ..., n-1]
            Integer[] indices = new Integer[n];
            for (int i = 0; i < n; i++) {
                indices[i] = i;
            }

            // Sort indices by corresponding exps in descending order
            Arrays.sort(indices, (a, b) -> Integer.compare(exps[b], exps[a]));

            // Build polynomial array
            for (int i = 0; i < n; i++) {
                if (coeffs[indices[i]] != 0) {
                    String elem = coeffs[indices[i]] + ", " + exps[indices[i]];
                    poly.add(elem);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Helper method to parse the term string into a coefficient and exponent.
    private static double[] parseTerm(String term) {
        // Expected format: "coefficient, exponent"
        String[] parts = term.split(",");
        double coeff = Double.parseDouble(parts[0].trim());
        int exp = Integer.parseInt(parts[1].trim());
        return new double[]{coeff, exp}; // index 0 is coeff, index 1 is exp (as double)
    }

    // Helper method to process polynomial terms and update the map
    private static void addTermsToMap(Map<Integer, Double> map, ArrayList<String> poly) {
        for (String term : poly) {
            double[] parsedTerm = parseTerm(term);
            int exponent = (int) parsedTerm[1];
            double coefficient = parsedTerm[0];
            if (coefficient == 0) {
                return;
            }
            map.put(exponent, map.getOrDefault(exponent, 0.0) + coefficient);
        }
    }

    // Adds two polynomials and returns a new Polynom
    public Polynom plus(Polynom other) {
        Map<Integer, Double> map = new HashMap<>();

        // Add terms from both polynomials to the map using the helper method
        addTermsToMap(map, this.poly);
        addTermsToMap(map, other.poly);

        int n = map.size();
        double[] coeffs = new double[n];
        int[] exps = new int[n];
        int i = 0;

        for (Map.Entry<Integer, Double> e : map.entrySet()) {
            exps[i] = e.getKey();
            coeffs[i] = e.getValue();
            i++;
        }

        return new Polynom(coeffs, exps);
    }

    // Negates the coefficients of the polynomial
    private Polynom negate() {
        double[] coeffs = new double[poly.size()];
        int[] exps = new int[poly.size()];

        for (int i = 0; i < poly.size(); i++) {
            double[] parsed = parseTerm(poly.get(i));
            coeffs[i] = -parsed[0];
            exps[i] = (int) parsed[1];
        }

        return new Polynom(coeffs, exps);
    }

    public Polynom minus(Polynom other) {
        Polynom negateOther = other.negate();
        return this.plus(negateOther);
    }

    public Polynom diff() {
        ArrayList<String> diffPoly = new ArrayList<>();

        for (String term : this.poly) {
            double[] parsedTerm = parseTerm(term);
            double coeff = parsedTerm[0];
            int exp = (int) parsedTerm[1];

            // Differentiate: if the exponent is 0, remove the term.
            if (exp > 0) {
                double newCoeff = coeff * exp;
                int newExp = exp - 1;
                // Add the differentiated term as a string in the form "coefficient, exponent"
                diffPoly.add(newCoeff + ", " + newExp);
            }
        }

        return new Polynom(diffPoly);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Iterate over the terms of the polynomial
        for (int i = 0; i < poly.size(); i++) {
            double[] parsedTerm = parseTerm(poly.get(i));
            double coeff = parsedTerm[0];
            int exp = (int) parsedTerm[1];

            if (coeff == 0) {
                continue;
            }
;
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
                    sb.append(coeff == -1 ? "-" : coeff);
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

    public boolean equals(Polynom other) {
        if (other == null || this.poly.size() != other.poly.size()) {
            return false; // Different sizes, cannot be equal
        }

        for (int i = 0; i < this.poly.size(); i++) {
            double[] thisTerm = parseTerm(this.poly.get(i));
            double[] otherTerm = parseTerm(other.poly.get(i));

            // Check if the terms have the same coefficient and exponent
            if (thisTerm[0] != otherTerm[0] || thisTerm[1] != otherTerm[1]) {
                return false;
            }
        }

        return true; // All terms match
    }
}
