package fr.unice.polytech.ogl.islbd.objective;

/**
 * Contains all the amount which can be returned by the island
 * @author user
 *
 */
public enum Amount {
    Unknown, Absent, Low, Medium, High;
    //TODO Put in UpperCase ? (it's final)
    /**
     * Return the amount associated to 
     * @param amount
     * @return
     */
    public static Amount getAmount(String amount) {
        for (Amount a : Amount.values()) {
            if (amount.toUpperCase().equals(a.toString().toUpperCase())) {
                return a;
            }
        }
        return null;
    }
}
