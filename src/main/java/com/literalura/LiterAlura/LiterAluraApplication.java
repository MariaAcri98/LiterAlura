package com.literalura.LiterAlura;

import com.literalura.LiterAlura.service.MenuService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

	private final MenuService menuService;

	public LiterAluraApplication(MenuService menuService) {
		this.menuService = menuService;
	}

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		menuService.mostarMenu();
	}
}
