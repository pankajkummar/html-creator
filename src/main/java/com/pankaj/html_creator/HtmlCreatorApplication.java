package com.pankaj.html_creator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HtmlCreatorApplication implements CommandLineRunner {

	@Autowired
	private ExcelToHtmlConverter excelToHtmlConverter;

	public static void main(String[] args) {
		SpringApplication.run(HtmlCreatorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		excelToHtmlConverter.convert();
	}
}
