package it.polimi.ingsw.cg_30;

import static org.junit.Assert.*;

import org.junit.Test;

public class FractionTest {

	@Test
	public void testGetNumerator() {
		int num = 3, exp_num = 3;
		Fraction f = new Fraction(num, 4);
		assertEquals(exp_num, f.getNumerator());
	}

}
