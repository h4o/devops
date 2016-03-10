package fr.unice.polytech.ogl.islbd.objective;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AmountCondPairTest {

    private AmountCondPair acp;
    private AmountCondPair acpNull;
    private AmountCondPair acpBis;

    @Before
    public void setup() {
        acp = new AmountCondPair(Amount.Low, Cond.Easy);
        acpNull = new AmountCondPair(null, null);
        acpBis = new AmountCondPair(Amount.Low, Cond.Easy);
    }

    @Test
    public void testAmountCondPair() {
        assertEquals(Amount.Low, acp.getAmount());
        assertEquals(Cond.Easy, acp.getCond());

        assertNull(acpNull.getAmount());
        assertNull(acpNull.getCond());

        assertEquals("Low / Easy", acp.toString());

        assertEquals(acp, acpBis);

        assertEquals(acp.hashCode(), acpBis.hashCode());
    }

}