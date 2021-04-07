package dev.xred.sb2test.util;

import dev.xred.sb2test.model.Currency;

import java.math.BigDecimal;

public class CurrencyConverter {

	// Base currency CharCode
	private static final String BASE = "RUB";

	/**
	 * Converting one currency to other. Use Russian rubble as base
	 * @param sum  : Currency
	 * @param from : Currency
	 * @param to   : Currency
	 * @return converted value
	 */
	public static BigDecimal convert(BigDecimal sum, Currency from, Currency to) {

		if (sum == null || from == null || to == null){
			throw new RuntimeException("Произошла ошибка во время конвертации");
		}

		BigDecimal result = sum;

		if(from.equals(to)){
			return sum;
		}
		// Convert "from" to base
		if(!from.getCharCode().contains(BASE))
			result = sum.multiply(from.getValue().divide(from.getNominal() , 5, 5 ) );

		// Convert base to "to"
		if(!to.getCharCode().contains(BASE))
			result = result.divide(to.getValue().divide(to.getNominal()), 5, 5);

		// СуммаКПереводу * Курс
		return result;
	}
}