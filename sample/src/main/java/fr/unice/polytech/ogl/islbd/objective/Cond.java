package fr.unice.polytech.ogl.islbd.objective;

/**
 * All the values which determine if this resource is hard to exploit or not
 * @author user
 *
 */
public enum Cond {
    Unknown, Easy, Fair, Harsh;

    /**
     * Return the cond
     * @param cond
     * @return Cond
     */
    public static Cond getCond(String cond) {
        for (Cond c : Cond.values()) {
            if (cond.toUpperCase().equals(c.toString().toUpperCase())) {
                return c;
            }
        }
        return null;
    }
    
}
