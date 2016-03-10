package fr.unice.polytech.ogl.islbd.objective;

/**
 * Makes a couple of attributes (an amount associated to a condition)
 * @author user
 *
 */
public class AmountCondPair {
    private Amount amount;
    private Cond cond;

    /**
     * Constructor
     * @param amount
     * @param cond
     */
    public AmountCondPair(Amount amount, Cond cond) {
        this.amount = amount;
        this.cond = cond;
    }

    /**
     * Return the amount
     * @return amount
     */
    public Amount getAmount() {
        return amount;
    }

    /**
     * Return the condition
     * @return cond
     */
    public Cond getCond() {
        return cond;
    }

    @Override
    public String toString() {
        return this.amount + " / " + this.cond;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AmountCondPair that = (AmountCondPair) o;

        if (amount != that.amount) return false;
        return cond == that.cond;

    }

    @Override
    public int hashCode() {
        int result = amount != null ? amount.hashCode() : 0;
        result = 31 * result + (cond != null ? cond.hashCode() : 0);
        return result;
    }
}