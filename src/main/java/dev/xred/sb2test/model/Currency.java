package dev.xred.sb2test.model;

import dev.xred.sb2test.util.DateUtils;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "currencies_tbl")
public class Currency implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "curr_id")
	private Integer id;

	@Column(name = "curr_ident")
	private String ident;

	@Column(name = "curr_numcode")
	private BigDecimal numCode;

	@Column(name = "curr_charcode")
	private String charCode;

	@Column(name = "curr_nominal")
	private BigDecimal nominal;

	@Column(name = "curr_name")
	private String name;

	@Column(name = "curr_value")
	private BigDecimal value;

	@Column(name = "curr_date")
	private Date date;

	/**
	 * Return base currency. For base currency used Russian Rubble.
	 */
	public static Currency getBase(){
		Currency rub = new Currency();
		rub.setNumCode(new BigDecimal(643));
		rub.setCharCode("RUB");
		rub.setNominal(new BigDecimal(1));
		rub.setName("Российский рубль");
		rub.setValue(new BigDecimal(1));
		rub.setDate(DateUtils.getCurrentDateWOTime());

		return rub;
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