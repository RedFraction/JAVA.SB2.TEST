package dev.xred.sb2test.util;

import dev.xred.sb2test.model.Currency;
import dev.xred.sb2test.util.DateUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CurrenciesScraper {

	private final String CBR_URL = "http://www.cbr.ru/scripts/XML_daily.asp";
	private final String DATE_PARAM = "?date_req=";

	public List<Currency> scrap(Date forDate) throws IOException, ParserConfigurationException, SAXException, ParseException {

		// Init
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(CBR_URL + DATE_PARAM + DateUtils.cbrFormat(forDate));
		document.getDocumentElement().normalize();

		// Scrapping
		Element root = document.getDocumentElement();
		Date date = new SimpleDateFormat("dd.MM.yyyy").parse(root.getAttribute("Date"));
		NodeList nList = document.getElementsByTagName("Valute");
		List<Currency> currList = new ArrayList<>();

		for (int temp = 0; temp < nList.getLength(); temp++){

			Node node = nList.item(temp);
			if (node.getNodeType() == Node.ELEMENT_NODE)
			{
				Currency currency = new Currency();

				Element e = (Element) node;

				// ID="R01035"
				currency.setIdent(e.getAttribute("ID"));
				// <NumCode>826</NumCode>
				currency.setNumCode(new BigDecimal(e.getElementsByTagName("NumCode").item(0).getTextContent()));
				// <CharCode>GBP</CharCode>
				currency.setCharCode(e.getElementsByTagName("CharCode").item(0).getTextContent());
				// <Nominal>1</Nominal>
				currency.setNominal(new BigDecimal(e.getElementsByTagName("Nominal").item(0).getTextContent()));
				// <Name>Фунт стерлингов Соединенного королевства</Name>
				currency.setName(e.getElementsByTagName("Name").item(0).getTextContent());
				// <Value>105,2856</Value>
				currency.setValue(new BigDecimal(e.getElementsByTagName("Value").item(0).getTextContent().replace(',', '.')));
				// <ValCurs Date="03.04.2021"...>
				currency.setDate(date);


				currList.add(currency);
			}
		}

		return currList;
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
	</ValCurs>
 **/