package com.sh.migros;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sh.migros.model.Store;
import com.sh.migros.service.StoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@SpringBootApplication
public class MigrosApplication implements CommandLineRunner {
	private final Logger logger = LoggerFactory.getLogger(MigrosApplication.class);
	private final StoreService storeService;

	public MigrosApplication(StoreService storeService) {
		this.storeService = storeService;
	}

	public static void main(String[] args) {
		SpringApplication.run(MigrosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<List<Store>> typeReference = new TypeReference<List<Store>>() {};
		InputStream inputStream = TypeReference.class.getResourceAsStream("/stores.json");
		try {
			List<Store> migrosStores = mapper.readValue(inputStream, typeReference);
			storeService.saveAll(migrosStores);
			logger.info("SUCCESS ----> Migros Stores Saved ");
		} catch (IOException e){
			logger.error(e.getMessage());
		}
	}
}
