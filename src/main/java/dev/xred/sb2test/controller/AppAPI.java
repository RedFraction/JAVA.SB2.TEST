package dev.xred.sb2test.controller;

import dev.xred.sb2test.model.ConvertHistory;
import dev.xred.sb2test.model.Currency;
import dev.xred.sb2test.model.CurrencyRepo;
import dev.xred.sb2test.model.HistoryRepo;
import dev.xred.sb2test.util.CurrenciesScraper;
import dev.xred.sb2test.util.CurrencyConverter;
import dev.xred.sb2test.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AppAPI {

	@Autowired
	CurrencyRepo cr;

	@Autowired
	HistoryRepo hr;

	@GetMapping("/convert")
	private BigDecimal convert(
			@RequestParam String from,
			@RequestParam String to,
			@RequestParam String sum ){

		if (!cr.existsByDate(DateUtils.getCurrentDateWOTime())) {
			try {
				cr.saveAll(new CurrenciesScraper().scrap(DateUtils.getCurrentDateWOTime()));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			cr.save(Currency.getBase());
		}

		Currency curFrom = cr.findCurrencyByCharCodeOrderByDateDesc(from).get();
		Currency curTo = cr.findCurrencyByCharCodeOrderByDateDesc(to).get();

		BigDecimal sumFrom = new BigDecimal(sum);

		BigDecimal sumTo = CurrencyConverter.convert(sumFrom, curFrom, curTo);

		ConvertHistory ch = new ConvertHistory();
		ch.setFrom(curFrom.getCharCode() + "-" + curFrom.getName());
		ch.setTo(curTo.getCharCode() + "-" + curTo.getName());
		ch.setValue_from(sumFrom);
		ch.setValue_to(sumTo);
		ch.setDate(DateUtils.getCurrentDateWOTime());

		hr.save(ch);

		return sumTo;
	}

	@GetMapping("/drop-db")
	private void app(){
		cr.deleteAll();
		hr.deleteAll();
	}

}
