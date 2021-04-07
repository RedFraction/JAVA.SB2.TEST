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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class API {

	@Autowired
	CurrencyRepo cr;

	@Autowired
	HistoryRepo hr;

	@GetMapping("/convert")
	private BigDecimal convert(
			@RequestParam String from,
			@RequestParam String to,
			@RequestParam String sum
	) throws ParserConfigurationException, SAXException, ParseException, IOException {

		if (!cr.existsByDate(DateUtils.getCurrentDateWOTime())) {
			cr.saveAll(new CurrenciesScraper().scrap(DateUtils.getCurrentDateWOTime()));
			cr.save(Currency.getBase());
		}

		Currency curFrom = cr.getFirstByCharCodeOrderByDateDesc(from).get();
		Currency curTo = cr.getFirstByCharCodeOrderByDateDesc(to).get();

		BigDecimal sumFrom = new BigDecimal(sum);

		BigDecimal sumTo = CurrencyConverter.convert(sumFrom, curFrom, curTo);

		ConvertHistory ch = new ConvertHistory();
		ch.setFrom(curFrom.getCharCode() + " (" + curFrom.getName() + ")");
		ch.setTo(curTo.getCharCode() + " (" + curTo.getName() + ")");
		ch.setValue_from(sumFrom);
		ch.setValue_to(sumTo);
		ch.setDate(DateUtils.getCurrentDateWOTime());

		hr.save(ch);

		return sumTo;
	}

	@GetMapping("/history")
	private List<ConvertHistory> history_post(
			@RequestParam String date,
			@RequestParam String from,
			@RequestParam String to
	) throws ParseException {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date parsed_date = dateFormat.parse(date);

		return hr.findAllByDateAndFromAndToOrderById(parsed_date, from, to);

	}

	@GetMapping("/drop-db")
	private void app(){
		cr.deleteAll();
		hr.deleteAll();
	}

}
