package dev.xred.sb2test.util;

import dev.xred.sb2test.model.Currency;

import java.math.BigDecimal;
import java.math.MathContext;

public class CurrencyConverter {

	// Base currency CharCode
	private static final String BASE = "RUB";

	/**
	 * Converting one currency to other. Use Russian rubble as base
	 * @param sum  : Currency
	 * @param from : Currency
	 * @param to   : Currency
	 * @return
	 */
	public static BigDecimal convert(BigDecimal sum, Currency from, Currency to) {

		if (sum == null || from == null || to == null){
			throw new RuntimeException("Произошла ошибка во время конвертации");
		}

		BigDecimal result = sum;

		// Convert "from" to base
		if(!from.getCharCode().contains(BASE)) {
			result = sum.multiply(from.getValue().divide(from.getNominal() , 2, BigDecimal.ROUND_HALF_DOWN ) );
		}
		// Convert base to "to"
		if(!from.getCharCode().contains(BASE)) {
			result = result.divide(to.getValue().divide(to.getNominal()), 2, BigDecimal.ROUND_HALF_DOWN	);	}

		// СуммаКПереводу * Курс
		return result;
	}
}
/**
 <ValCurs Date="03.04.2021" name="Foreign Currency Market">
	 <Valute ID="R01035">
		 <NumCode>826</NumCode>
		 <CharCode>GBP</CharCode>
		 <Nominal>1</Nominal>
		 <Name>Фунт стерлингов Соединенного королевства</Name>
		 <Value>105,2856</Value>
	 </Valute>
	 <Valute ID="R01235">
		 <NumCode>840</NumCode>
		 <CharCode>USD</CharCode>
		 <Nominal>1</Nominal>
		 <Name>Доллар США</Name>
		 <Value>76,0734</Value>
	 </Valute>
 </ValCurs>
 **/