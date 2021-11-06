package com.listing.listingAPI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
@EnableScheduling
public class ListingApiApplication {

	private static final Logger log = LoggerFactory.getLogger(ListingApiApplication.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");

	public static void main(String[] args) {
		SpringApplication.run(ListingApiApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> fetchCrypto();
	}

	@Scheduled(fixedRate = 3000)
	public void fetchCrypto() {

		RestTemplate restTemplate = new RestTemplate();

		Crypto[] crypto = restTemplate.getForObject("https://api.n.exchange/en/api/v1/price/BTCLTC/latest/?format=json&market_code=nex", Crypto[].class);

		StringBuilder builder = new StringBuilder();
		for (Crypto data : crypto) {
			builder.append(data);
		}

		String cryptoData = builder.toString();

		log.info("The time is now {}", dateFormat.format(new Date()));
		log.info(cryptoData);
	}

}








