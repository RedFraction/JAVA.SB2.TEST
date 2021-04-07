package dev.xred.sb2test.controller;

import dev.xred.sb2test.model.Currency;
import dev.xred.sb2test.model.HistoryRepo;
import dev.xred.sb2test.util.CurrenciesScraper;
import dev.xred.sb2test.model.CurrencyRepo;
import dev.xred.sb2test.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/app")
public class Web {

	@Autowired
	CurrencyRepo cr;
	@Autowired
	HistoryRepo hr;


	@GetMapping
	private String app(Model model) throws ParserConfigurationException, SAXException, ParseException, IOException {

		if (!cr.existsByDate(DateUtils.getCurrentDateWOTime())) {
			cr.saveAll(new CurrenciesScraper().scrap(DateUtils.getCurrentDateWOTime()));
			cr.save(Currency.getBase());
		}

		model.addAttribute("currencies", cr.findAll());
		return "index";
	}

	@GetMapping("/today")
	private String today(Model model) throws ParserConfigurationException, SAXException, ParseException, IOException {

		if (!cr.existsByDate(DateUtils.getCurrentDateWOTime())) {
			cr.saveAll(new CurrenciesScraper().scrap(DateUtils.getCurrentDateWOTime()));
			cr.save(Currency.getBase());
		}

		model.addAttribute("currencies", cr.findAllByDate(DateUtils.getCurrentDateWOTime()));

		return "today";
	}

	@GetMapping("/history")
	private String history(Model model){
		Date current = DateUtils.getCurrentDateWOTime();

		model
			.addAttribute("from", hr.getFromDistinct().stream().sorted().collect(Collectors.toList()))
			.addAttribute("to", hr.getToDistinct().stream().sorted().collect(Collectors.toList()))
			.addAttribute("history", hr.getConvertHistories(current, 100));

		return "history";
	}

}
