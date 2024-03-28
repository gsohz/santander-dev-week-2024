package com.gsohz.sdw24;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import com.gsohz.sdw24.application.AskChampionUseCase;
import com.gsohz.sdw24.application.ListChampionsUseCase;
import com.gsohz.sdw24.domain.ports.ChampionsRepository;
import com.gsohz.sdw24.domain.ports.GenerativeAiService;

@EnableFeignClients
@SpringBootApplication
public class SantanderDevWeek2024Application {

	public static void main(String[] args) {
		SpringApplication.run(SantanderDevWeek2024Application.class, args);
	}

    @Bean
    ListChampionsUseCase provideListChampionsUseCase(ChampionsRepository repository) {
		return new ListChampionsUseCase(repository);
	}

    @Bean
    AskChampionUseCase provideAskChampionUseCase(ChampionsRepository repository, GenerativeAiService genAiService) {
		return new AskChampionUseCase(repository, genAiService);
	}
}
