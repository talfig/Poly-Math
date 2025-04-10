import javafx.util.Pair;
import java.util.*;

public class Polynom {
    private ArrayList<Pair<Double, Integer>> poly;

    public Polynom(ArrayList<Pair<Double, Integer>> poly) {
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
                    Pair<Double, Integer> pair = new Pair<>(coeffs[indices[i]], exps[indices[i]]);
                    poly.add(pair);

                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Helper method to process polynomial terms and update the map
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
            coeffs[i] = -poly.get(i).getKey();
            exps[i] = poly.get(i).getValue();
        }

        return new Polynom(coeffs, exps);
    }

    public Polynom minus(Polynom other) {
        Polynom negateOther = other.negate();
        return this.plus(negateOther);
    }

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

                // Add the differentiated term as a string in the form "coefficient, exponent"
                diffPoly.add(pair);
            }
        }

        return new Polynom(diffPoly);
    }

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

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } if (!(other instanceof Polynom)) {
            return false;
        } 
        
        Polynom that = (Polynom) other;
        if (this.poly.size() != that.poly.size()) {
            return false; // Different sizes, cannot be equal
        }

        for (int i = 0; i < this.poly.size(); i++) {
            Pair<Double, Integer> thisTerm = this.poly.get(i);
            Pair<Double, Integer> otherTerm = that.poly.get(i);

            // Check if the terms have the same coefficient and exponent
            if (!thisTerm.getKey().equals(otherTerm.getKey())
                || !thisTerm.getValue().equals(otherTerm.getValue())) {
                return false;
            }
        }

        return true; // All terms match
    }
}
